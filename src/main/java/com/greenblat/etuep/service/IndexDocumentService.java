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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class IndexDocumentService {

    private final IndexDocumentRepository indexDocumentRepository;
    private final DocumentRepository documentRepository;
    private final UserRepository userRepository;
    private final DocumentMapper documentMapper;

    public List<DocumentResponse> searchDocument(String searchLine, UserDetails userDetails) {
        List<IndexDocument> indexDocuments = indexDocumentRepository.findByTextContains(searchLine);
        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("user not found"));

        return indexDocuments.stream()
                .map(this::findDocumentByIndex)
                .filter(document -> user.getDocuments().contains(document))
                .map(documentMapper::mapToDto)
                .collect(Collectors.toList());
    }

    private Document findDocumentByIndex(IndexDocument indexDocument) {
        Long id = indexDocument.getId();
        return documentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("index for document with id [%d] not found", id))
                );
    }

}
