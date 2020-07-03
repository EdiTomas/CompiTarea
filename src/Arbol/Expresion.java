/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Arbol;

import Ent.Simbolos;

/**
 *
 * @author edi
 */
public class Expresion {
 public Simbolos.EnumTipo tipo;
 public Object Valor;

    public Expresion(Simbolos.EnumTipo tipo, Object Valor) {
        this.tipo = tipo;
        this.Valor = Valor;
    }
   
}
