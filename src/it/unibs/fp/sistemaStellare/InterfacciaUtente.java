package it.unibs.fp.sistemaStellare;

import it.unibs.fp.mylib.InputDati;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Una classe per raggruppare tutti i metodi statici necessari per interagire con l'utente.
 */
public abstract class InterfacciaUtente {
    private static final String SEPARATORE = "----------------------------------------------------------------------";
    private static final String TABELLA_STELLA =
            "\t           Nome | Codice |   Massa   |    Coordinate    | Sistema Stellare";
    private static final String TABELLA_PIANETA =
            "\t           Nome | Codice |   Massa   |    Coordinate    | N° Satelliti";
    private static final String TABELLA_SATELLITE =
            "\t           Nome | Codice |   Massa   |    Coordinate    | Percorso (Stella > Pianeta > Satellite)";

    /**
     * La prima interazione che si ha con l'utente, che costruisce il sistema stellare e la stella a lui associata.
     *
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

        Stella stella = new Stella(0, massaStella, nomeStella, nomeSistema);

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
                > Premere 's' per ricercare un corpo celeste ('h' per info sul funzionamento)
                > Premere 'c' per calcolare la rotta fra due corpi celesti
                > Premere 'x' per verificare la possibilità di una collisione
                                
                """);
    }

    /**
     * Se l'utente desidera capire meglio come funziona la ricerca, può accedere a questo testo nel menu.
     */
    public static void printInfoRicerca() {
        System.out.println("""
                                
                La ricerca non è case sensitive e funziona attraverso i glob pattern:
                                
                * : Rappresenta una sequenza di zero o più caratteri qualsiasi (quindi anche una sequenza vuota).
                    Ad esempio, "abc*" trova corrispondenza in "abcde" ma anche in "abc" stesso.
                ? : Rappresenta un singolo carattere qualsiasi, che però deve essere presente.
                    Ad esempio, "ab?" trova corrispondenza in "abc" e in "ab1", ma non in "ab".
                                
                Quindi per ottenere una lista di tutti i corpi celesti all'interno del sistema basta cercare "*",
                mentre per ottenere tutti i corpi che iniziano per 's' o 'S' basta cercare "s*".
                                
                Questo sistema è incorporato in tutti i sistemi di selezione del programma, quindi anche
                all'interno della creazione di nuovi corpi, se al glob pattern corrisponde solo una possibile scelta
                il programma sceglierà quella automaticamente. Ad esempio se si vuole inserire un satellite
                ed esiste un unico pianeta all'interno del programma inserendo come pianeta associato "*" verrà
                automaticamente selezionato l'unico pianeta disponibile.
                """);
    }

    /**
     * Stampa a schermo la posizione del baricentro del sistema.
     *
     * @param sistema il sistema di cui si vuole calcolare il baricentro
     */
    public static void printBaricentro(SistemaStellare sistema) {
        System.out.println("Il baricentro complessivo del sistema si trova nel punto: " + sistema.getBaricentro());
    }

