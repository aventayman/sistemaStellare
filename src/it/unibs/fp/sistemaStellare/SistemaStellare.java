package it.unibs.fp.sistemaStellare;

public class SistemaStellare {
    private String nome;
    private Posizione baricentro = new Posizione(0, 0);
    private Stella stella = new Stella();

    public SistemaStellare(String nome, Stella stella) {
        this.nome = nome;
        this.stella = stella;
    }

    public Stella getStella() {
        return stella;
    }

    public Posizione getBaricentro() {
        return baricentro;
    }

    public void aggiungiPianeta(Pianeta pianeta) {
        //Aggiunta del pianeta
        stella.getListaPianeti().add(pianeta);

        //Aggiornamento del baricentro del sistema
        baricentro = Baricentro.calcolaBaricentro(this);

        //Aggiunta del nome del pianeta al database dei codici
        Ricerca.codiceNome.put(pianeta.getCodice(), pianeta.getNome());
    }

    public void aggiungiSatellite(Satellite satellite, Pianeta pianeta) {
        //Aggiunta della luna
        pianeta.getListaSatelliti().add(satellite);

        //Aggiornamento del baricentro del sistema
        baricentro = Baricentro.calcolaBaricentro(this);

        //Aggiunta del nome del satellite al database dei codici
        Ricerca.codiceNome.put(satellite.getCodice(), satellite.getNome());
    }

    public void rimuoviPianeta(Pianeta pianeta) {
        //Rimozione del pianeta
        stella.getListaPianeti().remove(pianeta);

        //Aggiornamento del baricentro del sistema
        baricentro = Baricentro.calcolaBaricentro(this);

        //Rimozione del nome del pianeta dal database dei codici
        Ricerca.codiceNome.remove(pianeta.getCodice(), pianeta.getNome());
    }

    public void rimuoviSatellite(Satellite satellite, Pianeta pianeta) {
        //Rimozione della luna
        pianeta.getListaSatelliti().remove(satellite);

        //Aggiornamento del baricentro del sistema
        baricentro = Baricentro.calcolaBaricentro(this);

        //Rimozione del nome del satellite dal database dei codici
        Ricerca.codiceNome.remove(satellite.getCodice(), satellite.getNome());
    }
}
