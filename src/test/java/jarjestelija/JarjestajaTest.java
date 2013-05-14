package jarjestelija;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import sitsiplaseeraus.random.RandomGenerator;
import sitsiplaseeraus.random.RandomGeneratorTest;

public class JarjestajaTest {
    private Jarjestaja jarjestaja;
    private RandomGenerator random;
    
    public JarjestajaTest() {
    }
    
    @Before
    public void setUp() {
        this.jarjestaja = new Jarjestaja();
        this.random = new RandomGenerator();
        
        this.random.taytaRandomDatalla(16, 50, this.jarjestaja.getTable());
    }
    
    @Test
    public void vaihtaaSitsaajienPaikkojaKaatumatta() {
        RandomGeneratorTest.tulostaSitsaajat(this.jarjestaja.getTable());
        
        assertEquals(true, this.jarjestaja.vaihdaRandom());
        
        RandomGeneratorTest.tulostaSitsaajat(this.jarjestaja.getTable());
    }
}