package it.unibs.fp.sistemaStellare;

public abstract class CorpoCeleste {
    private int codice;
    private float massa;
    private String nome;
    private Posizione posizione = new Posizione();

    //Costruttore vuoto
    public CorpoCeleste() {}

    //Costruttore base per pianeti e lune
    public CorpoCeleste(int codice, float massa, String nome, Posizione posizione) {
        this.codice = codice;
        this.massa = massa;
        this.nome = nome;
        this.posizione = posizione;
    }

    //Costruttore base per stelle
    public CorpoCeleste(int codice, float massa, String nome) {
        this.codice = codice;
        this.massa = massa;
        this.nome = nome;
    }

    public int getCodice() {
        return codice;
    }

    public float getMassa() {
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
