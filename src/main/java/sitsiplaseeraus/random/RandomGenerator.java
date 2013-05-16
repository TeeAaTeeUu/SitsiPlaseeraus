package sitsiplaseeraus.random;

import java.util.HashMap;
import java.util.Map;
import sitsiplaseeraus.Sitsaaja;
import sitsiplaseeraus.Sitsit;

public class RandomGenerator {

    public static void tulostaYhteydet(Sitsit sitsit) {
        System.out.println("\n" + "Yhteydet" + "\n");

        HashMap<Sitsaaja, HashMap> kaikkiYhteydet = sitsit.palautaYhteydet();
        int moneskoYhteys = 0;
        int moneskoSitsaaja = 0;
        int moneskoyhteydellinen = 0;
        for (Map.Entry sitsaajanYhteydet : kaikkiYhteydet.entrySet()) {
            Sitsaaja sitsaaja = (Sitsaaja) sitsaajanYhteydet.getKey();
            HashMap<Sitsaaja, HashMap> yhteydet = (HashMap<Sitsaaja, HashMap>) sitsaajanYhteydet.getValue();
            moneskoSitsaaja++;
            boolean eka = true;
            for (Map.Entry yhteys : yhteydet.entrySet()) {
                Sitsaaja kohdeSitsaaja = (Sitsaaja) yhteys.getKey();
                int arvo = (Integer) yhteys.getValue();
                System.out.println(sitsaaja.getNimi() + " pit\u00e4\u00e4 sitsaajasta " + kohdeSitsaaja.getNimi() + " arvolla " + arvo);
                moneskoYhteys++;
                if (eka) {
                    moneskoyhteydellinen++;
                    eka = false;
                }
            }
        }
        System.out.println("\n" + "----" + moneskoYhteys + "-" + moneskoSitsaaja + "-" + moneskoyhteydellinen + "--" + "\n");
    }

    public static void tulostaSitsaajat(Sitsit sitsit) {
        System.out.println("\n" + "Sitsaajat" + "\n");

        for (int i = 0; i < sitsit.poytienMaara(); i++) {
            boolean even = true;

            for (Sitsaaja sitsaaja : sitsit.palautaPoydanSitsaajat(i)) {
                if (even == true) {
                    System.out.print(sitsaaja.getNimi() + "\t:\t");
                    even = false;
                } else {
                    System.out.println(sitsaaja.getNimi());
                    even = true;
                }
            }
            if (even = true) {
                System.out.println("\n");
            } else {
                System.out.println();
            }
        }
        System.out.println("\n" + "--------" + "\n");
    }
    private RandomNimi nimet;

    public RandomGenerator() {
        this.nimet = new RandomNimi();
    }

    public String randomNimi() {
        int random = Random.luo(1);
        if (random == 1) {
            return this.palautaNimi(true);
        } else {
            return this.palautaNimi(false);
        }
    }

    public String palautaNimi(Boolean nainen) {
        if (nainen == true) {
            return this.nimet.palautaEtunimiNaisen() + " " + this.nimet.palautaSukunimi();
        } else {
            return this.nimet.palautaEtunimiMiehen() + " " + this.nimet.palautaSukunimi();
        }
    }

    public void taytaRandomDatalla(int montakoSitsaajaa, int montakoYhteytta, Sitsit sitsit) {
        this.lisaaNimia(montakoSitsaajaa, sitsit);
        while (sitsit.yhteyksienMaara() < montakoYhteytta) {
            Yhteydet.lisaaYhteyksia(montakoYhteytta, sitsit);
        }
    }

    protected void lisaaNimia(int montakoSitsaajaa, Sitsit sitsit) {
        for (int i = 0; i < montakoSitsaajaa; i++) {
            sitsit.addSitsaaja(randomNimi());
        }
    }
}
