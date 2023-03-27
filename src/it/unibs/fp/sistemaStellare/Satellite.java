package it.unibs.fp.sistemaStellare;

public class Satellite extends CorpoCeleste{
    private int codicePianeta;

    //Costruttore luna
    public Satellite (int codice, double massa, String nome, Posizione posizione, Pianeta pianeta) {
        //Parametri comuni al corpo celeste
        super(codice, massa, nome, posizione);

        //Inizializzazione codice pianeta
        this.codicePianeta = pianeta.getCodice();
    }

    @Override
    public String toString() {
        return String.format("%20s\t%5x\t%5.1f\t(%7.2f,%7.2f)\t%10s\n", getNome(), getCodice(), getMassa(),
                getPosizione().getX(), getPosizione().getY(), Ricerca.codiceNome.get(codicePianeta));
    }
}
