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
        //Ricavo tutti i codici dei corpi celesti con il nome inserito
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

    /**
     * Metodo che restituisce un ArrayList di satelliti dato un nome, questo metodo
     * serve perché supponiamo la possibilità di omonimia fra i corpi celesti.
     * @param nome nome del satellite
     * @param sistema sistema all'interno del quale si esegue la ricerca
     * @return una lista di satelliti corrispondenti
     */
    public static ArrayList<Satellite> getSatellitiByNome(String nome, SistemaStellare sistema) {
        //Ricavo tutti i codici dei corpi celesti con il nome inserito
        ArrayList<Integer> codici = new ArrayList<>();
        for (HashMap.Entry<Integer, String> entry : codiceNome.entrySet()) {
            if (Objects.equals(nome.toLowerCase(), entry.getValue().toLowerCase())) {
                codici.add(entry.getKey());
            }
        }

        //Creo un array di satelliti con lo stesso nome
        ArrayList<Satellite> corrispondenze = new ArrayList<>();
        for (int i = 0; i < sistema.getStella().getListaPianeti().size(); i++) {
            for (int j = 0; j < sistema.getStella().getListaPianeti().get(i).getListaSatelliti().size(); j++) {
                for (int k = 0; k < codici.size(); k++) {
                    if (sistema.getStella().getListaPianeti().get(i).
                            getListaSatelliti().get(j).getCodice() == codici.get(k)) {
                        corrispondenze.add(sistema.getStella().getListaPianeti().get(i).getListaSatelliti().get(j));
                    }
                }
            }
        }
        return corrispondenze;
    }

    /**
     * Controllo che esista almeno un satellite all'interno del sistema stellare.
     * @param sistema sistema all'interno del quale si vuole eseguire il controllo
     * @return se nel sistema esiste almeno un satellite ritorna true
     */
    public static boolean esisteSatellite(SistemaStellare sistema) {
        for (int i = 0; i < sistema.getStella().getListaPianeti().size(); i++) {
            if (sistema.getStella().getListaPianeti().get(i).getListaSatelliti().size() > 0)
                return true;
        }

        return false;
    }
}
