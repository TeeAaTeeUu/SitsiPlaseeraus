package jarjestelija;

import omatTietorakenteet.ArrayList;
import sitsiplaseeraus.Paikka;
import sitsiplaseeraus.Sitsaaja;
import sitsiplaseeraus.Sitsit;
import sitsiplaseeraus.random.Random;

class AlkuSijoittaja {

    private Sitsit sitsit;
    private ArrayList<Sitsaaja> sitsaajat;
    private ArrayList<Sitsaaja> miehet;
    private ArrayList<Sitsaaja> naiset;
    private ArrayList<Paikka> paikat;
    private ArrayList<Paikka> miehenPaikat;
    private ArrayList<Paikka> naisenPaikat;
    private Paikka paikka;
    private ArrayList<Sitsaaja> asetetutSitsaajat;
    private ArrayList<Paikka> asetetutPaikat;

    public AlkuSijoittaja(Sitsit sitsit) {
        this.sitsit = sitsit;

        alustaMuuttujat();
    }

    public void asetaAlkupaikat() {
        toinenAlustus();

        asetaSamanSukupuolenParit();
        asetaEriSukupuolenParit();

        asetaLoputSitsaajat();
    }

    private void alustaMuuttujat() {
        this.sitsaajat = sitsit.getSitsaajat();
        this.miehet = new ArrayList<Sitsaaja>();
        this.naiset = new ArrayList<Sitsaaja>();

        for (Sitsaaja sitsaaja : sitsaajat) {
            if (sitsaaja.isMies()) {
                miehet.add(sitsaaja);
            } else {
                naiset.add(sitsaaja);
            }
        }

        this.paikat = sitsit.getPaikat();
        this.miehenPaikat = new ArrayList<Paikka>();
        this.naisenPaikat = new ArrayList<Paikka>();

        this.asetetutSitsaajat = new ArrayList<Sitsaaja>();
        this.asetetutPaikat = new ArrayList<Paikka>();
    }

    private void toinenAlustus() {
        naisenPaikat.clear();
        miehenPaikat.clear();

        for (Paikka paikka : paikat) {
            if (paikka.isMiehenPaikka()) {
                miehenPaikat.add(paikka);
            } else {
                naisenPaikat.add(paikka);
            }
        }
        asetetutSitsaajat.clear();
        asetetutPaikat.clear();
    }

    private void asetaAvecPari(Sitsaaja sitsaaja, boolean samaaSukupuolta) {
        boolean jatkuu = true;
        if (onkoMies(sitsaaja)) {
            do {
                paikka = miehenPaikat.get(Random.luo(miehenPaikat.size() - 1));

                if (paikka.isMiehenPaikka()) {
                    if (paikka.getMiehenAvecinPaikka() != null && paikka.getMiehenAvecinPaikka().isMiehenPaikka()) {
                        System.out.println("löytyi mies mies");
                    }
                    if (paikka.getNaisenAvecinPaikka() != null && paikka.getNaisenAvecinPaikka().isMiehenPaikka()) {
                        System.out.println("löytyi mies mies");
                    }
                    if (paikka.getPuolisonPaikka() != null && paikka.getPuolisonPaikka().isMiehenPaikka()) {
                        System.out.println("löytyi mies mies puoliso");
                    }
                }
                if (josMiehenVieressaOnMyosTilaa(paikka, sitsaaja, samaaSukupuolta)) {
                    jatkuu = false;
                }
            } while (jatkuu);
        } else {
            do {
                paikka = naisenPaikat.get(Random.luo(naisenPaikat.size() - 1));

                if (paikka.isMiehenPaikka() == false) {
                    if (paikka.getMiehenAvecinPaikka() != null && paikka.getMiehenAvecinPaikka().isMiehenPaikka() == false) {
                        System.out.println("löytyi nais nais");
                    }
                    if (paikka.getNaisenAvecinPaikka() != null && paikka.getNaisenAvecinPaikka().isMiehenPaikka() == false) {
                        System.out.println("löytyi nais nais");
                    }
                    if (paikka.getPuolisonPaikka() != null && paikka.getPuolisonPaikka().isMiehenPaikka() == false) {
                        System.out.println("löytyi nais nais puoliso");
                    }
                }

                if (josNaisenVieressaOnMyosTilaa(paikka, sitsaaja, samaaSukupuolta)) {
                    jatkuu = false;
                }
            } while (jatkuu);
        }
    }

    private boolean onkoMies(Sitsaaja sitsaaja) {
        if (sitsaaja.isMies()) {
            return true;
        } else {
            return false;
        }
    }

    private boolean josMiehenVieressaOnMyosTilaa(Paikka paikka, Sitsaaja sitsaaja, boolean samaaSukupuolta) {
        if (samaaSukupuolta) {
            return josVieressaOnSamaaSukupuolta(paikka, sitsaaja);
        } else {
            return josVieressaOnEriSukupuolta(paikka, sitsaaja);
        }
    }

    private boolean josNaisenVieressaOnMyosTilaa(Paikka paikka, Sitsaaja sitsaaja, boolean samaaSukupuolta) {
        if (samaaSukupuolta) {
            return josVieressaOnSamaaSukupuolta(paikka, sitsaaja);
        } else {
            return josVieressaOnEriSukupuolta(paikka, sitsaaja);
        }
    }

