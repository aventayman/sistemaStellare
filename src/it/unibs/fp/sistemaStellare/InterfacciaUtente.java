package it.unibs.fp.sistemaStellare;

import it.unibs.fp.mylib.InputDati;

import java.util.ArrayList;
import java.util.Collections;

public abstract class InterfacciaUtente {
    private static final String SEPARATORE = "------------------------------------------------";
    public static SistemaStellare start() {
        String nomeSistema = InputDati.leggiStringaNonVuota("""
                Buongiorno comandante!
                Benvenuto nel sistema di censimento di sistemi stellari!
                Inserire il nome del sistema stellare:\s""");

        String nomeStella = InputDati.leggiStringaNonVuota("Inserire il nome della stella: ");

        double massaStella = InputDati.leggiDouble("Inserire la massa della stella: ");
        while (massaStella <= 0) {
            massaStella = InputDati.leggiDouble("La massa inserita non è valida: ");
        }

        Stella stella = new Stella(0, massaStella, nomeStella);

        System.out.printf("Ottimo! Il sistema %s è stato creato correttamente!%n", nomeSistema);
        return new SistemaStellare(nomeSistema, stella);
    }

    public static void printMenuPrincipale() {
        System.out.println(SEPARATORE);
        System.out.print("""
                Le diamo il benvenuto all'interno del menù del programma.
                Scegliere l'opzione desiderata:
                
                > Premere 'g' per inserire o rimuovere corpi celesti
                > Premere 'b' per visualizzare il baricentro del sistema
                > Premere 's' per ricercare un corpo celeste
                > Premere 'c' per calcolare la rotta fra due corpi celesti
                > Premere 'x' per verificare la possibilità di una collisione
                
                """);
    }

    public static void printBaricentro(SistemaStellare sistema) {
        System.out.println(sistema.getBaricentro());
    }

    public static void inserisciPianeta(SistemaStellare sistema) {
        String nome = InputDati.leggiStringaNonVuota("Inserire il nome del pianeta: ");
        double massa = InputDati.leggiDouble("Inserire la massa del pianeta: ");
        while (massa <= 0) {
            massa = InputDati.leggiDouble("La massa inserita non è valida: ");
        }

        double x, y;
        //Facciamo inserire valori della posizione finché essa non è valida
        x = InputDati.leggiDouble("Inserire il valore x della posizione: ");
        y = InputDati.leggiDouble("Inserire il valore y della posizione: ");
        while (!Posizione.posizioneValida(x, y, sistema)) {
            System.out.println("La posizione inserita è già occupata da un altro corpo!");
            x = InputDati.leggiDouble("Reinserire il valore x della posizione: ");
            y = InputDati.leggiDouble("Reinserire il valore y della posizione: ");
        }

        int codice = Collections.max(Ricerca.codiceNome.keySet()) + 1;

        Pianeta pianeta = new Pianeta(codice, massa, nome, new Posizione((float)x, (float)y));

        sistema.aggiungiPianeta(pianeta);

        System.out.println("Il pianeta è stato aggiunto con successo!");
        System.out.println(SEPARATORE);
    }

