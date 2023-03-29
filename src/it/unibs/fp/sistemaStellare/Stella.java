package it.unibs.fp.sistemaStellare;

import java.util.ArrayList;

/**
 * Tipo specifico di CorpoCeleste.
 */
public class Stella extends CorpoCeleste {
    //Lista dei pianeti della stella
    private ArrayList<Pianeta> listaPianeti = new ArrayList<>();

    /**
     * Costruttore vuoto della stella.
     */
    public Stella() {}

    /**
     * Costruttore della stella con parametri dati
     * @param codice codice univoco della stella, sempre uguale a zero
     * @param massa massa della stella
     * @param nome nome della stella
     */
    public Stella (int codice, double massa, String nome) {
        //Costruttore del corpo celeste
        super(codice, massa, nome);

        //Posizione stella impostata come origine
        Posizione origine = new Posizione(0, 0);
        setPosizione(origine);

        Ricerca.codiceNome.put(codice, nome);
    }

    /**
     * Getter per la lista dei pianeti della stella.
     * @return la lista dei pianeti associati alla stella
     */
    public ArrayList<Pianeta> getListaPianeti() {
        return listaPianeti;
    }

    @Override
    public String toString() {
        return String.format("%15s | %6d | %7.1f | (%6.2f ,%6.2f)", getNome(), getCodice(), getMassa(),
                getPosizione().getX(), getPosizione().getY());
    }
}
