package sitsiplaseeraus;

import java.util.HashMap;
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
    
    @Test
    public void sitsaajanLisaaminenJaPoistaminenToimii2() {
        assertEquals(poyta.sitsaajienMaara(), 0);
        
        poyta.addSitsaaja("Testi Nukke");
        
        poyta.addSitsaaja("Testi Juttu");
        
        HashMap<Sitsaaja, Integer> paikat = new HashMap<Sitsaaja, Integer>();
        paikat.putAll(poyta.getPaikat());
        
        assertEquals(0, poyta.getPaikka(poyta.getSitsaaja("Testi Nukke")));
        assertEquals(1, poyta.getPaikka(poyta.getSitsaaja("Testi Juttu")));
        
        poyta.setPaikka(poyta.getSitsaaja("Testi Nukke"), 1);
        poyta.setPaikka(poyta.getSitsaaja("Testi Juttu"), 0);
        
        System.out.println("väli");
        
        assertEquals(1, poyta.getPaikka(poyta.getSitsaaja("Testi Nukke")));
        assertEquals(0, poyta.getPaikka(poyta.getSitsaaja("Testi Juttu")));
        
        poyta.setPaikat(paikat);
        
        System.out.println("toinen");
        
        assertEquals(0, poyta.getPaikka(poyta.getSitsaaja("Testi Nukke")));
        assertEquals(1, poyta.getPaikka(poyta.getSitsaaja("Testi Juttu")));
    }
}
