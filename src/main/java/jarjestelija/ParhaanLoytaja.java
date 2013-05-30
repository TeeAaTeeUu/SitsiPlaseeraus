package jarjestelija;

import omatTietorakenteet.HashMap;
import java.util.Map;
import omatTietorakenteet.Vektori;
import sitsiplaseeraus.Paikka;
import sitsiplaseeraus.Sitsaaja;
import sitsiplaseeraus.Sitsit;
import sitsiplaseeraus.random.RandomGenerator;

public class ParhaanLoytaja {

    private Optimoija optimoija;
    private HashMap<Paikka, Sitsaaja> ajonParhaatPaikat;
    private HashMap<Integer, HashMap> parhaatPoydat;
    private HashMap<Integer, HashMap> ekatPoydat;
    private long aika;
    private double parhaanPisteet;
    private final Sitsit sitsit;
    private HashMap<Integer, Sitsaaja> ekatPaikatPoydassa;
    private HashMap<Integer, Paikka> kohdePaikat;
    private double ajossaPisteet;

    public ParhaanLoytaja(Sitsit sitsit) {
        this.sitsit = sitsit;
        this.optimoija = new Optimoija(sitsit);
        this.parhaatPoydat = new HashMap<Integer, HashMap>();
        this.ekatPoydat = new HashMap<Integer, HashMap>();
        this.parhaanPisteet = 0.0;

        asetaLopetusHook();
    }

    public void optimoiIstumapaikat(int sekunttia) {
        this.aika = System.currentTimeMillis();

        RandomGenerator.tulostaSitsaajat(sitsit);

        tallennaMuistiin(this.sitsit.palautaPaikat(), this.ekatPoydat);

        while (aika + 1000 * sekunttia > System.currentTimeMillis()) {
            this.ajonParhaatPaikat = optimoija.optimoiIstumapaikat(sekunttia);
            this.ajossaPisteet = this.optimoija.getVanhassaPisteita();
            if (this.parhaanPisteet < this.ajossaPisteet) {
                tallennaMuistiin(this.ajonParhaatPaikat, this.parhaatPoydat);
            }
            palautaEkatPaikat(this.ekatPoydat);

            System.out.println("tähän mennessä paras on saanut pisteitä " + this.parhaanPisteet);
        }
        palautaEkatPaikat(this.parhaatPoydat);

        RandomGenerator.tulostaSitsaajat(sitsit);

        System.out.println("löydettiin lopulta sellainen, joka sai pisteitä " + this.parhaanPisteet);
    }

    private void tallennaMuistiin(HashMap<Paikka, Sitsaaja> ajonParhaatPaikat, HashMap<Integer, HashMap> parhaatPoydat) {
        parhaatPoydat.clear();

        for (Vektori<Paikka, Sitsaaja> paikka : ajonParhaatPaikat) {
            if (parhaatPoydat.containsKey(paikka.getKey().getPoyta()) == false) {
                parhaatPoydat.put(paikka.getKey().getPoyta(), new HashMap<Integer, Sitsaaja>());
            }
            parhaatPoydat.get(paikka.getKey().getPoyta()).put(paikka.getKey().getPaikka(), paikka.getValue());
        }
        this.parhaanPisteet = this.ajossaPisteet;
    }

    private void palautaEkatPaikat(HashMap<Integer, HashMap> ekatPoydat) {
        for (Vektori<Integer, HashMap> poyta : ekatPoydat) {
            kohdePaikat = this.sitsit.palautaPoydanPaikat(poyta.getKey());
            ekatPaikatPoydassa = poyta.getValue();

            for (Vektori<Integer, Sitsaaja> paikka : this.ekatPaikatPoydassa) {
                kohdePaikat.get(paikka.getKey()).setSitsaaja(paikka.getValue());
            }
        }
        System.out.println("\n" + "\n" + "\n" + "vanhat Palautettu" + "\n" + "\n" + "\n");
    }

    private void asetaLopetusHook() {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                palautaEkatPaikat(parhaatPoydat);
                
                System.out.println("-- jotain rajaa ---");

                RandomGenerator.tulostaSitsaajat(sitsit);

                System.out.println("löydettiin lopulta sellainen, joka sai pisteitä " + parhaanPisteet);
            }
        });
    }
}
