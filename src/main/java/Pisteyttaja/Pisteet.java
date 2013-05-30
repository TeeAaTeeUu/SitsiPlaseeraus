package Pisteyttaja;

import omatTietorakenteet.ArrayList;
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
    private Laskin laskin;
    private double pariPisteet;
    private double sukupuoliPisteet;
    private double yhteysPisteet;

    public Pisteet(Sitsit sitsit) {
        this.sitsit = sitsit;
        this.paikat = new ArrayList<PaikanPisteet>();
        this.laskin = new Laskin();

        this.alustaPaikat();

        this.onYhteyksia = false;
    }

    public double palautaPisteet() {
        this.pisteet = 0.0;
        this.avec = 0;
        this.puoliso = 0;

        pariPisteet = 0.0;
        sukupuoliPisteet = 0.0;
        yhteysPisteet = 0.0;

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

            pariPisteet += sitsaajanPisteet.getPariPisteet();
            sukupuoliPisteet += sitsaajanPisteet.getSukupuoliPisteet();
            yhteysPisteet += sitsaajanPisteet.getYhteysPisteet();
        }
        return pisteet;
    }

    public double getPariPisteet() {
        return pariPisteet;
    }

    public double getSukupuoliPisteet() {
        return sukupuoliPisteet;
    }

    public double getYhteysPisteet() {
        return yhteysPisteet;
    }

    protected boolean onkoYhteyksia() {
        return onYhteyksia;
    }

    private void alustaPaikat() {
        this.paikkaLista = this.sitsit.getPaikat();

        for (Paikka paikka : this.paikkaLista) {
            PaikanPisteet paikanPisteet = new PaikanPisteet(paikka, this.laskin);
            this.paikat.add(paikanPisteet);
        }
    }

    public void alustaSitsaajat() {
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
