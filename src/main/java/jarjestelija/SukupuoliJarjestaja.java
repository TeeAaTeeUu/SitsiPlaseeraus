package jarjestelija;

import omatTietorakenteet.ArrayList;
import omatTietorakenteet.Hakemisto;
import omatTietorakenteet.Vektori;
import sitsiplaseeraus.Paikka;
import sitsiplaseeraus.Sitsaaja;
import sitsiplaseeraus.Sitsit;
import sitsiplaseeraus.random.Random;

/**
 * Vielä kokeilu vaiheessa, mutta pyrkii auttamaan järjestyksen luomista niin,
 * että luo valmiiksi hyviä "tyttöpoika-järjestyksiä".
 */
public class SukupuoliJarjestaja {

    private Sitsit sitsit;
    private int miestenMaara;
    private int naistenMaara;
    private int sitsaajienMaara;
    private int poytienMaara;
    private Paikka kasiteltavaPaikka;
    private int miestenPaikkoja;
    private int naistenPaikkoja;
    private int miesmiesAvecit;
    private int naisnaisAvecit;
    private int miesmiesPuolisot;
    private int naisnaisPuolisot;
    private int miesmiesAvecPaikat;
    private int naisnaisAvecPaikat;
    private int miesmiesPuolisoPaikat;
    private int naisnaisPuolisoPaikat;
    private ArrayList<Paikka> lasketutPaikat;
    private Paikka toinenKasiteltavaPaikka;

    public SukupuoliJarjestaja(Sitsit sitsit) {
        this.sitsit = sitsit;
        this.sitsit.lisaaPaikoilleTiedotAvecinJaPuolisonPaikoista();
        lasketutPaikat = new ArrayList<Paikka>();
    }

    public void jarjestaPaikatSukupuolittain() {
        laskeSitsaajienMaarat();
        poytienMaara = sitsit.poytienMaara();

        asetaPaikatSukupuolittain();
        asetaPaikatSukupuolittainSitsaajienSukupuoliJakaumalla();
        asetaPaikatSamanSukupuolenPareille();
    }

    private void laskeMiestenJaNaistenMaarat() {
        nollaaLaskurit();

        for (Sitsaaja sitsaaja : sitsit.getSitsaajat()) {
            if (sitsaaja.isMies()) {
                laskeMiesMaarat(sitsaaja);
            } else if (sitsaaja.isMies() == false) {
                laskeNaisMaarat(sitsaaja);
            }
        }
    }

    private void laskeSitsaajienMaarat() {
        this.laskeMiestenJaNaistenMaarat();
        this.sitsaajienMaara = sitsit.sitsaajienMaara();
    }

    private void asetaPaikatSukupuolittain() {
        for (int i = 0; i < this.poytienMaara; i++) {
            boolean mies;
            if (Random.luo(1) == 1) {
                mies = true;
            } else {
                mies = false;
            }
            asetaPoytaSukupuolittain(i, mies);
        }
    }

    private void asetaPaikatSukupuolittainSitsaajienSukupuoliJakaumalla() {
        laskeMiestenJaNaistenPaikkojenMaarat();
        System.out.println(miestenPaikkoja + " " + naistenPaikkoja + " " + miestenMaara + " " + naistenMaara);

        int erotus = (miestenPaikkoja - naistenPaikkoja) - (miestenMaara - naistenMaara);

        if (erotus >= 2) {
            lisaaMiehia(erotus / 2);
        } else if (erotus <= -2) {
            lisaaNaisia(erotus / 2);
        }

        tulostaJarjestys();
    }

    private void laskeMiestenJaNaistenPaikkojenMaarat() {
        miestenPaikkoja = 0;
        naistenPaikkoja = 0;
        for (Paikka paikka : sitsit.getPaikat()) {
            if (paikka.isMiehenPaikka()) {
                miestenPaikkoja++;
            } else {
                naistenPaikkoja++;
            }
        }
    }

