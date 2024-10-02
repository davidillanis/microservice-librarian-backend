package org.microservice.gateway.utils.other;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum ERole {
    STUDENT, LIBRARIAN;

    public static List<String> getListRole(){
        return Arrays.stream(ERole.values()).map(Enum::name).collect(Collectors.toList());
    }
}