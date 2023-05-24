package com.greenblat.etuep.service;

import com.greenblat.etuep.dto.DeleteDocumentDto;
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
                .build();
    }

    @SneakyThrows
    @Transactional
    public Document updateDocument(MultipartFile file, String filename) {
        Document updatedDocument = findDocument(filename);

        byte[] updatedText = file.getBytes();
        updatedDocument.setDocumentText(updatedText);
        updatedDocument.setUpdateDate(LocalDate.now());

        Long id = updatedDocument.getId();
        IndexDocument updateIndexDocument = indexDocumentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("index for document with id [%d] not found", id))
                );

        updateIndexDocument.setText(new String(updatedText, StandardCharsets.UTF_8));
        indexDocumentRepository.save(updateIndexDocument);

        return documentRepository.save(updatedDocument);
    }

    @Transactional
    public void deleteDocument(DeleteDocumentDto documentDto, UserDetails userDetails) {
        User user = findUser(userDetails.getUsername());

        Document removedDocument = documentMapper.mapToDocument(documentDto, user);

        documentRepository.delete(removedDocument);

        IndexDocument indexDocument = documentMapper.mapDocumentToIndexDocument(removedDocument);
        indexDocumentRepository.delete(indexDocument);
    }

    public Document getDocument(String filename, String downloadTime) {
        LocalDate date = LocalDate.parse(downloadTime);

        return documentRepository.findDocumentByDocumentNameAndDownloadDate(filename, date)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("document with name [%s] and download date [%s] not found", filename, downloadTime))
                );
    }

    private User findUser(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("user with name [%s] not found", username))
                );
    }

    private Document findDocument(String filename) {
        return documentRepository.findDocumentByDocumentName(filename)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("document with name [%s] not found", filename))
                );
    }

    private List<Document> checkIfEmptyCreateList(List<Document> userDocuments) {
        return userDocuments == null ? new ArrayList<>() : userDocuments;
    }
}
