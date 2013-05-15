package sitsiplaseeraus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Poyta {

    private ArrayList<Sitsaaja> sitsaajat;
    private HashMap<Sitsaaja, Integer> paikat;

    public Poyta() {
        this.sitsaajat = new ArrayList<Sitsaaja>();
        this.paikat = new HashMap<Sitsaaja, Integer>();
    }

    public ArrayList<Sitsaaja> getSitsaajat() {
        ArrayList<Sitsaaja> sitsaajat = new ArrayList<Sitsaaja>();

        for (int i = 0; i < this.sitsaajienMaara(); i++) {
            for (Map.Entry paikat : this.getPaikat().entrySet()) {
                if((Integer) paikat.getValue() == i) {
                    sitsaajat.add((Sitsaaja) paikat.getKey());
                }
            }
        }
        return sitsaajat;
    }

    public HashMap<Sitsaaja, Integer> getPaikat() {
        return this.paikat;
    }
    
    public void setPaikat(HashMap<Sitsaaja, Integer> uusi) {
        this.paikat.clear();
        this.paikat.putAll(uusi);
    }

    public int getPaikka(Sitsaaja sitsaaja) {
        if (this.paikat.containsKey(sitsaaja)) {
            return this.paikat.get(sitsaaja);
        } else {
            return -1;
        }
    }

    public boolean setPaikka(Sitsaaja sitsaaja, int paikka) {
        Object old = this.paikat.put(sitsaaja, paikka);
        if (old == null) {
            this.paikat.put(sitsaaja, null);
            return false;
        } else {
            return true;
        }
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
