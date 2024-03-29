package it.unibs.fp.sistemaStellare;

import java.util.ArrayList;

/**
 * Tipo specifico di CorpoCeleste.
 */
public class Pianeta extends CorpoCeleste {
    /**
     * Lista delle lune del pianeta.
     */
    private final ArrayList<Satellite> listaSatelliti = new ArrayList<>();

    /**
     * Costruttore del pianeta, utilizza il costruttore di CorpoCeleste.
     *
     * @param codice    codice univoco del pianeta
     * @param massa     massa del pianeta
     * @param nome      nome del pianeta
     * @param posizione posizione del pianeta all'interno del sistema stellare
     */
    public Pianeta(int codice, double massa, String nome, Posizione posizione) {
        super(codice, massa, nome, posizione);
    }

    /**
     * Costruttore vuoto per un pianeta.
     */
    public Pianeta() {
    }

    /**
     * Getter per la lista dei satelliti appartenenti al pianeta.
     *
     * @return lista dei satelliti del pianeta
     */
    public ArrayList<Satellite> getListaSatelliti() {
        return listaSatelliti;
    }

    @Override
    public String toString() {
        return String.format("%15s | %6d | %9.1f | (%6.2f ,%6.2f) | %6d", getNome(), getCodice(), getMassa(),
                getPosizione().getX(), getPosizione().getY(), listaSatelliti.size());
    }
}
