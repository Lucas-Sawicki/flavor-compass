package org.example.util;

import org.example.api.dto.rest.RestRegistrationDTO;

public class RestFixtures {

    public static RestRegistrationDTO someRegistrationDTO(){
        return RestRegistrationDTO.builder()
                .email("some22@email.com")
                .password("password")
                .build();
    }

}
