package org.example.business.teryt;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "ULIC")
public class ULIC {
    private String woj;
    private String pow;
    private String gmi;
    private String rodzGmi;
    private String sym;
    private String symUl;
    private String cecha;
    private String nazwa1;
    private String nazwa2;


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

    @XmlElement(name = "RODZ_GMI")
    public String getRodzGmi() {
        return rodzGmi;
    }

    public void setRodzGmi(String rodzGmi) {
        this.rodzGmi = rodzGmi;
    }

    @XmlElement(name = "SYM")
    public String getSym() {
        return sym;
    }

    public void setSym(String sym) {
        this.sym = sym;
    }

    @XmlElement(name = "SYM_UL")
    public String getSymUl() {
        return symUl;
    }

    public void setSymUl(String symUl) {
        this.symUl = symUl;
    }

    @XmlElement(name = "CECHA")
    public String getCecha() {
        return cecha;
    }

    public void setCecha(String cecha) {
        this.cecha = cecha;
    }

    @XmlElement(name = "NAZWA_1")
    public String getNazwa1() {
        return nazwa1;
    }

    public void setNazwa1(String nazwa1) {
        this.nazwa1 = nazwa1;
    }

    @XmlElement(name = "NAZWA_2")
    public String getNazwa2() {
        return nazwa2;
    }

    public void setNazwa2(String nazwa2) {
        this.nazwa2 = nazwa2;
    }

}
