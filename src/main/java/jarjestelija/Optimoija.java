package jarjestelija;

import Pisteyttaja.Pisteet;
import java.text.DecimalFormat;
import omatTietorakenteet.HashMap;
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
    private final DecimalFormat dtime;
    private double time;
    private boolean parempiLoytyi;
    private int kuinkaMontaVaihtoa;
    private int vaihtojenMaksimiMaara;
    private int epaOnnistumisia;

    public Optimoija(Sitsit sitsit) {
        this.sitsit = sitsit;

        this.sitsit.lisaaPaikoilleTiedotAvecinJaPuolisonPaikoista();

        this.jarjestelija = new Jarjestaja(this.sitsit);

        this.pisteet = new Pisteet(this.sitsit);

        this.sitsaajienMaara = this.sitsit.sitsaajienMaara();

        this.dtime = new DecimalFormat("#.##");
    }

    public HashMap<Paikka, Sitsaaja> optimoiIstumapaikat(int sekunttia) {
        alustaJuttuja();

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

    public double getVanhassaPisteita() {
        return VanhassaPisteita;
    }

    public Pisteet getPisteet() {
        return pisteet;
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

        System.out.println(this.pisteet.palautaPisteet());
        System.out.println("\n" + "kuinka monta kertaa kokeiltiin paikkojen vaihtoa: " + kuinkaMontaKokeiltiin);

        time = 1.0 * (System.currentTimeMillis() - this.aika) / 1000;
        time = Double.valueOf(dtime.format(time));
        System.out.println((time) + " sekunttia kului ");
    }

    private void alustaVaihtoaVarten() {
        this.VanhassaPisteita = this.pisteet.palautaPisteet();
        this.vanhatPaikat = this.sitsit.palautaPaikat();

    }

    private boolean tulostaOnnistumisviestiTaiPalautaVanha() {
        Double pisteita = this.pisteet.palautaPisteet();

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
        
        for (int i = 0; i < this.sitsaajienMaara * 3; i++) {
            this.jarjestelija.vaihdaRandom();
        }
        
        this.pisteet.alustaSitsaajat();
        
        RandomGenerator.tulostaSitsaajat(sitsit);
    }
}
