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
        sitsaaja = new Sitsaaja("Testi Nukke");
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
        assertEquals(0, sitsaaja.palautaYhteyksienMaara());
        assertEquals(false, sitsaaja.palautaYhteydet().containsValue(-3));
        
        Sitsaaja toinenSitsaaja = new Sitsaaja("Toinen Ukko");
        sitsaaja.setYhteys(toinenSitsaaja, -3);
        
        assertEquals(1, sitsaaja.palautaYhteydet().size());
        assertEquals(1, sitsaaja.palautaYhteyksienMaara());
        assertEquals(true, sitsaaja.palautaYhteydet().containsKey(toinenSitsaaja));
        assertEquals(true, sitsaaja.palautaYhteydet().containsValue(-3));
        
        sitsaaja.deleteYhteys(toinenSitsaaja);
        
        assertEquals(0, sitsaaja.palautaYhteydet().size());
        assertEquals(0, sitsaaja.palautaYhteyksienMaara());
        assertEquals(false, sitsaaja.palautaYhteydet().containsKey(toinenSitsaaja));
        assertEquals(false, sitsaaja.palautaYhteydet().containsValue(-3));
    }
}
