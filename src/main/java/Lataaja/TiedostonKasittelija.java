package Lataaja;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import omatTietorakenteet.ArrayList;
import omatTietorakenteet.HashMap;
import java.util.Map;
import omatTietorakenteet.Vektori;
import sitsiplaseeraus.Sitsaaja;

public class TiedostonKasittelija {

    private final String file;
    private Sitsaaja sitsaaja;
    private HashMap<Sitsaaja, HashMap> yhteydet;
    private HashMap<String, Sitsaaja> sitsaajat;
    private String[] tiedot;
    private HashMap<Sitsaaja, Integer> yhteys;
    private Sitsaaja kohdeSitsaaja;
    private HashMap<Sitsaaja, Integer> valiaikainenMap;
    private HashMap<Sitsaaja, HashMap> palautettavaMap;
    private ArrayList<Sitsaaja> sitsaajatLista;
    private int poytienMaara;
    private ArrayList<Integer> poytienKoot;
    private int arvo;
    private int index;

    public TiedostonKasittelija(String file) {
        this.file = file;
        this.alustaLuokat();
    }

    public HashMap<Sitsaaja, HashMap> getYhteydet() {
        return yhteydet;
    }

    public ArrayList<Sitsaaja> getSitsaajat() {
        sitsaajatLista.clear();
        for (Vektori<String, Sitsaaja> sitsaaja : sitsaajat) {
            sitsaajatLista.add(sitsaaja.getValue());
            if(sitsaaja.getValue().yhteyksienMaara() == 0)
                System.out.println("yhteyksiä nolla");
        }
        return sitsaajatLista;
    }

    public int getPoytienMaara() {
        return poytienMaara;
    }

    public ArrayList<Integer> getPoytienKoot() {
        return poytienKoot;
    }

    /**
     * http://www.mkyong.com/java/how-to-read-and-parse-csv-file-in-java/
     */
    public void run() {
        yhteydet.clear();
        sitsaajat.clear();


        String csvFile = file;
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
        try {
            br = new BufferedReader(new FileReader(csvFile));
        } catch (FileNotFoundException ex) {
            System.out.println("tiedostoa ei löytynyt");
            System.exit(0);
        }
        try {

            //poytien määrä
            System.out.println("pöytien määrä");
            while ((line = br.readLine()) != null && line.equals("--sitsaajat--") == false) {

                tiedot = line.split(cvsSplitBy);
                this.poytienMaara = Integer.parseInt(tiedot[0]);

                for (int i = 0; i < getPoytienMaara(); i++) {
                    this.poytienKoot.add((int) Integer.parseInt(tiedot[i + 1]));
                }
            }

            //sitsaajat
            System.out.println("sitsaajat");
            while ((line = br.readLine()) != null && line.equals("--avecit--") == false) {

                tiedot = line.split(cvsSplitBy);
                sitsaaja = lisaaSitsaaja(tiedot[0], tiedot[1]);

                sitsaajat.put(sitsaaja.getNimi(), sitsaaja);
            }

            //avecit
            System.out.println("avecit");
            while ((line = br.readLine()) != null && line.equals("--puolisot--") == false) {

                tiedot = line.split(cvsSplitBy);
                sitsaaja = sitsaaja(tiedot[0]);
                kohdeSitsaaja = sitsaaja(tiedot[1]);

                sitsaaja.setAvec(kohdeSitsaaja);
            }

            //puolisot
            System.out.println("puolisot");
            while ((line = br.readLine()) != null && line.equals("--yhteydet--") == false) {

                tiedot = line.split(cvsSplitBy);
                sitsaaja = sitsaaja(tiedot[0]);
                kohdeSitsaaja = sitsaaja(tiedot[1]);

                sitsaaja.setPuoliso(kohdeSitsaaja);
            }

            //yhteydet
            System.out.println("yhteydet");
            for (String string : tiedot) {
                
            }
            while ((line = br.readLine()) != null) {

                tiedot = line.split(cvsSplitBy);

                sitsaaja = sitsaaja(tiedot[0]);
                kohdeSitsaaja = sitsaaja(tiedot[1]);
                arvo = Integer.parseInt(tiedot[2]);

                if (sitsaajat.containsValue(sitsaaja)) {
                    sitsaajat.get(tiedot[0]).setYhteys(kohdeSitsaaja, arvo);
                    if(sitsaajat.get(tiedot[0]).yhteyksienMaara() == 0)
                        System.out.println("yhteyden muodostus epäonnostui");
                } else {
                    System.out.println("virhe");
                }
            }

        } catch (IOException ex) {
            System.out.println("hässäkkää");
        }

        System.out.println("Tiedoston luku onnistui");
    }

    private Sitsaaja lisaaSitsaaja(String nimi, String sukupuoli2) {
        int sukupuoli = sukupuoli(sukupuoli2);
        if (sukupuoli == 0) {
            return new Sitsaaja(tiedot[0]);
        } else {
            if (sukupuoli == 1) {
                return new Sitsaaja(tiedot[0], true);
            } else {
                return new Sitsaaja(tiedot[0], false);
            }
        }
    }

    private Sitsaaja sitsaaja(String nimi) {
        return sitsaajat.get(nimi);
    }

    private int sukupuoli(String tieto) {
        if (tieto.equals("1")) {
            return 1;
        } else if (tieto.equals("-1")) {
            return -1;
        } else {
            return 0;
        }
    }

    private void alustaLuokat() {
        yhteydet = new HashMap<Sitsaaja, HashMap>();
        sitsaajat = new HashMap<String, Sitsaaja>();
        yhteys = new HashMap<Sitsaaja, Integer>();
        valiaikainenMap = new HashMap<Sitsaaja, Integer>();
        palautettavaMap = new HashMap<Sitsaaja, HashMap>();
        sitsaajatLista = new ArrayList<Sitsaaja>();
        poytienKoot = new ArrayList<Integer>();
    }
}
