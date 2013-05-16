package jarjestelija;

import Pisteyttaja.Pisteet;
import java.util.HashMap;
import sitsiplaseeraus.Sitsaaja;
import sitsiplaseeraus.Sitsit;
import sitsiplaseeraus.random.Random;
import sitsiplaseeraus.random.RandomGenerator;

public class Optimoija {

    private Jarjestaja jarjestelija;
    private Sitsit sitsit;
    private Pisteet pisteet;
    private long aika;
    private double VanhassaPisteita;
    private HashMap<Sitsaaja, Integer> vanhatPaikat;
    private HashMap<Sitsaaja, Integer> vanhatPoydat;
    private final int sitsaajienMaara;
    private boolean muutosOnTapahtunut;

    public Optimoija(Sitsit sitsit) {
        this.sitsit = sitsit;

        this.jarjestelija = new Jarjestaja(this.sitsit);

        this.pisteet = new Pisteet(this.sitsit);
        this.vanhatPoydat = new HashMap<Sitsaaja, Integer>();

        this.sitsaajienMaara = this.sitsit.sitsaajienMaara();
    }

    public void optimoiIstumapaikat(int sekunttia) {
        this.aika = System.currentTimeMillis();

        long i = 0;
        while (aika + 1000 * sekunttia > System.currentTimeMillis()) {
            this.kokeileVaihtoa();
            i++;
        }
        this.tulostaLoppuTietoja(i);
    }

    public Pisteet getPisteet() {
        return pisteet;
    }

    private void kokeileVaihtoa() {
        this.alustaVaihtoaVarten();

        int kuinkaMontaVaihtoa = Random.luo(this.sitsaajienMaara, 1);
        for (int i = 0; i <= kuinkaMontaVaihtoa; i++) {
            this.jarjestelija.vaihdaRandom();
        }

        this.tulostaOnnistumisviestiTaiPalautaVanha();
    }

    private void tulostaLoppuTietoja(long kuinkaMontaKokeiltiin) {
        RandomGenerator.tulostaSitsaajat(this.sitsit);

        System.out.println(this.pisteet.palautaPisteet());
        System.out.println("\n" + "kuinka monta kertaa kokeiltiin paikkojen vaihtoa: " + kuinkaMontaKokeiltiin);
    }

    private void alustaVaihtoaVarten() {
        if (this.muutosOnTapahtunut = true) {
            this.VanhassaPisteita = this.pisteet.palautaPisteet();
            this.vanhatPaikat = this.sitsit.palautaPaikat();
            this.vanhatPoydat = this.sitsit.palautaPoydat();
        }
    }

    private void tulostaOnnistumisviestiTaiPalautaVanha() {
        if (this.pisteet.palautaPisteet() < this.VanhassaPisteita) {
            this.palautaVanhat();
            
            this.muutosOnTapahtunut = false;
        } else {
            this.muutosOnTapahtunut = true;
            System.out.print(System.currentTimeMillis() + "\t" + this.pisteet.palautaPisteet() + "\t");
            System.out.println((int) (System.currentTimeMillis() - this.aika) / 1000 + " sekunttia kulunut");
        }
    }

    private void palautaVanhat() {
        for (Sitsaaja sitsaaja : this.sitsit.getSitsaajat()) {
            sitsaaja.vaihdaPaikka(this.vanhatPoydat.get(sitsaaja), this.vanhatPaikat.get(sitsaaja));
        }
    }
}
