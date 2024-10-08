package org.microservice.users.utils.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import org.microservice.users.utils.other.EMessage;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.stream.Collectors;

@Builder
@JsonPropertyOrder({"isSuccess", "message", "errors"})
public record ResponseStatusDTO(Boolean isSuccess, EMessage message, List<String> errors) {
    public static ResponseStatusDTO responseStatusValid(BindingResult bindingResult, EMessage message) {
        List<String> errors = bindingResult.getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.toList());
        return new ResponseStatusDTO(false, message, errors);
    }
}
