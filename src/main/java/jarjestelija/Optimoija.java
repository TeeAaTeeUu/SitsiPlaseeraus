package jarjestelija;

import Pisteyttaja.Pisteet;
import java.util.HashMap;
import sitsiplaseeraus.Poyta;
import sitsiplaseeraus.Sitsaaja;
import sitsiplaseeraus.random.Random;
import sitsiplaseeraus.random.RandomGenerator;

public class Optimoija {

    private Jarjestaja jarjestelija;
    private Poyta table;
    private Pisteet pisteet;
    private long aika;
    private double VanhassaPisteita;
    private HashMap<Sitsaaja, Integer> vanha;
    private final int sitsaajienMaara;
    private long edellinenAika;

    public Optimoija(Poyta table) {
        this.table = table;

        this.jarjestelija = new Jarjestaja();
        this.jarjestelija.setTable(this.table);

        this.pisteet = new Pisteet(this.table);
        this.vanha = new HashMap<Sitsaaja, Integer>();
        
        this.sitsaajienMaara = this.table.kuinkaMontaSitsaajaa();
    }

    public void optimoiIstumapaikat(int sekunttia) {
        this.aika = System.currentTimeMillis();
        this.edellinenAika = this.aika;

        long i = 0;
        while (aika + 1000 * sekunttia > System.currentTimeMillis()) {
            this.kokeileVaihtoa();
            i++;
            if (edellinenAika + 1000 < System.currentTimeMillis()) {
                RandomGenerator.tulostaSitsaajat(this.table);
                System.out.println(this.VanhassaPisteita = this.pisteet.palautaPisteet());
                
                this.edellinenAika = System.currentTimeMillis();
            }
        }
        RandomGenerator.tulostaSitsaajat(this.table);
        System.out.println(this.pisteet.palautaPisteet());
        System.out.println("\n" + "kuinka monta kertaa kokeiltiin paikkojen vaihtoa: " + i);
    }

    public Pisteet getPisteet() {
        return pisteet;
    }

    private void kokeileVaihtoa() {
        this.VanhassaPisteita = this.pisteet.palautaPisteet();

        this.vanha.clear();
        this.vanha.putAll(this.table.getPaikat());

        int kuinkaMontaVaihtoa = Random.luo(this.sitsaajienMaara, 1);
        for (int i = 0; i <= kuinkaMontaVaihtoa; i++) {
            this.jarjestelija.vaihdaRandom();
        }

        if (this.pisteet.palautaPisteet() < this.VanhassaPisteita) {
            this.table.setPaikat(this.vanha);
        }
    }
}
