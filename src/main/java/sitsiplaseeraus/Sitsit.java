package sitsiplaseeraus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import sitsiplaseeraus.random.Random;

public class Sitsit {

    private int poytienMaara;
    private final ArrayList<Sitsaaja> sitsaajat;
    private final HashMap<Sitsaaja, Integer> sitsaajienPoydat;
    private final HashMap<Sitsaaja, Integer> sitsaajienPaikat;

    public Sitsit(int kuinkaMontaPoytaa) {
        this.setPoytienMaara(kuinkaMontaPoytaa);
        
        this.sitsaajat = new ArrayList<Sitsaaja>();
        
        this.sitsaajienPoydat = new HashMap<Sitsaaja, Integer>();
        this.sitsaajienPaikat = new HashMap<Sitsaaja, Integer>();
    }

    private void setPoytienMaara(int kuinkaMontaPoytaa) {
        if (kuinkaMontaPoytaa <= 0) {
            this.poytienMaara = 1;
        } else {
            this.poytienMaara = kuinkaMontaPoytaa;
        }
    }

    public int sitsaajienMaara() {
        return this.sitsaajat.size();
    }

    public void addSitsaaja(String nimi) {
        addSitsaaja(nimi, Random.luo(this.poytienMaara() - 1));
    }

    public void addSitsaaja(String nimi, int mikaPoyta) {
        Sitsaaja sitsaaja = new Sitsaaja(nimi, mikaPoyta, this.sitsaajienMaaraPoydassa(mikaPoyta));
        this.sitsaajat.add(sitsaaja);
    }

    public boolean deleteSitsaaja(Sitsaaja poistettava) {
        if (this.sitsaajat.contains(poistettava)) {
            return this.deleteSitsaajaLoop(poistettava);
        } else {
            return false;
        }
    }

    public boolean deleteSitsaaja(String nimi) {
        Sitsaaja poistettava = this.getSitsaaja(nimi);
        if (poistettava == null) {
            return false;
        }
        return this.deleteSitsaajaLoop(poistettava);
    }

    private boolean deleteSitsaajaLoop(Sitsaaja poistettava) {
        boolean loytyi = false;

        for (int i = 0; i < this.sitsaajienMaara(); i++) {
            this.sitsaajat.get(i).deleteYhteys(poistettava);

            if (this.sitsaajat.get(i).equals(poistettava)) {
                this.sitsaajat.remove(i);
                loytyi = true;
            }
        }
        return loytyi;
    }

    public Sitsaaja getSitsaaja(int monesko) {
        return this.sitsaajat.get(monesko);
    }

    public Sitsaaja getSitsaaja(String nimi) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public ArrayList<Sitsaaja> getSitsaajat() {
        return this.sitsaajat;
    }

    public int yhteyksienMaara() {
        int yhteyksienMaara = 0;
        
        for (Sitsaaja sitsaaja : sitsaajat) {
            yhteyksienMaara += sitsaaja.yhteyksienMaara();
        }
        return yhteyksienMaara;
    }

    public int poytienMaara() {
        return this.poytienMaara;
    }
    
    public ArrayList<Sitsaaja> palautaPoydanSitsaajat(int moneskoPoyta) {
        HashMap<Integer, Sitsaaja> paikat = new HashMap<Integer, Sitsaaja>();
        ArrayList<Sitsaaja> poydanSitsaajat = new ArrayList<Sitsaaja>();
        
        for (Sitsaaja sitsaaja : sitsaajat) {
            if(sitsaaja.getPoyta() == moneskoPoyta)
                paikat.put(sitsaaja.getPaikka(), sitsaaja);
        }
        for (int i = 0; i < paikat.size(); i++) {
            poydanSitsaajat.add(paikat.get(i));
        }
        
        return poydanSitsaajat;
    }

    private int sitsaajienMaaraPoydassa(int mikaPoyta) {
        int maara = 0;

        for (Sitsaaja sitsaaja : sitsaajat) {
            if (sitsaaja.getPoyta() == mikaPoyta) {
                maara++;
            }
        }
        return maara;
    }

    public HashMap<Sitsaaja, HashMap> palautaYhteydet() {
        HashMap<Sitsaaja, HashMap> kaikkiYhteydet = new HashMap<Sitsaaja, HashMap>();
        
        for (Sitsaaja sitsaaja : sitsaajat) {
            kaikkiYhteydet.put(sitsaaja, sitsaaja.palautaYhteydet());
        }
        return kaikkiYhteydet;
    }

    public HashMap<Sitsaaja, Integer> palautaPoydat() {
        this.sitsaajienPoydat.clear();
        
        for (Sitsaaja sitsaaja : sitsaajat) {
            this.sitsaajienPoydat.put(sitsaaja, sitsaaja.getPoyta());
        }
        return this.sitsaajienPoydat;
    }

    public HashMap<Sitsaaja, Integer> palautaPaikat() {
        this.sitsaajienPaikat.clear();
        
        for (Sitsaaja sitsaaja : sitsaajat) {
            this.sitsaajienPaikat.put(sitsaaja, sitsaaja.getPaikka());
        }
        return this.sitsaajienPaikat;
    }
}
