/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FaseCompilador;

/**
 *
 * @author edi
 */
public class ErrorParser {
    public  String error;
    public  String tipo;
    public  int linea;
    public  int columna;
    public  String Descripcion;
    
    public ErrorParser(String tipo,String error,int linea,int columna,String Descripcion)
    {
    this.tipo=tipo;
        this.error=error;
        this.linea=linea;
        this.columna=columna;
        this.Descripcion= Descripcion;
    }
    
}
