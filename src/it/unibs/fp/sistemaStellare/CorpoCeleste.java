package it.unibs.fp.sistemaStellare;

public abstract class CorpoCeleste {
    //Il codice univoco è uguale al codice del corpo precedentemente inserito + 1 partendo da 0
    private int codice;
    private double massa;
    private String nome;
    private Posizione posizione = new Posizione();

    //Costruttore vuoto
    public CorpoCeleste() {}

    //Costruttore base per pianeti e lune
    public CorpoCeleste(int codice, double massa, String nome, Posizione posizione) {
        this.codice = codice;
        this.massa = massa;
        this.nome = nome;
        this.posizione = posizione;
    }

    //Costruttore base per stelle
    public CorpoCeleste(int codice, double massa, String nome) {
        this.codice = codice;
        this.massa = massa;
        this.nome = nome;
    }

    public int getCodice() {
        return codice;
    }

    public double getMassa() {
        return massa;
    }

    public String getNome() {
        return nome;
    }

    public Posizione getPosizione() {
        return posizione;
    }

    public void setPosizione(Posizione posizione) {
        this.posizione = posizione;
    }
}
