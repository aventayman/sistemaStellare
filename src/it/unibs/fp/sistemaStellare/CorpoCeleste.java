package it.unibs.fp.sistemaStellare;

/**
 * Gli attributi più generici di corpo celeste sono definiti in questa classe.
 */
public abstract class CorpoCeleste {
    //Il codice univoco è uguale al codice del corpo precedentemente inserito + 1 partendo da 0
    private int codice;
    private double massa;
    private String nome;
    private Posizione posizione = new Posizione();

    /**
     * Costruttore vuoto per un corpo celeste.
     */
    public CorpoCeleste() {}

    /**
     * Costruttore di un corpo celeste con parametri dati.
     * @param codice codice univoco di ogni corpo celeste
     * @param massa massa del corpo celeste
     * @param nome nome del corpo celeste
     * @param posizione posizione del corpo celeste all'interno del sistema stellare
     */
    public CorpoCeleste(int codice, double massa, String nome, Posizione posizione) {
        this.codice = codice;
        this.massa = massa;
        this.nome = nome;
        this.posizione = posizione;
    }

    /**
     * Costruttore del corpo celeste specifico per la stella poiché la stella si trova sempre in posizione (0, 0).
     * @param codice codice univoco della stella
     * @param massa massa della stella
     * @param nome nome della stella
     */
    public CorpoCeleste(int codice, double massa, String nome) {
        this.codice = codice;
        this.massa = massa;
        this.nome = nome;
    }

    /**
     * Getter per il codice del corpo celeste.
     * @return il codice univoco del corpo celeste
     */
    public int getCodice() {
        return codice;
    }

    /**
     * Getter della massa del corpo celeste.
     * @return la massa del corpo celeste
     */
    public double getMassa() {
        return massa;
    }

    /**
     * Getter del nome del corpo celeste.
     * @return il nome del corpo celeste
     */
    public String getNome() {
        return nome;
    }

    /**
     * Getter della posizione del corpo celeste all'interno del sistema stellare.
     * @return la posizione del corpo celeste all'interno del sistema stellare
     */
    public Posizione getPosizione() {
        return posizione;
    }

    /**
     * Setter della posizione del corpo celeste all'interno del sistema stellare.
     * @param posizione la posizione che si vuole assegnare al corpo celeste
     */
    public void setPosizione(Posizione posizione) {
        this.posizione = posizione;
    }
}
