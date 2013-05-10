package Pisteyttaja;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import sitsiplaseeraus.Poyta;
import sitsiplaseeraus.Sitsaaja;
import sitsiplaseeraus.random.Random;
import sitsiplaseeraus.random.RandomGenerator;
import sitsiplaseeraus.random.RandomGeneratorTest;

public class PisteetTest {

    private RandomGenerator random;
    private Poyta table;
    private Sitsaaja sitsaaja;
    private Pisteet pisteet;

    public PisteetTest() {
    }

    @Before
    public void setUp() {
        this.random = new RandomGenerator();
        this.table = new Poyta();
    }

    @Test
    public void testPalautaPisteetToimii() {
        double luku = 0;

        for (int i = 0; i < 1000; i++) {
            this.table = new Poyta();
            random.taytaRandomDatalla(10, 30, this.table);

            this.pisteet = new Pisteet(this.table);
            double pisteet = this.pisteet.palautaPisteet();
            if (pisteet != 0) {
                luku = pisteet;
            }

            assertTrue(this.pisteet.onkoYhteyksia());
            assertTrue(luku != 0);
        }
    }
}