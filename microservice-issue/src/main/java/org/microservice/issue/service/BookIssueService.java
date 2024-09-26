package org.microservice.issue.service;

import org.microservice.issue.util.dto.BookIssueDTO;

import java.util.List;
import java.util.Map;

public interface BookIssueService {
    Boolean createEntity(BookIssueDTO obj);
    Map<String, List<String>> searchBookByIndexByStream(String issue);
}