    private void lisaaMiehia(int erotus) {
        for (int i = 0; i < erotus; i++) {

            kasiteltavaPaikka = sitsit.getPaikka(Random.luo(sitsaajienMaara - 1));
            do {
                kasiteltavaPaikka = sitsit.getPaikka(Random.luo(sitsaajienMaara - 1));
            } while (kasiteltavaPaikka.isMiehenPaikka() == false);

            kasiteltavaPaikka.setMiehenPaikka(false);
        }
    }

    private void lisaaNaisia(int erotus) {
        for (int i = 0; i < Math.abs(erotus); i++) {

            kasiteltavaPaikka = sitsit.getPaikka(Random.luo(sitsaajienMaara - 1));
            do {
                kasiteltavaPaikka = sitsit.getPaikka(Random.luo(sitsaajienMaara - 1));
            } while (kasiteltavaPaikka.isMiehenPaikka());

            kasiteltavaPaikka.setMiehenPaikka(true);
        }
    }

    private void tulostaJarjestys() {
        for (int i = 0; i < sitsit.poytienMaara(); i++) {
            boolean even = true;
            for (Vektori<Integer, Paikka> paikka : sitsit.palautaPoydanPaikat(i)) {
                if (paikka.getValue().isMiehenPaikka()) {
                    System.out.print("mies");
                } else {
                    System.out.print("nainen");
                }

                if (even) {
                    System.out.print(" : ");
                    even = false;
                } else {
                    System.out.println();
                    even = true;
                }
            }
            System.out.println();
            System.out.println("---------");
        }
    }

    private void asetaPoytaSukupuolittain(int i, boolean mies) {
        Hakemisto paikat = sitsit.palautaPoydanPaikat(i);

        for (int j = 0; j < paikat.size(); j++) {
            this.kasiteltavaPaikka = (Paikka) paikat.getArvoIndexilla(j);

            if (mies) {
                this.kasiteltavaPaikka.setMiehenPaikka(true);
            } else {
                this.kasiteltavaPaikka.setMiehenPaikka(false);
            }
            if (j % 2 == 0) {
                if (mies == false) {
                    mies = true;
                } else {
                    mies = false;
                }
            }
        }
    }

    private void asetaPaikatSamanSukupuolenPareille() {
        laskeJoOlemassaOlevienSamanSukupuolenPaikkojenMaarat();

        int erotus = naisnaisAvecPaikat - (naisnaisAvecit / 2);
        if (erotus != 0) {
            if (erotus < 0) {
                for (int i = 0; i < Math.abs(erotus); i++) {
                    do {
                        kasiteltavaPaikka = sitsit.getPaikka(Random.luo(sitsaajienMaara - 1));
                    } while (kasiteltavaPaikka.isMiehenPaikka());
                    kasiteltavaPaikka.setMiehenPaikka(false);
                    
                    do {
                        toinenKasiteltavaPaikka = sitsit.getPaikka(Random.luo(sitsaajienMaara - 1));
                    } while (toinenKasiteltavaPaikka.isMiehenPaikka() == false && kasiteltavaPaikka.equals(toinenKasiteltavaPaikka) == false);
                    toinenKasiteltavaPaikka.setMiehenPaikka(true);
                }
            } else {
                for (int i = 0; i < erotus; i++) {
                    do {
                        kasiteltavaPaikka = sitsit.getPaikka(Random.luo(sitsaajienMaara - 1));
                    } while (kasiteltavaPaikka.isMiehenPaikka() == false);
                    kasiteltavaPaikka.setMiehenPaikka(true);
                    
                    do {
                        toinenKasiteltavaPaikka = sitsit.getPaikka(Random.luo(sitsaajienMaara - 1));
                    } while (toinenKasiteltavaPaikka.isMiehenPaikka() && kasiteltavaPaikka.equals(toinenKasiteltavaPaikka) == false);
                    toinenKasiteltavaPaikka.setMiehenPaikka(false);
                }
            }
        }
    }

