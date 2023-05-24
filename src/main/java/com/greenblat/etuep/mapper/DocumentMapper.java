package com.greenblat.etuep.mapper;

import com.greenblat.etuep.dto.DeleteDocumentDto;
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

    public Document mapToDocument(DeleteDocumentDto dto, User author) {
        return Document.builder()
                .documentName(dto.getDocumentName())
                .author(author)
                .downloadDate(dto.getDownloadDate())
                .updateDate(dto.getUpdateDate())
                .build();
    }

    public IndexDocument mapDocumentToIndexDocument(Document document) {
        return IndexDocument.builder()
                .id(document.getId())
                .text(new String(document.getDocumentText(), StandardCharsets.UTF_8))
                .build();
    }

    public DocumentResponse mapToDto(String username, String filename) {
        return DocumentResponse.builder()
                .author(username)
                .documentName(filename)
                .build();
    }

}
