package it.unibs.fp.sistemaStellare;

public class SistemaStellare {
    private String nome;
    private Posizione baricentro = new Posizione();
    private Stella stella = new Stella();

    public SistemaStellare(String nome, Stella stella) {
        this.nome = nome;
        this.stella = stella;
    }

    public Stella getStella() {
        return stella;
    }
}
