package sitsiplaseeraus.random;

public class Random {
    static int luo;
    
    public static int luo(int max) {
        return (int) (Math.random() * (max + 1));
    }

    public static int luo(int max, int min) {
        return min + (int)(Math.random() * (max - min + 1));
    }
}