    private void nollaaLaskurit() {
        miestenMaara = 0;
        naistenMaara = 0;
        miesmiesAvecit = 0;
        naisnaisAvecit = 0;
        miesmiesPuolisot = 0;
        naisnaisPuolisot = 0;
    }

    private void laskeMiesMaarat(Sitsaaja sitsaaja) {
        this.miestenMaara++;
        if (sitsaaja.avecIsSet() && sitsaaja.getAvec().isMies()) {
            miesmiesAvecit++;
        } else if (sitsaaja.puolisoIsSet() && sitsaaja.getPuoliso().isMies()) {
            miesmiesPuolisot++;
        }
    }

    private void laskeNaisMaarat(Sitsaaja sitsaaja) {
        this.naistenMaara++;
        if (sitsaaja.avecIsSet() && sitsaaja.getAvec().isMies() == false) {
            naisnaisAvecit++;
        } else if (sitsaaja.puolisoIsSet() && sitsaaja.getPuoliso().isMies() == false) {
            naisnaisPuolisot++;
        }
    }

    private void nollaaToisetkinLaskurit() {
        miesmiesAvecPaikat = 0;
        naisnaisAvecPaikat = 0;
        miesmiesPuolisoPaikat = 0;
        naisnaisPuolisoPaikat = 0;
        
        lasketutPaikat.clear();
    }

    private void laskeMiesMiesPaikat(Paikka paikka, ArrayList<Paikka> lasketutPaikat) {
        if (paikka.getMiehenAvecinPaikka() != null && paikka.getMiehenAvecinPaikka().isMiehenPaikka()) {
            miesmiesAvecPaikat++;
            lasketutPaikat.add(paikka.getMiehenAvecinPaikka());
        } else if (paikka.getNaisenAvecinPaikka() != null && paikka.getNaisenAvecinPaikka().isMiehenPaikka()) {
            miesmiesAvecPaikat++;
            lasketutPaikat.add(paikka.getNaisenAvecinPaikka());
        } else if (paikka.getPuolisonPaikka() != null && paikka.getPuolisonPaikka().isMiehenPaikka()) {
            miesmiesPuolisoPaikat++;
            lasketutPaikat.add(paikka.getPuolisonPaikka());
        }
    }

    private void laskeNaisNaisPaikat(Paikka paikka, ArrayList<Paikka> lasketutPaikat) {
        if (paikka.getMiehenAvecinPaikka() != null && paikka.getMiehenAvecinPaikka().isMiehenPaikka() == false) {
            naisnaisAvecPaikat++;
            lasketutPaikat.add(paikka.getMiehenAvecinPaikka());
        }
        if (paikka.getNaisenAvecinPaikka() != null && paikka.getNaisenAvecinPaikka().isMiehenPaikka() == false) {
            naisnaisAvecPaikat++;
            lasketutPaikat.add(paikka.getNaisenAvecinPaikka());
        }
        if (paikka.getPuolisonPaikka() != null && paikka.getPuolisonPaikka().isMiehenPaikka() == false) {
            naisnaisPuolisoPaikat++;
            lasketutPaikat.add(paikka.getPuolisonPaikka());
        }
    }

    private void laskeJoOlemassaOlevienSamanSukupuolenPaikkojenMaarat() {
        nollaaToisetkinLaskurit();

        for (Paikka paikka : sitsit.getPaikat()) {

            if (paikka.isMiehenPaikka() && lasketutPaikat.contains(paikka) == false) {
                laskeMiesMiesPaikat(paikka, lasketutPaikat);

            } else if (paikka.isMiehenPaikka() == false && lasketutPaikat.contains(paikka) == false) {
                laskeNaisNaisPaikat(paikka, lasketutPaikat);
            }

            lasketutPaikat.add(paikka);
        }
    }
}
