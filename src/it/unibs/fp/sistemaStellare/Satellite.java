package it.unibs.fp.sistemaStellare;

/**
 * Tipo specifico di corpo celeste.
 */
public class Satellite extends CorpoCeleste{
    private int codicePianeta;

    /**
     * Costruttore del satellite.
     * @param codice codice univoco del satellite
     * @param massa massa del satellite
     * @param nome nome del satellite
     * @param posizione posizione del satellite all'interno del sistema stellare
     * @param pianeta pianeta al quale il satellite appartiene
     */
    public Satellite (int codice, double massa, String nome, Posizione posizione, Pianeta pianeta) {
        //Parametri comuni al corpo celeste
        super(codice, massa, nome, posizione);

        //Inizializzazione codice pianeta
        this.codicePianeta = pianeta.getCodice();
    }

    /**
     * Getter del codice del pianeta associato al satellite.
     * @return il codice del pianeta associato
     */
    public int getCodicePianeta() {
        return codicePianeta;
    }

    @Override
    public String toString() {
        return String.format("%20s\t%5x\t%5.1f\t(%7.2f,%7.2f)\t%10s\n", getNome(), getCodice(), getMassa(),
                getPosizione().getX(), getPosizione().getY(), Ricerca.codiceNome.get(codicePianeta));
    }
}
