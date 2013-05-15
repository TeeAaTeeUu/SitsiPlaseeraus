package sitsiplaseeraus.random;

import java.util.HashMap;
import java.util.Map;
import sitsiplaseeraus.Poyta;
import sitsiplaseeraus.Sitsaaja;

public class RandomGenerator {

    public static void tulostaYhteydet(Poyta table) {
        System.out.println("\n" + "Yhteydet" + "\n");
        HashMap<Sitsaaja, HashMap> kaikkiYhteydet = table.palautaYhteydet();
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

    public static void tulostaSitsaajat(Poyta table) {
        System.out.println("\n" + "Sitsaajat" + "\n");
        boolean even = true;
        for (Sitsaaja sitsaaja : table.getSitsaajat()) {
            if (even) {
                System.out.print(sitsaaja.getNimi() + " : ");
                even = false;
            } else {
                System.out.println(sitsaaja.getNimi());
                even = true;
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
    
    public void taytaRandomDatalla(int montakoSitsaajaa, int montakoYhteytta, Poyta table) {
        this.lisaaNimia(montakoSitsaajaa, table);
        while(table.yhteyksienMaara() < montakoYhteytta) {
            Yhteydet.lisaaYhteyksia(montakoYhteytta, table);
        }
    }
    
    protected void lisaaNimia(int montakoSitsaajaa, Poyta table) {
        for (int i = 0; i < montakoSitsaajaa; i++) {
            table.addSitsaaja(randomNimi());
        }
    }
}
