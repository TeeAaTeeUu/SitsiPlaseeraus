package jarjestelija;

import Pisteyttaja.Pisteet;
import sitsiplaseeraus.Poyta;

public class optimoija {
    private Jarjestaja jarjestelija;
    private Poyta table;
    private Pisteet pisteet;
    private long aika;

    public optimoija(Poyta table) {
        this.jarjestelija = new Jarjestaja();
        this.table = table;
        this.pisteet = new Pisteet(table);
    }
    
    public void optimoiIstumapaikat() {
        this.aika = System.currentTimeMillis();
        
        while (aika + 1000*100 > System.currentTimeMillis()) {
            
        }
    }
    
}
