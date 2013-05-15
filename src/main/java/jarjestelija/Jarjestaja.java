package jarjestelija;

import sitsiplaseeraus.Poyta;
import sitsiplaseeraus.Sitsaaja;
import sitsiplaseeraus.random.Random;

public class Jarjestaja {

    private Poyta table;
    private Sitsaaja tokaSitsaaja;
    private Sitsaaja ekaSitsaaja;
    private int ekaPaikka;
    private int tokaPaikka;

    public Jarjestaja() {
        this.table = new Poyta();
    }

    public Poyta getTable() {
        return table;
    }
    
    public void setTable(Poyta table) {
        this.table = table;
    }

    protected boolean vaihdaRandom() {
        tokaSitsaaja = null;

        ekaSitsaaja = table.getSitsaaja(Random.luo(table.sitsaajienMaara() - 1));
        do {
            tokaSitsaaja = table.getSitsaaja(Random.luo(table.sitsaajienMaara() - 1));
        } while (ekaSitsaaja.equals(tokaSitsaaja));


        return this.vaihdaPaikat();
    }
    
    protected boolean vaihdaPaikat(Sitsaaja ekaSitsaaja, Sitsaaja tokaSitsaaja) {
        this.ekaSitsaaja = ekaSitsaaja;
        this.tokaSitsaaja = tokaSitsaaja;
        
        return this.vaihdaPaikat();
    }

    private boolean vaihdaPaikat() {
        ekaPaikka = this.table.getPaikka(ekaSitsaaja);
        tokaPaikka = this.table.getPaikka(tokaSitsaaja);

        if (ekaPaikka == -1 || tokaPaikka == -1) {
            return false;
        } else {
            if (this.table.setPaikka(ekaSitsaaja, tokaPaikka)) {
                if (this.table.setPaikka(tokaSitsaaja, ekaPaikka)) {
                    return true;
                }
            }
            return false;
        }
    }
}
