package it.unibs.fp.sistemaStellare;

import java.util.ArrayList;

public class Pianeta extends CorpoCeleste{
    //Lista lune del pianeta
    private ArrayList<Satellite> listaSatelliti = new ArrayList<>();

    //Costruttore pianeta
    public Pianeta (int codice, double massa, String nome, Posizione posizione) {
        super(codice, massa, nome, posizione);
    }

    public ArrayList<Satellite> getListaSatelliti() {
        return listaSatelliti;
    }

    @Override
    public String toString() {
        return String.format("%20s\t%5d\t%5.1f\t(%7.2f,%7.2f)\t%4d\n", getNome(), getCodice(), getMassa(),
                getPosizione().getX(), getPosizione().getY(), listaSatelliti.size());
    }
}
