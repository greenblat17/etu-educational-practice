package com.greenblat.etuep.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteDocumentDto {

    private String documentName;

    private String author;

    private LocalDate downloadDate;

    private LocalDate updateDate;
}
