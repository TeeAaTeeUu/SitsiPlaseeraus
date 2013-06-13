package jarjestelija;

import Pisteyttaja.Pisteet;
import java.text.DecimalFormat;
import omatTietorakenteet.ArrayList;
import omatTietorakenteet.Hakemisto;
import sitsiplaseeraus.Paikka;
import sitsiplaseeraus.Sitsaaja;
import sitsiplaseeraus.Sitsit;
import sitsiplaseeraus.random.Random;
import sitsiplaseeraus.random.RandomGenerator;

/**
 * Pyrkii löytämään mahdollisimman nopeasti ja mahdollisimman hyvän
 * järjestyksen.
 */
public class Optimoija {

    private Jarjestaja jarjestelija;
    private Sitsit sitsit;
    private Pisteet pisteet;
    private ArrayList<Paikka> paikat;
    private long aika;
    private double VanhassaPisteita;
    private Hakemisto<Paikka, Sitsaaja> vanhatPaikat;
    private final int sitsaajienMaara;
    private boolean muutosOnTapahtunut;
    private final DecimalFormat dtime;
    private double time;
    private boolean parempiLoytyi;
    private int kuinkaMontaVaihtoa;
    private int vaihtojenMaksimiMaara;
    private int epaOnnistumisia;
    private SukupuoliJarjestaja sukupuoliJarjestaja;
    private AlkuSijoittaja alkuSijoittaja;
    private boolean vainParitJaSukupuolet;

    /**
     * Alustaa olion käyttöön.
     *
     * @param sitsit
     */
    public Optimoija(Sitsit sitsit) {
        this.sitsit = sitsit;
        this.sitsit.lisaaPaikoilleTiedotAvecinJaPuolisonPaikoista();

        this.jarjestelija = new Jarjestaja(this.sitsit);

        this.pisteet = new Pisteet(this.sitsit);

        this.sitsaajienMaara = this.sitsit.sitsaajienMaara();

        this.dtime = new DecimalFormat("#.##");

        this.sukupuoliJarjestaja = new SukupuoliJarjestaja(sitsit);

        this.alkuSijoittaja = new AlkuSijoittaja(sitsit);
        this.paikat = new ArrayList<Paikka>();
    }

    /**
     * Käy läpi tietyllä toivottavasti fiksulla tavalla erinäisiä järjestyksiä
     * läpi ja jatkaa siihen mennessä parhaasta löydetystä aina eteenpäin.
     *
     * @param sekunttia
     * @return tämän haun parhaan järjestyksen.
     */
    public Hakemisto<Paikka, Sitsaaja> optimoiIstumapaikat(int sekunttia, boolean vainParitJaSukupuolet) {
        alustaJuttuja();

        this.vainParitJaSukupuolet = vainParitJaSukupuolet;

        this.aika = System.currentTimeMillis();

        this.vaihtojenMaksimiMaara = 2;
        long i = 0;

        while (aika + 1000 * sekunttia > System.currentTimeMillis()) {
            if (this.kokeileVaihtoa()) {
                this.epaOnnistumisia = 0;
            } else {
                this.epaOnnistumisia++;
            }
            if (epaOnnistumisia == sitsaajienMaara * sitsaajienMaara * 10) {
                if (parempiLoytyi == true) {
                    do {
                        vaihdaKaikki();
                    } while (parempiLoytyi == true);
                    this.epaOnnistumisia = 0;
                } else {
                    this.tulostaLoppuTietoja(i);
                    return this.vanhatPaikat;
                }
            }
            i++;
        }
        return this.vanhatPaikat;
    }

    /**
     * Palauttaa tähän mennessä parhaan pisteet.
     *
     * @return pisteitä tähän mennessä parhaassa.
     */
    protected double getVanhassaPisteita() {
        return VanhassaPisteita;
    }

    private boolean kokeileVaihtoa() {
        if (this.muutosOnTapahtunut = true) {
            this.alustaVaihtoaVarten();
        }

        this.kuinkaMontaVaihtoa = Random.luo(this.vaihtojenMaksimiMaara, 1);
        for (int i = 0; i <= kuinkaMontaVaihtoa; i++) {
            this.jarjestelija.vaihdaRandom();
        }

        return this.tulostaOnnistumisviestiTaiPalautaVanha();
    }

