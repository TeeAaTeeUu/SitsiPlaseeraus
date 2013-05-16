package Pisteyttaja;

import java.util.ArrayList;
import sitsiplaseeraus.Sitsaaja;
import sitsiplaseeraus.Sitsit;

public class Pisteet {
    private Sitsit sitsit;
    private ArrayList<SitsaajanPisteet> sitsaajat;
    private double pisteet;
    private boolean onYhteyksia;

    public Pisteet(Sitsit sitsit) {
        this.sitsit = sitsit;
        this.sitsaajat = new ArrayList<SitsaajanPisteet>();
        
        this.alustaSitsaajat();
        
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
    
    protected boolean onkoYhteyksia() {
        return onYhteyksia;
    }

    private void alustaSitsaajat() {
        ArrayList<Sitsaaja> sitsaajatLista = this.sitsit.getSitsaajat();
        
        for (Sitsaaja sitsaaja : sitsaajatLista) {
            SitsaajanPisteet sitsaajanPisteet = new SitsaajanPisteet(sitsaaja);
            this.sitsaajat.add(sitsaajanPisteet);
        }
    }
    
}
