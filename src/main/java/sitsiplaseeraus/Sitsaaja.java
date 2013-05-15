package sitsiplaseeraus;

import java.util.HashMap;
import java.util.Map;

public class Sitsaaja {

    private String nimi;
    private HashMap<Sitsaaja, Integer> yhteydet;

    public Sitsaaja(String nimi) {
        this.setNimi(nimi);
        this.yhteydet = new HashMap<Sitsaaja, Integer>();
    }

    public String getNimi() {
        return nimi;
    }

    public void setNimi(String nimi) {
        this.nimi = nimi;
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
    
    public int palautaYhteyksienMaara() {
        int yhteyksienMaara = 0;
        for (Map.Entry yhteys : this.yhteydet.entrySet()) {
            yhteyksienMaara++;
        }
        return yhteyksienMaara;
    }

    public Sitsaaja palautaTarkeinKanssaSitsaaja() {
        Sitsaaja tarkein = this;
        int tarkeimmallaPisteita = -5;
        
        for (Map.Entry<Sitsaaja, Integer> yhteys : this.palautaYhteydet().entrySet()) {
            if(yhteys.getValue() > tarkeimmallaPisteita) {
                tarkein = yhteys.getKey();
                tarkeimmallaPisteita = yhteys.getValue();
            }
        }
        return tarkein;
    }
}