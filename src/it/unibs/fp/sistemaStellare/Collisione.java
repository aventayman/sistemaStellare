package it.unibs.fp.sistemaStellare;

public abstract class Collisione {
    /**
     * Metodo invocato ogni volta che viene inserito o rimosso un pianeta all'interno del sistema
     * Confronta il pianeta con tutti i corpi del sistema e verifica se può verificarsi una collisione
     *
     * @param sistema il sistema all'interno del quale viene eseguita la verifica
     * @param pianeta pianeta su cui svolgere la verifica
     * @return true se si verifica una collisione con un corpo qualsiasi del sistema, false in caso contrario
     */
    public static boolean collisionePianeta(SistemaStellare sistema, Pianeta pianeta) {

        //Primo caso: scontro pianeta-pianeta
        //Si verifica esclusivamente se due pianeti hanno la stessa distanza dalla stella
        for (Pianeta pianetaN : sistema.getStella().getListaPianeti()){
            //Per ogni pianeta del sistema controllo che la sua distanza dalla stella sia uguale alla
            //distanza del pianeta inserito, controllando che non si tratti del pianeta stesso
            if (Posizione.distanza(sistema.getStella().getPosizione(), pianeta.getPosizione()) ==
                    Posizione.distanza(sistema.getStella().getPosizione(), pianetaN.getPosizione()) &&
                    pianetaN.getCodice() != pianeta.getCodice()) {
                return true;
            }
        }

        //Secondo caso: scontro pianeta-satellite
        //Si distinguono due sotto casi:
        for (Pianeta pianetaN : sistema.getStella().getListaPianeti()) {
            //Se il pianeta inserito é più vicino alla stella rispetto al pianeta associato al satellite
            if (Posizione.distanza(sistema.getStella().getPosizione(), pianeta.getPosizione()) <
                    Posizione.distanza(sistema.getStella().getPosizione(), pianetaN.getPosizione())){
                //allora applico
                // d(stella, pianeta1) < |d(stella, pianeta2) - d(pianeta2, satellite)|
                //Ritorna true se tale condizione non é verificata
                for (Satellite satelliteN : pianetaN.getListaSatelliti()) {
                    //applico la formula matematica
                    if (Posizione.distanza(sistema.getStella().getPosizione(), pianeta.getPosizione()) >=
                            Math.abs(Posizione.distanza(sistema.getStella().getPosizione(), pianetaN.getPosizione()) -
                                    Posizione.distanza(pianetaN.getPosizione(), satelliteN.getPosizione()))) {
                        return true;
                    }
                }
            }
            //Se il pianeta inserito è più lontano dalla stella rispetto al pianeta associato al satellite,
            //controllando che non si tratti dello stesso pianeta,
            else if (pianetaN.getCodice() != pianeta.getCodice()) {
                //applico
                // d(stella, pianeta1) > d(stella, pianeta2) + d(pianeta2, satellite)
                //Ritorna true se tale condizione non é verificata
                for (Satellite satelliteN : pianetaN.getListaSatelliti()) {
                    //applico la formula matematica
                    if (Posizione.distanza(sistema.getStella().getPosizione(), pianeta.getPosizione()) <=
                            (Posizione.distanza(sistema.getStella().getPosizione(), pianetaN.getPosizione()) +
                                    Posizione.distanza(pianetaN.getPosizione(), satelliteN.getPosizione()))) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Metodo invocato ogni volta che viene inserito o rimosso un satellite all'interno del sistema
     * confronta il satellite con tutti i corpi del sistema e verifica se può verificarsi una collisione.
     *
     * @param sistema sistema in cui si trovano i corpi analizzati
     * @param satellite satellite per cui si potrebbe verificare una collisione
     * @return true se avviene una collisione con qualsiasi corpo, false in caso contrario
     */
    public static boolean collisioneSatellite(SistemaStellare sistema, Satellite satellite) {

        //Primo caso: scontro satellite-satellite (stesso pianeta)
        //Due satelliti appartenenti allo stesso pianeta possono collidere solo se si trovano alla stessa distanza
        //dal pianeta che condividono

        //Ricavo il pianeta a cui il satellite appartiene
        Pianeta pianeta = new Pianeta();
        int codice = Ricerca.codicePianetaBySatellite(satellite.getCodice(), sistema);
        for (Pianeta pianetaN : sistema.getStella().getListaPianeti()) {
            if (pianetaN.getCodice() == codice)
                pianeta = pianetaN;
        }
        for (Satellite satelliteN : pianeta.getListaSatelliti()) {
            //per ogni satellite del pianeta controllo che la sua distanza dal pianeta sia uguale alla
            //distanza del satellite inserito dallo stesso pianeta
            if (Posizione.distanza(satellite.getPosizione(), pianeta.getPosizione()) ==
                    Posizione.distanza(satelliteN.getPosizione(), pianeta.getPosizione()) &&
                    satelliteN.getCodice() != satellite.getCodice()) {
                return true;
            }
        }

        //Secondo caso: scontro satellite-satellite (pianeti diversi)

        //Due satelliti appartenenti a pianeti diversi possono collidere se la corona circolare con centro la stella
        //descritta dal primo pianeta con il suo satellite si interseca con la corona circolare descritta dal secondo
        //pianeta con il suo satellite

        //si distinguono due sotto casi:

        for (Pianeta pianetaN : sistema.getStella().getListaPianeti()) {
            //Se il pianeta relativo al satellite inserito é più vicino alla stella rispetto al pianeta
            //associato al secondo satellite
            if (Posizione.distanza(sistema.getStella().getPosizione(), pianeta.getPosizione()) <
                    Posizione.distanza(sistema.getStella().getPosizione(), pianetaN.getPosizione())) {
                //allora applico
                // d(stella, pianeta) + d(pianeta, satellite) < |d(stella, pianetaN) - d(pianetaN, satellite)|,
                //ritorna true se tale condizione non é verificata
                for (Satellite satelliteN : pianetaN.getListaSatelliti()) {
                    //applico la formula matematica
                    if ((Posizione.distanza(sistema.getStella().getPosizione(), pianeta.getPosizione()) +
                            Posizione.distanza(pianeta.getPosizione(), satellite.getPosizione())) >=
                            Math.abs(Posizione.distanza(sistema.getStella().getPosizione(), pianetaN.getPosizione()) -
                                    Posizione.distanza(pianetaN.getPosizione(), satelliteN.getPosizione()))) {
                        return true;
                    }
                }
            }
            //Se il pianeta relativo al satellite inserito é più lontano dalla stella rispetto al pianeta
            //associato al secondo satellite, controllando che non si tratti dello stesso pianeta
            else if (pianetaN.getCodice() != pianeta.getCodice()){
                //applico
                // |d(stella, pianeta) - d(pianeta, satellite)| > (d(stella, pianetaN) + d(pianetaN, satellite))
                //ritorna true se tale condizione non é verificata
                for (Satellite satelliteN : pianetaN.getListaSatelliti()) {
                    //applico la formula matematica
                    if (Math.abs(Posizione.distanza(sistema.getStella().getPosizione(), pianeta.getPosizione()) -
                            Posizione.distanza(pianeta.getPosizione(), satellite.getPosizione())) <=
                            (Posizione.distanza(sistema.getStella().getPosizione(), pianetaN.getPosizione()) +
                                    Posizione.distanza(pianetaN.getPosizione(), satelliteN.getPosizione()))) {
                        return true;
                    }
                }
            }
        }

        //Terzo caso: scontro satellite-stella

        //Un satellite può scontrarsi contro la stella se la distanza del satellite dal suo pianeta associato
        //é uguale alla distanza fra il suo pianeta associato e la stella. Essendo l'ultima condizione
        //il suo risultato sarà determinante per il valore di ritorno.

        return Posizione.distanza(pianeta.getPosizione(), satellite.getPosizione()) ==
                Posizione.distanza(pianeta.getPosizione(), sistema.getStella().getPosizione());
    }



    /**
     * Controllo che vi sia una collisione, iterando in tutti i corpi del sistema.
     *
     * @param sistema sistema a cui appartengono i corpi controllati
     * @return true se si verifica una collisione, false in caso contrario
     */
    public static boolean controlloSistema(SistemaStellare sistema) {
        for (Pianeta pianetaN : sistema.getStella().getListaPianeti()) {
            if (collisionePianeta(sistema, pianetaN)) {
                return true;
            }
            for (Satellite satelliteN : pianetaN.getListaSatelliti()) {
                if (collisioneSatellite(sistema, satelliteN))
                    return true;
            }
        }
        return false;
    }
}
