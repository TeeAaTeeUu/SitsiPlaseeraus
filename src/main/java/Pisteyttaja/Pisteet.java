package Pisteyttaja;

import java.util.ArrayList;
import sitsiplaseeraus.Paikka;
import sitsiplaseeraus.Sitsit;

public class Pisteet {

    private Sitsit sitsit;
    private ArrayList<PaikanPisteet> paikat;
    private double pisteet;
    private boolean onYhteyksia;
    private ArrayList<Paikka> paikkaLista;
    private int avec;
    private int puoliso;

    public Pisteet(Sitsit sitsit) {
        this.sitsit = sitsit;
        this.paikat = new ArrayList<PaikanPisteet>();

        this.alustaPaikat();

        this.onYhteyksia = false;
    }

    public double palautaPisteet() {
        this.pisteet = 0.0;
        this.avec = 0;
        this.puoliso = 0;

        this.alustaSitsaajat();

        for (PaikanPisteet sitsaajanPisteet : paikat) {
            this.pisteet += sitsaajanPisteet.palautaPisteet();
            if (sitsaajanPisteet.isAvec()) {
                this.avec++;
            }
            if (sitsaajanPisteet.isPuoliso()) {
                this.puoliso++;
            }
            this.onYhteyksia = true;
        }
        return pisteet;
    }

    protected boolean onkoYhteyksia() {
        return onYhteyksia;
    }

    private void alustaPaikat() {
        this.paikkaLista = this.sitsit.getPaikat();

        for (Paikka paikka : this.paikkaLista) {
            PaikanPisteet paikanPisteet = new PaikanPisteet(paikka);
            this.paikat.add(paikanPisteet);
        }
    }

    private void alustaSitsaajat() {
        for (Paikka paikka : this.paikkaLista) {
            paikka.getSitsaaja().setPaikka(paikka);
        }
        
    }

    public int getAvecienMaara() {
        return avec;
    }

    public int getPuolisojenMaara() {
        return puoliso;
    }
}
