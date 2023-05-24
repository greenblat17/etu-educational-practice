package com.greenblat.etuep.handler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FieldErrorResponse {
    private Map<String, String> fieldErrors;

    public void add(String key, String message) {
        if (fieldErrors == null) {
            fieldErrors = new HashMap<>();
        }
        fieldErrors.put(key, message);
    }

    @Override
    public String toString() {
        return "fieldErrors=" + fieldErrors;
    }
}
