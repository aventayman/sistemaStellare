package it.unibs.fp.sistemaStellare;

import java.util.ArrayList;

public class Pianeta extends CorpoCeleste{
    ArrayList<Satellite> listaSatelliti = new ArrayList<>();

    public Pianeta (int codice, int massa, String nome, Posizione posizione) {
        super(codice, massa, nome, posizione);
    }


}
