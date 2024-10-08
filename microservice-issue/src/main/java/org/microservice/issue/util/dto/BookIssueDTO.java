package org.microservice.issue.util.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@ToString
public class BookIssueDTO {
    private Integer bookId;

    @NotBlank(message = "this Isbn not Blank")
    @Size(min = 10, max = 13, message = "The ISBN must have between 10 and 13 digits.")
    private String isbnLibr;

    private List<String> listIssue;
}