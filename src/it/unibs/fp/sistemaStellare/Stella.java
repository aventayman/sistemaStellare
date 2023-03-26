package it.unibs.fp.sistemaStellare;

import java.util.ArrayList;

public class Stella extends CorpoCeleste {
    //Lista dei pianeti
    private ArrayList<Pianeta> listaPianeti = new ArrayList<>();

    //Costruttore vuoto
    public Stella() {}

    //Costruttore per costruire stella
    public Stella (int codice, int massa, String nome) {
        //Costruttore del corpo celeste
        super(codice, massa, nome);

        //Posizione stella impostata come origine
        Posizione origine = new Posizione(0, 0);
        super.setPosizione(origine);
    }

    public ArrayList<Pianeta> getListaPianeti() {
        return listaPianeti;
    }
}
