package org.microservice.gateway.utils.dto;

import lombok.*;
import org.microservice.gateway.utils.other.ERole;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoleDTO{
    private Integer id;
    private ERole role;
}
