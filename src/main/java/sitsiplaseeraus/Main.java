package sitsiplaseeraus;

import jarjestelija.Optimoija;
import sitsiplaseeraus.random.RandomGenerator;

public class Main {

    public static void main(String[] args) {
        Poyta table = new Poyta();
        RandomGenerator random = new RandomGenerator();
        
        random.taytaRandomDatalla(80, 80*10, table);
        
        Optimoija optimoija = new Optimoija(table);
        optimoija.optimoiIstumapaikat(1000);
    }
}
