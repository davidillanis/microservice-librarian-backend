package org.microservice.librarian.client;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import util.dto.BookIssueDTO;

@FeignClient(name = "microservice-issue", url = "localhost:8082/api/book-issue")
public interface BookIssueClient {

    @PostMapping("/create")
    Boolean createBookIssue(@RequestBody BookIssueDTO bookIssueDTO) ;
}