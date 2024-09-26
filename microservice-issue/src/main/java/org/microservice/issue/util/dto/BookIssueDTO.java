package org.microservice.issue.util.dto;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@ToString
public class BookIssueDTO {
    private Integer bookId;
    private String isbnLibr;
    private List<String> listIssue;
}