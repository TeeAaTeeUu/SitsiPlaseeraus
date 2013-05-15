package jarjestelija;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import sitsiplaseeraus.Poyta;
import sitsiplaseeraus.random.RandomGenerator;

public class OptimoijaTest {
    private Poyta table;
    private Optimoija optimoija;
    private RandomGenerator random;
    
    public OptimoijaTest() {
    }
    
    @Before
    public void setUp() {
        this.table = new Poyta();
        
        this.random = new RandomGenerator();
        this.random.taytaRandomDatalla(80, 80*5, this.table);
        
        this.optimoija = new Optimoija(this.table);
    }

    @Test
    public void testOptimoiIstumapaikat() {
        double pisteet = this.optimoija.getPisteet().palautaPisteet();
        
        System.out.println("random alun pisteet: " + pisteet);
        
        RandomGenerator.tulostaSitsaajat(table);
        
        this.optimoija.optimoiIstumapaikat(10);
        
        assertTrue(this.optimoija.getPisteet().palautaPisteet() > pisteet);
        
        RandomGenerator.tulostaYhteydet(table);
    }
}
