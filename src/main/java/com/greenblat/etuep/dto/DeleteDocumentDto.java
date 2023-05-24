package com.greenblat.etuep.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

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

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @NotEmpty
    @PastOrPresent
    private LocalDate downloadDate;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @PastOrPresent
    private LocalDate updateDate;
}
