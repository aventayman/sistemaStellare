package it.unibs.fp.sistemaStellare;

/**
 * Una posizione che è definita da due coordinate
 * cartesiane x e y.
 */
public class Posizione {
    private float x, y;

    /**
     * Costruttore nullo di una posizione.
     */
    public Posizione () {}

    /**
     * Costruttore con x e y definiti.
     * @param x ascissa
     * @param y ordinata
     */
    public Posizione (float x, float y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Getter per la coordinata x.
     * @return la coordinata x della posizione.
     */
    public float getX() {
        return x;
    }

    /**
     * Getter per la coordinata y.
     * @return la coordinata y della posizione.
     */
    public float getY() {
        return y;
    }

    /**
     * Dato un sistema stellare controlla che nessun CorpoCeleste occupi già una data posizione.
     * @param x la coordinata x da controllare
     * @param y la coordinata y da controllare
     * @param sistema il sistema con i corpi celesti al suo interno
     * @return se la posizione è valida ritorna true
     */
    public static boolean posizioneValida (double x, double y, SistemaStellare sistema) {
        //Non può trovarsi nella posizione della stella
        if (x == 0 && y == 0)
            return false;

        //Ciclo per verificare se si trova nella stessa posizione di un altro pianeta
        for (int i = 0; i < sistema.getStella().getListaPianeti().size(); i++) {
            if (sistema.getStella().getListaPianeti().get(i).getPosizione().getX() == x
            && sistema.getStella().getListaPianeti().get(i).getPosizione().getY() == y) {
                return false;
            }

            //Ciclo per verificare se si trova nella stessa posizione di una delle lune del pianeta
            for (int j = 0; j < sistema.getStella().getListaPianeti().get(i).getListaSatelliti().size(); j++) {
                if (sistema.getStella().getListaPianeti().get(i).getListaSatelliti().get(j).getPosizione().getX() == x
            && sistema.getStella().getListaPianeti().get(i).getListaSatelliti().get(j).getPosizione().getY() == y) {
                    return false;
                }
            }
        }

        return true;
    }
    @Override
    public String toString() {
        return String.format("(%6.2f,%6.2f)", x, y);
    }
}
