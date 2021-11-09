/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examen.cubilete;

import java.util.*;

/**
 * Clase principal del juego de cubilete para 2,3 o 4 jugadores
 *
 * @author oscar jl Plata
 * @version 08/11/21
 */
public class Cubilete {

    static int numJugadores;
    static final String[] caras = {"9", "10", "J", "Q", "K", "A"};

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<Jugador> jugadores = new ArrayList();
        Random rn = new Random();
        String[] nombreAux = new String[numJugadores];
        int turnosJuego;

        System.out.println(">>>>> CUBILETE: INICIO");
        //Establecer Numero de jugadores
        do {
            System.out.println("Numero de jugadores[2 a 4]");
            numJugadores = sc.nextInt();
            nombreAux = new String[numJugadores];
            if (numJugadores < 2 || numJugadores > 4) {
                System.out.println("Error volver a intentar");
            } else {

                for (int i = 0; i < numJugadores; i++) {
                    System.out.println("Ingresa nombre del jugador: ");
                    nombreAux[i] = sc.next().trim().toUpperCase();
                }
            }
        } while (numJugadores < 2 || numJugadores > 4);

        //TIRAR DADOS INICIALES
        int[] tiroAux = new int[numJugadores];
        int max = 0;
        int min = 100;
        int indicador = 0;
        int indicador2 = 0;
        System.out.println(">>>>>Tirando dados inciales...");
        for (int i = 0; i < numJugadores; i++) {
            tiroAux[i] = rn.nextInt(6);
            System.out.println(nombreAux[i] + "\t [" + caras[tiroAux[i]] + "]");
            if (tiroAux[i] > max) {
                max = tiroAux[i];
                indicador = i;
            }
            if (tiroAux[i] < min) {
                min = tiroAux[i];
                indicador2 = i;
            }
        }

        //ESTABLECIENDO EL ORDEN DE JUEGO DE LOS JUGADORES
        switch (numJugadores) {
            case 2:
                jugadores.add(new Jugador(nombreAux[indicador]));
                jugadores.get(0).tiradaInicial = tiroAux[indicador];
                if (tiroAux[1] == tiroAux[0]) {
                    jugadores.add(new Jugador(nombreAux[1]));
                    jugadores.get(1).tiradaInicial = tiroAux[1];
                } else {
                    jugadores.add(new Jugador(nombreAux[indicador2]));
                    jugadores.get(1).tiradaInicial = tiroAux[indicador2];
                }
                break;
            case 3:
                jugadores.add(new Jugador(nombreAux[indicador]));
                jugadores.get(0).tiradaInicial = tiroAux[indicador];
                for (int i = 0; i < 3; i++) {
                    if ((!(nombreAux[i].equals(nombreAux[indicador])) && !(nombreAux[i].equals(nombreAux[indicador2])))) {
                        jugadores.add(new Jugador(nombreAux[i]));
                        jugadores.get(1).tiradaInicial = tiroAux[i];
                    }
                }
                jugadores.add(new Jugador(nombreAux[indicador2]));
                jugadores.get(2).tiradaInicial = tiroAux[indicador2];
                break;
            default:
                jugadores.add(new Jugador(nombreAux[indicador]));
                jugadores.get(0).tiradaInicial = tiroAux[indicador];
                int cont = 1;
                for (int i = 0; i < 4; i++) {
                    if ((!(nombreAux[i].equals(nombreAux[indicador])) && !(nombreAux[i].equals(nombreAux[indicador2])))) {
                        jugadores.add(new Jugador(nombreAux[i]));
                        jugadores.get(cont).tiradaInicial = tiroAux[i];
                        cont++;
                    }
                }
                jugadores.add(new Jugador(nombreAux[indicador2]));
                jugadores.get(3).tiradaInicial = tiroAux[indicador2];
                if (jugadores.get(2).tiradaInicial > jugadores.get(1).tiradaInicial) {
                    Collections.swap(jugadores, 1, 2);
                }
                break;
        }
        System.out.println("\n>>>>>>ORDEN DE JUEGO :");
        for (Jugador jugador : jugadores) {
            System.out.println("~\t" + jugador.getNombre());
        }
        //Seleccionar los turnos maximos de la partida
        System.out.println(">>>>>> CANTIDAD DE TURNOS :\n"
                + "Ingresa cantidad de turnos en la partida: ");
        turnosJuego = sc.nextInt();
        int contadorTurnos = 0;

