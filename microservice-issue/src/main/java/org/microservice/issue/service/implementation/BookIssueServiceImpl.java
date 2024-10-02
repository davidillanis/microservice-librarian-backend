package org.microservice.issue.service.implementation;

import org.microservice.issue.model.entity.BookEntity;
import org.microservice.issue.model.entity.IssueEntity;
import org.microservice.issue.model.repository.BookRepository;
import org.microservice.issue.model.repository.IssueRepository;
import org.microservice.issue.service.BookIssueService;
import org.microservice.issue.util.dto.BookIssueDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class BookIssueServiceImpl implements BookIssueService {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private IssueRepository issueRepository;

    @Override
    public Boolean createEntity(BookIssueDTO obj) {
        if(obj!=null) {
            List<IssueEntity> issues = obj.getListIssue().stream()
                    .map(t -> new IssueEntity(0, t))
                    .collect(Collectors.toList());

            bookRepository.save(new BookEntity(0, obj.getIsbnLibr(), issues));
            return true;
        }
        return false;
    }

    @Override
    public Map<String, List<String>> searchBookByIndexByStream(String issue) {
        return bookRepository.findAll().stream()
                .map(book -> {
                    List<String> matchingIssues = book.getIssueEntities().stream()
                            .map(issueEntity -> issueEntity.getNombTema())
                            .filter(nameIssue -> nameIssue.toLowerCase().contains(issue.toLowerCase()))
                            .collect(Collectors.toList());

                    return matchingIssues.isEmpty()? null:new AbstractMap.SimpleEntry<>(book.getIsbnLibr(), matchingIssues);
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue
                ));

    }
}