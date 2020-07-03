/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Arbol;

import static Aplicacion.Ventana.imprimir;
import Ent.Entorno;
import Ent.Simbolos;
import java.util.ArrayList;

/**
 *
 * @author edi
 */
/*
 
*/


public class RecorrerAst {
   
    public void Ejecutar(Nodo Raiz){
        this.S(Raiz, null);
    }
    
    private void S(Nodo Raiz, Entorno ent){
           this.BLOQUE(Raiz.getHijos().get(0), ent);
    }

    private void BLOQUE(Nodo Raiz, Entorno ent){
        
            for (Nodo hijos : Raiz.getHijos())
            {
                Entorno  nuevoEntorno = new Entorno(ent);
                this.L_INSTRUCCIONES(hijos, nuevoEntorno);
            }
    }

    private void L_INSTRUCCIONES(Nodo Raiz, Entorno ent)
    {
           for (Nodo hijos : Raiz.getHijos())
            {
                  if(hijos.getEtiqueta().equals("IMPRIMIR"))
                  {
                     this.EjecutarImprimir(hijos, ent);
                  }else if(hijos.getEtiqueta().equals("REPETIR"))
                  {
                     this.EjecutarRepetir(hijos, ent);
                  }else if(hijos.getEtiqueta().equals("BLOQUE"))
                  {
                            this.BLOQUE(hijos, ent);
                  }else if(hijos.getEtiqueta().equals("DECLARACION"))
                     {
                     this.EjecutarDeclaracion(hijos, ent);
                  }else if(hijos.getEtiqueta().equals("L_INSTRUCCIONES"))
                  {
                               this.L_INSTRUCCIONES(hijos, ent);
                           
                  }                   
               
            }
    }
    
   
    public void EjecutarDeclaracion(Nodo Raiz, Entorno ent){
       Expresion resultado = resolverExpresiones(Raiz.getHijos().get(2),ent);
       Simbolos.EnumTipo tipoVariable = obtenerTipo(Raiz.getHijos().get(0).getValor());     
       String id = Raiz.getHijos().get(1).getValor();
       if(tipoVariable !=resultado.tipo)
       {
           System.out.println("Error no se permite");
           return;
       }
       Simbolos nuevo = new Simbolos(tipoVariable,resultado.Valor);
       ent.put(id, nuevo,Raiz.getColumna(),Raiz.getFila());
       
       
    }
    
    
    public void EjecutarImprimir(Nodo Raiz, Entorno ent)
    {
       Expresion resultado = resolverExpresiones(Raiz.getHijos().get(0),ent);
          if(resultado.tipo !=Simbolos.EnumTipo.error){
              System.out.println(resultado.Valor);
              imprimir.add(resultado);
          }
    }
    
    public void EjecutarRepetir(Nodo Raiz, Entorno ent){
       Expresion resultado = resolverExpresiones(Raiz.getHijos().get(0),ent);
          if(resultado.tipo !=Simbolos.EnumTipo.entero){
              System.out.println("Error en la funcion repetir");
              return;
          }
         Nodo nodobloque = Raiz.getHijos().get(1);
         if(nodobloque.getHijos().size()>0)
         {
             int iterador = Integer.parseInt(resultado.Valor.toString());
             while(iterador >0){
                Entorno local = new  Entorno(ent);
                this.L_INSTRUCCIONES(nodobloque.getHijos().get(0), local);
                iterador--;
             }
         }
    }
    
