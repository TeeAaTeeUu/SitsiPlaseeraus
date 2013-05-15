package Pisteyttaja;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import sitsiplaseeraus.Poyta;
import sitsiplaseeraus.Sitsaaja;
import sitsiplaseeraus.random.Random;
import sitsiplaseeraus.random.RandomGenerator;
import sitsiplaseeraus.random.RandomGeneratorTest;

public class SitsaajanPisteetTest {

    private RandomGenerator random;
    private Poyta table;
    private Sitsaaja sitsaaja;
    private SitsaajanPisteet pisteet;

    public SitsaajanPisteetTest() {
    }

    @Before
    public void setUp() {
        this.random = new RandomGenerator();
        this.table = new Poyta();
    }

    @Test
    public void testPalautaPisteet() {
        double luku = 0;

        for (int i = 0; i < 1000; i++) {
            this.table = new Poyta();
            random.taytaRandomDatalla(10, 30, this.table);

            this.sitsaaja = palautaYhteydellinenSitsaaja();
            this.pisteet = new SitsaajanPisteet(this.sitsaaja, this.table);

            double pisteet = this.pisteet.palautaPisteet();
            if (pisteet != 0) {
                luku = pisteet;
            }
            assertTrue(this.pisteet.onkoYhteyksia());
        }
        assertTrue(luku != 0);
        
        RandomGenerator.tulostaSitsaajat(table);
        RandomGenerator.tulostaYhteydet(table);
        System.out.println("\n" + "pisteet " + this.pisteet.palautaPisteet() + " sitsaajalta " + this.sitsaaja.getNimi() + "\n");
    }
    
    @Test
    public void pisteidenMuodostusOnKunnollinen() {
        this.table.addSitsaaja("Tero Merkki");
        Sitsaaja tero = this.table.getSitsaaja(0);
                
        this.table.addSitsaaja("Toinen Heikki");
        Sitsaaja heikki = this.table.getSitsaaja(1);
        
        this.table.addSitsaaja("Toinen Milla");
        Sitsaaja milla = this.table.getSitsaaja(2);
        
        this.table.addSitsaaja("Nelos hilla");
        Sitsaaja hilla = this.table.getSitsaaja(3);
        
        this.table.addSitsaaja("TestiNukke");
        Sitsaaja nukke = this.table.getSitsaaja(4);
        
        this.table.addSitsaaja("Nukke Testi");
        Sitsaaja testi = this.table.getSitsaaja(5);
        
        this.table.addSitsaaja("Joku Tyyppi");
        Sitsaaja tyyppi = this.table.getSitsaaja(6);
        
        this.table.addSitsaaja("Ihan Vaan");
        Sitsaaja vaan = this.table.getSitsaaja(7);
        
        heikki.setYhteys(tero, 4);
        
        this.pisteet = new SitsaajanPisteet(heikki, table);
        assertEquals(4, this.pisteet.palautaPisteet(), 0.01);
        
        heikki.setYhteys(milla, 1);
        
        this.pisteet = new SitsaajanPisteet(heikki, table);
        assertEquals(4 + 1 / Math.hypot(1, 1), this.pisteet.palautaPisteet() , 0.01);
        
        heikki.setYhteys(hilla, 4);
        
        this.pisteet = new SitsaajanPisteet(heikki, table);
        assertEquals(4 + 1 / Math.hypot(1, 1) + 4, this.pisteet.palautaPisteet() , 0.01);
        
        heikki.setYhteys(nukke, 3);
        
        this.pisteet = new SitsaajanPisteet(heikki, table);
        assertEquals(4 + 1 / Math.hypot(1, 1) + 4 + 3 / Math.hypot(1, 2), this.pisteet.palautaPisteet() , 0.01);
        
        heikki.setYhteys(testi, -5);
        
        this.pisteet = new SitsaajanPisteet(heikki, table);
        assertEquals( 1.0 * 4 + 1 / Math.hypot(1, 1) + 4 + 3 / Math.hypot(1, 2) -  1.0 * 5 / 2, this.pisteet.palautaPisteet() , 0.01);
        
        heikki.setYhteys(tyyppi, -3);
        
        this.pisteet = new SitsaajanPisteet(heikki, table);
        assertEquals( 1.0 * 4 + 1 / Math.hypot(1, 1) + 4 + 3 / Math.hypot(1, 2) -  1.0 * 5 / 2 - 1.0 * 3/Math.hypot(1, 3), this.pisteet.palautaPisteet() , 0.01);
        
        heikki.setYhteys(vaan, 2);
        
        this.pisteet = new SitsaajanPisteet(heikki, table);
        assertEquals( 1.0 * 4 + 1 / Math.hypot(1, 1) + 4 + 3 / Math.hypot(1, 2) -  1.0 * 5 / 2 - 1.0 * 3/Math.hypot(1, 3) + 1.0 * 2 / 3, this.pisteet.palautaPisteet() , 0.01);
                
        this.pisteet = new SitsaajanPisteet(tero, table);
        assertTrue(this.pisteet.palautaPisteet() == 0);
    }