    private void asetaPuolisoPari(Sitsaaja sitsaaja, boolean samaaSukupuolta) {
        boolean jatkuu = true;
        if (onkoMies(sitsaaja)) {
            do {
                paikka = miehenPaikat.get(Random.luo(miehenPaikat.size() - 1));

                if (josVastapaataOnMyosMiehenPaikka(paikka, sitsaaja, samaaSukupuolta)) {
                    jatkuu = false;
                }
            } while (jatkuu);
            miehenPaikat.remove(paikka);
        } else {
            do {
                paikka = naisenPaikat.get(Random.luo(naisenPaikat.size() - 1));

                if (josVastapaataOnMyosNaisenPaikka(paikka, sitsaaja, samaaSukupuolta)) {
                    jatkuu = false;
                }
            } while (jatkuu);
            naisenPaikat.remove(paikka);
        }
    }

    private boolean josVieressaOnEriSukupuolta(Paikka paikka, Sitsaaja sitsaaja) {
        if (sitsaaja.isMies() && paikka.getMiehenAvecinPaikka() != null && paikka.getMiehenAvecinPaikka().isMiehenPaikka() != sitsaaja.isMies()) {
            System.out.println("hee");
            paikka.setSitsaaja(sitsaaja);
            paikka.getMiehenAvecinPaikka().setSitsaaja(sitsaaja.getAvec());

            asetaMiehenAvecPaikatLaitetuksi(paikka);

            return true;
        } else if (sitsaaja.isMies() == false && paikka.getNaisenAvecinPaikka() != null && paikka.getNaisenAvecinPaikka().isMiehenPaikka() != sitsaaja.isMies()) {
            System.out.println("hee2");
            paikka.setSitsaaja(sitsaaja);
            paikka.getNaisenAvecinPaikka().setSitsaaja(sitsaaja.getAvec());

            asetaNaisenAvecPaikatLaitetuksi(paikka);

            return true;
        }
        return false;
    }

    private boolean josVieressaOnSamaaSukupuolta(Paikka paikka, Sitsaaja sitsaaja) {
        if (paikka.getMiehenAvecinPaikka() != null && paikka.getMiehenAvecinPaikka().isMiehenPaikka() == sitsaaja.isMies()) {
            System.out.println("joo");
            paikka.setSitsaaja(sitsaaja);
            paikka.getMiehenAvecinPaikka().setSitsaaja(sitsaaja.getAvec());

            asetaMiehenAvecPaikatLaitetuksi(paikka);

            return true;
        } else if (paikka.getNaisenAvecinPaikka() != null && paikka.getNaisenAvecinPaikka().isMiehenPaikka() == sitsaaja.isMies()) {
            System.out.println("joo2");
            paikka.setSitsaaja(sitsaaja);
            paikka.getNaisenAvecinPaikka().setSitsaaja(sitsaaja.getAvec());

            asetaNaisenAvecPaikatLaitetuksi(paikka);

            return true;
        }
        return false;
    }

    private boolean josVastapaataOnMyosMiehenPaikka(Paikka paikka, Sitsaaja sitsaaja, boolean samaaSukupuolta) {
        if (samaaSukupuolta) {
            return josVastapaataOnMyosSamaaSukupuolta(paikka, sitsaaja);
        } else {
            return josVastapaataOnMyosEriSukupuolta(paikka, sitsaaja);
        }
    }

    private boolean josVastapaataOnMyosNaisenPaikka(Paikka paikka, Sitsaaja sitsaaja, boolean samaaSukupuolta) {
        if (samaaSukupuolta) {
            return josVastapaataOnMyosSamaaSukupuolta(paikka, sitsaaja);
        } else {
            return josVastapaataOnMyosEriSukupuolta(paikka, sitsaaja);
        }
    }

    private boolean josVastapaataOnMyosSamaaSukupuolta(Paikka paikka, Sitsaaja sitsaaja) {
        if (paikka != null && paikka.getPuolisonPaikka() != null && paikka.getPuolisonPaikka().isMiehenPaikka() == sitsaaja.isMies()) {
            paikka.setSitsaaja(sitsaaja);
            paikka.getPuolisonPaikka().setSitsaaja(sitsaaja.getPuoliso());

            asetaPuolisonPaikatLaitetuksi(paikka);

            return true;
        }
        return false;
    }

    private boolean josVastapaataOnMyosEriSukupuolta(Paikka paikka, Sitsaaja sitsaaja) {
        if (paikka != null && paikka.getPuolisonPaikka() != null && paikka.getPuolisonPaikka().isMiehenPaikka() != sitsaaja.isMies()) {
            paikka.setSitsaaja(sitsaaja);
            paikka.getPuolisonPaikka().setSitsaaja(sitsaaja.getPuoliso());

            asetaPuolisonPaikatLaitetuksi(paikka);

            return true;
        }
        return false;
    }

