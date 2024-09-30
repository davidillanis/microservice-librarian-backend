package org.microservice.librarian.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.microservice.librarian.util.dto.BookIssueDTO;

@FeignClient(name = "microservice-issue", url = "http://localhost:8085/issue/api/v1/book-issue")
public interface BookIssueClient {

    @PostMapping("/create")
    Boolean createBookIssue(@RequestBody BookIssueDTO bookIssueDTO) ;
}