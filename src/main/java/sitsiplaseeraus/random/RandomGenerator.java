package sitsiplaseeraus.random;

import sitsiplaseeraus.Poyta;

public class RandomGenerator {
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
