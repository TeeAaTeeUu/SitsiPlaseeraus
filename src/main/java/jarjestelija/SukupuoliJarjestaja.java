package jarjestelija;

import Pisteyttaja.Pisteet;
import omatTietorakenteet.Hakemisto;
import sitsiplaseeraus.Paikka;
import sitsiplaseeraus.Sitsaaja;
import sitsiplaseeraus.Sitsit;
import sitsiplaseeraus.random.Random;

/**
 * Vielä kokeilu vaiheessa, mutta pyrkii auttamaan järjestyksen luomista niin, että luo valmiiksi hyviä "tyttöpoika-järjestyksiä".
 */
public class SukupuoliJarjestaja {

    private Sitsit sitsit;
    private int miestenMaara;
    private int naistenMaara;
    private int sitsaajienMaara;
    private int poytienMaara;
    private Paikka kasiteltavaPaikka;
    private Pisteet pisteet;

    public SukupuoliJarjestaja(Sitsit sitsit) {
        this.sitsit = sitsit;
        this.sitsit.lisaaPaikoilleTiedotAvecinJaPuolisonPaikoista();
    }

    public void jarjestaPaikatSukupuolittain() {
        laskeSitsaajienMaarat();
        this.poytienMaara = sitsit.poytienMaara();

        for (int i = 0; i < 100; i++) {
            this.asetaPaikatSukupuolittain();
            this.asetaPaikatSukupuolittainSitsaajienSukupuoliJakaumalla();

            this.pisteet = new Pisteet(sitsit);
            this.pisteet.palautaPisteet();
            System.out.println(this.pisteet.getSukupuoliPisteet());
        }
    }

    private void laskeMiestenJaNaistenMaarat() {
        miestenMaara = 0;
        naistenMaara = 0;
        for (Sitsaaja sitsaaja : sitsit.getSitsaajat()) {
            if (sitsaaja.isMies()) {
                this.miestenMaara++;
            } else if (sitsaaja.isMies() == false) {
                this.naistenMaara++;
            }
        }
    }

    private void laskeSitsaajienMaarat() {
        this.laskeMiestenJaNaistenMaarat();
        this.sitsaajienMaara = sitsit.sitsaajienMaara();
    }

    private void asetaPaikatSukupuolittain() {
        for (int i = 0; i < this.poytienMaara; i++) {
            boolean jokaToinen;
            if (Random.luo(1) == 1) {
                jokaToinen = true;
            } else {
                jokaToinen = false;
            }

            Hakemisto paikat = sitsit.palautaPoydanPaikat(i);

            for (int j = 0; j < paikat.size(); j++) {
                this.kasiteltavaPaikka = (Paikka) paikat.getArvoIndexilla(j);

                if (jokaToinen) {
                    jokaToinen = false;
                    this.kasiteltavaPaikka.setMiehenPaikka(true);
                } else {
                    jokaToinen = true;
                    this.kasiteltavaPaikka.setMiehenPaikka(false);
                }
            }
        }
    }

    private void asetaPaikatSukupuolittainSitsaajienSukupuoliJakaumalla() {
        int miestenPaikkoja = 0;
        int naistenPaikkoja = 0;
        for (Paikka paikka : sitsit.getPaikat()) {
            if (paikka.isMiehenPaikka()) {
                miestenPaikkoja++;
            } else {
                naistenPaikkoja++;
            }
        }

        System.out.println(miestenPaikkoja + " " + naistenPaikkoja + " " + miestenMaara + " " + naistenMaara);

        int erotus = (miestenPaikkoja - naistenPaikkoja) - (miestenMaara - naistenMaara);
        if (erotus >= 2) {
            for (int i = 0; i < erotus / 2; i++) {

                kasiteltavaPaikka = sitsit.getPaikka(Random.luo(sitsaajienMaara - 1));
                do {
                    kasiteltavaPaikka = sitsit.getPaikka(Random.luo(sitsaajienMaara - 1));
                } while (kasiteltavaPaikka.isMiehenPaikka() == false);

                kasiteltavaPaikka.setMiehenPaikka(false);
            }
        } else if (erotus <= -2) {
            for (int i = 0; i < Math.abs(erotus) / 2; i++) {

                kasiteltavaPaikka = sitsit.getPaikka(Random.luo(sitsaajienMaara - 1));
                do {
                    kasiteltavaPaikka = sitsit.getPaikka(Random.luo(sitsaajienMaara - 1));
                } while (kasiteltavaPaikka.isMiehenPaikka());

                kasiteltavaPaikka.setMiehenPaikka(true);
            }
        }
        miestenPaikkoja = 0;
        naistenPaikkoja = 0;
        for (Paikka paikka : sitsit.getPaikat()) {
            if (paikka.isMiehenPaikka()) {
                miestenPaikkoja++;
            } else {
                naistenPaikkoja++;
            }
        }
        System.out.println(miestenPaikkoja + " " + naistenPaikkoja + " " + miestenMaara + " " + naistenMaara);
    }
}
