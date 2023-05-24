package com.greenblat.etuep.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteDocumentDto {

    @NotEmpty
    private String documentName;

    @NotEmpty
    private String author;

    @NotEmpty
    @PastOrPresent
    private LocalDate downloadDate;

    @PastOrPresent
    private LocalDate updateDate;
}
