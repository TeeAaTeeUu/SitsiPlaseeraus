package sitsiplaseeraus.random;

import jarjestelija.Jarjestaja;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import sitsiplaseeraus.Sitsaaja;
import sitsiplaseeraus.Sitsit;

public class RandomGeneratorTest {

    private Jarjestaja jarjestaja;
    private RandomGenerator random;
    private Sitsit Sitsit;

    public RandomGeneratorTest() {
    }

    @Before
    public void setUp() {
        this.Sitsit = new Sitsit(3);
        this.jarjestaja = new Jarjestaja(Sitsit);
        this.random = new RandomGenerator();
    }

    @Test
    public void testPalauttaaEriNimia() throws FileNotFoundException, IOException {
        RandomGenerator instance = new RandomGenerator();

        String result1 = instance.randomNimi();
        System.out.println(result1);

        String result2 = instance.randomNimi();
        System.out.println(result2);

        assertEquals(false, result1.equals(result2));
    }

    @Test
    public void randomDatanLisaysTuottaaJotain() {
        this.random.taytaRandomDatalla(16, 40, this.Sitsit);

        assertTrue(Sitsit.getSitsaaja(7).getNimi().length() >= 3);
    }

    @Test
    public void randomDatanLisaysTuottaaOikeanMaaranSitsaajia() {
        this.random.taytaRandomDatalla(50, 300, Sitsit);

        assertEquals(50, Sitsit.sitsaajienMaara());
    }

    @Test
    public void randomDatanLisaysToimiiMyosSarjatuotantonaSuuressaMittakaavassa() {
        int monesko = 0;
        for (int i = 0; i < 1000; i++) {
            this.Sitsit = new Sitsit(3);
            this.jarjestaja = new Jarjestaja(Sitsit);
            
            this.random = new RandomGenerator();
            this.random.taytaRandomDatalla(200, 5000, Sitsit);

            assertEquals(5000, Sitsit.yhteyksienMaara());
            assertEquals(200, Sitsit.sitsaajienMaara());
        }
    }
}