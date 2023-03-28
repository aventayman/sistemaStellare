package it.unibs.fp.sistemaStellare;

import it.unibs.fp.mylib.InputDati;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Una classe per raggruppare tutti i metodi statici necessari per interagire con l'utente.
 */
public abstract class InterfacciaUtente {
    private static final String SEPARATORE = "------------------------------------------------";

    /**
     * La prima interazione che si ha con l'utente, che costruisce il sistema stellare e la stella a lui associata.
     * @return il sistema stellare che è stato creato dall'interazione con l'utente
     */
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

    /**
     * Stampa a schermo il menu principale attraverso il quale l'utente può capire come interagire con il software.
     */
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

    /**
     * Stampa a schermo la posizione del baricentro del sistema.
     * @param sistema il sistema di cui si vuole calcolare il baricentro
     */
    public static void printBaricentro(SistemaStellare sistema) {
        System.out.println(sistema.getBaricentro());
    }

    /**
     * L'interazione con l'utente che permette d'inserire un pianeta all'interno del sistema.
     * @param sistema il sistema all'interno del quale si vuole inserire il pianeta
     */
    public static void inserisciPianeta(SistemaStellare sistema) {
        String nome = InputDati.leggiStringaNonVuota("Inserire il nome del pianeta " +
                "('m' per tornare al menu): ");

        if (nome.equals("m")) {
            return;
        }

        double massa = InputDati.leggiDouble("Inserire la massa del pianeta: ");

        //Controllo che la massa non sia negativa o nulla
        while (massa <= 0) {
            massa = InputDati.leggiDouble("La massa inserita non è valida: ");
        }

        double x, y;
        //Inserimento valori della posizione finché essa non è valida
        x = InputDati.leggiDouble("Inserire il valore x della posizione: ");
        y = InputDati.leggiDouble("Inserire il valore y della posizione: ");
        while (!Posizione.posizioneValida(x, y, sistema)) {
            System.out.println("La posizione inserita è già occupata da un altro corpo!");
            x = InputDati.leggiDouble("Reinserire il valore x della posizione: ");
            y = InputDati.leggiDouble("Reinserire il valore y della posizione: ");
        }

        //Al pianeta viene assegnato il codice del corpo celeste con codice di valore più alto maggiorato di uno
        int codice = Collections.max(Ricerca.codiceNome.keySet()) + 1;

        Pianeta pianeta = new Pianeta(codice, massa, nome, new Posizione((float)x, (float)y));

        sistema.aggiungiPianeta(pianeta);

        System.out.println("Il pianeta è stato aggiunto con successo!");
        System.out.println(SEPARATORE);
    }

    /**
     * L'interazione con l'utente che permette d'inserire un nuovo satellite a uno dei pianeti del sistema.
     * @param sistema il sistema stellare dove risiede il satellite
     */
    public static void inserisciSatellite(SistemaStellare sistema) {
        //Controllo che esista almeno un pianeta all'interno del sistema
        if (Ricerca.codiceNome.size() < 2) {
            System.out.println("Il sistema non contiene pianeti, prima di proseguire aggiungere almeno un pianeta!");
            return;
        }

        String nomePianeta = InputDati.leggiStringaNonVuota("Inserire il nome del pianeta associato " +
                "('m' per tornare al menu): ");

        if (nomePianeta.equals("m")) {
            return;
        }

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
        //Inserimento valori della posizione finché essa non è valida
        x = InputDati.leggiDouble("Inserire il valore x della posizione: ");
        y = InputDati.leggiDouble("Inserire il valore y della posizione: ");
        while (!Posizione.posizioneValida(x, y, sistema)) {
            System.out.println("La posizione inserita è già occupata da un altro corpo!");
            x = InputDati.leggiDouble("Reinserire il valore x della posizione: ");
            y = InputDati.leggiDouble("Reinserire il valore y della posizione: ");
        }

        //Al satellite viene assegnato il codice del corpo celeste con codice di valore più alto maggiorato di uno
        int codice = Collections.max(Ricerca.codiceNome.keySet()) + 1;

        Satellite satellite = new Satellite(codice, massa, nome, new Posizione((float)x, (float)y),
                pianetiOmonimi.get(indicePianeta));

        sistema.aggiungiSatellite(satellite, pianetiOmonimi.get(indicePianeta));

        System.out.println("Il satellite è stato aggiunto con successo!");
        System.out.println(SEPARATORE);
    }

