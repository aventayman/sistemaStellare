package it.unibs.fp.sistemaStellare;

import java.util.ArrayList;

/**
 * Tipo specifico di CorpoCeleste.
 */
public class Pianeta extends CorpoCeleste{
    //Lista lune del pianeta
    private ArrayList<Satellite> listaSatelliti = new ArrayList<>();

    /**
     * Costruttore del pianeta, utilizza il costruttore di CorpoCeleste.
     * @param codice codice univoco del pianeta
     * @param massa massa del pianeta
     * @param nome nome del pianeta
     * @param posizione posizione del pianeta all'interno del sistema stellare
     */
    public Pianeta (int codice, double massa, String nome, Posizione posizione) {
        super(codice, massa, nome, posizione);
    }

    /**
     * Costruttore vuoto per un pianeta.
     */
    public Pianeta() {}

    /**
     * Getter per la lista dei satelliti appartenenti al pianeta.
     * @return lista dei satelliti del pianeta
     */
    public ArrayList<Satellite> getListaSatelliti() {
        return listaSatelliti;
    }

    @Override
    public String toString() {
        return String.format("%20s\t%5d\t%5.1f\t(%7.2f,%7.2f)\t%4d\n", getNome(), getCodice(), getMassa(),
                getPosizione().getX(), getPosizione().getY(), listaSatelliti.size());
    }
}
