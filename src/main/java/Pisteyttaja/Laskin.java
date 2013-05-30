package Pisteyttaja;

import omatTietorakenteet.HashMap;

public class Laskin {

    private final HashMap<Double, Double> varasto;

    public Laskin() {
        this.varasto = new HashMap<Double, Double>();
    }

    public double hypot(double x, double y) {
        if (x == 0.0) {
            return y;
        } else if(x == 1.0) {
            if(this.varasto.containsKey(y) == true) {
                return this.varasto.get(y);
            } else {
                this.varasto.put(y, Math.hypot(x, y));
                return this.varasto.get(y);
            }
        }
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
