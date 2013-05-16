package Pisteyttaja;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import sitsiplaseeraus.Sitsaaja;
import sitsiplaseeraus.Sitsit;
import sitsiplaseeraus.random.Random;
import sitsiplaseeraus.random.RandomGenerator;

public class SitsaajanPisteetTest {

    private RandomGenerator random;
    private Sitsit sitsit;
    private Sitsaaja sitsaaja;
    private SitsaajanPisteet pisteet;

    public SitsaajanPisteetTest() {
    }

    @Before
    public void setUp() {
        this.random = new RandomGenerator();
        this.sitsit = new Sitsit(3);
    }

    @Test
    public void testPalautaPisteet() {
        double luku = 0;

        for (int i = 0; i < 10000; i++) {
            this.sitsit = new Sitsit(3);
            random.taytaRandomDatalla(10, 30, this.sitsit);

            this.sitsaaja = palautaYhteydellinenSitsaaja();
            this.pisteet = new SitsaajanPisteet(this.sitsaaja);

            double pisteet = this.pisteet.palautaPisteet();
            if (pisteet != 0) {
                luku = pisteet;
            }
            assertTrue(this.pisteet.onkoYhteyksia());
        }
        assertTrue(luku != 0);
        
        RandomGenerator.tulostaSitsaajat(sitsit);
        RandomGenerator.tulostaYhteydet(sitsit);
        System.out.println("\n" + "pisteet " + this.pisteet.palautaPisteet() + " sitsaajalta " + this.sitsaaja.getNimi() + "\n");
    }
    
    @Test
    public void pisteidenMuodostusOnKunnollinen() {
        this.sitsit.addSitsaaja("Tero Merkki", 0);
        Sitsaaja tero = this.sitsit.getSitsaaja(0);
                
        this.sitsit.addSitsaaja("Toinen Heikki", 0);
        Sitsaaja heikki = this.sitsit.getSitsaaja(1);
        
        this.sitsit.addSitsaaja("Toinen Milla", 0);
        Sitsaaja milla = this.sitsit.getSitsaaja(2);
        
        this.sitsit.addSitsaaja("Nelos hilla", 0);
        Sitsaaja hilla = this.sitsit.getSitsaaja(3);
        
        this.sitsit.addSitsaaja("TestiNukke", 0);
        Sitsaaja nukke = this.sitsit.getSitsaaja(4);
        
        this.sitsit.addSitsaaja("Nukke Testi", 0);
        Sitsaaja testi = this.sitsit.getSitsaaja(5);
        
        this.sitsit.addSitsaaja("Joku Tyyppi", 0);
        Sitsaaja tyyppi = this.sitsit.getSitsaaja(6);
        
        this.sitsit.addSitsaaja("Ihan Vaan", 0);
        Sitsaaja vaan = this.sitsit.getSitsaaja(7);
        
        heikki.setYhteys(tero, 4);
        
        this.pisteet = new SitsaajanPisteet(heikki);
        assertEquals(4, this.pisteet.palautaPisteet(), 0.01);
        
        heikki.setYhteys(milla, 1);
        
        this.pisteet = new SitsaajanPisteet(heikki);
        assertEquals(4 + 1 / Math.hypot(1, 1), this.pisteet.palautaPisteet() , 0.01);
        
        heikki.setYhteys(hilla, 4);
        
        this.pisteet = new SitsaajanPisteet(heikki);
        assertEquals(4 + 1 / Math.hypot(1, 1) + 4, this.pisteet.palautaPisteet() , 0.01);
        
        heikki.setYhteys(nukke, 3);
        
        this.pisteet = new SitsaajanPisteet(heikki);
        assertEquals(4 + 1 / Math.hypot(1, 1) + 4 + 3 / Math.hypot(1, 2), this.pisteet.palautaPisteet() , 0.01);
        
        heikki.setYhteys(testi, -5);
        
        this.pisteet = new SitsaajanPisteet(heikki);
        assertEquals( 1.0 * 4 + 1 / Math.hypot(1, 1) + 4 + 3 / Math.hypot(1, 2) -  1.0 * 5 / 2, this.pisteet.palautaPisteet() , 0.01);
        
        heikki.setYhteys(tyyppi, -3);
        
        this.pisteet = new SitsaajanPisteet(heikki);
        assertEquals( 1.0 * 4 + 1 / Math.hypot(1, 1) + 4 + 3 / Math.hypot(1, 2) -  1.0 * 5 / 2 - 1.0 * 3/Math.hypot(1, 3), this.pisteet.palautaPisteet() , 0.01);
        
        heikki.setYhteys(vaan, 2);
        
        this.pisteet = new SitsaajanPisteet(heikki);
        assertEquals( 1.0 * 4 + 1 / Math.hypot(1, 1) + 4 + 3 / Math.hypot(1, 2) -  1.0 * 5 / 2 - 1.0 * 3/Math.hypot(1, 3) + 1.0 * 2 / 3, this.pisteet.palautaPisteet() , 0.01);
                
        this.pisteet = new SitsaajanPisteet(tero);
        assertTrue(this.pisteet.palautaPisteet() == 0);
    }