    /**
     * L'interazione con l'utente che permette di rimuovere un pianeta dato il suo nome.
     * @param sistema il sistema stellare dove risiede il pianeta
     */
    public static void rimuoviPianeta(SistemaStellare sistema) {
        //Controllo che esista almeno un pianeta all'interno del sistema
        if (Ricerca.codiceNome.size() < 2) {
            System.out.println("Il sistema non contiene pianeti, prima di proseguire aggiungere almeno un pianeta!");
            return;
        }

        String nomePianeta = InputDati.leggiStringaNonVuota("Inserire il nome del pianeta da rimuovere " +
                "('m' per tornare al menu): ");

        if (nomePianeta.equals("m")) {
            return;
        }

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

    public static void rimuoviSatellite(SistemaStellare sistema) {
        //Controllo che esista almeno un satellite nel sistema
        if (!Ricerca.esisteSatellite(sistema)) {
            System.out.println("Non esiste nessun satellite all'interno del sistema!");
            return;
        }

        String nomeSatellite = InputDati.leggiStringaNonVuota("Inserire il nome del satellite da rimuovere " +
                "('m' per tornare al menu): ");

        if (nomeSatellite.equals("m")) {
            return;
        }

        ArrayList<Satellite> satellitiOmonimi;

        satellitiOmonimi = Ricerca.getSatellitiByNome(nomeSatellite, sistema);

        //Controllo che nella lista satellitiOmonimi esista almeno un'istanza del satellite in questione
        while (satellitiOmonimi.size() == 0) {
            nomeSatellite = InputDati.leggiStringaNonVuota("Il satellite richiesto non esiste, reinserire il nome: ");
            satellitiOmonimi = Ricerca.getSatellitiByNome(nomeSatellite, sistema);
        }

        int indiceSatellite = 0;

        //Se sono presenti satelliti omonimi stampa a schermo una lista tra cui scegliere
        if (satellitiOmonimi.size() > 1) {
            System.out.println(String.format("%20s\t%6s\t%6s\t%16s\t%10s\n",
                "Nome", "Codice", "Massa", "Coordinate", "Pianeta Associato"));
            for (int i = 0; i < satellitiOmonimi.size(); i++) {
                System.out.println(String.format("%d - ", i + 1) + satellitiOmonimi.get(i));
            }
            indiceSatellite = InputDati.leggiIntero("Inserire il numero del satellite desiderato: ") - 1;
            while (indiceSatellite >= satellitiOmonimi.size()) {
                indiceSatellite = InputDati.leggiIntero("Numero non valido, reinserire: ") - 1;
            }
        }

        Pianeta pianetaSatellite = new Pianeta();

        for (int i = 0; i < sistema.getStella().getListaPianeti().size(); i++) {
            if (sistema.getStella().getListaPianeti().get(i).getCodice()
                    == satellitiOmonimi.get(indiceSatellite).getCodicePianeta())
                pianetaSatellite = sistema.getStella().getListaPianeti().get(i);
        }

        sistema.rimuoviSatellite(satellitiOmonimi.get(indiceSatellite), pianetaSatellite);

        System.out.println("Il satellite è stato rimosso con successo!");
        System.out.println(SEPARATORE);

    }

    /**
     * L'interazione con l'utente che permette di stampare a video i dati dei corpi celesti
     * corrispondenti a un nome.
     * @param sistema sistema all'interno del quale avviene la ricerca
     */
    public static void printRicerca(SistemaStellare sistema) {
        String nome = InputDati.leggiStringaNonVuota("Inserire il nome del corpo celeste da ricercare " +
                "('m' per tornare al menu): ");

        //Conta il numero di corrispondenze
        int counter = 0;

        if (nome.equals("m")) {
            return;
        }

        //Stampa della stella
        if (Ricerca.isNomeStella(nome, sistema)) {
            System.out.println("Stella:");
            System.out.println(String.format("%20s\t%6s\t%6s\t%16s\t\n",
                    "Nome", "Codice", "Massa", "Coordinate"));
            counter++;
            System.out.printf("%d - ", counter);
            System.out.println(sistema.getStella());
        }

        //Stampa del menu pianeti
        if (Ricerca.getPianetiByNome(nome, sistema).size() > 0) {
            System.out.println("Pianeta:");
            System.out.println(String.format("%20s\t%6s\t%6s\t%16s\t%10s\n",
                    "Nome", "Codice", "Massa", "Coordinate", "N-Satelliti"));
        }

        //Stampa dei pianeti
        for (int i = 0; i < Ricerca.getPianetiByNome(nome, sistema).size(); i++) {
            System.out.printf("%d - ", ++counter);
            System.out.println(Ricerca.getPianetiByNome(nome, sistema).get(i));
        }

        //Stampa del menu satelliti
        if (Ricerca.getSatellitiByNome(nome, sistema).size() > 0) {
            System.out.println("Satellite:");
            System.out.println(String.format("%20s\t%6s\t%6s\t%16s\t%10s\n",
                    "Nome", "Codice", "Massa", "Coordinate", "Pianeta Associato"));
        }

        //Stampa dei satelliti
        for (int i = 0; i < Ricerca.getSatellitiByNome(nome, sistema).size(); i++) {
            System.out.printf("%d - ", ++counter);
            System.out.println(Ricerca.getSatellitiByNome(nome, sistema).get(i));
        }

        if (counter == 1)
            System.out.println("E' stata trovata 1 corrispondenza con il nome dato.");
        else
            System.out.printf("Sono state trovate %d corrispondenze.\n", counter);
    }

    /**
     * Il menu che permette all'utente di gestire i corpi celesti all'interno del sistema.
     * @param sistema il sistema dove devono avvenire le modifiche
     */
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
                case 'r' -> {
                    rimuoviSatellite(sistema);
                    running = false;
                }
                default -> System.out.println("Il carattere inserito non e' valido!");
            }
        }

    }
}
