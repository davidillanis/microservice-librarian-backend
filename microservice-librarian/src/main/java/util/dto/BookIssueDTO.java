package util.dto;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class BookIssueDTO {
    private Integer bookId;
    private String isbnLibr;
    private List<String> listIssue;
}