package org.example.business.teryt;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "SIMC")
public class SIMC {
    private String woj;
    private String pow;
    private String gmi;
    private String rodzGmi;
    private String rm;
    private String mz;
    private String nazwa;
    private String sym;
    private String sympod;


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

    @XmlElement(name = "RM")
    public String getRm() {
        return rm;
    }

    public void setRm(String rm) {
        this.rm = rm;
    }

    @XmlElement(name = "MZ")
    public String getMz() {
        return mz;
    }

    public void setMz(String mz) {
        this.mz = mz;
    }

    @XmlElement(name = "NAZWA")
    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    @XmlElement(name = "SYM")
    public String getSym() {
        return sym;
    }

    public void setSym(String sym) {
        this.sym = sym;
    }

    @XmlElement(name = "SYMPOD")
    public String getSympod() {
        return sympod;
    }

    public void setSympod(String sympod) {
        this.sympod = sympod;
    }

}
