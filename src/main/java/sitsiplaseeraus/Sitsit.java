package sitsiplaseeraus;

import Pisteyttaja.PaikanPisteet;
import java.util.ArrayList;
import java.util.HashMap;
import sitsiplaseeraus.random.Random;

public class Sitsit {

    private int poytienMaara;
    private final ArrayList<Paikka> paikat;
    private final HashMap<Paikka, Sitsaaja> sitsaajienPaikat;

    public Sitsit(int kuinkaMontaPoytaa) {
        this.setPoytienMaara(kuinkaMontaPoytaa);

        this.paikat = new ArrayList<Paikka>();

        this.sitsaajienPaikat = new HashMap<Paikka, Sitsaaja>();
    }

    public ArrayList<Paikka> getPaikat() {
        return paikat;
    }

    private void setPoytienMaara(int kuinkaMontaPoytaa) {
        if (kuinkaMontaPoytaa <= 0) {
            this.poytienMaara = 1;
        } else {
            this.poytienMaara = kuinkaMontaPoytaa;
        }
    }

    public int sitsaajienMaara() {
        return this.paikat.size();
    }

    public Paikka addPaikka() {
        return addPaikka(Random.luo(this.poytienMaara() - 1));
    }

    public Paikka addPaikka(int mikaPoyta) {
        Paikka paikka = new Paikka(mikaPoyta, this.sitsaajienMaaraPoydassa(mikaPoyta));

        this.paikat.add(paikka);

        return paikka;
    }

    public boolean deleteSitsaaja(Sitsaaja poistettava) {
        int onnistuiko = 0;

        for (int i = 0; i < this.paikat.size(); i++) {
            if (this.paikat.get(i).getSitsaaja() != null) {
                if (this.paikat.get(i).getSitsaaja().equals(poistettava)) {
                    onnistuiko++;
                    this.paikat.get(i).setSitsaaja(null);
                } else {
                    this.paikat.get(i).getSitsaaja().deleteYhteys(poistettava);
                }
            }
        }
        if (onnistuiko != 1) {
            return false;
        } else {
            return true;
        }
    }

    public Paikka getPaikka(int monesko) {
        return this.paikat.get(monesko);
    }

    public ArrayList<Sitsaaja> getSitsaajat() {
        ArrayList<Sitsaaja> sitsaajat = new ArrayList<Sitsaaja>();

        for (Paikka paikka : this.paikat) {
            sitsaajat.add(paikka.getSitsaaja());
        }
        return sitsaajat;
    }

    public int yhteyksienMaara() {
        int yhteyksienMaara = 0;

        for (Paikka paikka : paikat) {
            if (paikka.getSitsaaja() != null) {
                yhteyksienMaara += paikka.getSitsaaja().yhteyksienMaara();
            }
        }
        return yhteyksienMaara;
    }

    public int poytienMaara() {
        return this.poytienMaara;
    }

    public ArrayList<Sitsaaja> palautaPoydanSitsaajat(int moneskoPoyta) {
        HashMap<Integer, Sitsaaja> poydanSitsaajat = new HashMap<Integer, Sitsaaja>();
        ArrayList<Sitsaaja> poydanSitsaajatJarjestyksessa = new ArrayList<Sitsaaja>();

        for (Paikka paikka : this.paikat) {
            if (paikka.getPoyta() == moneskoPoyta) {
                if (paikka.getSitsaaja() != null) {
                    poydanSitsaajat.put(paikka.getPaikka(), paikka.getSitsaaja());
                }
            }
        }
        for (int i = 0; i < poydanSitsaajat.size(); i++) {
            poydanSitsaajatJarjestyksessa.add(poydanSitsaajat.get(i));
        }

        return poydanSitsaajatJarjestyksessa;
    }

    protected int sitsaajienMaaraPoydassa(int mikaPoyta) {
        int maara = 0;

        for (Paikka sitsaaja : paikat) {
            if (sitsaaja.getPoyta() == mikaPoyta) {
                maara++;
            }
        }
        return maara;
    }

    public HashMap<Sitsaaja, HashMap> palautaYhteydet() {
        HashMap<Sitsaaja, HashMap> kaikkiYhteydet = new HashMap<Sitsaaja, HashMap>();

        for (Paikka paikka : paikat) {
            kaikkiYhteydet.put(paikka.getSitsaaja(), paikka.getSitsaaja().palautaYhteydet());
        }
        return kaikkiYhteydet;
    }

    public HashMap<Paikka, Sitsaaja> palautaPaikat() {
        this.sitsaajienPaikat.clear();

        for (Paikka paikka : paikat) {
            this.sitsaajienPaikat.put(paikka, paikka.getSitsaaja());
        }
        return this.sitsaajienPaikat;
    }

    public void lisaaPaikoilleTiedotAvecinJaPuolisonPaikoista() {
        for (Paikka paikka : paikat) {
            for (Paikka kohdePaikka : paikat) {
                if (paikka.getPoyta() == kohdePaikka.getPoyta()) {
                    if (paikka.getPaikka() % 2 == 0) {
                        if (paikka.getPaikka() - kohdePaikka.getPaikka() == -2) {
                            paikka.setNaisenAvecinPaikka(kohdePaikka);
                        } else if (paikka.getPaikka() - kohdePaikka.getPaikka() == 2) {
                            paikka.setMiehenAvecinPaikka(kohdePaikka);
                        }
                    } else {
                        if (paikka.getPaikka() - kohdePaikka.getPaikka() == 2) {
                            paikka.setNaisenAvecinPaikka(kohdePaikka);
                        } else if (paikka.getPaikka() - kohdePaikka.getPaikka() == -2) {
                            paikka.setMiehenAvecinPaikka(kohdePaikka);
                        }
                    }
                    if (Math.abs(paikka.getPaikka() - kohdePaikka.getPaikka()) == 1) {
                        if (PaikanPisteet.paikkaOnVasemmalla(paikka.getPaikka()) == true && (paikka.getPaikka() - kohdePaikka.getPaikka()) == -1) {
                            paikka.setPuolisonPaikka(kohdePaikka);
                        } else if (PaikanPisteet.paikkaOnVasemmalla(paikka.getPaikka()) == false && (paikka.getPaikka() - kohdePaikka.getPaikka()) == 1) {
                            paikka.setPuolisonPaikka(kohdePaikka);
                        }
                    }
                }
            }
        }
    }
}
