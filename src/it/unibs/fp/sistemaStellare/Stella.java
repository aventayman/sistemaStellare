package it.unibs.fp.sistemaStellare;

import java.util.ArrayList;

/**
 * Tipo specifico di CorpoCeleste.
 */
public class Stella extends CorpoCeleste {
    /**
     * Lista dei pianeti della stella.
     */
    private ArrayList<Pianeta> listaPianeti = new ArrayList<>();
    private String nomeSistema;

    /**
     * Costruttore vuoto della stella.
     */
    public Stella() {}

    /**
     * Costruttore della stella con parametri dati
     * @param codice codice univoco della stella, sempre uguale a zero
     * @param massa massa della stella
     * @param nome nome della stella
     * @param nomeSistema nome del sistema stellare
     */
    public Stella (int codice, double massa, String nome, String nomeSistema) {
        //Costruttore del corpo celeste
        super(codice, massa, nome);

        //Posizione stella impostata come origine
        Posizione origine = new Posizione(0, 0);
        setPosizione(origine);

        this.nomeSistema = nomeSistema;

        Ricerca.codiceNome.put(codice, nome);
    }

    /**
     * Getter per la lista dei pianeti della stella.
     * @return la lista dei pianeti associati alla stella
     */
    public ArrayList<Pianeta> getListaPianeti() {
        return listaPianeti;
    }

    /**
     * Getter per il nome del sistema stellare associato alla stella.
     * @return il nome del sistema stellare associato alla stella
     */
    public String getNomeSistema() {
        return nomeSistema;
    }

    @Override
    public String toString() {
        return String.format("%15s | %6d | %9.1f | (%6.2f ,%6.2f) | %s", getNome(), getCodice(), getMassa(),
                getPosizione().getX(), getPosizione().getY(), getNomeSistema());
    }
}
