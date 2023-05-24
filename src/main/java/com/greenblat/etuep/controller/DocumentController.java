package com.greenblat.etuep.controller;

import com.greenblat.etuep.dto.DeleteDocumentDto;
import com.greenblat.etuep.dto.DocumentResponse;
import com.greenblat.etuep.exception.DeleteDocumentException;
import com.greenblat.etuep.handler.FieldErrorResponse;
import com.greenblat.etuep.model.Document;
import com.greenblat.etuep.service.DocumentService;
import com.greenblat.etuep.service.IndexDocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/documents")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentService documentService;
    private final IndexDocumentService indexDocumentService;

    @PostMapping("/upload")
    @ResponseBody
    public DocumentResponse uploadDocument(@RequestParam("file") MultipartFile file,
                                           @RequestParam("filename") String filename,
                                           @AuthenticationPrincipal UserDetails userDetails) {
        return documentService.saveDocument(file, filename, userDetails);
    }

    @PutMapping("/update")
    public Document updateDocument(@RequestParam("file") MultipartFile file,
                                   @RequestParam("filename") String filename) {
        return documentService.updateDocument(file, filename);
    }

    @GetMapping("/download")
    public Document downloadDocument(@RequestParam("filename") String filename,
                                     @RequestParam("download-date") String downloadDate) {
        return documentService.getDocument(filename, downloadDate);
    }

    @DeleteMapping("/delete")
    public void deleteDocument(@RequestBody @Validated DeleteDocumentDto deleteDocumentDto,
                               BindingResult bindingResult,
                               @AuthenticationPrincipal UserDetails userDetails) {
        validationDeletingDocument(bindingResult);
        documentService.deleteDocument(deleteDocumentDto, userDetails);
    }

    @GetMapping("/search")
    public List<DocumentResponse> searchDocument(@RequestParam("search-line") String searchLine) {
        return indexDocumentService.searchDocument(searchLine);
    }

    private void validationDeletingDocument(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            FieldErrorResponse error = new FieldErrorResponse();

            for (FieldError field : bindingResult.getFieldErrors()) {
                error.add(field.getField(), field.getDefaultMessage());
            }

            throw new DeleteDocumentException(error.toString());
        }

    }

}
