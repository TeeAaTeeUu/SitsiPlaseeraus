package sitsiplaseeraus.random;

import java.util.ArrayList;
import sitsiplaseeraus.Poyta;
import sitsiplaseeraus.Sitsaaja;

class Yhteydet {

    protected static void lisaaYhteyksia(int montakoYhteytta, Poyta table) {
        ArrayList<Sitsaaja> sitsaajat = table.getSitsaajat();
        if (table.yhteyksienMaara() > 0) {
            luoRandomYhteyksia(montakoYhteytta, table);
            return;
        }
        int lisatytYhteydet = 0;
        for (int i = 0; i < sitsaajat.size(); i++) {
            int lisatyt = luoYhteydet(sitsaajat, montakoYhteytta, i, lisatytYhteydet);
            lisatytYhteydet += lisatyt;
        }
    }

    private static int luoYhteydet(ArrayList<Sitsaaja> sitsaajat, int montakoYhteytta, int monesko, int lisatytYhteydet) {
        int nytLisatytYhteydet = 0;
        int montakoYhteyttaLuodaan = palautaLuotavienYhteyksienMaara(montakoYhteytta, sitsaajat.size(), monesko, lisatytYhteydet);
        for (int j = 0; j < montakoYhteyttaLuodaan; j++) {
            luoYhteys(sitsaajat.get(monesko), sitsaajat);
            nytLisatytYhteydet++;
        }
        return nytLisatytYhteydet;
    }

    private static void luoRandomYhteyksia(int montakoyhteytta, Poyta table) {
        while (table.yhteyksienMaara() < montakoyhteytta) {
            ArrayList<Sitsaaja> sitsaajat = table.getSitsaajat();

            Sitsaaja sitsaaja;
            do {
                sitsaaja = annaRandomSitsaaja(sitsaajat);
            } while (sitsaaja.palautaYhteyksienMaara() == table.sitsaajienMaara() - 1);
            
            luoYhteys(sitsaaja, sitsaajat);
        }
    }

    private static int palautaLuotavienYhteyksienMaara(int montakoYhteytta, int sitsaajienmaara, int monesko, int lisatytYhteydet) {
        int yhteyksiaJaljella = montakoYhteytta - lisatytYhteydet;
        int sitsaajiaJaljella = sitsaajienmaara - monesko;
        double korjausVakio = palautaKorjausVakio(lisatytYhteydet, montakoYhteytta, monesko, sitsaajienmaara);
        int lisattavienMaksimiMaara = palautaLisattavienMaksimiMaara(yhteyksiaJaljella, sitsaajiaJaljella, korjausVakio);

        return palautaLuku(lisattavienMaksimiMaara, yhteyksiaJaljella, sitsaajienmaara, montakoYhteytta, monesko, lisatytYhteydet, sitsaajiaJaljella, korjausVakio);
    }

    private static double palautaKorjausVakio(int lisatytYhteydet, int montakoYhteytta, int monesko, int sitsaajienmaara) {
        if (monesko != 0 && lisatytYhteydet != 0) {
            if (lisatytYhteydet < (1.0 * (montakoYhteytta / sitsaajienmaara) * monesko)) {
                return 2.2;
            } else {
                return 1.8;
            }
        } else {
            return 2;
        }
    }

    private static Sitsaaja annaToinenRandomSitsaaja(Sitsaaja eriSitsaaja, ArrayList<Sitsaaja> sitsaajat) {
        Sitsaaja sitsaaja = annaRandomSitsaaja(sitsaajat);
        if (sitsaaja.getNimi().equals(eriSitsaaja.getNimi())) {
            return annaToinenRandomSitsaaja(eriSitsaaja, sitsaajat);
        } else {
            return sitsaaja;
        }
    }

    private static Sitsaaja annaRandomSitsaaja(ArrayList<Sitsaaja> sitsaajat) {
        int moneskoSitsaaja = Random.luo(sitsaajat.size() - 1);
        return sitsaajat.get(moneskoSitsaaja);
    }

    private static void luoYhteys(Sitsaaja sitsaaja, ArrayList<Sitsaaja> sitsaajat) {
        Sitsaaja toinenSitsaajaa = annaToinenRandomSitsaaja(sitsaaja, sitsaajat);
        int arvo = palautaNollastaEroavaArvo();
        if (sitsaaja.setYhteys(toinenSitsaajaa, arvo) == false) {
            luoYhteys(sitsaaja, sitsaajat);
        }
    }

    private static int palautaNollastaEroavaArvo() {
        int arvo = Random.luo(5, -5);
        if (arvo == 0) {
            return palautaNollastaEroavaArvo();
        } else {
            return arvo;
        }
    }

//    private static void soutShit(int montakoYhteytta, int sitsaajienmaara, int monesko, int lisatytYhteydet, int lisattavienMaksimiMaara, int luku, int yhteyksiaJaljella, int sitsaajiaJaljella, double korjausVakio) {
//        System.out.print("montako yhteyttä: " + montakoYhteytta + " : sitsaajien määrä: " + sitsaajienmaara);
//        System.out.print(" : monesko lisäys: " + monesko + " : tähän mennessä lisätyt: " + lisatytYhteydet);
//        System.out.print(" : lisätään maksimissaan: " + lisattavienMaksimiMaara + " : lisättiin: " + luku);
//        System.out.print(" : yhteyksiä Jäljellä: " + yhteyksiaJaljella + " : sitsaajia jäljellä: " + sitsaajiaJaljella);
//        System.out.print(" : korjausvakio: " + korjausVakio);
//        System.out.println();
//    }

    private static int palautaLisattavienMaksimiMaara(int yhteyksiaJaljella, int sitsaajiaJaljella, double korjausVakio) {
        return (int) (1.0 * (0.5 + yhteyksiaJaljella / sitsaajiaJaljella) * korjausVakio);
    }

    private static int palautaLuku(int lisattavienMaksimiMaara, int yhteyksiaJaljella, int sitsaajienmaara, int montakoYhteytta, int monesko, int lisatytYhteydet, int sitsaajiaJaljella, double korjausVakio) {
        int luku = Random.luo(lisattavienMaksimiMaara);
        if (luku >= yhteyksiaJaljella || luku >= sitsaajienmaara) {
            return palautaLuotavienYhteyksienMaara(montakoYhteytta, sitsaajienmaara, monesko, lisatytYhteydet);
        }
//        soutShit(montakoYhteytta, sitsaajienmaara, monesko, lisatytYhteydet, lisattavienMaksimiMaara, luku, yhteyksiaJaljella, sitsaajiaJaljella, korjausVakio);
        return luku;
    }
}
