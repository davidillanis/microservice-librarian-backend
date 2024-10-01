package org.microservice.utils.util.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmailLoanDTO {
    private String[] toUser;
    private String subject;
    private Integer idLoan;
    private Integer idStudent;
    private String copyBookCode;
}
