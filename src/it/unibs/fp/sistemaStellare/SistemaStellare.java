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
        baricentro = CalcoloBaricentro.calcolaBaricentro(this);
    }

    public void aggiungiSatellite(Satellite satellite, Pianeta pianeta) {
        //Aggiunta della luna
        pianeta.getListaSatelliti().add(satellite);

        //Aggiornamento del baricentro del sistema
        baricentro = CalcoloBaricentro.calcolaBaricentro(this);
    }

    public void rimuoviPianeta(Pianeta pianeta) {
        //Rimozione del pianeta
        stella.getListaPianeti().remove(pianeta);

        //Aggiornamento del baricentro del sistema
        baricentro = CalcoloBaricentro.calcolaBaricentro(this);
    }

    public void rimuoviSatellite(Satellite satellite, Pianeta pianeta) {
        //Rimozione della luna
        pianeta.getListaSatelliti().remove(satellite);

        //Aggiornamento del baricentro del sistema
        baricentro = CalcoloBaricentro.calcolaBaricentro(this);
    }
}
