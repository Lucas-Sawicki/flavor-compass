package org.example.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StreetsDTO {
    private String sym;
    private String symUl;
    private String cecha;
    private String nazwa_1;
    private String nazwa_2;


}
