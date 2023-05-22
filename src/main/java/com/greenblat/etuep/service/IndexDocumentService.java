package com.greenblat.etuep.service;

import com.greenblat.etuep.exception.ResourceNotFoundException;
import com.greenblat.etuep.model.Document;
import com.greenblat.etuep.model.IndexDocument;
import com.greenblat.etuep.repository.DocumentRepository;
import com.greenblat.etuep.repository.IndexDocumentRepository;
import lombok.RequiredArgsConstructor;
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

    public List<Document> searchDocument(String searchLine) {
        List<IndexDocument> indexDocuments = indexDocumentRepository.findByTextContains(searchLine);

        return indexDocuments.stream()
                .map(indexDocument -> {
                    Long id = indexDocument.getId();
                    Document document = documentRepository.findById(id)
                            .orElseThrow(() -> new ResourceNotFoundException(
                                    String.format("index for document with id [%d] not found", id))
                            );
                    return document;
                }).collect(Collectors.toList());
    }
}