    public static void inserisciSatellite(SistemaStellare sistema) {
        String nomePianeta = InputDati.leggiStringaNonVuota("Inserire il nome del pianeta associato: ");
        ArrayList<Pianeta> pianetiOmonimi;

        pianetiOmonimi = Ricerca.getPianetiByNome(nomePianeta, sistema);

        //Controllo che nella lista pianetiOmonimi esista almeno un'istanza del pianeta in questione
        while (pianetiOmonimi.size() == 0) {
            nomePianeta = InputDati.leggiStringaNonVuota("Il pianeta richiesto non esiste, reinserire il nome: ");
            pianetiOmonimi = Ricerca.getPianetiByNome(nomePianeta, sistema);
        }

        int indicePianeta = 0;

        //Se sono presenti pianeti omonimi stampa a schermo una lista tra cui scegliere
        if (pianetiOmonimi.size() > 1) {
            System.out.println(String.format("%20s\t%6s\t%6s\t%16s\t%10s\n",
                "Nome", "Codice", "Massa", "Coordinate", "N-Satelliti"));
            for (int i = 0; i < pianetiOmonimi.size(); i++) {
                System.out.println(String.format("%d - ", i + 1) + pianetiOmonimi.get(i));
            }
            indicePianeta = InputDati.leggiIntero("Inserire il numero del pianeta desiderato: ") - 1;
            while (indicePianeta >= pianetiOmonimi.size()) {
                indicePianeta = InputDati.leggiIntero("Numero non valido, reinserire: ") - 1;
            }
        }

        String nome = InputDati.leggiStringaNonVuota("Inserire il nome del satellite: ");
        double massa = InputDati.leggiDouble("Inserire la massa del satellite: ");
        while (massa <= 0) {
            massa = InputDati.leggiDouble("La massa inserita non è valida: ");
        }

        double x, y;
        //Facciamo inserire valori della posizione finché essa non è valida
        x = InputDati.leggiDouble("Inserire il valore x della posizione: ");
        y = InputDati.leggiDouble("Inserire il valore y della posizione: ");
        while (!Posizione.posizioneValida(x, y, sistema)) {
            System.out.println("La posizione inserita è già occupata da un altro corpo!");
            x = InputDati.leggiDouble("Reinserire il valore x della posizione: ");
            y = InputDati.leggiDouble("Reinserire il valore y della posizione: ");
        }

        int codice = Collections.max(Ricerca.codiceNome.keySet()) + 1;

        Satellite satellite = new Satellite(codice, massa, nome, new Posizione((float)x, (float)y),
                pianetiOmonimi.get(indicePianeta));

        sistema.aggiungiSatellite(satellite, pianetiOmonimi.get(indicePianeta));

        System.out.println("Il satellite è stato aggiunto con successo!");
        System.out.println(SEPARATORE);
    }

    public static void rimuoviPianeta(SistemaStellare sistema) {
        String nomePianeta = InputDati.leggiStringaNonVuota("Inserire il nome del pianeta da rimuovere: ");
        ArrayList<Pianeta> pianetiOmonimi;

        pianetiOmonimi = Ricerca.getPianetiByNome(nomePianeta, sistema);

        //Controllo che nella lista pianetiOmonimi esista almeno un'istanza del pianeta in questione
        while (pianetiOmonimi.size() == 0) {
            nomePianeta = InputDati.leggiStringaNonVuota("Il pianeta richiesto non esiste, reinserire il nome: ");
            pianetiOmonimi = Ricerca.getPianetiByNome(nomePianeta, sistema);
        }

        int indicePianeta = 0;

        //Se sono presenti pianeti omonimi stampa a schermo una lista tra cui scegliere
        if (pianetiOmonimi.size() > 1) {
            System.out.println(String.format("%20s\t%6s\t%6s\t%16s\t%10s\n",
                "Nome", "Codice", "Massa", "Coordinate", "N-Satelliti"));
            for (int i = 0; i < pianetiOmonimi.size(); i++) {
                System.out.println(String.format("%d - ", i + 1) + pianetiOmonimi.get(i));
            }
            indicePianeta = InputDati.leggiIntero("Inserire il numero del pianeta desiderato: ") - 1;
            while (indicePianeta >= pianetiOmonimi.size()) {
                indicePianeta = InputDati.leggiIntero("Numero non valido, reinserire: ") - 1;
            }
        }

        sistema.rimuoviPianeta(pianetiOmonimi.get(indicePianeta));

        System.out.println("Il pianeta è stato rimosso con successo!");
        System.out.println(SEPARATORE);
    }

    public static void gestioneCorpi(SistemaStellare sistema) {
        System.out.println(SEPARATORE);
        System.out.print("""
                Scegliere l'opzione desiderata:
                
                > Premere 'I' per inserire un pianeta
                > Premere 'R' per rimuovere un pianeta
                > Premere 'i' per inserire un satellite
                > Premere 'r' per rimuovere un satellite
                > Premere 'm' per tornare al menu principale
                
                """);

        boolean running = true;
        while (running) {
            char scelta = InputDati.leggiChar("> ");

            switch (scelta) {
                case 'm' -> running = false;
                case 'I' -> {
                    inserisciPianeta(sistema);
                    running = false;
                }
                case 'i' -> {
                    inserisciSatellite(sistema);
                    running = false;
                }
                case 'R' -> {
                    rimuoviPianeta(sistema);
                    running = false;
                }
                default -> System.out.println("Il carattere inserito non e' valido!");
            }
        }

    }
}