    /**
     * L'interazione con l'utente che permette d'inserire un pianeta all'interno del sistema.
     *
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
        while (Posizione.posizioneNotValida(x, y, sistema)) {
            System.out.println("La posizione inserita è già occupata da un altro corpo!");
            x = InputDati.leggiDouble("Reinserire il valore x della posizione: ");
            y = InputDati.leggiDouble("Reinserire il valore y della posizione: ");
        }

        //Al pianeta viene assegnato il codice del corpo celeste con codice di valore più alto maggiorato di uno
        int codice = Collections.max(Ricerca.codiceNome.keySet()) + 1;

        Pianeta pianeta = new Pianeta(codice, massa, nome, new Posizione((float) x, (float) y));

        sistema.aggiungiPianeta(pianeta);

        System.out.println("Il pianeta è stato aggiunto con successo!");
        System.out.println(SEPARATORE);
    }

    /**
     * L'interazione con l'utente che permette d'inserire un nuovo satellite a uno dei pianeti del sistema.
     *
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
            System.out.println("\t           Nome | Codice |  Massa  |    Coordinate    | N° Satelliti");
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
        while (Posizione.posizioneNotValida(x, y, sistema)) {
            System.out.println("La posizione inserita è già occupata da un altro corpo!");
            x = InputDati.leggiDouble("Reinserire il valore x della posizione: ");
            y = InputDati.leggiDouble("Reinserire il valore y della posizione: ");
        }

        //Al satellite viene assegnato il codice del corpo celeste con codice di valore più alto maggiorato di uno
        int codice = Collections.max(Ricerca.codiceNome.keySet()) + 1;

        Satellite satellite = new Satellite(codice, massa, nome, new Posizione((float) x, (float) y),
                pianetiOmonimi.get(indicePianeta));

        sistema.aggiungiSatellite(satellite, pianetiOmonimi.get(indicePianeta));

        System.out.println("Il satellite è stato aggiunto con successo!");
        System.out.println(SEPARATORE);
    }

    /**
     * L'interazione con l'utente che permette di rimuovere un pianeta dato il suo nome.
     *
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
            System.out.println("\t           Nome | Codice |  Massa  |    Coordinate    | N° Satelliti");
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

    /**
     * L'interazione con l'utente che permette di rimuovere un satellite dato il suo nome.
     *
     * @param sistema il sistema stellare dove risiede il satellite
     */
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
            System.out.println(TABELLA_SATELLITE);
            for (int i = 0; i < satellitiOmonimi.size(); i++) {
                System.out.println(String.format("%d - ", i + 1) + satellitiOmonimi.get(i));
            }
            indiceSatellite = InputDati.leggiIntero("Inserire il numero del satellite desiderato: ") - 1;
            while (indiceSatellite >= satellitiOmonimi.size() || indiceSatellite < 0) {
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
     * e su richiesta dell'utente visualizzare i satelliti associati a un pianeta
     * corrispondenti a un nome.
     *
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
            System.out.println(TABELLA_STELLA);
            counter++;
            System.out.printf("%d - ", counter);
            System.out.println(sistema.getStella());
        }

        //Stampa del menu pianeti
        if (Ricerca.getPianetiByNome(nome, sistema).size() > 0) {
            System.out.println("Pianeta:");
            System.out.println(TABELLA_PIANETA);
        }

        //Stampa dei pianeti
        for (int i = 0; i < Ricerca.getPianetiByNome(nome, sistema).size(); i++) {
            System.out.printf("%d - ", ++counter);
            System.out.println(Ricerca.getPianetiByNome(nome, sistema).get(i));
        }

        //Stampa del menu satelliti
        if (Ricerca.getSatellitiByNome(nome, sistema).size() > 0) {
            System.out.println("Satellite:");
            System.out.println(TABELLA_SATELLITE);
        }

        //Stampa dei satelliti
        for (int i = 0; i < Ricerca.getSatellitiByNome(nome, sistema).size(); i++) {
            System.out.printf("%d - ", ++counter);
            System.out.println(Ricerca.getSatellitiByNome(nome, sistema).get(i));
        }

        if (counter == 1)
            System.out.println("\nE' stata trovata 1 corrispondenza con il nome dato.");
        else
            System.out.printf("\nSono state trovate %d corrispondenze.\n", counter);

        System.out.println(SEPARATORE);

