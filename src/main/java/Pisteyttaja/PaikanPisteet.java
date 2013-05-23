package Pisteyttaja;

import java.util.HashMap;
import java.util.Map;
import sitsiplaseeraus.Paikka;
import sitsiplaseeraus.Sitsaaja;

public class PaikanPisteet {

    private Paikka paikka;
    private HashMap<Sitsaaja, Integer> yhteydet;
    private Paikka kohdePaikka;
    private int kohteidenErotus;
    private boolean onYhteyksia;
    private int arvo;
    private Sitsaaja kohdeSitsaaja;
    private final Laskin laskin;
    private boolean avec;
    private boolean puoliso;

    protected PaikanPisteet(Paikka paikka) {
        this.paikka = paikka;
        this.onYhteyksia = false;
        this.laskin = new Laskin();
    }

    protected double palautaPisteet() {
        this.yhteydet = this.paikka.getSitsaaja().palautaYhteydet();

        double pisteet = 0.0;
        this.avec = false;
        this.puoliso = false;

        pisteet += this.tarkistaAvecJaPuoliso();
        pisteet += this.tarkistaYmparillaOlevienSukupuolet();

        for (Map.Entry yhteys : yhteydet.entrySet()) {
            this.onYhteyksia = true;

            this.kohdeSitsaaja = (Sitsaaja) yhteys.getKey();
            this.kohdePaikka = this.kohdeSitsaaja.getPaikka();

            if (this.paikka.getPoyta() == this.kohdePaikka.getPoyta()) {
                this.arvo = (Integer) yhteys.getValue();

                this.kohteidenErotus = this.paikka.getPaikka() - this.kohdePaikka.getPaikka();

                pisteet += this.palautaPisteet(arvo, kohteidenErotus, this.paikka.getPaikka());
            }
        }
        return pisteet;
    }

    protected boolean onkoYhteyksia() {
        return this.onYhteyksia;
    }

    private double palautaPisteet(int arvo, int kohteidenErotus, int paikka) {
        if (paikkaOnVasemmalla(paikka) == true) {
            return pisteetKunPaikkaOnVasemmalla(kohteidenErotus, arvo);
        } else {
            return pisteetKunPaikkaOnOikealla(kohteidenErotus, arvo);
        }

    }

    public static boolean paikkaOnVasemmalla(int paikka) {
        return paikka % 2 == 0;
    }

    private static boolean kohdeOnSamallaPuolella(int kohteidenErotus) {
        return Math.abs(kohteidenErotus) % 2 == 0;
    }

    private double pisteetKunPaikkaOnVasemmalla(int kohteidenErotus, int arvo) {
        if (kohdeOnSamallaPuolella(kohteidenErotus) == true) {
            return 1.0 * arvo / Math.abs(kohteidenErotus / 2);
        } else {
            if (kohteidenErotus > 0) {
                return 1.0 * arvo / this.laskin.hypot(1, 1.0 * (kohteidenErotus + 1) / 2);
            } else {
                return 1.0 * arvo / this.laskin.hypot(1, 1.0 * (Math.abs(kohteidenErotus) - 1) / 2);
            }
        }
    }

    private double pisteetKunPaikkaOnOikealla(int kohteidenErotus, int arvo) {
        if (kohdeOnSamallaPuolella(kohteidenErotus) == true) {
            return 1.0 * arvo / Math.abs(kohteidenErotus / 2);
        } else {
            if (kohteidenErotus > 0) {
                return 1.0 * arvo / this.laskin.hypot(1, 1.0 * (kohteidenErotus - 1) / 2);
            } else {
                return 1.0 * arvo / this.laskin.hypot(1, 1.0 * Math.abs((kohteidenErotus) / 2 - 1));
            }
        }
    }

    private double tarkistaAvecJaPuoliso() {
        double pisteet = this.tarkistaAvec();
        if (pisteet == 0.0) {
            return this.tarkistaPuoliso();
        } else {
            return pisteet;
        }
    }

    private double tarkistaAvec() {
        if (this.paikka.getSitsaaja().avecIsSet() == false) {
            return 0.0;
        } else {
            int mikaPari = onkoMillainenPari(this.paikka.getSitsaaja());
            if (mikaPari == 1 || mikaPari == 0) {
                if (this.paikka.getMiehenAvecinPaikka() != null) {
                    if (this.paikka.getSitsaaja().getAvec().equals(this.paikka.getMiehenAvecinPaikka().getSitsaaja())) {
                        setAvec();
                        return 1000.0;
                    }
                }
            } else if (mikaPari == -1 || mikaPari == 0) {
                if (this.paikka.getNaisenAvecinPaikka() != null) {
                    if (this.paikka.getSitsaaja().getAvec().equals(this.paikka.getNaisenAvecinPaikka().getSitsaaja())) {
                        setAvec();
                        return 1000.0;
                    }
                }
            }
            return 0.0;
        }
    }

    private double tarkistaPuoliso() {
        if (this.paikka.getSitsaaja().puolisoIsSet() == false) {
            return 0.0;
        } else {
            if (this.paikka.getPuolisonPaikka() != null) {
                if (this.paikka.getSitsaaja().getPuoliso().equals(this.paikka.getPuolisonPaikka().getSitsaaja())) {
                    setPuoliso();
                    return 1000.0;
                }
            }
        }
        return 0.0;
    }

    public boolean isAvec() {
        return avec;
    }

    public void setAvec() {
        this.avec = true;
    }

    public boolean isPuoliso() {
        return puoliso;
    }

    public void setPuoliso() {
        this.puoliso = true;
    }

    private static int onkoMillainenPari(Sitsaaja sitsaaja) {
        if (sitsaaja.avecIsSet()) {
            if (sitsaaja.isMies() && sitsaaja.getAvec().isMies() == false) {
                return 1;
            } else if (sitsaaja.isMies() == false && sitsaaja.getAvec().isMies()) {
                return -1;
            } else {
                return 0;
            }
        }
        return -2;
    }

    private double tarkistaYmparillaOlevienSukupuolet() {
        int pointsit = 0;
        int tarkeys = 25;

        try {
            if (paikka.getMiehenAvecinPaikka() != null) {
                if (paikka.getMiehenAvecinPaikka().getSitsaaja().isMies()) {
                    pointsit++;
                }
            }
        } catch (UnsupportedOperationException e) {
        }
        try {
            if (paikka.getNaisenAvecinPaikka() != null) {
                if (paikka.getNaisenAvecinPaikka().getSitsaaja().isMies()) {
                    pointsit++;
                }
            }
        } catch (UnsupportedOperationException e) {
        }
        try {
            if (paikka.getPuolisonPaikka() != null) {
                if (paikka.getPuolisonPaikka().getSitsaaja().isMies()) {
                    pointsit++;
                }
            }
        } catch (UnsupportedOperationException e) {
        }
        try {
            if (paikka.getSitsaaja().isMies()) {
                return (3 - pointsit) * tarkeys;
            } else {
                return pointsit * tarkeys;
            }
        } catch (UnsupportedOperationException e) {
        }
        return 0.0;
    }
}
