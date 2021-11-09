/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examen.cubilete;

import java.util.*;

/**
 * Clase del jugador del cubilete
 *
 * @author oscar jl Plata
 * @version 08/11/21
 */
public class Jugador {

    public int tiradaInicial;
    private ArrayList<String> tiroDados;
    private ArrayList<String> tiroAux;
    private int puntos;
    private int turno;
    private Dado dado;
    private String jugada;
    private String nombre;
    private int valorTirada;

    public Jugador(String nom) {
        tiradaInicial = 0;
        jugada = " ";
        tiroDados = new ArrayList<>(5);
        puntos = 0;
        turno = 0;
        dado = new Dado();
        tiroAux = new ArrayList<>();
        nombre = nom;
        valorTirada = 0;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void lanzar5Dados() {
        tiroDados.clear();
        for (int i = 0; i < 5; i++) {
            tiroDados.add(dado.tirarDado());
            //System.out.println(tiroDados.get(i));
        }
        Collections.sort(tiroDados);
        System.out.println(tiroDados);
    }

    public void lanzarXDados(int x) {
        tiroAux.clear();
        for (int i = 0; i < x; i++) {
            tiroAux.add(dado.tirarDado());
            //System.out.println(tiroDados.get(i));
        }
        Collections.sort(tiroDados);
        System.out.println(tiroAux);
    }

    public String lanzar1Dado() {
        String tirada = dado.tirarDado();
        System.out.println("TIRARA : " + tirada);
        return tirada;
    }

    public int valorCara(String str) {
        int num = 0;
        if (str.equals("9")) {
            num = 1;
        } else if (str.equals("10")) {
            num = 2;
        } else if (str.equals("J")) {
            num = 3;
        } else if (str.equals("Q")) {
            num = 4;
        } else if (str.equals("K")) {
            num = 5;
        } else if (str.equals("A")) {
            num = 6;
        }
        return num;
    }

    public ArrayList<String> getTiroDados() {
        return tiroDados;
    }

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    public int getTurno() {
        return turno;
    }

    public void setTurno(int turno) {
        this.turno = turno;
    }

    public ArrayList<String> getTiroAux() {
        return tiroAux;
    }

    public void setTiroAux(ArrayList<String> tiroAux) {
        this.tiroAux = tiroAux;
    }

    public String getJugada() {
        return jugada;
    }

    public void setJugada(String jugada) {
        this.jugada = jugada;
    }

    public int getValorTirada() {
        return valorTirada;
    }

    public void setValorTirada(int valorTirada) {
        this.valorTirada = valorTirada;
    }

}
