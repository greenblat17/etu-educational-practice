package com.greenblat.etuep.service;

import com.greenblat.etuep.dto.DocumentResponse;
import com.greenblat.etuep.exception.ResourceNotFoundException;
import com.greenblat.etuep.mapper.DocumentMapper;
import com.greenblat.etuep.model.Document;
import com.greenblat.etuep.model.IndexDocument;
import com.greenblat.etuep.model.User;
import com.greenblat.etuep.repository.DocumentRepository;
import com.greenblat.etuep.repository.IndexDocumentRepository;
import com.greenblat.etuep.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DocumentService {

    private final DocumentRepository documentRepository;
    private final IndexDocumentRepository indexDocumentRepository;
    private final UserRepository userRepository;
    private final DocumentMapper documentMapper;

    @SneakyThrows
    @Transactional
    public DocumentResponse saveDocument(MultipartFile file, String filename, UserDetails userDetails) {

        User user = findUser(userDetails.getUsername());


        Document mapToDocument = documentMapper.mapToDocument(file, filename, user);
        Document document = documentRepository.save(mapToDocument);

        checkIfEmptyCreateList(user.getDocuments()).add(document);

        IndexDocument indexDocument = documentMapper.mapDocumentToIndexDocument(document);
        indexDocumentRepository.save(indexDocument);

        return DocumentResponse.builder()
                .documentName(filename)
                .author(userDetails.getUsername())
                .updatedDate(document.getUpdateDate())
                .build();
    }

    @SneakyThrows
    @Transactional
    public DocumentResponse updateDocument(MultipartFile file, Long id, UserDetails userDetails) {
        Document updatedDocument = findDocument(id);

        byte[] updatedText = file.getBytes();
        updatedDocument.setDocumentText(updatedText);
        updatedDocument.setUpdateDate(LocalDate.now());

        IndexDocument updateIndexDocument = indexDocumentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("index for document with id [%d] not found", id))
                );

        updateIndexDocument.setText(new String(updatedText, StandardCharsets.UTF_8));
        indexDocumentRepository.save(updateIndexDocument);

        Document document = documentRepository.save(updatedDocument);
        return documentMapper.mapToDto(userDetails.getUsername(), document);
    }

    @Transactional
    public void deleteDocument(Long id) {
        documentRepository.deleteById(id);
        indexDocumentRepository.deleteById(id);
    }

    public Document getDocument(Long id) {
        return documentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("document with id [%d] not found", id))
                );
    }

    private User findUser(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("user with name [%s] not found", username))
                );
    }

    private Document findDocument(Long id) {
        return documentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("document with id [%d] not found", id))
                );
    }

    private List<Document> checkIfEmptyCreateList(List<Document> userDocuments) {
        return userDocuments == null ? new ArrayList<>() : userDocuments;
    }
}
