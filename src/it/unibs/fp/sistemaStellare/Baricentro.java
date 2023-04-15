package it.unibs.fp.sistemaStellare;

/**
 * Classe per il calcolo del baricentro totale del sistema stellare.
 */
public abstract class Baricentro {
    /**
     * Calcola il baricentro totale del sistema stellare.
     *
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

        //Cicli annidati per calcolare momenti di pianeti e lune
        for (Pianeta pianeta : sistema.getStella().getListaPianeti()) {

            //Aggiunta momento statico del i-esimo pianeta
            iX += pianeta.getPosizione().getX() * pianeta.getMassa();
            iY += pianeta.getPosizione().getY() * pianeta.getMassa();

            //Aggiunta massa del i-esimo pianeta
            m += pianeta.getMassa();

            //Ciclo per calcolare momenti statici lune
            for (Satellite satellite : pianeta.getListaSatelliti()) {

                //Aggiunta momento statico del j-esimo satellite
                iX += satellite.getPosizione().getX() * satellite.getMassa();
                iY += satellite.getPosizione().getY() * satellite.getMassa();

                //Aggiunta massa del j-esimo satellite
                m += satellite.getMassa();
            }
        }

        //Calcolo delle coordinate del baricentro attraverso i momenti statici
        float xBaricentro = iX / (float) m;
        float yBaricentro = iY / (float) m;

        return new Posizione(xBaricentro, yBaricentro);
    }
}
