package org.microservice.librarian.util.http.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class BookIssueRequest {
    private String tituLibr;
    private boolean librHabi;

    @NotBlank(message = "not blank")
    @Size(min = 10, max = 13, message = "must have 10 to 13 digits")
    private String isbnLibr;
    private String descLibr;
    private LocalDate fechPublLibr;

    @NotBlank(message = "not blank")
    private String editLibr;

    @NotBlank(message = "not blank")
    private String autoLibr;

    @NotNull(message = "not null")
    private List<String> listIssue;
}