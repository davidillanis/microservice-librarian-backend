package org.microservice.issue.service.implementation;

import org.microservice.issue.service.BookIssueService;
import org.microservice.issue.util.dto.BookIssueDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddDataServiceImpl {
    @Autowired
    private BookIssueService bookIssueService;
    public void addData(){
        addBookIssue();
    }

    private void addBookIssue() {
        List<BookIssueDTO> bookIssueDTOList= List.of(
                BookIssueDTO.builder()
                        .isbnLibr("1234567890")
                        .listIssue(List.of(
                                "Capítulo 1: La Familia Bennet",
                                "Capítulo 2: La Llegada de Bingley",
                                "Capítulo 3: El Baile en Meryton",
                                "Capítulo 4: Elizabeth y Darcy",
                                "Capítulo 5: La Propuesta de Collins",
                                "Capítulo 6: La Visita a Rosings",
                                "Capítulo 7: El Cambio de Darcy"
                        )).build(),
                BookIssueDTO.builder()
                        .isbnLibr("1234567891")
                        .listIssue(List.of(
                                "Capítulo 1: Introducción a la Física de Plasmas",
                                "Capítulo 2: Plasmas en Equilibrio",
                                "Capítulo 3: Oscilaciones y Ondas en Plasmas",
                                "Capítulo 4: Confinamiento Magnético",
                                "Capítulo 5: Fusión Nuclear Controlada",
                                "Capítulo 6: Aplicaciones de la Física de Plasmas",
                                "Capítulo 7: LaInvestigación y Desarrollo"
                        )).build()
        );
        bookIssueDTOList.stream().forEach(t->bookIssueService.createEntity(t));
    }
}