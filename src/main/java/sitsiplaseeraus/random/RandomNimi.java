package sitsiplaseeraus.random;

public class RandomNimi {

    private Sukunimet sukunimet;
    private EtunimetMiehen etunimetMiehen;
    private EtunimetNaisen etunimetNaisen;

    public RandomNimi() {
        sukunimet = new Sukunimet();
        etunimetMiehen = new EtunimetMiehen();
        etunimetNaisen = new EtunimetNaisen();
    }
    
    protected String palautaSukunimi() {
        return this.sukunimet.palautaSukunimi();
    }

    protected String palautaEtunimiMiehen() {
        return this.etunimetMiehen.palautaEtunimi();
    }

    protected String palautaEtunimiNaisen() {
        return this.etunimetNaisen.palautaEtunimi();
    }
}
