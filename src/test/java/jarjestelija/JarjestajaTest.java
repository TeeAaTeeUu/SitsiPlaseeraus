package jarjestelija;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import sitsiplaseeraus.Sitsit;
import sitsiplaseeraus.random.RandomGenerator;

public class JarjestajaTest {
    private Jarjestaja jarjestaja;
    private RandomGenerator random;
    private Sitsit sitsit;
    
    public JarjestajaTest() {
    }
    
    @Before
    public void setUp() {
        this.sitsit = new Sitsit(3);
        this.jarjestaja = new Jarjestaja(sitsit);
        this.random = new RandomGenerator();
        
        this.random.taytaRandomDatalla(16, 50, this.sitsit);
    }
    
    @Test
    public void vaihtaaSitsaajienPaikkojaKaatumatta() {
        RandomGenerator.tulostaSitsaajat(this.sitsit);
        
        assertEquals(true, this.jarjestaja.vaihdaRandom());
        
        RandomGenerator.tulostaSitsaajat(this.sitsit);
    }
}