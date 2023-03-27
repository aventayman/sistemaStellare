package it.unibs.fp.sistemaStellare;

import java.util.*;

public abstract class Ricerca {
    //Mappa che collega i codici dei corpi celesti ai loro nomi
    public static HashMap<Integer, String> codiceNome = new HashMap<>();

    //Metodo che restituisce un ArrayList di codici dato un nome
    //Questo metodo serve perché supponiamo la possibilità di omonimia fra i corpi celesti
    //In questo modo possiamo risalire a tutti i codici di corpi celesti possibili dato un nome
    public static ArrayList<Integer> getCodiciByNome(String nome) {
        ArrayList<Integer> codici = new ArrayList<>();
        for (HashMap.Entry<Integer, String> entry : codiceNome.entrySet()) {
            if (Objects.equals(nome.toLowerCase(), entry.getValue().toLowerCase())) {
                codici.add(entry.getKey());
            }
        }
        return codici;
    }
}