    @Test
    public void pisteidenMuodostusOnKunnollinen2() {
        this.table.addSitsaaja("Terminen Ukko");
        Sitsaaja ukko = this.table.getSitsaaja(0);
        
        this.table.addSitsaaja("Ihan Vaan");
        Sitsaaja vaan = this.table.getSitsaaja(1);
        
        this.table.addSitsaaja("Tero Merkki");
        Sitsaaja tero = this.table.getSitsaaja(2);
                
        this.table.addSitsaaja("Toinen Heikki");
        Sitsaaja heikki = this.table.getSitsaaja(3);
        
        this.table.addSitsaaja("Toinen Milla");
        Sitsaaja milla = this.table.getSitsaaja(4);
        
        this.table.addSitsaaja("Nelos hilla");
        Sitsaaja hilla = this.table.getSitsaaja(5);
        
        this.table.addSitsaaja("TestiNukke");
        Sitsaaja nukke = this.table.getSitsaaja(6);
        
        this.table.addSitsaaja("Nukke Testi");
        Sitsaaja testi = this.table.getSitsaaja(7);
        
        this.table.addSitsaaja("Joku Tyyppi");
        Sitsaaja tyyppi = this.table.getSitsaaja(8);
        
        this.table.addSitsaaja("Härö Pallo");
        Sitsaaja pallo = this.table.getSitsaaja(9);
        
        this.table.addSitsaaja("Pallo juttu");
        Sitsaaja juttu = this.table.getSitsaaja(10);
        
        this.table.addSitsaaja("Piilo Pallo");
        Sitsaaja piilo = this.table.getSitsaaja(11);
        
        nukke.setYhteys(tero, 4);
        
        this.pisteet = new SitsaajanPisteet(nukke, table);
        assertEquals(2, this.pisteet.palautaPisteet(), 0.01);
        
        nukke.setYhteys(milla, 1);
        
        this.pisteet = new SitsaajanPisteet(nukke, table);
        assertEquals(2 + 1, this.pisteet.palautaPisteet() , 0.01);
        
        nukke.setYhteys(hilla, 4);
        
        this.pisteet = new SitsaajanPisteet(nukke, table);
        assertEquals(2 + 1 + 4 / Math.hypot(1, 1), this.pisteet.palautaPisteet() , 0.01);
        
        nukke.setYhteys(heikki, 3);
        
        this.pisteet = new SitsaajanPisteet(nukke, table);
        assertEquals(2 + 1 + 4 / Math.hypot(1, 1) + 1.0 * 3 / Math.hypot(1, 2), this.pisteet.palautaPisteet() , 0.01);
        
        nukke.setYhteys(testi, -5);
        
        this.pisteet = new SitsaajanPisteet(nukke, table);
        assertEquals(2 + 1 + 4 / Math.hypot(1, 1) + 1.0 * 3 / Math.hypot(1, 2) - 5, this.pisteet.palautaPisteet() , 0.01);
        
        nukke.setYhteys(tyyppi, -3);
        
        this.pisteet = new SitsaajanPisteet(nukke, table);
        assertEquals(2 + 1 + 4 / Math.hypot(1, 1) + 1.0 * 3 / Math.hypot(1, 2) - 5 - 3.0, this.pisteet.palautaPisteet() , 0.01);
        
        nukke.setYhteys(vaan, -2);
        
        this.pisteet = new SitsaajanPisteet(nukke, table);
        assertEquals(2 + 1 + 4 / Math.hypot(1, 1) + 1.0 * 3 / Math.hypot(1, 2) - 5 - 3.0 - 2.0 / Math.hypot(1, 3), this.pisteet.palautaPisteet() , 0.01);
        
        nukke.setYhteys(pallo, -5);
        
        this.pisteet = new SitsaajanPisteet(nukke, table);
        assertEquals(2 + 1 + 4 / Math.hypot(1, 1) + 1.0 * 3 / Math.hypot(1, 2) - 5 - 3.0 - 2.0 / Math.hypot(1, 3) - 5.0 / Math.hypot(1, 1), this.pisteet.palautaPisteet() , 0.01);
        
        nukke.setYhteys(piilo, 3);
        
        this.pisteet = new SitsaajanPisteet(nukke, table);
        assertEquals(2 + 1 + 4 / Math.hypot(1, 1) + 1.0 * 3 / Math.hypot(1, 2) - 5 - 3.0 - 2.0 / Math.hypot(1, 3) - 5.0 / Math.hypot(1, 1) + 3.0 / Math.hypot(1, 2), this.pisteet.palautaPisteet() , 0.01);
                
        this.pisteet = new SitsaajanPisteet(tero, table);
        assertTrue(this.pisteet.palautaPisteet() == 0);
    }
    
    private Sitsaaja palautaYhteydellinenSitsaaja() {
        this.sitsaaja = this.table.getSitsaaja(Random.luo(this.table.kuinkaMontaSitsaajaa() - 1));

        if (this.sitsaaja.palautaYhteyksienMaara() == 0) {
            return palautaYhteydellinenSitsaaja();
        } else {
            return this.sitsaaja;
        }
    }
}