        boolean running = true;
        //Se ci sono pianeti, possibilità di visualizzarne le lune
        while (Ricerca.codiceNome.size() > 1 && running) {
            String nomePianeta = InputDati.leggiStringaNonVuota("Inserire il nome del pianeta di cui " +
                    "si vogliono visualizzare i satelliti ('m' per tornare al menu): ");

            if (nomePianeta.equals("m")) {
                System.out.println(SEPARATORE);
                return;
            }

            ArrayList<Pianeta> pianetiOmonimi;

            pianetiOmonimi = Ricerca.getPianetiByNome(nomePianeta, sistema);

            //Controllo che nella lista pianetiOmonimi esista almeno un'istanza del pianeta in questione
            while (pianetiOmonimi.size() == 0) {
                nomePianeta = InputDati.leggiStringaNonVuota("Il pianeta richiesto non esiste, reinserire il nome " +
                        "('m' per tornare al menu): ");
                if (nomePianeta.equals("m")) {
                    System.out.println(SEPARATORE);
                    return;
                }
                pianetiOmonimi = Ricerca.getPianetiByNome(nomePianeta, sistema);
            }

            int indicePianeta = 0;

            //Se sono presenti pianeti omonimi stampa a schermo una lista tra cui scegliere
            if (pianetiOmonimi.size() > 1) {
                System.out.println(TABELLA_PIANETA);
                for (int i = 0; i < pianetiOmonimi.size(); i++) {
                    System.out.println(String.format("%d - ", i + 1) + pianetiOmonimi.get(i));
                }
                indicePianeta = InputDati.leggiIntero("Inserire il numero del pianeta desiderato: ") - 1;
                while (indicePianeta >= pianetiOmonimi.size() || indicePianeta < 0) {
                    indicePianeta = InputDati.leggiIntero("Numero non valido, reinserire: ") - 1;
                }
            }

            if (pianetiOmonimi.get(indicePianeta).getListaSatelliti().size() > 0) {
                running = false;
                System.out.println(TABELLA_SATELLITE);
                for (int i = 0; i < pianetiOmonimi.get(indicePianeta).getListaSatelliti().size(); i++) {
                    System.out.printf("%d - ", i + 1);
                    System.out.println(pianetiOmonimi.get(indicePianeta).getListaSatelliti().get(i));
                }
            } else
                System.out.println("Il pianeta selezionato non possiede satelliti!");

            System.out.println(SEPARATORE);
        }
    }

    /**
     * Il menu che permette all'utente di gestire i corpi celesti all'interno del sistema.
     *
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

    /**
     * L'interazione con l'utente che permette di calcolare il percorso fra un corpo celeste e un altro
     * all'interno di un sistema stellare.
     *
     * @param sistema il sistema all'interno del quale si vuole calcolare la rotta
     */
    public static void calcolaRotta(SistemaStellare sistema) {
        //Controllo che esista almeno un pianeta all'interno del sistema
        if (Ricerca.codiceNome.size() < 2) {
            System.out.println("Il sistema non contiene pianeti, prima di proseguire aggiungere almeno un pianeta!");
            return;
        }

        String nome1 = InputDati.leggiStringaNonVuota("Inserire il nome del primo corpo celeste " +
                "('m' per tornare al menu): ");
        if (nome1.equals("m")) {
            return;
        }

        //ArrayList di pianetiOmonimi se esistono
        ArrayList<Pianeta> pianetiOmonimi;
        pianetiOmonimi = Ricerca.getPianetiByNome(nome1, sistema);

        //ArrayList di satellitiOmonimi se esistono
        ArrayList<Satellite> satellitiOmonimi;
        satellitiOmonimi = Ricerca.getSatellitiByNome(nome1, sistema);

        //Controllo che esista almeno un corpo celeste che possiede quel nome
        while (pianetiOmonimi.size() == 0 && satellitiOmonimi.size() == 0 && !Ricerca.isNomeStella(nome1, sistema)) {
            nome1 = InputDati.leggiStringaNonVuota("Il corpo celeste richiesto non esiste, reinserire il nome: ");
            pianetiOmonimi = Ricerca.getPianetiByNome(nome1, sistema);
            satellitiOmonimi = Ricerca.getSatellitiByNome(nome1, sistema);
        }

        int stellaPresente = 0;

        if (Ricerca.isNomeStella(nome1, sistema))
            stellaPresente++;

        //Inizializzo il primo e il secondo corpo celeste
        CorpoCeleste corpo1 = new CorpoCeleste();
        CorpoCeleste corpo2 = new CorpoCeleste();

        //Se esiste più di un corpo celeste con il nome dato stampare una lista di scelta
        if (pianetiOmonimi.size() + satellitiOmonimi.size() + stellaPresente > 1) {

            int counter = 0;

            //Stampa della stella
            if (Ricerca.isNomeStella(nome1, sistema)) {
                System.out.println("Stella:");
                System.out.println(TABELLA_STELLA);
                System.out.printf("%d - ", ++counter);
                System.out.println(sistema.getStella());
            }

            //Stampa del menu pianeti
            if (pianetiOmonimi.size() > 0) {
                System.out.println("Pianeta:");
                System.out.println(TABELLA_PIANETA);
            }

            //Stampa dei pianeti
            for (Pianeta pianeta : pianetiOmonimi) {
                System.out.printf("%d - ", ++counter);
                System.out.println(pianeta);
            }

            //Stampa del menu satelliti
            if (satellitiOmonimi.size() > 0) {
                System.out.println("Satellite:");
                System.out.println(TABELLA_SATELLITE);
            }

            //Stampa dei satelliti
            for (Satellite satellite : satellitiOmonimi) {
                System.out.printf("%d - ", ++counter);
                System.out.println(satellite);
            }

            int indice = InputDati.leggiIntero("Inserire il numero del corpo celeste desiderato: ") - 1;
            while (indice >= pianetiOmonimi.size() + satellitiOmonimi.size() + stellaPresente || indice < 0)
                indice = InputDati.leggiIntero("Numero non valido, reinserire: ") - 1;

            //Se l'indice scelto dall'utente è minore della lunghezza della lista dei pianeti corrispondenti
            //più l'eventuale presenza della stella allora assegno al corpo il pianeta all'indice dato
            //tenendo conto dell'eventuale presenza della stella
            if (indice < stellaPresente + pianetiOmonimi.size()) {
                //Se l'indice è zero ed esiste la stella allora il corpo1 è la stella del sistema
                if (indice == 0 && stellaPresente == 1) {
                    corpo1 = sistema.getStella();
                } else {
                    corpo1 = pianetiOmonimi.get(indice - stellaPresente);
                }
            }
            //Altrimenti prendo il satellite all'indice dato sottraendo la quantità di pianeti
            //nella lista dei pianeti e l'eventuale presenza della stella
            else {
                corpo1 = satellitiOmonimi.get(indice - pianetiOmonimi.size() - stellaPresente);
            }
        }
        //Se è presente solo la stella assegnarla al primo corpo celeste
        else if (stellaPresente == 1) {
            corpo1 = sistema.getStella();
        }
        //Se è presente solo un pianeta assegnarlo al primo corpo celeste
        else if (pianetiOmonimi.size() == 1) {
            corpo1 = pianetiOmonimi.get(0);
        }
        //Se è presente solo un satellite assegnarlo al primo corpo celeste
        else if (satellitiOmonimi.size() == 1) {
            corpo1 = satellitiOmonimi.get(0);
        }

        String nome2 = InputDati.leggiStringaNonVuota("Inserire il nome del secondo corpo celeste " +
                "('m' per tornare al menu): ");
        if (nome2.equals("m")) {
            return;
        }

        //Riassegno pianetiOmonimi e satellitiOmonimi per il secondo nome
        pianetiOmonimi = Ricerca.getPianetiByNome(nome2, sistema);
        satellitiOmonimi = Ricerca.getSatellitiByNome(nome2, sistema);

        //Controllo che esista almeno un corpo celeste che possiede quel nome
        while (pianetiOmonimi.size() == 0 && satellitiOmonimi.size() == 0 && !Ricerca.isNomeStella(nome2, sistema)) {
            nome2 = InputDati.leggiStringaNonVuota("Il corpo celeste richiesto non esiste, reinserire il nome: ");
            pianetiOmonimi = Ricerca.getPianetiByNome(nome2, sistema);
            satellitiOmonimi = Ricerca.getSatellitiByNome(nome2, sistema);
        }

        stellaPresente = 0;

        if (Ricerca.isNomeStella(nome2, sistema) && corpo1.getCodice() != sistema.getStella().getCodice())
            stellaPresente++;

        //Questa volta però bisogna eliminare il corpo celeste selezionato per primo dalle due liste
        //in modo che non possa essere poi visualizzato e selezionato
        for (Pianeta pianeta : pianetiOmonimi)
            if (pianeta.getCodice() == corpo1.getCodice()) {
                pianetiOmonimi.remove(pianeta);
                //Una volta trovato il pianeta o satellite da rimuovere il ciclo può fermarsi
                break;
            }

        for (Satellite satellite : satellitiOmonimi)
            if (satellite.getCodice() == corpo1.getCodice()) {
                satellitiOmonimi.remove(satellite);
                //Una volta trovato il pianeta o satellite da rimuovere il ciclo può fermarsi
                break;
            }

        //Se esiste più di un corpo celeste con il nome dato stampare una lista di scelta
        if (pianetiOmonimi.size() + satellitiOmonimi.size() + stellaPresente > 1) {

            int counter = 0;

            //Stampa della stella se il nome è uguale e se non è già stata selezionata
            if (Ricerca.isNomeStella(nome2, sistema) && corpo1.getCodice() != sistema.getStella().getCodice()) {
                System.out.println("Stella:");
                System.out.println(TABELLA_STELLA);
                System.out.printf("%d - ", ++counter);
                System.out.println(sistema.getStella());
            }

            //Stampa del menu pianeti
            if (pianetiOmonimi.size() > 0) {
                System.out.println("Pianeta:");
                System.out.println(TABELLA_PIANETA);
            }

            //Stampa dei pianeti
            for (Pianeta pianeta : pianetiOmonimi) {
                System.out.printf("%d - ", ++counter);
                System.out.println(pianeta);
            }

            //Stampa del menu satelliti
            if (satellitiOmonimi.size() > 0) {
                System.out.println("Satellite:");
                System.out.println(TABELLA_SATELLITE);
            }

            //Stampa dei satelliti
            for (Satellite satellite : satellitiOmonimi) {
                System.out.printf("%d - ", ++counter);
                System.out.println(satellite);
            }

            int indice = InputDati.leggiIntero("Inserire il numero del corpo celeste desiderato: ") - 1;
            while (indice >= pianetiOmonimi.size() + satellitiOmonimi.size() + stellaPresente || indice < 0)
                indice = InputDati.leggiIntero("Numero non valido, reinserire: ") - 1;

            //Se l'indice scelto dall'utente è minore della lunghezza della lista dei pianeti corrispondenti
            //più l'eventuale presenza della stella allora assegno al corpo il pianeta all'indice dato
            //tenendo conto dell'eventuale presenza della stella
            if (indice < stellaPresente + pianetiOmonimi.size()) {
                if (indice == 0 && stellaPresente == 1) {
                    corpo2 = sistema.getStella();
                } else {
                    corpo2 = pianetiOmonimi.get(indice - stellaPresente);
                }
            }
            //Altrimenti prendo il satellite all'indice dato sottraendo la quantità di pianeti
            //nella lista dei pianeti e l'eventuale presenza della stella
            else {
                corpo2 = satellitiOmonimi.get(indice - pianetiOmonimi.size() - stellaPresente);
            }
        }
        //Se è presente solo la stella assegnarla al primo corpo celeste
        else if (stellaPresente == 1) {
            corpo2 = sistema.getStella();
        }
        //Se è presente solo un pianeta assegnarlo al primo corpo celeste
        else if (pianetiOmonimi.size() == 1) {
            corpo2 = pianetiOmonimi.get(0);
        }
        //Se è presente solo un satellite assegnarlo al primo corpo celeste
        else if (satellitiOmonimi.size() == 1) {
            corpo2 = satellitiOmonimi.get(0);
        }

        //Ora si entra nella parte vera e propria di stampa a video della rotta fra i due corpi
        float distanzaTotale;

        //Se il primo è la stella e il secondo è un pianeta oppure
        //Se il primo è un pianeta e il secondo è la stella oppure
        //Se il primo è un pianeta e il secondo un suo satellite oppure
        //Se il primo è un satellite e il secondo il suo pianeta
        if (corpo1.getCodice() == 0 && Ricerca.isPianeta(corpo2.getCodice(), sistema) ||
                corpo2.getCodice() == 0 && Ricerca.isPianeta(corpo1.getCodice(), sistema) ||
                Ricerca.isPianeta(corpo1.getCodice(), sistema) &&
                        Ricerca.codicePianetaBySatellite(corpo2.getCodice(), sistema) == corpo1.getCodice() ||
                Ricerca.isPianeta(corpo2.getCodice(), sistema) &&
                        Ricerca.codicePianetaBySatellite(corpo1.getCodice(), sistema) == corpo2.getCodice()) {
            distanzaTotale = Posizione.distanza(corpo1.getPosizione(), corpo2.getPosizione());
            System.out.printf("""
                                        
                    La rotta da seguire è:
                    %s > %s
                    La distanza totale da percorrere è di %.2f
                    """, corpo1.getNome(), corpo2.getNome(), distanzaTotale);
            System.out.println(SEPARATORE);
        }

        //Se sono entrambi pianeti
        else if (Ricerca.isPianeta(corpo1.getCodice(), sistema) && Ricerca.isPianeta(corpo2.getCodice(), sistema)) {
            distanzaTotale = Posizione.distanza(corpo1.getPosizione(), sistema.getStella().getPosizione())
                    + Posizione.distanza(corpo2.getPosizione(), sistema.getStella().getPosizione());
            System.out.printf("""
                                        
                    La rotta da seguire è:
                    %s > %s > %s
                    La distanza totale da percorrere è di %.2f
                    """, corpo1.getNome(), sistema.getStella().getNome(), corpo2.getNome(), distanzaTotale);
            System.out.println(SEPARATORE);
        }

        //Se il primo è il satellite e il secondo è la stella o viceversa
        else if (Ricerca.isSatellite(corpo1.getCodice(), sistema) && corpo2.getCodice() == 0 ||
                Ricerca.isSatellite(corpo2.getCodice(), sistema) && corpo1.getCodice() == 0) {
            //Pianeta fra la stella e il satellite
            Pianeta pianeta = new Pianeta();
            for (Pianeta p : sistema.getStella().getListaPianeti())
                //Devo trovare il pianeta e fra i due corpi il satellite sarà quello con codice maggiore
                //siccome la stella ha sempre codice zero
                if (p.getCodice() ==
                        Ricerca.codicePianetaBySatellite(Math.max(corpo1.getCodice(), corpo2.getCodice()), sistema))
                    pianeta = p;

            distanzaTotale = Posizione.distanza(corpo1.getPosizione(), pianeta.getPosizione())
                    + Posizione.distanza(pianeta.getPosizione(), corpo2.getPosizione());

            System.out.printf("""
                                        
                    La rotta da seguire è:
                    %s > %s > %s
                    La distanza totale da percorrere è di %.2f
                    """, corpo1.getNome(), pianeta.getNome(), corpo2.getNome(), distanzaTotale);
            System.out.println(SEPARATORE);
        }

        //Se sono entrambi satelliti dello stesso pianeta
        else if (Ricerca.isSatellite(corpo1.getCodice(), sistema) && Ricerca.isSatellite(corpo2.getCodice(), sistema)
                && Ricerca.codicePianetaBySatellite(corpo1.getCodice(), sistema) ==
                Ricerca.codicePianetaBySatellite(corpo2.getCodice(), sistema)) {
            //Pianeta fra i due satelliti
            Pianeta pianeta = new Pianeta();
            for (Pianeta p : sistema.getStella().getListaPianeti())
                //Devo trovare il pianeta e fra i due satelliti
                if (p.getCodice() ==
                        Ricerca.codicePianetaBySatellite(corpo1.getCodice(), sistema))
                    pianeta = p;

            distanzaTotale = Posizione.distanza(corpo1.getPosizione(), pianeta.getPosizione())
                    + Posizione.distanza(pianeta.getPosizione(), corpo2.getPosizione());

            System.out.printf("""
                                        
                    La rotta da seguire è:
                    %s > %s > %s
                    La distanza totale da percorrere è di %.2f
                    """, corpo1.getNome(), pianeta.getNome(), corpo2.getNome(), distanzaTotale);
            System.out.println(SEPARATORE);
        }

        //Se sono due satelliti di due pianeti diversi
        else if (Ricerca.isSatellite(corpo1.getCodice(), sistema) && Ricerca.isSatellite(corpo2.getCodice(), sistema)
                && Ricerca.codicePianetaBySatellite(corpo1.getCodice(), sistema) !=
                Ricerca.codicePianetaBySatellite(corpo2.getCodice(), sistema)) {
            //Pianeti dei due satelliti
            Pianeta pianeta1 = new Pianeta();
            Pianeta pianeta2 = new Pianeta();
            for (Pianeta p : sistema.getStella().getListaPianeti()) {
                //Devo trovare il pianeta e fra i due satelliti
                if (p.getCodice() ==
                        Ricerca.codicePianetaBySatellite(corpo1.getCodice(), sistema))
                    pianeta1 = p;
                else if (p.getCodice() ==
                        Ricerca.codicePianetaBySatellite(corpo2.getCodice(), sistema))
                    pianeta2 = p;
            }

            distanzaTotale = Posizione.distanza(corpo1.getPosizione(), pianeta1.getPosizione())
                    + Posizione.distanza(pianeta1.getPosizione(), sistema.getStella().getPosizione())
                    + Posizione.distanza(sistema.getStella().getPosizione(), pianeta2.getPosizione())
                    + Posizione.distanza(pianeta2.getPosizione(), corpo2.getPosizione());

            System.out.printf("""
                                                
                            La rotta da seguire è:
                            %s > %s > %s > %s > %s
                            La distanza totale da percorrere è di %.2f
                            """, corpo1.getNome(), pianeta1.getNome(), sistema.getStella().getNome(),
                    pianeta2.getNome(), corpo2.getNome(), distanzaTotale);
            System.out.println(SEPARATORE);
        }

        //L'ultimo caso possibile è che siano il primo un satellite e il secondo un pianeta non a lui associato
        //oppure il primo un pianeta e il secondo un satellite non a lui associato
        else {
            //Il pianeta associato al satellite
            Pianeta pianetaSatellite = new Pianeta();

            //Se il primo corpo è il pianeta
            if (Ricerca.isPianeta(corpo1.getCodice(), sistema)) {
                for (Pianeta p : sistema.getStella().getListaPianeti())
                    //Devo trovare il pianeta associato al secondo corpo, cioè il satellite
                    if (p.getCodice() ==
                            Ricerca.codicePianetaBySatellite(corpo2.getCodice(), sistema))
                        pianetaSatellite = p;

                distanzaTotale = Posizione.distanza(corpo1.getPosizione(), sistema.getStella().getPosizione())
                        + Posizione.distanza(sistema.getStella().getPosizione(), pianetaSatellite.getPosizione())
                        + Posizione.distanza(pianetaSatellite.getPosizione(), corpo2.getPosizione());

                System.out.printf("""
                                                    
                                La rotta da seguire è:
                                %s > %s > %s > %s
                                La distanza totale da percorrere è di %.2f
                                """, corpo1.getNome(), sistema.getStella().getNome(),
                        pianetaSatellite.getNome(), corpo2.getNome(), distanzaTotale);
                System.out.println(SEPARATORE);
            }
            //Se il secondo corpo è il pianeta
            else {
                for (Pianeta p : sistema.getStella().getListaPianeti())
                    //Devo trovare il pianeta associato al secondo corpo, cioè il satellite
                    if (p.getCodice() ==
                            Ricerca.codicePianetaBySatellite(corpo1.getCodice(), sistema))
                        pianetaSatellite = p;

                distanzaTotale = Posizione.distanza(pianetaSatellite.getPosizione(), corpo1.getPosizione())
                        + Posizione.distanza(sistema.getStella().getPosizione(), pianetaSatellite.getPosizione())
                        + Posizione.distanza(corpo2.getPosizione(), sistema.getStella().getPosizione());

                System.out.printf("""
                                                    
                                La rotta da seguire è:
                                %s > %s > %s > %s
                                La distanza totale da percorrere è di %.2f
                                """, corpo1.getNome(), pianetaSatellite.getNome(),
                        sistema.getStella().getNome(), corpo2.getNome(), distanzaTotale);
                System.out.println(SEPARATORE);
            }
        }
    }

    /**
     * Metodo invocato ogni volta che viene inserito o rimosso un pianeta all'interno del sistema
     * Confronta il pianeta con tutti i corpi del sistema e verifica se può verificarsi una collisione
     *
     * @param sistema il sistema all'interno del quale viene eseguita la verifica
     * @param pianeta pianeta su cui svolgere la verifica
     * @return true se si verifica una collisione con un corpo qualsiasi del sistema, false in caso contrario
     */
    public static boolean CollisionePianeta(SistemaStellare sistema, Pianeta pianeta) {
        //Primo caso: scontro pianeta-pianeta
        //Si verifica esclusivamente se due pianeti hanno la stessa distanza, in modulo, dalla stella
        for (int i = 0; i < sistema.getStella().getListaPianeti().size(); i++) {
            //per ogni pianeta del sistema controllo che la sua distanza in modulo dalla stella sia uguale alla
            //distanza in modulo del pianeta inserito
            if (Math.abs(Posizione.distanza(sistema.getStella().getPosizione(), pianeta.getPosizione())) ==
                    Math.abs(Posizione.distanza(sistema.getStella().getPosizione(),
                            sistema.getStella().getListaPianeti().get(i).getPosizione()))) {
                return true;
            }
        }

        //Secondo caso: scontro pianeta-satellite
        //se il pianeta inserito é più vicino alla stella rispetto al pianeta associato al satellite allora applico
        // |d(stella, pianeta1)| < |d(stella, pianeta2) - |d(pianeta2, satellite)||
        for (int i = 0; i < sistema.getStella().getListaPianeti().size(); i++) {
            for (int j = 0; j < sistema.getStella().getListaPianeti().get(i).getListaSatelliti().size(); j++) {
                //applico la formula matematica
                if (Math.abs(Posizione.distanza(sistema.getStella().getPosizione(), pianeta.getPosizione())) <
                        Math.abs(Posizione.distanza(sistema.getStella().getPosizione(),
                                sistema.getStella().getListaPianeti().get(i).getPosizione()) -
                                Math.abs(Posizione.distanza(sistema.getStella().getListaPianeti().get(i).getPosizione(),
                                                sistema.getStella().getListaPianeti().get(i)
                                                        .getListaSatelliti().get(j).getPosizione())))) {
                    return true;
                }
            }
        }

        //se il pianeta inserito é più lontano dalla stella rispetto al pianeta associato al satellite allora applico
        // |d(stella, pianeta1)| > |d(stella, pianeta2) + |d(pianeta2, satellite)||
        for (int i = 0; i < sistema.getStella().getListaPianeti().size(); i++) {
            for (int j = 0; j < sistema.getStella().getListaPianeti().get(i).getListaSatelliti().size(); j++) {
                //applico la formula matematica
                if (Math.abs(Posizione.distanza(sistema.getStella().getPosizione(), pianeta.getPosizione())) >
                        Math.abs(Posizione.distanza(sistema.getStella().getPosizione(),
                                sistema.getStella().getListaPianeti().get(i).getPosizione()) +
                                Math.abs(Posizione.distanza(sistema.getStella().getListaPianeti().get(i).getPosizione(),
                                        sistema.getStella().getListaPianeti().get(i)
                                                .getListaSatelliti().get(j).getPosizione())))) {
                    return true;
                }
            }
        }


        return false;
    }

    public static boolean CollisioneSatellite(SistemaStellare sistema, Satellite satellite){
        //Primo caso: scontro satellite-satellite (stesso pianeta)
        //Due satelliti appartenenti allo stesso pianeta possono collidere solo se si trovano alla stessa distanza
        //dal pianeta di riferimento
        Pianeta pianeta = new Pianeta();
        int codice = Ricerca.codicePianetaBySatellite(satellite.getCodice(), sistema);
        for (int i = 0; i < sistema.getStella().getListaPianeti().size(); i++){
            if(sistema.getStella().getListaPianeti().get(i).getCodice() == codice)
                pianeta = sistema.getStella().getListaPianeti().get(i);
        }
        for (int i = 0; i < sistema.getStella().getListaPianeti().size(); i++) {
            //per ogni satellite del pianeta controllo che la sua distanza in modulo dal pianeta sia uguale alla
            //distanza in modulo del satellite inserito
            if (Math.abs(Posizione.distanza(satellite.getPosizione(), pianeta.getPosizione())) ==
                    Math.abs(Posizione.distanza(pianeta.getListaSatelliti().get(i).getPosizione(),
                            pianeta.getPosizione()))) {
                return true;
            }
        }
        return false;
    }
}