    private void tulostaLoppuTietoja(long kuinkaMontaKokeiltiin) {
        RandomGenerator.tulostaSitsaajat(this.sitsit);

        if (vainParitJaSukupuolet) {
            System.out.println(this.pisteet.palautaSukupuoliJaPariPisteet());
        } else {
            System.out.println(this.pisteet.palautaPisteet());
        }
        System.out.println("\n" + "kuinka monta kertaa kokeiltiin paikkojen vaihtoa: " + kuinkaMontaKokeiltiin);

        time = 1.0 * (System.currentTimeMillis() - this.aika) / 1000;
        time = Double.valueOf(dtime.format(time));
        System.out.println((time) + " sekunttia kului ");
    }

    private void alustaVaihtoaVarten() {
        if (vainParitJaSukupuolet) {
            this.VanhassaPisteita = this.pisteet.palautaSukupuoliJaPariPisteet();
        } else {
            this.VanhassaPisteita = this.pisteet.palautaPisteet();
        }
        
        this.vanhatPaikat = this.sitsit.palautaPaikkaSitsaajaParit();

    }

    private boolean tulostaOnnistumisviestiTaiPalautaVanha() {
        Double pisteita;
        if (vainParitJaSukupuolet) {
            pisteita = this.pisteet.palautaSukupuoliJaPariPisteet();
        } else {
            pisteita = this.pisteet.palautaPisteet();
        }

        if (Double.isInfinite(pisteita)) {
            System.out.println("virhe");
        }

        if (pisteita < this.VanhassaPisteita) {

            this.palautaVanhat();
            this.muutosOnTapahtunut = false;
            return false;
        } else if (pisteita > this.VanhassaPisteita) {
            this.muutosOnTapahtunut = true;
            tulostustaTiedot(pisteita);

            this.parempiLoytyi = true;
            return true;
        }

        return false;
    }

    private void palautaVanhat() {
        for (Paikka paikka : this.sitsit.getPaikat()) {
            paikka.setSitsaaja(this.vanhatPaikat.get(paikka));
        }
    }

    private void vaihdaKaikki() {
        System.out.println("ollaan loopissa");
        this.vaihdaJarjestyksessä();
        System.out.println("tultiin ulos");
    }

    private void vaihdaJarjestyksessä() {
        int montakoLoytyi = 0;
        for (Paikka paikka : sitsit.getPaikat()) {
            for (Paikka kohdePaikka : sitsit.getPaikat()) {
                if (paikka.equals(kohdePaikka) == false) {
                    if (this.muutosOnTapahtunut = true) {
                        this.alustaVaihtoaVarten();
                    }
                    jarjestelija.vaihdaPaikat(paikka, kohdePaikka);
                    if (this.tulostaOnnistumisviestiTaiPalautaVanha()) {
                        montakoLoytyi++;
                        epaOnnistumisia = 0;
                    }
                }
            }
        }
        if (montakoLoytyi == 0) {
            this.parempiLoytyi = false;
        }
    }

    private void tulostustaTiedot(Double pisteita) {
        time = 1.0 * (System.currentTimeMillis() - this.aika) / 1000;

        time = Double.valueOf(dtime.format(time));
        System.out.print(pisteita + "\t");
        System.out.print((time) + " sekunttia kulunut ");
        System.out.print(this.pisteet.getAvecienMaara() + " avecia parina ja " + this.pisteet.getPuolisojenMaara() + " puolisoa parina");
        System.out.print(" ja " + kuinkaMontaVaihtoa + " vaihtoa tehtiin kun vaihtojenmaksimaara oli " + this.vaihtojenMaksimiMaara);
        System.out.println(" paripisteitä " + this.pisteet.getPariPisteet() + " sukupuolipisteitä " + this.pisteet.getSukupuoliPisteet() + " yhteyspisteitä " + this.pisteet.getYhteysPisteet());
    }

    private void alustaJuttuja() {
        this.parempiLoytyi = true;
        this.epaOnnistumisia = 0;

        sijoitaAlkupaikat();

        this.pisteet.alustaSitsaajat();

        RandomGenerator.tulostaSitsaajat(sitsit);
    }

    private void sijoitaAlkupaikat() {
//        sukupuoliJarjestaja.jarjestaPaikatSukupuolittain();
//        
//        this.alkuSijoittaja.asetaAlkupaikat();

        for (int i = 0; i < sitsaajienMaara * 10; i++) {
            jarjestelija.vaihdaRandom();
        }
    }

    public Pisteet getPisteet() {
        return pisteet;
    }
}
