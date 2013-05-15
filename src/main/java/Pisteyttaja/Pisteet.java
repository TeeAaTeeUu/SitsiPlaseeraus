package Pisteyttaja;

import java.util.ArrayList;
import sitsiplaseeraus.Poyta;
import sitsiplaseeraus.Sitsaaja;

public class Pisteet {
    private Poyta table;
    private ArrayList<SitsaajanPisteet> sitsaajat;
    private double pisteet;
    private boolean onYhteyksia;

    public Pisteet(Poyta table) {
        this.table = table;
        this.sitsaajat = new ArrayList<SitsaajanPisteet>();
        
        ArrayList<Sitsaaja> sitsaajatLista = this.table.getSitsaajat();
        for (Sitsaaja sitsaaja : sitsaajatLista) {
            SitsaajanPisteet sitsaajanPisteet = new SitsaajanPisteet(sitsaaja, this.table);
            this.sitsaajat.add(sitsaajanPisteet);
        }
        this.onYhteyksia = false;
    }
    
    public double palautaPisteet() {
        this.pisteet = 0.0;
        for (SitsaajanPisteet sitsaajanPisteet : sitsaajat) {
            this.pisteet += sitsaajanPisteet.palautaPisteet();
            this.onYhteyksia = true;
        }
        return pisteet;
    }
    
    public boolean onkoYhteyksia() {
        return onYhteyksia;
    }
    
}
