package com.greenblat.etuep.controller;

import com.greenblat.etuep.dto.DocumentResponse;
import com.greenblat.etuep.model.Document;
import com.greenblat.etuep.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/api/v1/documents")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentService documentService;

    @PostMapping("/upload")
    public DocumentResponse uploadDocument(@RequestParam("file") MultipartFile file,
                                           @RequestParam("filename") String filename,
                                           @AuthenticationPrincipal UserDetails userDetails) {
        return documentService.saveDocument(file, filename, userDetails);
    }

    @PutMapping("/update")
    public DocumentResponse updateDocument(@RequestParam("file") MultipartFile file,
                                           @RequestParam("filename") String filename,
                                           @AuthenticationPrincipal UserDetails userDetails) {
        return documentService.updateDocument(file, filename, userDetails);
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<ByteArrayResource> downloadDocument(@PathVariable Long id) {
        Document document = documentService.getDocument(id);
        ByteArrayResource resource = new ByteArrayResource(document.getDocumentText());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentDisposition(
                ContentDisposition.builder("attachment")
                        .filename(document.getDocumentName(), StandardCharsets.UTF_8)
                        .build()
        );

        return ResponseEntity
                .status(HttpStatus.OK)
                .headers(headers)
                .body(resource);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteDocument(@PathVariable("id") Long id) {
        documentService.deleteDocument(id);
        return ResponseEntity.ok().build();
    }


}
