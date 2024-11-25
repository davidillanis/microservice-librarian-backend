package org.microservice.librarian.util.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class StudentEntityDTO {
    private Integer idAlumn;
    private LocalDate fechRegiAlum;
    private String gradoYseccion;
    private String nivelEstudio;

    //private UserEntityDTO userEntity;
}
