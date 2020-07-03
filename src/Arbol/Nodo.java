/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Arbol;

import java.util.ArrayList;

/**
 *
 * @author edi
 */
public class Nodo {

    private String Etiqueta;
    private ArrayList<Nodo> hijos ;
    private String valor;
    private int idNod;
    private int Columna;
    private int Fila;

    public Nodo() {
        this.Etiqueta = "";
        this.valor = "";
        this.idNod = 0;
        this.Columna = -1;
        this.Fila = -1;
        this.hijos = new ArrayList<>();
    }

     
    public Nodo(String Etiqueta, String valor, int idNod) {
        this.Etiqueta = Etiqueta;
        this.valor = valor;
        this.idNod = idNod;
        this.Columna = -1;
        this.Fila = -1;
        this.hijos = new ArrayList<>();
    }
    
    public Nodo(String Etiqueta, String valor, int idNod, int Columna, int Fila) {
        this.Etiqueta = Etiqueta;
        this.valor = valor;
        this.idNod = idNod;
        this.Columna = Columna;
        this.Fila = Fila;
        this.hijos =  new ArrayList<>();
    }

    
    
    public void setColumna(int Columna) {
        this.Columna = Columna;
    }

    public void setFila(int Fila) {
        this.Fila = Fila;
    }

    public int getColumna() {
        return Columna;
    }

    public int getFila() {
        return Fila;
    }

    public void AddHijos(Nodo hijo) {
        getHijos().add(hijo);
    }

    /**
     * @return the Etiqueta
     */
    public String getEtiqueta() {
        return Etiqueta;
    }

    /**
     * @param Etiqueta the Etiqueta to set
     */
    public void setEtiqueta(String Etiqueta) {
        this.Etiqueta = Etiqueta;
    }

    /**
     * @return the valor
     */
    public String getValor() {
        return valor;
    }

    /**
     * @param valor the valor to set
     */
    public void setValor(String valor) {
        this.valor = valor;
    }

    /**
     * @return the idNod
     */
    public int getIdNod() {
        return idNod;
    }

    /**
     * @param idNod the idNod to set
     */
    public void setIdNod(int idNod) {
        this.idNod = idNod;
    }

    /**
     * @return the hijos
     */
    public ArrayList<Nodo> getHijos() {
        return hijos;
    }

    /**
     * @param hijos the hijos to set
     */
    public void setHijos(ArrayList<Nodo> hijos) {
        this.hijos = hijos;
    }

}
