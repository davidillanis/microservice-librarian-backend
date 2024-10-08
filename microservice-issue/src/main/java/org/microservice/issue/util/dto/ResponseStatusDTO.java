package org.microservice.issue.util.dto;


import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import org.microservice.issue.util.other.EMessage;

import java.util.List;

@Builder
@JsonPropertyOrder({"isSuccess", "message", "errors"})
public record ResponseStatusDTO(Boolean isSuccess, EMessage message, List<String> errors) {
}