    private void asetaSamaaSukupuoltaAvecPariJosEiJoAsetettu(Sitsaaja sitsaaja) {
        if (asetetutSitsaajat.contains(sitsaaja) == false) {
            asetaAvecPari(sitsaaja, true);
            asetaAvecPariLaitetuksi(sitsaaja);
        }
    }

    private void asetaSamaaSukupuoltaPuolisoPariJosEiJoAsetettu(Sitsaaja sitsaaja) {
        if (asetetutSitsaajat.contains(sitsaaja) == false) {
            asetaPuolisoPari(sitsaaja, true);
            asetaPuolisoPariLaitetuksi(sitsaaja);
        }
    }

    private void asetaEriSukupuoltaAvecPariJosEiJoAsetettu(Sitsaaja sitsaaja) {
        if (asetetutSitsaajat.contains(sitsaaja) == false) {
            asetaAvecPari(sitsaaja, false);
            asetaAvecPariLaitetuksi(sitsaaja);
        }
    }

    private void asetaEriSukupuoltaPuolisoPariJosEiJoAsetettu(Sitsaaja sitsaaja) {
        if (asetetutSitsaajat.contains(sitsaaja) == false) {
            asetaPuolisoPari(sitsaaja, false);
            asetaPuolisoPariLaitetuksi(sitsaaja);
        }
    }

    private void asetaAvecPariLaitetuksi(Sitsaaja sitsaaja) {
        this.asetetutSitsaajat.add(sitsaaja);
        this.asetetutSitsaajat.add(sitsaaja.getAvec());
    }

    private void asetaPuolisoPariLaitetuksi(Sitsaaja sitsaaja) {
        this.asetetutSitsaajat.add(sitsaaja);
        this.asetetutSitsaajat.add(sitsaaja.getPuoliso());
    }

    private void asetaMiehenAvecPaikatLaitetuksi(Paikka paikka) {
        asetetutPaikat.add(paikka);
        asetetutPaikat.add(paikka.getMiehenAvecinPaikka());
    }

    private void asetaNaisenAvecPaikatLaitetuksi(Paikka paikka) {
        asetetutPaikat.add(paikka);
        asetetutPaikat.add(paikka.getNaisenAvecinPaikka());
    }

    private void asetaPuolisonPaikatLaitetuksi(Paikka paikka) {
        asetetutPaikat.add(paikka);
        asetetutPaikat.add(paikka.getPuolisonPaikka());
    }

    private void asetaSamanSukupuolenParit() {
        for (Sitsaaja sitsaaja : sitsaajat) {
            if (sitsaaja.avecIsSet() && sitsaaja.isMies() == sitsaaja.getAvec().isMies()) {
                System.out.println("avec");
                asetaSamaaSukupuoltaAvecPariJosEiJoAsetettu(sitsaaja);
            } else if (sitsaaja.puolisoIsSet() && sitsaaja.isMies() == sitsaaja.getPuoliso().isMies()) {
                System.out.println("puoliso");
                asetaSamaaSukupuoltaPuolisoPariJosEiJoAsetettu(sitsaaja);
            }
        }
    }

    private void asetaEriSukupuolenParit() {
        for (Sitsaaja sitsaaja : sitsaajat) {
            if (sitsaaja.avecIsSet() && sitsaaja.isMies() != sitsaaja.getAvec().isMies()) {
                asetaEriSukupuoltaAvecPariJosEiJoAsetettu(sitsaaja);
            } else if (sitsaaja.puolisoIsSet() && sitsaaja.isMies() != sitsaaja.getPuoliso().isMies()) {
                asetaEriSukupuoltaPuolisoPariJosEiJoAsetettu(sitsaaja);
            }
        }
    }

    private void asetaLoputSitsaajat() {
        for (int i = 0; i < asetetutSitsaajat.size(); i++) {
            sitsaajat.remove(i);
        }
        miehet.clear();
        naiset.clear();

        for (Sitsaaja sitsaaja : sitsaajat) {
            if (sitsaaja.isMies()) {
                miehet.add(sitsaaja);
            } else {
                naiset.add(sitsaaja);
            }
        }

        for (int i = 0; i < asetetutPaikat.size(); i++) {
            paikat.remove(i);
        }

        naisenPaikat.clear();
        miehenPaikat.clear();

        for (Paikka paikka : paikat) {
            if (paikka.isMiehenPaikka()) {
                miehenPaikat.add(paikka);
            } else {
                naisenPaikat.add(paikka);
            }
        }

        int monesko;
        for (Sitsaaja sitsaaja : miehet) {
            do {
                monesko = Random.luo(miehenPaikat.size() - 1);
                paikka = miehenPaikat.get(monesko);
            } while (sitsaaja.isMies() == paikka.isMiehenPaikka());

            paikka.setSitsaaja(sitsaaja);
            miehenPaikat.remove(monesko);
        }

        for (Sitsaaja sitsaaja : naiset) {
            do {
                monesko = Random.luo(naisenPaikat.size() - 1);
                paikka = naisenPaikat.get(monesko);
            } while (sitsaaja.isMies() == paikka.isMiehenPaikka());

            paikka.setSitsaaja(sitsaaja);
            naisenPaikat.remove(monesko);
        }
    }
}
