package jarjestelija;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import sitsiplaseeraus.Poyta;
import sitsiplaseeraus.random.RandomGenerator;

public class optimoijaTest {
    private Poyta table;
    private optimoija optimoija;
    private RandomGenerator random;
    
    public optimoijaTest() {
    }
    
    @Before
    public void setUp() {
        this.table = new Poyta();
        
        this.random = new RandomGenerator();
        this.random.taytaRandomDatalla(16, 50, this.table);
        
        this.optimoija = new optimoija(this.table);
    }

    @Test
    public void testOptimoiIstumapaikat() {
        double pisteet = this.optimoija.getPisteet().palautaPisteet();
        
        this.optimoija.optimoiIstumapaikat(10);
        
        assertTrue(this.optimoija.getPisteet().palautaPisteet() > pisteet);
    }
}
