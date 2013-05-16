package sitsiplaseeraus;

import jarjestelija.Optimoija;
import sitsiplaseeraus.random.RandomGenerator;

public class Main {

    public static void main(String[] args) {
        Sitsit sitsit = new Sitsit(3);
        RandomGenerator random = new RandomGenerator();
        
        random.taytaRandomDatalla(80, 80*10, sitsit);
        
        RandomGenerator.tulostaSitsaajat(sitsit);
        
        Optimoija optimoija = new Optimoija(sitsit);
        optimoija.optimoiIstumapaikat(1000);
    }
}
