package com.greenblat.etuep.mapper;

import com.greenblat.etuep.dto.DocumentInfoResponse;
import com.greenblat.etuep.dto.DocumentResponse;
import com.greenblat.etuep.model.Document;
import com.greenblat.etuep.model.IndexDocument;
import com.greenblat.etuep.model.User;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

@Component
public class DocumentMapper {

    public Document mapToDocument(MultipartFile file, String filename, User author) throws IOException {
        return Document.builder()
                .documentName(filename)
                .author(author)
                .downloadDate(LocalDate.now())
                .updateDate(LocalDate.now())
                .documentText(file.getBytes())
                .build();
    }

    public IndexDocument mapDocumentToIndexDocument(Document document) {
        return IndexDocument.builder()
                .id(document.getId())
                .text(getDocumentText(document))
                .build();
    }

    public DocumentResponse mapToDto(Document document) {
        return DocumentResponse.builder()
                .id(document.getId())
                .author(document.getAuthor().getUsername())
                .documentName(document.getDocumentName())
                .updatedDate(document.getUpdateDate())
                .build();
    }

    public DocumentResponse mapToDto(String username, Document document) {
        return DocumentResponse.builder()
                .id(document.getId())
                .author(username)
                .documentName(document.getDocumentName())
                .updatedDate(document.getUpdateDate())
                .build();
    }

    public DocumentInfoResponse mapToInfoDto(Document document) {
        return DocumentInfoResponse.builder()
                .documentName(document.getDocumentName())
                .author(document.getAuthor().getUsername())
                .updateDate(document.getDownloadDate())
                .documentText(getDocumentText(document))
                .build();
    }

    private String getDocumentText(Document document) {
        return new String(document.getDocumentText(), StandardCharsets.UTF_8);
    }

}
