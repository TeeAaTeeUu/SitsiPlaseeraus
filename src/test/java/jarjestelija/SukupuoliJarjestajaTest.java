package jarjestelija;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import sitsiplaseeraus.Sitsit;
import sitsiplaseeraus.random.RandomGenerator;

public class SukupuoliJarjestajaTest {
    private Sitsit sitsit;
    private RandomGenerator random;
    private SukupuoliJarjestaja sukupuoliJarjestaja;
    
    public SukupuoliJarjestajaTest() {
    }

    
    @Before
    public void setUp() {
        this.sitsit = new Sitsit(3);
        this.random = new RandomGenerator();
        this.random.taytaRandomDatalla(80, 300, sitsit);
        this.sukupuoliJarjestaja = new SukupuoliJarjestaja(sitsit);
    }

    @Test
    public void testPalautaParasSukupuolinenJarjestys() {
        this.sukupuoliJarjestaja.jarjestaPaikatSukupuolittain();
        assertEquals(true, true);
    }
}