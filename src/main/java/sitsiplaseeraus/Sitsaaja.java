package sitsiplaseeraus;

import omatTietorakenteet.HashMap;
import java.util.Map;
import omatTietorakenteet.Vektori;

public class Sitsaaja {

    private String nimi;
    private int mies;
    private HashMap<Sitsaaja, Integer> yhteydet;
    private Sitsaaja avec;
    private boolean avecIsSet;
    private boolean puolisoIsSet;
    private Sitsaaja puoliso;
    private Paikka paikka;

    public Sitsaaja(String nimi) {
        this.nimi = nimi;

        this.yhteydet = new HashMap<Sitsaaja, Integer>();

        this.avecIsSet = false;
        this.puolisoIsSet = false;
        this.mies = -1;
    }

    public Sitsaaja(String nimi, boolean mies) {
        this(nimi);
        this.setMies(mies);
    }

    public void setNimi(String nimi) {
        this.nimi = nimi;
    }

    public String getNimi() {
        return nimi;
    }

    public boolean isMies() {
        if (this.mies == 1) {
            return true;
        } else if (this.mies == 0) {
            return false;
        } else {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates. 
        }
    }

    public void setMies(boolean mies) {
        if (mies == true) {
            this.mies = 1;
        } else {
            this.mies = 0;
        }
    }

    public void setAvec(Sitsaaja avec) {
        this.avec = avec;
        this.setAvecIsSet();
    }

    public Sitsaaja getAvec() {
        return avec;
    }

    public Sitsaaja getPuoliso() {
        return puoliso;
    }

    public void setPuoliso(Sitsaaja puoliso) {
        this.puoliso = puoliso;
        this.setPuolisoIsSet();
    }

    public Paikka getPaikka() {
        return paikka;
    }

    public void setPaikka(Paikka paikka) {
        this.paikka = paikka;
    }

    public boolean avecIsSet() {
        return avecIsSet;
    }

    private void setAvecIsSet() {
        this.avecIsSet = true;
    }

    public boolean puolisoIsSet() {
        return puolisoIsSet;
    }

    private void setPuolisoIsSet() {
        this.puolisoIsSet = true;
    }

    public boolean setYhteys(Sitsaaja sitsaaja, int arvo) {
        if (this.equals(sitsaaja) || arvo > 5 || arvo < -5 || this.yhteydet.containsKey(sitsaaja)) {
            return false;
        }
        this.yhteydet.put(sitsaaja, arvo);
        return true;
    }

    public boolean deleteYhteys(Sitsaaja sitsaaja) {
        if (this.yhteydet.containsKey(sitsaaja)) {
            this.yhteydet.remove(sitsaaja);
            return true;
        } else {
            return false;
        }
    }

    public HashMap<Sitsaaja, Integer> palautaYhteydet() {
        return this.yhteydet;
    }

    public int yhteyksienMaara() {
        int yhteyksienMaara = 0;
        for (Vektori<Sitsaaja, Integer> yhteys : this.yhteydet) {
            yhteyksienMaara++;
        }
        return yhteyksienMaara;
    }
}
