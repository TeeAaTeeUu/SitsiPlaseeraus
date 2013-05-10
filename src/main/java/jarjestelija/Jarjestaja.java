package jarjestelija;

import sitsiplaseeraus.Poyta;

public class Jarjestaja {

    private Poyta table;

    public Jarjestaja() {
        this.table = new Poyta();
    }
    
    public Poyta getTable() {
        return table;
    }

    public void lisaaSitsaaja(String nimi) {
        this.table.addSitsaaja(nimi);
    }

    public void poistaSitsaaja(String nimi) {
        this.table.deleteSitsaaja(nimi);
    }
}
