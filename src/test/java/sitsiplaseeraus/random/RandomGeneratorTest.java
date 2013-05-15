package sitsiplaseeraus.random;

import jarjestelija.Jarjestaja;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import sitsiplaseeraus.Poyta;
import sitsiplaseeraus.Sitsaaja;

public class RandomGeneratorTest {

    private Jarjestaja jarjestaja;
    private RandomGenerator random;
    private Poyta table;

    public RandomGeneratorTest() {
    }

    @Before
    public void setUp() {
        this.jarjestaja = new Jarjestaja();
        this.table = jarjestaja.getTable();
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
        this.random.taytaRandomDatalla(16, 40, this.table);

//        tulostaSitsaajat();
//        tulostaYhteydet();

        assertTrue(table.getSitsaaja(7).getNimi().length() >= 3);
    }

    @Test
    public void randomDatanLisaysTuottaaOikeanMaaranSitsaajia() {
        this.random.taytaRandomDatalla(50, 300, table);

//        tulostaSitsaajat(table);
//        tulostaYhteydet(table);

        assertEquals(50, table.sitsaajienMaara());
    }

    @Test
    public void randomDatanLisaysToimiiMyosSarjatuotantonaSuuressaMittakaavassa() {
        int monesko = 0;
        for (int i = 0; i < 1000; i++) {
            this.jarjestaja = new Jarjestaja();
            this.table = jarjestaja.getTable();
            this.random = new RandomGenerator();
            this.random.taytaRandomDatalla(200, 5000, table);
            
            assertEquals(5000, table.yhteyksienMaara());
            assertEquals(200, table.sitsaajienMaara());
        }
    }
}