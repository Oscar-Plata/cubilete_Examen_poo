/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examen.cubilete;

import java.util.*;

/**
 * Clase del dado tipo cubilete
 *
 * @author oscar jl Plata
 * @version 08/11/21
 */
public class Dado {

    public final String[] caras = {"9", "10", "J", "Q", "K", "A"};

    public Dado() {
    }

    public String tirarDado() {
        String str;
        Random rnd = new Random();
        str = caras[rnd.nextInt(6)];
        return str;
    }
}
