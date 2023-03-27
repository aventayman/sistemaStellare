package it.unibs.fp.sistemaStellare;

import java.util.*;

/**
 * Una classe con al suo interno metodi static di ricerca all'interno del sistema.
 */
public abstract class Ricerca {
    //Mappa che collega i codici dei corpi celesti ai loro nomi
    public static HashMap<Integer, String> codiceNome = new HashMap<>();

    /**
     * Metodo che restituisce un ArrayList di pianeti dato un nome, questo metodo
     * serve perché supponiamo la possibilità di omonimia fra i corpi celesti.
     * @param nome il nome del pianeta del quale si vuole ricercare le corrispondenze di codici
     * @param sistema il sistema all'interno del quale risiede il pianeta
     * @return una lista di pianeti con il corrispondente nome
     */
    public static ArrayList<Pianeta> getPianetiByNome(String nome, SistemaStellare sistema) {
        //Ricavo tutti i codici dei pianeti con il nome inserito
        ArrayList<Integer> codici = new ArrayList<>();
        for (HashMap.Entry<Integer, String> entry : codiceNome.entrySet()) {
            if (Objects.equals(nome.toLowerCase(), entry.getValue().toLowerCase())) {
                codici.add(entry.getKey());
            }
        }

        //Creo un array di pianeti con lo stesso nome
        ArrayList<Pianeta> corrispondenze = new ArrayList<>();
        for (int i = 0; i < sistema.getStella().getListaPianeti().size(); i++) {
            for (int j = 0; j < codici.size(); j++) {
                if (sistema.getStella().getListaPianeti().get(i).getCodice() == codici.get(j)) {
                    corrispondenze.add(sistema.getStella().getListaPianeti().get(i));
                }
            }
        }

        return corrispondenze;
    }
}