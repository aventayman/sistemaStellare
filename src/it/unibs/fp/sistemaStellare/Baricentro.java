package it.unibs.fp.sistemaStellare;

/**
 * Classe per il calcolo del baricentro totale del sistema stellare.
 */
public abstract class Baricentro {
    /**
     * Calcola il baricentro totale del sistema stellare.
     * @param sistema il sistema di cui si deve calcolare il baricentro
     * @return la posizione del baricentro del sistema
     */
    public static Posizione calcolaBaricentro(SistemaStellare sistema) {
        //Accumulatori momenti statici
        float iX = 0, iY = 0;
        //Accumulatore massa
        double m = 0;

        //Aggiunta momento statico stella
        iX += sistema.getStella().getPosizione().getX() * sistema.getStella().getMassa();
        iY += sistema.getStella().getPosizione().getY() * sistema.getStella().getMassa();

        //Aggiunta massa stella
        m += sistema.getStella().getMassa();

        //Lunghezza lista pianeti
        int lenPianeti = sistema.getStella().getListaPianeti().size();

        //Cicli annidati per calcolare momenti di pianeti e lune
        for (int i = 0; i < lenPianeti; i++) {

            float xPianeta = sistema.getStella().getListaPianeti().get(i).getPosizione().getX();
            float yPianeta = sistema.getStella().getListaPianeti().get(i).getPosizione().getY();
            double massaPianeta = sistema.getStella().getListaPianeti().get(i).getMassa();

            //Aggiunta momento statico del i-esimo pianeta
            iX +=  xPianeta * massaPianeta;
            iY +=  yPianeta * massaPianeta;

            //Aggiunta massa del i-esimo pianeta
            m += massaPianeta;

            //Lunghezza lista satelliti
            int lenSatelliti = sistema.getStella().getListaPianeti().get(i).getListaSatelliti().size();

            //Ciclo per calcolare momenti statici lune
            for (int j = 0; j < lenSatelliti; j++) {
                float xLuna = sistema.getStella().getListaPianeti().get(i)
                        .getListaSatelliti().get(j).getPosizione().getX();
                float yLuna = sistema.getStella().getListaPianeti().get(i)
                        .getListaSatelliti().get(j).getPosizione().getY();
                double massaLuna = sistema.getStella().getListaPianeti().get(i)
                        .getListaSatelliti().get(j).getMassa();

                //Aggiunta momento statico del j-esimo satellite
                iX += xLuna * massaLuna;
                iY += yLuna * massaLuna;

                //Aggiunta massa del j-esimo satellite
                m += massaLuna;
            }
        }

        //Calcolo delle coordinate del baricentro attraverso i momenti statici
        float xBaricentro = iX / (float) m;
        float yBaricentro = iY / (float) m;

        return new Posizione(xBaricentro, yBaricentro);
    }
}
