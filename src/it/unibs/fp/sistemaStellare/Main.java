package it.unibs.fp.sistemaStellare;

import it.unibs.fp.mylib.InputDati;

public class Main {
    public static void main(String[] args) {
        SistemaStellare sistema = InterfacciaUtente.start();
        boolean running = true;
        InterfacciaUtente.printMenuPrincipale();

        while (running) {
            System.out.println("> Premere 'm' per visualizzare il menu o 'q' per uscire");
            char scelta = InputDati.leggiChar("> ");

            switch (scelta) {
                case 'q' -> running = false;
                case 'g' -> {
                    InterfacciaUtente.gestioneCorpi(sistema);
                }
                case 'b' -> InterfacciaUtente.printBaricentro(sistema);
                case 'm' -> InterfacciaUtente.printMenuPrincipale();
                default -> System.out.println("Il carattere inserito non e' valido!");
            }
        }
    }

}
