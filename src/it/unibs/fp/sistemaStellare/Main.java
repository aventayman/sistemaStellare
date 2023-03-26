package it.unibs.fp.sistemaStellare;
import it.unibs.fp.mylib.InputDati;

public class Main {
    public static void main(String[] args) {
        //Per test e debugging
        Stella sole = new Stella(0x0000, 40, "Sole");
        SistemaStellare sistema = new SistemaStellare("alpha", sole);

        Pianeta terra = new Pianeta (0x0001, 5, "Terra", new Posizione(3, 5));

        sistema.aggiungiPianeta(terra);

        System.out.println(sistema.getBaricentro());
    }
}
