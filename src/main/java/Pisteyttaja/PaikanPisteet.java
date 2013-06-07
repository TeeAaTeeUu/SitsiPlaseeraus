package Pisteyttaja;

import omatTietorakenteet.Hakemisto;
import omatTietorakenteet.Vektori;
import sitsiplaseeraus.Paikka;
import sitsiplaseeraus.Sitsaaja;

public class PaikanPisteet {

    private Paikka paikka;
    private Hakemisto<Sitsaaja, Integer> yhteydet;
    private Paikka kohdePaikka;
    private int kohteidenErotus;
    private boolean onYhteyksia;
    private int arvo;
    private Sitsaaja kohdeSitsaaja;
    private final Laskin laskin;
    private boolean avec;
    private boolean puoliso;
    private double pariPisteet;
    private double sukupuoliPisteet;
    private double yhteysPisteet;

    protected PaikanPisteet(Paikka paikka, Laskin laskin) {
        this.paikka = paikka;
        this.onYhteyksia = false;
        this.laskin = laskin;
    }

    protected double palautaPisteet() {
        this.yhteydet = this.paikka.getSitsaaja().palautaYhteydet();

        this.avec = false;
        this.puoliso = false;

        pariPisteet = 0.0;
        sukupuoliPisteet = 0.0;
        yhteysPisteet = 0.0;

        pariPisteet += this.tarkistaAvecJaPuoliso();
        sukupuoliPisteet += this.tarkistaYmparillaOlevienSukupuolet();


        for (Vektori<Sitsaaja, Integer> yhteys : yhteydet) {
            this.onYhteyksia = true;

            this.kohdeSitsaaja = (Sitsaaja) yhteys.getKey();
            this.kohdePaikka = this.kohdeSitsaaja.getPaikka();

            if (this.paikka.getPoyta() == this.kohdePaikka.getPoyta()) {
                this.arvo = (Integer) yhteys.getValue();

                this.kohteidenErotus = this.paikka.getPaikka() - this.kohdePaikka.getPaikka();

                yhteysPisteet += this.palautaPisteet(arvo, kohteidenErotus, this.paikka.getPaikka());
            }
        }

        return pariPisteet + sukupuoliPisteet + yhteysPisteet;
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
        double pisteet = this.tarkistaAvec(this.paikka);
        if (pisteet == 0.0) {
            pisteet = this.tarkistaPuoliso(this.paikka);
            if (pisteet > 0.0) {
                setPuoliso();
            }
            return pisteet;
        } else {
            if (pisteet > 0.0) {
                setAvec();
            }
            return pisteet;
        }
    }

    public static double tarkistaAvecJaPuoliso(Paikka paikka) {
        double pisteet = tarkistaAvec(paikka);
        if (pisteet == 0.0) {
            pisteet = tarkistaPuoliso(paikka);
            return pisteet;
        } else {
            return pisteet;
        }
    }

    private static double tarkistaAvec(Paikka paikka) {
        if (paikka != null) {
            if (paikka.getSitsaaja().avecIsSet() == false) {
                return 0.0;
            } else {
                int mikaPari = onkoMillainenPari(paikka.getSitsaaja());
                if (mikaPari == 1) {
                    if (paikka.getMiehenAvecinPaikka() != null) {
                        if (paikka.getSitsaaja().getAvec().equals(paikka.getMiehenAvecinPaikka().getSitsaaja())) {

                            return 10000.1;
                        }
                    }
                } else if (mikaPari == -1) {
                    if (paikka.getNaisenAvecinPaikka() != null) {
                        if (paikka.getSitsaaja().getAvec().equals(paikka.getNaisenAvecinPaikka().getSitsaaja())) {
                            return 10000.2;
                        }
                    }
                } else if (mikaPari == 0) {
                    if (paikka.getNaisenAvecinPaikka() != null) {
                        if (paikka.getSitsaaja().getAvec().equals(paikka.getNaisenAvecinPaikka().getSitsaaja())) {
                            return 10000.2;
                        }
                    }
                    if (paikka.getMiehenAvecinPaikka() != null) {
                        if (paikka.getSitsaaja().getAvec().equals(paikka.getMiehenAvecinPaikka().getSitsaaja())) {
                            return 10000.1;
                        }
                    }
                }
            }
        }
        return 0.0;
    }

    private static double tarkistaPuoliso(Paikka paikka) {
        if (paikka != null) {
            if (paikka.getSitsaaja().puolisoIsSet() == false) {
                return 0.0;
            } else {
                if (paikka.getPuolisonPaikka() != null) {
                    if (paikka.getSitsaaja().getPuoliso().equals(paikka.getPuolisonPaikka().getSitsaaja())) {

                        return 10000.0;
                    }
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

        int tarkeys = 500;
        int pisteita = 0;
        boolean mies = false;
        
        try {
        if (paikka.getSitsaaja().isMies()) {
            mies = true;
        } else {
            mies = false;
        }
        } catch (UnsupportedOperationException e) {
        }

        try {
            if (paikka.getMiehenAvecinPaikka() != null) {
                if (paikka.getMiehenAvecinPaikka().getSitsaaja().isMies()) {
                    if (mies == false) {
                        pisteita += 500;
                    }
                } else {
                    if (mies == true) {
                        pisteita += 500;
                    }
                }
            }
        } catch (UnsupportedOperationException e) {
        }
        try {
            if (paikka.getNaisenAvecinPaikka() != null) {
                if (paikka.getNaisenAvecinPaikka().getSitsaaja().isMies()) {
                    if (mies == false) {
                        pisteita += 500;
                    }
                } else {
                    if (mies == true) {
                        pisteita += 500;
                    }
                }
            }
        } catch (UnsupportedOperationException e) {
        }
        try {
            if (paikka.getPuolisonPaikka() != null) {
                if (paikka.getPuolisonPaikka().getSitsaaja().isMies()) {
                    if (mies == false) {
                        pisteita += 500;
                    }
                } else {
                    if (mies == true) {
                        pisteita += 500;
                    }
                }
            }
        } catch (UnsupportedOperationException e) {
        }
        return pisteita;
    }
}
