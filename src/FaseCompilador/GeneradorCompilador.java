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
public class GeneradorCompilador {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
    generarcompiladores();
    }
    public static void generarcompiladores()
    {
    try {
            String ruta = "src/FaseCompilador/";
            String opcFlex[] = {ruta + "Lexico.jflex", "-d", ruta};
            jflex.Main.generate(opcFlex);
            
           
            String opcCUP[] = {"-destdir", ruta, "-parser", "Sintactico", ruta + "Sintactico.cup"};
            java_cup.Main.main(opcCUP);
            
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    
    }
    
}
