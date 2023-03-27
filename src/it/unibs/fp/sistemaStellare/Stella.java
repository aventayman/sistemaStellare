package it.unibs.fp.sistemaStellare;

import java.util.ArrayList;

public class Stella extends CorpoCeleste {
    //Lista dei pianeti
    private ArrayList<Pianeta> listaPianeti = new ArrayList<>();

    //Costruttore vuoto
    public Stella() {}

    //Costruttore per costruire stella
    public Stella (int codice, double massa, String nome) {
        //Costruttore del corpo celeste
        super(codice, massa, nome);

        //Posizione stella impostata come origine
        Posizione origine = new Posizione(0, 0);
        setPosizione(origine);

        Ricerca.codiceNome.put(codice, nome);
    }

    public ArrayList<Pianeta> getListaPianeti() {
        return listaPianeti;
    }

    @Override
    public String toString() {
        return String.format("%20s\t%5d\t%5.1f\t(%7.2f,%7.2f)\n", getNome(), getCodice(), getMassa(),
                getPosizione().getX(), getPosizione().getY());
    }
}
