package jarjestelija;

import sitsiplaseeraus.Sitsaaja;
import sitsiplaseeraus.Sitsit;
import sitsiplaseeraus.random.Random;

public class Jarjestaja {

    private Sitsit sitsit;
    private Sitsaaja tokaSitsaaja;
    private Sitsaaja ekaSitsaaja;
    private int ekaPaikka;
    private int tokaPaikka;
    private int ekaPoyta;
    private int tokaPoyta;

    public Jarjestaja(Sitsit sitsit) {
        this.sitsit = sitsit;
    }

    protected boolean vaihdaRandom() {
        tokaSitsaaja = null;
        ekaSitsaaja = sitsit.getSitsaaja(Random.luo(sitsit.sitsaajienMaara() - 1));

        do {
            tokaSitsaaja = sitsit.getSitsaaja(Random.luo(sitsit.sitsaajienMaara() - 1));
        } while (ekaSitsaaja.equals(tokaSitsaaja));

        return this.vaihdaPaikat();
    }

    protected boolean vaihdaPaikat(Sitsaaja ekaSitsaaja, Sitsaaja tokaSitsaaja) {
        this.ekaSitsaaja = ekaSitsaaja;
        this.tokaSitsaaja = tokaSitsaaja;

        return this.vaihdaPaikat();
    }

    private boolean vaihdaPaikat() {
        ekaPaikka = ekaSitsaaja.getPaikka();
        ekaPoyta = ekaSitsaaja.getPoyta();
               
        tokaPaikka = tokaSitsaaja.getPaikka();
        tokaPoyta = tokaSitsaaja.getPoyta();
        
        ekaSitsaaja.vaihdaPaikka(tokaPoyta, tokaPaikka);
        tokaSitsaaja.vaihdaPaikka(ekaPoyta, ekaPaikka);
        
        return true;
    }
}
