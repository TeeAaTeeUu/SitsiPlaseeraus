package jarjestelija;

import sitsiplaseeraus.Paikka;
import sitsiplaseeraus.Sitsaaja;
import sitsiplaseeraus.Sitsit;
import sitsiplaseeraus.random.Random;

public class Jarjestaja {

    private Sitsit sitsit;
    private Paikka tokaPaikka;
    private Paikka ekaPaikka;
    private Sitsaaja ekaSitsaaja;
    private Sitsaaja tokaSitsaaja;


    public Jarjestaja(Sitsit sitsit) {
        this.sitsit = sitsit;
    }

    protected boolean vaihdaRandom() {
        tokaPaikka = null;
        ekaPaikka = sitsit.getPaikka(Random.luo(sitsit.sitsaajienMaara() - 1));

        do {
            tokaPaikka = sitsit.getPaikka(Random.luo(sitsit.sitsaajienMaara() - 1));
        } while (ekaPaikka.equals(tokaPaikka));

        return this.vaihdaPaikat();
    }

    protected boolean vaihdaPaikat() {
        this.ekaSitsaaja = ekaPaikka.getSitsaaja();
        this.tokaSitsaaja = tokaPaikka.getSitsaaja();
        
        ekaPaikka.setSitsaaja(tokaSitsaaja);
        tokaPaikka.setSitsaaja(ekaSitsaaja);
        
        return true;
    }
    
    protected boolean vaihdaPaikat(Paikka ekaPaikka, Paikka tokaPaikka) {
        this.ekaPaikka = ekaPaikka;
        this.tokaPaikka = tokaPaikka;
        
        return vaihdaPaikat();
    }
}
