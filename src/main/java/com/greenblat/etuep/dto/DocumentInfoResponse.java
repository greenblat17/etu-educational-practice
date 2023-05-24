package com.greenblat.etuep.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DocumentInfoResponse {
    private String documentName;
    private String author;
    private LocalDate updateDate;
    private String documentText;
}
