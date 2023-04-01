package it.unibs.fp.sistemaStellare;

import com.hrakaroo.glob.GlobPattern;
import com.hrakaroo.glob.MatchingEngine;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Una classe con al suo interno metodi static di ricerca all'interno del sistema.
 */
public abstract class Ricerca {
    /**
     * Mappa che collega a ogni codice univoco di un corpo celeste il corrispondente nome.
     */
    public static HashMap<Integer, String> codiceNome = new HashMap<>();

    /**
     * Metodo che restituisce un ArrayList di pianeti dato un nome.
     * Inoltre si può effettuare la ricerca anche attraverso i glob patterns,
     * per esempio se abbiamo una lista ["Mercurio", "Venere", "Terra", "Marte"]:
     * se effettuo la ricerca con la stringa "*er*" ritornerà ["Mercurio", "Venere", "Terra"],
     * se abbiamo una lista ["Terra","Tartorre" , "Terre", "Torre"]:
     * se effettuo la ricerca con la stringa "t?rre" ritornerà ["Terre", "Torre"]
     *
     * @param nome    il nome del pianeta del quale si vuole ricercare le corrispondenze di codici
     * @param sistema il sistema all'interno del quale risiede il pianeta
     * @return una lista di pianeti con il corrispondente nome
     */
    public static ArrayList<Pianeta> getPianetiByNome(String nome, SistemaStellare sistema) {
        //Converte il nome in regex per consentire la ricerca attraverso i glob pattern
        MatchingEngine regex = GlobPattern.compile(nome.toLowerCase());
        //Ricavo tutti i codici dei corpi celesti con il nome inserito
        ArrayList<Integer> codici = new ArrayList<>();
        for (HashMap.Entry<Integer, String> entry : codiceNome.entrySet()) {
            if (regex.matches(entry.getValue().toLowerCase())) {
                codici.add(entry.getKey());
            }
        }

        //Creo un array di pianeti con lo stesso nome
        ArrayList<Pianeta> corrispondenze = new ArrayList<>();
        for (int i = 0; i < sistema.getStella().getListaPianeti().size(); i++) {
            for (Integer codice : codici) {
                if (sistema.getStella().getListaPianeti().get(i).getCodice() == codice) {
                    corrispondenze.add(sistema.getStella().getListaPianeti().get(i));
                }
            }
        }
        return corrispondenze;
    }

    /**
     * Metodo che restituisce un ArrayList di satelliti dato un nome.
     * Inoltre si può effettuare la ricerca anche attraverso i glob patterns,
     * per esempio se abbiamo una lista ["Mercurio", "Venere", "Terra", "Marte"]:
     * se effettuo la ricerca con la stringa "*er*" ritornerà ["Mercurio", "Venere", "Terra"],
     * se abbiamo una lista ["Terra","Tartorre" , "Terre", "Torre"]:
     * se effettuo la ricerca con la stringa "t?rre" ritornerà ["Terre", "Torre"]
     *
     * @param nome    nome del satellite
     * @param sistema sistema all'interno del quale si esegue la ricerca
     * @return una lista di satelliti corrispondenti
     */
    public static ArrayList<Satellite> getSatellitiByNome(String nome, SistemaStellare sistema) {
        //Converte il nome in regex per consentire la ricerca attraverso i glob pattern
        MatchingEngine regex = GlobPattern.compile(nome.toLowerCase());
        //Ricavo tutti i codici dei corpi celesti con il nome inserito
        ArrayList<Integer> codici = new ArrayList<>();
        for (HashMap.Entry<Integer, String> entry : codiceNome.entrySet()) {
            if (regex.matches(entry.getValue().toLowerCase())) {
                codici.add(entry.getKey());
            }
        }

        //Creo un array di satelliti con lo stesso nome
        ArrayList<Satellite> corrispondenze = new ArrayList<>();
        for (int i = 0; i < sistema.getStella().getListaPianeti().size(); i++) {
            for (int j = 0; j < sistema.getStella().getListaPianeti().get(i).getListaSatelliti().size(); j++) {
                for (Integer codice : codici) {
                    if (sistema.getStella().getListaPianeti().get(i).
                            getListaSatelliti().get(j).getCodice() == codice) {
                        corrispondenze.add(sistema.getStella().getListaPianeti().get(i).getListaSatelliti().get(j));
                    }
                }
            }
        }
        return corrispondenze;
    }

    /**
     * Controllo che il nome inserito sia quello della stella.
     * Anche questo metodo è compatibile con i glob patterns.
     *
     * @param nome    nome della stella
     * @param sistema sistema contenente la stella
     * @return se il nome è quello della stella torna true
     */
    public static boolean isNomeStella(String nome, SistemaStellare sistema) {
        //Converte il nome in regex per consentire la ricerca attraverso i glob pattern
        MatchingEngine regex = GlobPattern.compile(nome.toLowerCase());

        return regex.matches(sistema.getStella().getNome().toLowerCase());
    }

    /**
     * Controllo che esista almeno un satellite all'interno del sistema stellare.
     *
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

    /**
     * Dato un codice ritorna se si tratta di un pianeta.
     *
     * @param codice  codice che si vuole ricercare
     * @param sistema sistema all'interno del quale si vuole ricercare
     * @return se al codice corrisponde un pianeta ritorna true
     */
    public static boolean isPianeta(int codice, SistemaStellare sistema) {
        for (Pianeta pianeta : sistema.getStella().getListaPianeti())
            if (pianeta.getCodice() == codice)
                return true;

        return false;
    }

    /**
     * Dato un codice ritorna se si tratta di un satellite.
     *
     * @param codice  codice che si vuole ricercare
     * @param sistema sistema all'interno del quale si vuole ricercare
     * @return se al codice corrisponde un satellite ritorna true
     */
    public static boolean isSatellite(int codice, SistemaStellare sistema) {
        for (Pianeta pianeta : sistema.getStella().getListaPianeti())
            for (Satellite satellite : pianeta.getListaSatelliti())
                if (satellite.getCodice() == codice)
                    return true;

        return false;
    }

    /**
     * Dato il codice di un satellite ritorna il codice del pianeta a lui associato.
     *
     * @param codiceSatellite codice del satellite
     * @param sistema         sistema all'interno del quale si vuole ricercare
     * @return il codice del pianeta associato al satellite,
     * se gli viene passato il codice di un non satellite ritorna -1
     */
    public static int codicePianetaBySatellite(int codiceSatellite, SistemaStellare sistema) {
        for (Pianeta pianeta : sistema.getStella().getListaPianeti())
            for (Satellite satellite : pianeta.getListaSatelliti())
                if (satellite.getCodice() == codiceSatellite)
                    return satellite.getCodicePianeta();

        return -1;
    }
}
