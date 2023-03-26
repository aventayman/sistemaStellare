package it.unibs.fp.sistemaStellare;

import java.util.ArrayList;

public class Pianeta extends CorpoCeleste{
    //Lista lune del pianeta
    private ArrayList<Satellite> listaSatelliti = new ArrayList<>();

    //Costruttore pianeta
    public Pianeta (int codice, int massa, String nome, Posizione posizione) {
        super(codice, massa, nome, posizione);
    }

    public ArrayList<Satellite> getListaSatelliti() {
        return listaSatelliti;
    }
}
