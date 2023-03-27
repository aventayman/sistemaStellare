package it.unibs.fp.sistemaStellare;

public class Posizione {
    private float x, y;

    public Posizione () {}

    public Posizione (float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

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
        return String.format("(%7.2f,%7.2f)", x, y);
    }
}
