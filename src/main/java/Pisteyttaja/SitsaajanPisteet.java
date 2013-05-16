package Pisteyttaja;

import java.util.HashMap;
import java.util.Map;
import sitsiplaseeraus.Sitsaaja;

public class SitsaajanPisteet {

    private Sitsaaja sitsaaja;
    private int paikka;
    private int poyta;
    private HashMap<Sitsaaja, Integer> yhteydet;
    private int kohdeSitsaajanPaikka;
    private Sitsaaja kohdeSitsaaja;
    private int kohdeSitsaajanPoyta;
    private int kohteidenErotus;
    private boolean onYhteyksia;
    private int arvo;

    protected SitsaajanPisteet(Sitsaaja sitsaaja) {
        this.sitsaaja = sitsaaja;
        this.onYhteyksia = false;
    }

    protected double palautaPisteet() {
        alustaMuuttujat();

        double pisteet = 0.0;
        for (Map.Entry yhteys : yhteydet.entrySet()) {
            this.onYhteyksia = true;

            this.kohdeSitsaaja = (Sitsaaja) yhteys.getKey();
            this.kohdeSitsaajanPaikka = this.kohdeSitsaaja.getPaikka();
            this.kohdeSitsaajanPoyta = this.kohdeSitsaaja.getPoyta();

            if (this.poyta == this.kohdeSitsaajanPoyta) {
                this.arvo = (Integer) yhteys.getValue();

                this.kohteidenErotus = this.paikka - this.kohdeSitsaajanPaikka;

                pisteet += this.palautaPisteet(arvo, kohteidenErotus, this.paikka);
            }
        }
        return pisteet;
    }

    protected boolean onkoYhteyksia() {
        return this.onYhteyksia;
    }

    private void alustaMuuttujat() {
        this.yhteydet = this.sitsaaja.palautaYhteydet();
        this.paikka = this.sitsaaja.getPaikka();
        this.poyta = this.sitsaaja.getPoyta();
    }

    private static double palautaPisteet(int arvo, int kohteidenErotus, int paikka) {
        if (paikkaOnVasemmalla(paikka) == true) {
            return pisteetKunPaikkaOnVasemmalla(kohteidenErotus, arvo);
        } else {
            return pisteetKunPaikkaOnOikealla(kohteidenErotus, arvo);
        }

    }

    private static boolean paikkaOnVasemmalla(int paikka) {
        return paikka % 2 == 0;
    }

    private static boolean kohdeOnSamallaPuolella(int kohteidenErotus) {
        return Math.abs(kohteidenErotus) % 2 == 0;
    }

    private static double pisteetKunPaikkaOnVasemmalla(int kohteidenErotus, int arvo) {
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

    private static double pisteetKunPaikkaOnOikealla(int kohteidenErotus, int arvo) {
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
