package com.greenblat.etuep.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class DocumentResponse {
    private Long id;
    private String documentName;
    private String author;
    private LocalDate updatedDate;
}
