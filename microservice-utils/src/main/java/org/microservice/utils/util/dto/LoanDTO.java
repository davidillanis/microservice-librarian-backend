package org.microservice.utils.util.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoanDTO {
    private Integer idLoan;
    private Integer idStudent;
    private String copyBookCode;
}
