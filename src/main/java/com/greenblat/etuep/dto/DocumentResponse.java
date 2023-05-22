package com.greenblat.etuep.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DocumentResponse {
    private String documentName;
    private String author;
}
