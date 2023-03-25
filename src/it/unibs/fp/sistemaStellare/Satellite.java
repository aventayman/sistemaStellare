package it.unibs.fp.sistemaStellare;

public class Satellite extends CorpoCeleste{
    private int codicePianeta;

    //Costruttore luna
    public Satellite (int codice, int massa, String nome, Posizione posizione, Pianeta pianeta) {
        //Parametri comuni al corpo celeste
        super(codice, massa, nome, posizione);

        //Inizializzazione codice pianeta
        this.codicePianeta = pianeta.getCodice();
    }
}