        //CICLO PRINCIPAL DEL JUEGO
        do {
            System.out.println("\n>>>>> TURNO # " + (contadorTurnos + 1));
            String opc = "";
            //TURNO DE CADA JUGADOR
            for (Jugador jug : jugadores) {
                int intentoTiro = 0;

                do {
                    System.out.println(jug.getNombre() + ": Tiro # " + (intentoTiro + 1));
                    jug.lanzar5Dados();
                    if (intentoTiro < 2) {
                        System.out.println("¿  Volver a tirar? [si/no]");
                        opc = sc.next().trim().toLowerCase();
                    }
                    intentoTiro++;
                } while (intentoTiro < 3 && opc.equals("si"));

                //VERIFICAR JUGADAS
                if (verificarCarabina(jug.getTiroDados())) {
                    jug.setValorTirada(60 * jug.valorCara(jug.getTiroDados().get(2)));
                    //System.out.println(jug.getValorTirada());
                    //System.out.println(jug.getTiroDados());

                } else if (verificarPoker(jug.getTiroDados())) {
                    jug.setValorTirada(50 * jug.valorCara(jug.getTiroDados().get(2)));
                    // System.out.println(jug.getValorTirada());

                } else if (verificarFull(jug.getTiroDados())) {
                    jug.setValorTirada(40 * jug.valorCara(jug.getTiroDados().get(2)));

                    //System.out.println(jug.getValorTirada());
                    //System.out.println(jug.getTiroDados());
                } else if (verificarTercia(jug.getTiroDados())) {
                    jug.setValorTirada(30 * jug.valorCara(jug.getTiroDados().get(2)));
                    // System.out.println(jug.getValorTirada());
                    //System.out.println(jug.getTiroDados());

                } else if (verificarDoblePar(jug.getTiroDados())) {
                    jug.setValorTirada(10 * jug.valorCara(jug.getTiroDados().get(0)) + 10 * jug.valorCara(jug.getTiroDados().get(1)));
                    //System.out.println(jug.getValorTirada());
                    //System.out.println(jug.getTiroDados());

                } else if (verificarPar(jug.getTiroDados())) {
                    jug.setValorTirada(10 * jug.valorCara(jug.getTiroDados().get(0)));
                    //System.out.println(jug.getValorTirada());
                    // System.out.println(jug.getTiroDados());

                } else {
                    ArrayList<Integer> valorTiro = new ArrayList();
                    for (int i = 0; i < 5; i++) {
                        valorTiro.add(jug.valorCara(jug.getTiroDados().get(i)));
                    }
                    Collections.sort(valorTiro);
                    jug.setValorTirada(valorTiro.get(0));
                }
                System.out.println("\n");
            }
            int jugadorGanaTurno = 0;
            int valorMax = 0;
            for (int i = 0; i < numJugadores; i++) {
                if (jugadores.get(i).getValorTirada() > valorMax) {
                    valorMax = jugadores.get(i).getValorTirada();
                    jugadorGanaTurno = i;
                }
            }
            jugadores.get(jugadorGanaTurno).setPuntos(jugadores.get(jugadorGanaTurno).getPuntos() + 1);
            System.out.println(jugadores.get(jugadorGanaTurno).getNombre() + " Gano este turno <<<<");
            contadorTurnos++;

        } while (contadorTurnos < turnosJuego);
        //VERIFICAR GANADORES
        int win = 0;
        int puntosMax = 0;
        System.out.println("PUNTOS FINALES");
        for (int i = 0; i < numJugadores; i++) {
            System.out.println(jugadores.get(i).getNombre() + ": " + jugadores.get(i).getPuntos());
            if (jugadores.get(i).getPuntos() > puntosMax) {
                win = i;
                System.out.println(win);
                puntosMax = jugadores.get(i).getPuntos();
                System.out.println(puntosMax);

            }
        }
        System.out.println(jugadores.get(win).getNombre() + " Gano La partida\nFin del juego");
    }

    /**
     * Calcula si los valores de los 5 dados forman una carabina (5 Dados
     * iguales)
     *
     * @param dados
     * @return true: es carabina false: no es carabina
     */
    public static boolean verificarCarabina(ArrayList<String> dados) {
        boolean carabine = false;

        for (int i = 0; i < 6; i++) {
            int contador = 0;
            String actual = caras[i];
            for (int j = 0; j < 5; j++) {
                String cara = dados.get(j);
                if (cara.equals(actual)) {
                    contador++;
                }
            }
            //System.out.println(actual + " " + contador);
            if (contador == 5) {
                carabine = true;
                System.out.println("~ Es carabina");
            }
        }
        return carabine;
    }

    /**
     * Calcula si los valores de los 5 dados forman una jugada de poker (4 Dados
     * iguales)
     *
     * @param dados
     * @return true: es poker false: no es poker
     */
    public static boolean verificarPoker(ArrayList<String> dados) {
        boolean poker = false;

        for (int i = 0; i < 6; i++) {
            int contador = 0;
            String actual = caras[i];
            for (int j = 0; j < 5; j++) {
                String cara = dados.get(j);
                if (cara.equals(actual)) {
                    contador++;
                }
            }
            //System.out.println(actual + " " + contador);
            if (contador == 4) {
                poker = true;
                System.out.println("~ Es poker");
            }
        }
        return poker;
    }

    /**
     * Calcula si los valores de los 5 dados forman una jugada de tercia (3
     * Dados iguales, 2 distinos)
     *
     * @param dados
     * @return true: hay tercia false: no hay tercia
     */
    public static boolean verificarTercia(ArrayList<String> dados) {
        boolean tercia = false;

        for (int i = 0; i < 6; i++) {
            int contador = 0;
            String actual = caras[i];
            for (int j = 0; j < 5; j++) {
                String cara = dados.get(j);
                if (cara.equals(actual)) {
                    contador++;
                }
            }
            //System.out.println(actual + " " + contador);
            if (contador == 3) {
                tercia = true;
                System.out.println("~ Es tercia");
            }
        }
        return tercia;
    }

    /**
     * Calcula si los valores de los 5 dados forman una jugada de par (2 Dados
     * iguales, 3 desiguales)
     *
     * @param dados
     * @return true: hay par false: no hay par
     */
    public static boolean verificarPar(ArrayList<String> dados) {
        boolean par = false;
        int posicion = 0;

        for (int i = 0; i < 6; i++) {
            int contador = 0;
            String actual = caras[i];
            for (int j = 0; j < 5; j++) {
                String cara = dados.get(j);
                if (cara.equals(actual)) {
                    contador++;

                }
            }

            if (contador == 2) {

                posicion = dados.indexOf(actual);
                par = true;
                System.out.println("~ Es par");
            }

        }
        Collections.swap(dados, posicion, 0);
        return par;
    }

    /**
     * Calcula si los valores de los 5 dados forman una jugada de full (1 tercia
     * y 1 par)
     *
     * @param dados
     * @return true: hay full false: no hay full
     */
    public static boolean verificarFull(ArrayList<String> dados) {
        boolean full = false;
        if (verificarTercia(dados) && verificarPar(dados)) {
            full = true;
            System.out.println("~ Es Full");
        }

        return full;
    }

    /**
     * Calcula si los valores de los 5 dados forman una jugada de duo de pares
     * (2 y 2 Dados iguales)
     *
     * @param dados
     * @return true: hay doble par false: no hay doble
     */
    public static boolean verificarDoblePar(ArrayList<String> dados) {
        boolean par = false;
        int posicion = 0;
        int posicion2 = 0;
        int doble = 0;

        for (int i = 0; i < 6; i++) {
            int contador = 0;
            String actual = caras[i];
            for (int j = 0; j < 5; j++) {
                String cara = dados.get(j);
                if (cara.equals(actual)) {
                    contador++;

                }
            }

            if (contador == 2) {
                if (doble == 0) {
                    posicion = dados.indexOf(actual);
                } else {
                    posicion2 = dados.indexOf(actual);
                    par = true;
                    System.out.println("~ Es Doblepar");
                }

                doble++;
            }

        }
        Collections.swap(dados, posicion, 0);
        Collections.swap(dados, posicion2, 1);

        return par;
    }

}
