package sitsiplaseeraus;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class PoytaTest {

    Poyta poyta;

    public PoytaTest() {
    }

    @Before
    public void setUp() {
        poyta = new Poyta();
    }

    @Test
    public void sitsaajanLisaaminenJaPoistaminenToimii() {
        assertEquals(poyta.sitsaajienMaara(), 0);
        poyta.addSitsaaja("Testi Nukke");
        assertEquals("Testi Nukke", poyta.getSitsaaja(0).getNimi());
        assertEquals(poyta.sitsaajienMaara(), 1);
        poyta.deleteSitsaaja("Testi Nukke");
        assertEquals(poyta.sitsaajienMaara(), 0);
    }
    
    @Test
    public void yhteyksienLisaaminenToimii() {
        assertEquals(poyta.yhteyksienMaara(), 0);
        poyta.addSitsaaja("Testi Nukke");
        assertEquals("Testi Nukke", poyta.getSitsaaja(0).getNimi());
    }
}
