package jarjestelija;

import Pisteyttaja.Pisteet;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;
import sitsiplaseeraus.Poyta;
import sitsiplaseeraus.Sitsaaja;
import sitsiplaseeraus.random.Random;
import sitsiplaseeraus.random.RandomGenerator;

public class optimoija {
    private Jarjestaja jarjestelija;
    private Poyta table;
    private Pisteet pisteet;
    private long aika;
    private double VanhassaPisteita;
    private HashMap<Sitsaaja, Integer> vanha;

    public optimoija(Poyta table) {
        this.table = table;
        
        this.jarjestelija = new Jarjestaja();
        this.jarjestelija.setTable(this.table);
        
        this.pisteet = new Pisteet(this.table);
        this.vanha = new HashMap<Sitsaaja, Integer>();
    }
    
    public void optimoiIstumapaikat(int sekunttia) {
        this.aika = System.currentTimeMillis();
        
        int i = 0;
        while (aika + 1000 * sekunttia > System.currentTimeMillis()) {
            this.kokeileVaihtoa();
            i++;
            if(i % 100 == 0) {
                RandomGenerator.tulostaSitsaajat(this.table);
                System.out.println(this.VanhassaPisteita = this.pisteet.palautaPisteet());
                System.out.println("");
            }
        }
    }

    public Pisteet getPisteet() {
        return pisteet;
    }

    private void kokeileVaihtoa() {
        this.VanhassaPisteita = this.pisteet.palautaPisteet();
        
        this.vanha.clear();
        this.vanha.putAll(this.table.getPaikat());
        
        int kuinkaMontaVaihtoa = Random.luo(10);
        for (int i = 0; i <= kuinkaMontaVaihtoa; i++) {
            this.jarjestelija.vaihdaRandom();
        }
        
        if(this.pisteet.palautaPisteet() < this.VanhassaPisteita) {
            this.table.setPaikat(this.vanha);
        }
    }
    
}
