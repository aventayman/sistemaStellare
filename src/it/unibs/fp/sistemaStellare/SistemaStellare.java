package it.unibs.fp.sistemaStellare;

import java.util.HashMap;

/**
 * Il contenitore di tutti i corpi appartenenti al sistema stellare.
 */
public class SistemaStellare {
    private String nome;
    private Posizione baricentro = new Posizione(0, 0);
    private Stella stella = new Stella();

    /**
     * Inizializza un sistema stellare attraverso il suo nome e una stella.
     * @param nome nome del sistema stellare
     * @param stella stella di riferimento del sistema stellare
     */
    public SistemaStellare(String nome, Stella stella) {
        this.nome = nome;
        this.stella = stella;
    }

    /**
     * Getter per la stella del sistema.
     * @return la stella di riferimento del sistema stellare
     */
    public Stella getStella() {
        return stella;
    }

    /**
     * Getter del baricentro del sistema.
     * @return la posizione del baricentro del sistema stellare
     */
    public Posizione getBaricentro() {
        return baricentro;
    }

    /**
     * Aggiunge il pianeta all'interno della lista di pianeti della stella del sistema, aggiorna il baricentro e
     * ne aggiunge il codice all'interno del database.
     * @param pianeta il pianeta da inserire all'interno del sistema
     */
    public void aggiungiPianeta(Pianeta pianeta) {
        //Aggiunta del pianeta
        stella.getListaPianeti().add(pianeta);

        //Aggiornamento del baricentro del sistema
        baricentro = Baricentro.calcolaBaricentro(this);

        //Aggiunta del nome del pianeta al database dei codici
        Ricerca.codiceNome.put(pianeta.getCodice(), pianeta.getNome());
    }

    /**
     * Aggiunge il satellite all'interno della lista di satelliti del pianeta chiamato,
     * aggiorna il baricentro del sistema e aggiunge il codice del satellite all'interno del database.
     * @param satellite il satellite da aggiungere al pianeta
     * @param pianeta il pianeta a cui si vuole aggiungere il satellite
     */
    public void aggiungiSatellite(Satellite satellite, Pianeta pianeta) {
        //Aggiunta della luna
        pianeta.getListaSatelliti().add(satellite);

        //Aggiornamento del baricentro del sistema
        baricentro = Baricentro.calcolaBaricentro(this);

        //Aggiunta del nome del satellite al database dei codici
        Ricerca.codiceNome.put(satellite.getCodice(), satellite.getNome());
    }

    /**
     * Rimuove un pianeta dal sistema stellare, comprese tutte le sue lune sia dal sistema stellare che
     * dal database dei codici e infine ricalcola il baricentro del sistema.
     * @param pianeta pianeta che si vuole rimuovere
     */
    public void rimuoviPianeta(Pianeta pianeta) {
        //Rimozione del nome di tutte le lune del pianeta dal database dei codici
        for (int i = 0; i < pianeta.getListaSatelliti().size(); i++) {
            for (HashMap.Entry<Integer, String> entry : Ricerca.codiceNome.entrySet()) {
                if (pianeta.getListaSatelliti().get(i).getCodice() == entry.getKey())
                    Ricerca.codiceNome.remove(entry.getKey());
            }
        }

        //Rimozione del pianeta
        stella.getListaPianeti().remove(pianeta);

        //Aggiornamento del baricentro del sistema
        baricentro = Baricentro.calcolaBaricentro(this);

        //Rimozione del nome del pianeta dal database dei codici
        Ricerca.codiceNome.remove(pianeta.getCodice(), pianeta.getNome());
    }

    /**
     * Rimuove un satellite dalla lista dei satelliti di un pianeta, dal database dei codici e
     * ricalcola il baricentro del sistema.
     * @param satellite satellite che si vuole rimuovere
     * @param pianeta pianeta dal quale si vuole rimuovere il satellite
     */
    public void rimuoviSatellite(Satellite satellite, Pianeta pianeta) {
        //Rimozione della luna
        pianeta.getListaSatelliti().remove(satellite);

        //Aggiornamento del baricentro del sistema
        baricentro = Baricentro.calcolaBaricentro(this);

        //Rimozione del nome del satellite dal database dei codici
        Ricerca.codiceNome.remove(satellite.getCodice(), satellite.getNome());
    }
}
