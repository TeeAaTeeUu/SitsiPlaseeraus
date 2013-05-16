package sitsiplaseeraus;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class SitsaajaTest {

    Sitsaaja sitsaaja;

    public SitsaajaTest() {
    }

    @Before
    public void setUp() {
        sitsaaja = new Sitsaaja("Testi Nukke", 0, 0);
    }

    @Test
    public void nimiToimii() {
        assertEquals("Testi Nukke", sitsaaja.getNimi());
        sitsaaja.setNimi("Nukke Testi2");
        assertEquals("Nukke Testi2", sitsaaja.getNimi());
    }
    
    @Test
    public void yhteydenLisaysJaPoistoToimii() {
        assertEquals(0, sitsaaja.palautaYhteydet().size());
        assertEquals(0, sitsaaja.yhteyksienMaara());
        assertEquals(false, sitsaaja.palautaYhteydet().containsValue(-3));
        
        Sitsaaja toinenSitsaaja = new Sitsaaja("Toinen Ukko", 0, 1);
        sitsaaja.setYhteys(toinenSitsaaja, -3);
        
        assertEquals(1, sitsaaja.palautaYhteydet().size());
        assertEquals(1, sitsaaja.yhteyksienMaara());
        assertEquals(true, sitsaaja.palautaYhteydet().containsKey(toinenSitsaaja));
        assertEquals(true, sitsaaja.palautaYhteydet().containsValue(-3));
        
        sitsaaja.deleteYhteys(toinenSitsaaja);
        
        assertEquals(0, sitsaaja.palautaYhteydet().size());
        assertEquals(0, sitsaaja.yhteyksienMaara());
        assertEquals(false, sitsaaja.palautaYhteydet().containsKey(toinenSitsaaja));
        assertEquals(false, sitsaaja.palautaYhteydet().containsValue(-3));
    }
}
