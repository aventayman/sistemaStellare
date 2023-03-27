package it.unibs.fp.sistemaStellare;
import it.unibs.fp.mylib.InputDati;


public class Main {
    public static void main(String[] args) {
        //Per test e debugging
        Stella sole = new Stella(0x0000, 40, "Sole");
        SistemaStellare sistema = new SistemaStellare("alpha", sole);

        Pianeta terra = new Pianeta(0x0001, 5, "Terra", new Posizione(3, 5));

        Pianeta marte = new Pianeta(0x0003, 4, "Marte", new Posizione(10, -5));

        Satellite luna = new Satellite(0x0002, 2, "Luna", new Posizione(2, 5), terra);

        sistema.aggiungiPianeta(terra);
        sistema.aggiungiPianeta(marte);

        sistema.aggiungiSatellite(luna, terra);

        System.out.println(String.format("%20s\t%6s\t%6s\t%16s\t%10s\n",
                "Nome", "Codice", "Massa", "Coordinate", "Pianeta/N-Satelliti"));
        System.out.print(sole);
        System.out.print(terra);
        System.out.print(marte);
        System.out.print(luna);
        System.out.println(Ricerca.codiceNome);


        System.out.println(Ricerca.getCodiciByNome("TeRrA"));
    }
}
