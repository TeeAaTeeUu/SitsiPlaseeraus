package sitsiplaseeraus;

import java.util.ArrayList;
import java.util.HashMap;

public class Poyta {

    private ArrayList<Sitsaaja> sitsaajat;
    private HashMap<Sitsaaja, Integer> paikat;

    public Poyta() {
        this.sitsaajat = new ArrayList<Sitsaaja>();
        this.paikat = new HashMap<Sitsaaja, Integer>();
    }

    public ArrayList<Sitsaaja> getSitsaajat() {
        return this.sitsaajat;
    }
    
    public HashMap<Sitsaaja, Integer> getPaikat() {
        return this.paikat;
    }

    public int sitsaajienMaara() {
        return this.sitsaajat.size();
    }
    
    public void addSitsaaja(String nimi) {
        addSitsaaja(nimi, this.sitsaajienMaara());
    }

    public void addSitsaaja(String nimi, int paikka) {
        Sitsaaja sitsaaja = new Sitsaaja(nimi);
        sitsaajat.add(sitsaaja);
        this.paikat.put(sitsaaja, paikka);
    }

    public Sitsaaja getSitsaaja(int monesko) {
        if (monesko > this.kuinkaMontaSitsaajaa()) {
            return null;
        } else if (monesko < 0) {
            return null;
        }
        return sitsaajat.get(monesko);
    }

    public Sitsaaja getSitsaaja(String nimi) {
        for (Sitsaaja sitsaaja : sitsaajat) {
            if (sitsaaja.getNimi().equals(nimi)) {
                return sitsaaja;
            }
        }
        return null;
    }

    public int kuinkaMontaSitsaajaa() {
        return this.sitsaajat.size();
    }

    public boolean deleteSitsaaja(String nimi) {
        Sitsaaja poistettava = this.getSitsaaja(nimi);
        if (poistettava == null) {
            return false;
        }
        return deleteSitsaajaLoop(poistettava);
    }

    public HashMap palautaYhteydet() {
        HashMap<Sitsaaja, HashMap> yhteydet = new HashMap<Sitsaaja, HashMap>();
        for (Sitsaaja sitsaaja : this.sitsaajat) {
            yhteydet.put(sitsaaja, sitsaaja.palautaYhteydet());
        }
        return yhteydet;
    }

    public int yhteyksienMaara() {
        int yhteyksienMaara = 0;
        for (Sitsaaja sitsaaja : sitsaajat) {
            yhteyksienMaara += sitsaaja.palautaYhteyksienMaara();
        }
        return yhteyksienMaara;
    }

    private boolean deleteSitsaajaLoop(Sitsaaja poistettava) {
        boolean loytyi = false;

        for (int i = 0; i < this.sitsaajat.size(); i++) {
            this.sitsaajat.get(i).deleteYhteys(poistettava);

            if (this.sitsaajat.get(i).equals(poistettava)) {
                this.sitsaajat.remove(i);
                loytyi = true;
            }
        }
        return loytyi;
    }
}
