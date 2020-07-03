/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ent;

/**
 *
 * @author edi
 */
public class Simbolos {
   public EnumTipo tipo;
   public Object valor;
 
    public Simbolos(EnumTipo tipo, Object valor) {
        this.tipo = tipo;
        this.valor = valor;
              
    }
    public enum EnumTipo{
       entero,doble,cadena,error
    }
}
