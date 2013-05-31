package Pisteyttaja;

import omatTietorakenteet.Hakemisto;

/**
 * Helpottaa etäisyyksien laskemista, eli luo tulosvaraston Math.hypot funktiolle, jotta samoja laskuja ei tarvitse laskea moneen kertaan
 */
public class Laskin {

    private final Hakemisto<Double, Double> varasto;
    private int luku;

    public Laskin() {
        this.varasto = new Hakemisto<Double, Double>();
    }

    public double hypot(double x, double y) {
        if (x == 0.0) {
            return y;
        } else if(x == 1.0) {
            luku = this.varasto.getAvainIndexAvaimella(y);
            if(luku >= 0) {
                return this.varasto.getAvainIndexilla(luku);
            } else {
                this.varasto.add(y, Math.hypot(x, y));
                return this.varasto.getAvainIndexilla(luku);
            }
        }
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
