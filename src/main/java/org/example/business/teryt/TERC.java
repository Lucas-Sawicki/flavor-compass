package org.example.business.teryt;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "teryt")
public class TERC {
    private String woj;
    private String pow;
    private String gmi;
    private String rodz;
    private String nazwa;
    private String nazwaDod;

    @XmlElement(name = "WOJ")
    public String getWoj() {
        return woj;
    }

    public void setWoj(String woj) {
        this.woj = woj;
    }

    @XmlElement(name = "POW")
    public String getPow() {
        return pow;
    }

    public void setPow(String pow) {
        this.pow = pow;
    }

    @XmlElement(name = "GMI")
    public String getGmi() {
        return gmi;
    }

    public void setGmi(String gmi) {
        this.gmi = gmi;
    }

    @XmlElement(name = "RODZ")
    public String getRodz() {
        return rodz;
    }

    public void setRodz(String rodz) {
        this.rodz = rodz;
    }

    @XmlElement(name = "NAZWA")
    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    @XmlElement(name = "NAZWA_DOD")
    public String getNazwaDod() {
        return nazwaDod;
    }

    public void setNazwaDod(String nazwaDod) {
        this.nazwaDod = nazwaDod;
    }
}
