package org.example.business.teryt;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Location {
    private String rm; // z pliku WM
    private String woj; // z pliku TERC
    private String pow; // z pliku TERC
    private String gmi; // z pliku TERC
    private String rodz; // z pliku TERC
    private String mz; // z pliku SIMC
    private String cecha; // z pliku LIC
    private String nazwa; // z pliku LIC
}
