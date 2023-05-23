package com.greenblat.etuep.service;

import com.greenblat.etuep.dto.DocumentResponse;
import com.greenblat.etuep.exception.ResourceNotFoundException;
import com.greenblat.etuep.mapper.DocumentMapper;
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
    private final DocumentMapper documentMapper;

    public List<DocumentResponse> searchDocument(String searchLine) {
        List<IndexDocument> indexDocuments = indexDocumentRepository.findByTextContains(searchLine);

        return indexDocuments.stream()
                .map(this::findDocumentByIndex)
                .map(this::mapDocument)
                .collect(Collectors.toList());
    }

    private Document findDocumentByIndex(IndexDocument indexDocument) {
        Long id = indexDocument.getId();
        return documentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("index for document with id [%d] not found", id))
                );
    }

    private DocumentResponse mapDocument(Document document) {
        String documentName = document.getDocumentName();
        String username = document.getAuthor().getUsername();
        return documentMapper.mapToDto(username, documentName);
    }
}
