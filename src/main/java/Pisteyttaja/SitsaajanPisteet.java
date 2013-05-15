package Pisteyttaja;

import java.util.HashMap;
import java.util.Map;
import sitsiplaseeraus.Poyta;
import sitsiplaseeraus.Sitsaaja;

public class SitsaajanPisteet {

    private Sitsaaja sitsaaja;
    private int paikka;
    private HashMap<Sitsaaja, Integer> yhteydet;
    private Poyta table;
    private HashMap<Sitsaaja, Integer> paikat;
    private int kohdeSitsaajanPaikka;
    private int kohteidenErotus;
    private boolean onYhteyksia;

    protected SitsaajanPisteet(Sitsaaja sitsaaja, Poyta table) {
        this.sitsaaja = sitsaaja;
        this.table = table;
        this.onYhteyksia = false;
    }

    protected double palautaPisteet() {
        alustaMuuttujat();

        double pisteet = 0.0;
        double temp = 0.0;
        for (Map.Entry yhteys : yhteydet.entrySet()) {
            this.kohdeSitsaajanPaikka = paikat.get((Sitsaaja) yhteys.getKey());

            this.onYhteyksia = true;

            int arvo = (Integer) yhteys.getValue();
            this.kohteidenErotus = this.paikka - this.kohdeSitsaajanPaikka;

            temp = this.palautaPisteet(arvo, kohteidenErotus);
//            System.out.println("paikka: " + this.paikka + " arvo: " + arvo + " kohteiden erotus: " + kohteidenErotus + " kohteen paikka: " + this.kohdeSitsaajanPaikka + " pisteet: " + temp);

            pisteet += temp;
        }
        return pisteet;
    }

    protected boolean onkoYhteyksia() {
        return this.onYhteyksia;
    }

    private void alustaMuuttujat() {
        this.yhteydet = this.sitsaaja.palautaYhteydet();
        this.paikat = this.table.getPaikat();
        this.paikka = this.paikat.get(this.sitsaaja);
    }

    private double palautaPisteet(int arvo, int kohteidenErotus) {
        if (paikkaOnVasemmalla() == true) {
            return pisteetKunPaikkaOnVasemmalla(kohteidenErotus, arvo);
        } else {
            return pisteetKunPaikkaOnOikealla(kohteidenErotus, arvo);
        }

    }

    private boolean paikkaOnVasemmalla() {
        return this.paikka % 2 == 0;
    }

    private boolean kohdeOnSamallaPuolella(int kohteidenErotus) {
        return Math.abs(kohteidenErotus) % 2 == 0;
    }

    private double pisteetKunPaikkaOnVasemmalla(int kohteidenErotus, int arvo) {
        if (kohdeOnSamallaPuolella(kohteidenErotus) == true) {
            return 1.0 * arvo / Math.abs(kohteidenErotus / 2);
        } else {
            if (kohteidenErotus > 0) {
                return 1.0 * arvo / Math.hypot(1, 1.0 * (kohteidenErotus + 1) / 2);
            } else {
                return 1.0 * arvo / Math.hypot(1, 1.0 * (Math.abs(kohteidenErotus) - 1) / 2);
            }
        }
    }

    private double pisteetKunPaikkaOnOikealla(int kohteidenErotus, int arvo) {
        if (kohdeOnSamallaPuolella(kohteidenErotus) == true) {
            return 1.0 * arvo / Math.abs(kohteidenErotus / 2);
        } else {
            if (kohteidenErotus > 0) {
                return 1.0 * arvo / Math.hypot(1, 1.0 * (kohteidenErotus - 1) / 2);
            } else {
                return 1.0 * arvo / Math.hypot(1, 1.0 * Math.abs((kohteidenErotus) / 2 - 1));
            }
        }
    }
}