    @Test
    public void pisteidenMuodostusOnKunnollinen2() {
        this.sitsit.addSitsaaja("Terminen Ukko", 0);
        Sitsaaja ukko = this.sitsit.getSitsaaja(0);
        
        this.sitsit.addSitsaaja("Ihan Vaan", 0);
        Sitsaaja vaan = this.sitsit.getSitsaaja(1);
        
        this.sitsit.addSitsaaja("Tero Merkki", 0);
        Sitsaaja tero = this.sitsit.getSitsaaja(2);
                
        this.sitsit.addSitsaaja("Toinen Heikki", 0);
        Sitsaaja heikki = this.sitsit.getSitsaaja(3);
        
        this.sitsit.addSitsaaja("Toinen Milla", 0);
        Sitsaaja milla = this.sitsit.getSitsaaja(4);
        
        this.sitsit.addSitsaaja("Nelos hilla", 0);
        Sitsaaja hilla = this.sitsit.getSitsaaja(5);
        
        this.sitsit.addSitsaaja("TestiNukke", 0);
        Sitsaaja nukke = this.sitsit.getSitsaaja(6);
        
        this.sitsit.addSitsaaja("Nukke Testi", 0);
        Sitsaaja testi = this.sitsit.getSitsaaja(7);
        
        this.sitsit.addSitsaaja("Joku Tyyppi", 0);
        Sitsaaja tyyppi = this.sitsit.getSitsaaja(8);
        
        this.sitsit.addSitsaaja("Härö Pallo", 0);
        Sitsaaja pallo = this.sitsit.getSitsaaja(9);
        
        this.sitsit.addSitsaaja("Pallo juttu", 0);
        Sitsaaja juttu = this.sitsit.getSitsaaja(10);
        
        this.sitsit.addSitsaaja("Piilo Pallo", 0);
        Sitsaaja piilo = this.sitsit.getSitsaaja(11);
        
        nukke.setYhteys(tero, 4);
        
        this.pisteet = new SitsaajanPisteet(nukke);
        assertEquals(2, this.pisteet.palautaPisteet(), 0.01);
        
        nukke.setYhteys(milla, 1);
        
        this.pisteet = new SitsaajanPisteet(nukke);
        assertEquals(2 + 1, this.pisteet.palautaPisteet() , 0.01);
        
        nukke.setYhteys(hilla, 4);
        
        this.pisteet = new SitsaajanPisteet(nukke);
        assertEquals(2 + 1 + 4 / Math.hypot(1, 1), this.pisteet.palautaPisteet() , 0.01);
        
        nukke.setYhteys(heikki, 3);
        
        this.pisteet = new SitsaajanPisteet(nukke);
        assertEquals(2 + 1 + 4 / Math.hypot(1, 1) + 1.0 * 3 / Math.hypot(1, 2), this.pisteet.palautaPisteet() , 0.01);
        
        nukke.setYhteys(testi, -5);
        
        this.pisteet = new SitsaajanPisteet(nukke);
        assertEquals(2 + 1 + 4 / Math.hypot(1, 1) + 1.0 * 3 / Math.hypot(1, 2) - 5, this.pisteet.palautaPisteet() , 0.01);
        
        nukke.setYhteys(tyyppi, -3);
        
        this.pisteet = new SitsaajanPisteet(nukke);
        assertEquals(2 + 1 + 4 / Math.hypot(1, 1) + 1.0 * 3 / Math.hypot(1, 2) - 5 - 3.0, this.pisteet.palautaPisteet() , 0.01);
        
        nukke.setYhteys(vaan, -2);
        
        this.pisteet = new SitsaajanPisteet(nukke);
        assertEquals(2 + 1 + 4 / Math.hypot(1, 1) + 1.0 * 3 / Math.hypot(1, 2) - 5 - 3.0 - 2.0 / Math.hypot(1, 3), this.pisteet.palautaPisteet() , 0.01);
        
        nukke.setYhteys(pallo, -5);
        
        this.pisteet = new SitsaajanPisteet(nukke);
        assertEquals(2 + 1 + 4 / Math.hypot(1, 1) + 1.0 * 3 / Math.hypot(1, 2) - 5 - 3.0 - 2.0 / Math.hypot(1, 3) - 5.0 / Math.hypot(1, 1), this.pisteet.palautaPisteet() , 0.01);
        
        nukke.setYhteys(piilo, 3);
        
        this.pisteet = new SitsaajanPisteet(nukke);
        assertEquals(2 + 1 + 4 / Math.hypot(1, 1) + 1.0 * 3 / Math.hypot(1, 2) - 5 - 3.0 - 2.0 / Math.hypot(1, 3) - 5.0 / Math.hypot(1, 1) + 3.0 / Math.hypot(1, 2), this.pisteet.palautaPisteet() , 0.01);
                
        this.pisteet = new SitsaajanPisteet(tero);
        assertTrue(this.pisteet.palautaPisteet() == 0);
    }
    
    private Sitsaaja palautaYhteydellinenSitsaaja() {
        this.sitsaaja = this.sitsit.getSitsaaja(Random.luo(this.sitsit.sitsaajienMaara() - 1));

        if (this.sitsaaja.yhteyksienMaara() == 0) {
            return palautaYhteydellinenSitsaaja();
        } else {
            return this.sitsaaja;
        }
    }
}