    public Simbolos.EnumTipo obtenerTipo(String tipo){
           switch(tipo.toLowerCase()){
           case "int":
                          return Simbolos.EnumTipo.entero;  
           case "double":
                          return Simbolos.EnumTipo.doble;  
           case "string":
                         return Simbolos.EnumTipo.cadena;  
           default:
                   return Simbolos.EnumTipo.error;    
       }   
    }
    
    
    public Expresion  resolverExpresiones(Nodo Raiz, Entorno ent){
               
                  
       switch(Raiz.getEtiqueta()){
           case "cadena":
                          return new Expresion(Simbolos.EnumTipo.cadena,Raiz.getValor());  
           case "entero":
                          int re = Integer.parseInt(Raiz.getValor());
               return new Expresion(Simbolos.EnumTipo.entero,re );  
           case "decimal":
                           double rede = Double.parseDouble(Raiz.getValor()); 
                         return new Expresion(Simbolos.EnumTipo.doble,rede);  
           case "id":
                  Simbolos sim  = ent.buscar(Raiz.getValor(),Raiz.getColumna(), Raiz.getFila());
                  if(sim !=null)  return new Expresion(sim.tipo,sim.valor);
                  break;
            case "mas":
                     Expresion hijoIzquierdo = resolverExpresiones(Raiz.getHijos().get(0),ent);
                     Expresion hijoDerecho = resolverExpresiones(Raiz.getHijos().get(1),ent);
                   
                   if(hijoIzquierdo.Valor instanceof String && hijoDerecho.Valor instanceof String){
                            
                   return new Expresion(Simbolos.EnumTipo.cadena,hijoIzquierdo.Valor.toString()+hijoDerecho.Valor.toString());
                  
                   }else if(hijoIzquierdo.Valor instanceof Double && hijoDerecho.Valor instanceof Double) {
                   
                       return new Expresion(Simbolos.EnumTipo.doble, Double.parseDouble(hijoIzquierdo.Valor.toString())+Double.parseDouble(hijoDerecho.Valor.toString()));
                   }else if(hijoIzquierdo.Valor instanceof Integer &&  hijoDerecho.Valor instanceof Integer) {
                      
                    return new Expresion(Simbolos.EnumTipo.entero,Integer.parseInt(hijoIzquierdo.Valor.toString())+Integer.parseInt(hijoDerecho.Valor.toString()) );
                   
                   }
                   else if(hijoIzquierdo.Valor instanceof String && hijoDerecho.Valor instanceof Integer){
                     return new Expresion(Simbolos.EnumTipo.cadena,hijoIzquierdo.Valor.toString()+hijoDerecho.Valor.toString());
                         
                   }else if(hijoIzquierdo.Valor instanceof String && hijoDerecho.Valor instanceof Double){
                      return new Expresion(Simbolos.EnumTipo.cadena,hijoIzquierdo.Valor.toString()+hijoDerecho.Valor.toString());
                   
                   }else if(hijoIzquierdo.Valor instanceof Integer && hijoDerecho.Valor instanceof Double){
                        return new Expresion(Simbolos.EnumTipo.doble, Double.parseDouble(hijoIzquierdo.Valor.toString())+Double.parseDouble(hijoDerecho.Valor.toString()));
                          
                   }else if(hijoIzquierdo.Valor instanceof Integer && hijoDerecho.Valor instanceof String){
                      return new Expresion(Simbolos.EnumTipo.cadena,hijoIzquierdo.Valor.toString()+hijoDerecho.Valor.toString());
                   
                   }else if(hijoIzquierdo.Valor instanceof Double && hijoDerecho.Valor instanceof Integer){
                         return new Expresion(Simbolos.EnumTipo.doble, Double.parseDouble(hijoIzquierdo.Valor.toString())+Double.parseDouble(hijoDerecho.Valor.toString()));
                        
                   }else if(hijoIzquierdo.Valor instanceof Double && hijoDerecho.Valor instanceof String){
                          return new Expresion(Simbolos.EnumTipo.cadena,hijoIzquierdo.Valor.toString()+hijoDerecho.Valor.toString());
                   
                   }
           case "menos":
                     hijoIzquierdo = resolverExpresiones(Raiz.getHijos().get(0),ent);
                     hijoDerecho = resolverExpresiones(Raiz.getHijos().get(1),ent);
                  if(hijoIzquierdo.Valor instanceof Double && hijoDerecho.Valor instanceof Double) {
                   
                       return new Expresion(Simbolos.EnumTipo.doble, Double.parseDouble(hijoIzquierdo.Valor.toString())-Double.parseDouble(hijoDerecho.Valor.toString()));
                   }
                  else if(hijoIzquierdo.Valor instanceof Integer &&  hijoDerecho.Valor instanceof Integer) {
                      
                    return new Expresion(Simbolos.EnumTipo.entero,Integer.parseInt(hijoIzquierdo.Valor.toString())-Integer.parseInt(hijoDerecho.Valor.toString()) );
                   
                   }else if(hijoIzquierdo.Valor instanceof Double && hijoDerecho.Valor instanceof Integer){
                         return new Expresion(Simbolos.EnumTipo.doble, Double.parseDouble(hijoIzquierdo.Valor.toString())-Double.parseDouble(hijoDerecho.Valor.toString()));
                        
                   }if(hijoIzquierdo.Valor instanceof Integer && hijoDerecho.Valor instanceof Double){
                        return new Expresion(Simbolos.EnumTipo.doble, Double.parseDouble(hijoIzquierdo.Valor.toString())-Double.parseDouble(hijoDerecho.Valor.toString()));
                          
                   }
               
               
           case "por":
                        hijoIzquierdo = resolverExpresiones(Raiz.getHijos().get(0),ent);
                     hijoDerecho = resolverExpresiones(Raiz.getHijos().get(1),ent);
                  if(hijoIzquierdo.Valor instanceof Double && hijoDerecho.Valor instanceof Double) {
                   
                       return new Expresion(Simbolos.EnumTipo.doble, Double.parseDouble(hijoIzquierdo.Valor.toString())*Double.parseDouble(hijoDerecho.Valor.toString()));
                   }
                  else if(hijoIzquierdo.Valor instanceof Integer &&  hijoDerecho.Valor instanceof Integer) {
                      
                    return new Expresion(Simbolos.EnumTipo.entero,Integer.parseInt(hijoIzquierdo.Valor.toString())*Integer.parseInt(hijoDerecho.Valor.toString()) );
                   
                   }else if(hijoIzquierdo.Valor instanceof Double && hijoDerecho.Valor instanceof Integer){
                         return new Expresion(Simbolos.EnumTipo.doble, Double.parseDouble(hijoIzquierdo.Valor.toString())*Double.parseDouble(hijoDerecho.Valor.toString()));
                        
                   }else if(hijoIzquierdo.Valor instanceof Integer && hijoDerecho.Valor instanceof Double){
                        return new Expresion(Simbolos.EnumTipo.doble, Double.parseDouble(hijoIzquierdo.Valor.toString())*Double.parseDouble(hijoDerecho.Valor.toString()));
                          
                   }
                   System.out.println("operacion incorrecta: "+ hijoIzquierdo.tipo+" * " +  hijoDerecho.tipo);
                           return  new Expresion(Simbolos.EnumTipo.error,"@error");
                       
                  
           case "div":
                     hijoIzquierdo = resolverExpresiones(Raiz.getHijos().get(0),ent);
                     hijoDerecho = resolverExpresiones(Raiz.getHijos().get(1),ent);
                 
                     
                     
                     if(hijoIzquierdo.Valor instanceof Double && hijoDerecho.Valor instanceof Double) {
                   
                          if(Double.parseDouble(hijoDerecho.Valor.toString())  == 0){
                           System.out.println("operacion incorrecta division entre cero: "+ hijoIzquierdo.tipo+" / " +  hijoDerecho.Valor);
                           
                              return  new Expresion(Simbolos.EnumTipo.error,"@error");
                            }
                       return new Expresion(Simbolos.EnumTipo.doble, Double.parseDouble(hijoIzquierdo.Valor.toString())/Double.parseDouble(hijoDerecho.Valor.toString()));
                   }
                  else if(hijoIzquierdo.Valor instanceof Integer &&  hijoDerecho.Valor instanceof Integer) {
                      
                      if(Integer.parseInt(hijoDerecho.Valor.toString())  == 0){
                           System.out.println("operacion incorrecta division entre cero: "+ hijoIzquierdo.tipo+" / " +  hijoDerecho.Valor);
                     
                          return  new Expresion(Simbolos.EnumTipo.error,"@error");
                            }
                    return new Expresion(Simbolos.EnumTipo.entero,Integer.parseInt(hijoIzquierdo.Valor.toString())/Integer.parseInt(hijoDerecho.Valor.toString()) );
                   
                   }else if(hijoIzquierdo.Valor instanceof Double && hijoDerecho.Valor instanceof Integer){
                        if(Integer.parseInt(hijoDerecho.Valor.toString())  == 0){
                            System.out.println("operacion incorrecta division entre cero: "+ hijoIzquierdo.tipo+" / " +  hijoDerecho.Valor);
                   
                            return  new Expresion(Simbolos.EnumTipo.error,"@error");
                            }   
                       return new Expresion(Simbolos.EnumTipo.doble, Double.parseDouble(hijoIzquierdo.Valor.toString())/Double.parseDouble(hijoDerecho.Valor.toString()));
                        
                   }if(hijoIzquierdo.Valor instanceof Integer && hijoDerecho.Valor instanceof Double){
                       if(Double.parseDouble(hijoDerecho.Valor.toString())  == 0){
                       System.out.println("operacion incorrecta division entre cero: "+ hijoIzquierdo.tipo+" / " +  hijoDerecho.Valor);
                   
                           return  new Expresion(Simbolos.EnumTipo.error,"@error");
                            }
                      
                       return new Expresion(Simbolos.EnumTipo.doble, Double.parseDouble(hijoIzquierdo.Valor.toString())/Double.parseDouble(hijoDerecho.Valor.toString()));
                   }
                     
           case "potencia":
                 hijoIzquierdo = resolverExpresiones(Raiz.getHijos().get(0),ent);
                     hijoDerecho = resolverExpresiones(Raiz.getHijos().get(1),ent);
                         if(hijoIzquierdo.Valor instanceof Double && hijoDerecho.Valor instanceof Double) {
                   
                       return new Expresion(Simbolos.EnumTipo.doble, Math.pow(Double.parseDouble(hijoIzquierdo.Valor.toString()), Double.parseDouble(hijoDerecho.Valor.toString())) );
                   }
                  else if(hijoIzquierdo.Valor instanceof Integer &&  hijoDerecho.Valor instanceof Integer) {
                       
                         double a = Double.parseDouble(hijoIzquierdo.Valor.toString());
                         double b = Double.parseDouble(hijoDerecho.Valor.toString()); 
                         int  c = (int) Math.pow(a,b);
                      return new Expresion(Simbolos.EnumTipo.entero,  c);
                      
                   
                   }else if(hijoIzquierdo.Valor instanceof Double && hijoDerecho.Valor instanceof Integer){
                                   return new Expresion(Simbolos.EnumTipo.doble, Math.pow(Double.parseDouble(hijoIzquierdo.Valor.toString()), Double.parseDouble(hijoDerecho.Valor.toString())) );
                        
                   }if(hijoIzquierdo.Valor instanceof Integer && hijoDerecho.Valor instanceof Double){
                                 return new Expresion(Simbolos.EnumTipo.doble, Math.pow(Double.parseDouble(hijoIzquierdo.Valor.toString()), Double.parseDouble(hijoDerecho.Valor.toString())) );
                          
                   }
                     
           case  "umenos":       
                   hijoIzquierdo = resolverExpresiones(Raiz.getHijos().get(0),ent);
                   
                   
                   
                   if(hijoIzquierdo.Valor instanceof Double){
                       double a = -Double.parseDouble(hijoIzquierdo.Valor.toString());                        
                     return new Expresion(Simbolos.EnumTipo.entero,  a);
                  
                   
                   }else if(hijoIzquierdo.Valor instanceof Integer){
                      int a = -Integer.parseInt(hijoIzquierdo.Valor.toString());                        
                     return new Expresion(Simbolos.EnumTipo.entero,  a);
                  }
           break;
       } 
        
        return  new Expresion(Simbolos.EnumTipo.error,"@error");
    }
    
    
    
    
    
    
    
    
    




}
