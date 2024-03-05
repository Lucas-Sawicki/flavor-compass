package org.example.business.teryt;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "SIMC")
public class WMRODZ {

    private String rm;
    private String nazwaRm;


    @XmlElement(name = "RM")
    public String getRm() {
        return rm;
    }

    public void setRm(String rm) {
        this.rm = rm;
    }

    @XmlElement(name = "NAZWA_RM")
    public String getNazwaRm() {
        return nazwaRm;
    }

    public void setNazwaRm(String nazwaRm) {
        this.nazwaRm = nazwaRm;
    }

}
