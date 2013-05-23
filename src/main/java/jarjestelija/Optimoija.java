package jarjestelija;

import Pisteyttaja.Pisteet;
import java.util.HashMap;
import sitsiplaseeraus.Paikka;
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
    private HashMap<Paikka, Sitsaaja> vanhatPaikat;
    private final int sitsaajienMaara;
    private boolean muutosOnTapahtunut;

    public Optimoija(Sitsit sitsit) {
        this.sitsit = sitsit;
        this.sitsit.lisaaPaikoilleTiedotAvecinJaPuolisonPaikoista();

        this.jarjestelija = new Jarjestaja(this.sitsit);

        this.pisteet = new Pisteet(this.sitsit);

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
        if (this.muutosOnTapahtunut = true) {
            this.alustaVaihtoaVarten();
        }

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
        this.VanhassaPisteita = this.pisteet.palautaPisteet();
        this.vanhatPaikat = this.sitsit.palautaPaikat();
    }

    private void tulostaOnnistumisviestiTaiPalautaVanha() {
        Double pisteita = this.pisteet.palautaPisteet();
        if (pisteita < this.VanhassaPisteita) {
            this.palautaVanhat();

            this.muutosOnTapahtunut = false;
        } else {
            this.muutosOnTapahtunut = true;
            System.out.print(System.currentTimeMillis() + "\t" + pisteita + "\t");
            System.out.print((int) (System.currentTimeMillis() - this.aika) / 1000 + " sekunttia kulunut ");
            System.out.println(this.pisteet.getAvecienMaara() + " avecia parina ja " + this.pisteet.getPuolisojenMaara() + " puolisoa parina");
        }
    }

    private void palautaVanhat() {
        for (Paikka paikka : this.sitsit.getPaikat()) {
            paikka.setSitsaaja(this.vanhatPaikat.get(paikka));
        }
    }
}
