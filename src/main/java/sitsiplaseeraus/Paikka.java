package sitsiplaseeraus;

public class Paikka {

    private int paikka;
    private int poyta;
    private Sitsaaja sitsaaja;
    private Paikka naisenAvecinPaikka;
    private Paikka puolisonPaikka;
    private Paikka miehenAvecinPaikka;

    public Paikka(int poyta, int paikka) {
        this.setPoyta(poyta);
        this.setPaikka(paikka);
    }
    
    public int getPaikka() {
        return paikka;
    }

    public void setPaikka(int paikka) {
        this.paikka = paikka;
    }

    public int getPoyta() {
        return poyta;
    }

    public void setPoyta(int poyta) {
        this.poyta = poyta;
    }

    public void setSitsaaja(Sitsaaja sitsaaja) {
        this.sitsaaja = sitsaaja;
    }

    public Sitsaaja getSitsaaja() {
        return sitsaaja;
    }

    public Paikka getNaisenAvecinPaikka() {
        return naisenAvecinPaikka;
    }

    public void setNaisenAvecinPaikka(Paikka avecinPaikka) {
        this.naisenAvecinPaikka = avecinPaikka;
    }

    public Paikka getPuolisonPaikka() {
        return puolisonPaikka;
    }

    public void setPuolisonPaikka(Paikka puolisonPaikka) {
        this.puolisonPaikka = puolisonPaikka;
    }

    public void setMiehenAvecinPaikka(Paikka avecinPaikka) {
        this.miehenAvecinPaikka = avecinPaikka;
    }
    
    public Paikka getMiehenAvecinPaikka() {
        return miehenAvecinPaikka;
    }
    
}