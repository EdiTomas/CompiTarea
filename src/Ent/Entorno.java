/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ent;

import java.util.Hashtable;

/**
 *
 * @author edi
 */
public class Entorno {

    private Hashtable tabla;
    public Entorno anterior;

    public Entorno(Entorno anterior) {
        tabla = new Hashtable();
        this.anterior = anterior;
    }

    public void put(String s, Simbolos sim, int Columna, int Fila) {
        tabla.put(s, sim);
    }

    public Simbolos buscar(String s,int Columna,int Fila) {
        for (Entorno e = this; e != null; e = e.anterior) {
            Simbolos encontrado = (Simbolos) (e.tabla.get(s));
            if (encontrado != null) {
                return encontrado;
            }
        }
     return null;
    }

}
