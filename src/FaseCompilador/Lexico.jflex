package FaseCompilador;
import java_cup.runtime.Symbol;
import java.util.ArrayList;

%%

//--------------------------------Codigo Java-------------------------------------------------------------

%{
    // codigo java
     public static ArrayList<ErrorLexico> listaError = new ArrayList();
     String cadena = "";
%}


//------>   Directivas
%cupsym sym
%class Lexico
%state    COMENTARIO STRING 
%cup
%public
%line
%column
%char
%full
%8bit
%unicode
%ignorecase




//--------------------Expresiones regulares Numeros---------------------------------------
       letra = [a-zA-ZñÑ]+      
       entero = [0-9]+
       decimal =[0-9]+ "." [0-9]*
       ID = {letra}({letra} |"_"| {entero})*
       space = [\t|\f|" "|\r|\n]
       enter = [\ \n]  
%%
<YYINITIAL> {
//------------------------------PALABRAS RESERVADAS----------------------------------------------------
  

    "print"                  { 
                                 return new Symbol(sym.print ,yyline, yycolumn,yytext());
                             }
//----------------------------------------------------------------------------------------------------------------------------
 
    "int"                    { 
                                 return new Symbol(sym.tipo_entero ,yyline, yycolumn,yytext());
                             }
//----------------------------------------------------------------------------------------------------------------------------
 
    "double"                 { 
                                 return new Symbol(sym.tipo_doble ,yyline, yycolumn,yytext());
                             }
//----------------------------------------------------------------------------------------------------------------------------
    "string"                 { 
                                 return new Symbol(sym.tipo_cadena ,yyline, yycolumn,yytext());
                             }
//----------------------------------------------------------------------------------------------------------------------------
 
    "repetir"                { 
                                 return new Symbol(sym.repetir ,yyline, yycolumn,yytext());
                             }

//----------------------------------------------------------------------------------------------------------------------------
    ";"                      { 
                                 return new Symbol(sym.puntoycoma ,yyline, yycolumn,yytext());
                             }
//----------------------------------------------------------------------------------------------------------------------------
    "="                      { 
                                 return new Symbol(sym.igual ,yyline, yycolumn,yytext());
                             }
//----------------------------------------------------------------------------------------------------------------------------
    "("                      { 
                                 return new Symbol(sym.pa_open ,yyline, yycolumn,yytext());
                             }
//----------------------------------------------------------------------------------------------------------------------------
    ")"                      { 
                                 return new Symbol(sym.pa_close ,yyline, yycolumn,yytext());
                             }
//----------------------------------------------------------------------------------------------------------------------------
    "{"                       {
                                return new Symbol(sym.llaveabierta ,yyline, yycolumn,yytext());
                              }
//----------------------------------------------------------------------------------------------------------------------------
    "}"                       { 
                                return new Symbol(sym.llavecerrada ,yyline, yycolumn,yytext());
                              }

//----------------------------------------------------------------------------------------------------------------------------
    "+"                       { 
                                return new Symbol(sym.mas ,yyline, yycolumn,yytext());
                              }

//----------------------------------------------------------------------------------------------------------------------------
    "-"                       { 
                                return new Symbol(sym.menos ,yyline, yycolumn,yytext());
                              }
//----------------------------------------------------------------------------------------------------------------------------
    "*"                       { 
                                return new Symbol(sym.por ,yyline, yycolumn,yytext());
                              }
//----------------------------------------------------------------------------------------------------------------------------
    "/"                       { 
                                return new Symbol(sym.div ,yyline, yycolumn,yytext());
                              }
//----------------------------------------------------------------------------------------------------------------------------
    "pow"                      { 
                                return new Symbol(sym.potencia ,yyline, yycolumn,yytext());
                              }



// ------------ RETORNAR EXPRESIONES REGULARES-------------------------------------------
 
 {ID}      {    
                      return new Symbol(sym.id ,yyline, yycolumn,yytext());
           }
//--------------------------------------------------------------------------------------------------------------------------- 
{entero}   {   
                      return new Symbol(sym.entero ,yyline, yycolumn,yytext());  
           }
//---------------------------------------------------------------------------------------------------------------------------
 {decimal} {    
                      return new Symbol(sym.decimal ,yyline, yycolumn,yytext()); 
           }

//-----------------------Se Inicializan los Estados----------------------------------------------------------------------------------------------------

 "//" {yybegin(COMENTARIO);}
  \"  {yybegin(STRING);}

 {space} {/*Se ignora*/}
 {enter} {/*Se ignora*/}


 //-----------------------------------CUALQUIER OTRO ERROR---------------------------------------------------------------

/* Cualquier otro error siempre se separa el punto con la llave para que no de error de compilacion. */

.  

{
    ErrorLexico e = new ErrorLexico("Error lexico",yytext(),yyline, yycolumn,"El caracter no pertenece al lenguaje");
     listaError.add(e);
     System.err.println("Error lexico: "+yytext()+ " Linea:"+(yyline+1)+" Columna:"+(yycolumn+1));
}



}// fin del YYINTIAL

//-------------------------------ESTADOS--------------------------------------------------------
<COMENTARIO>{
[\n]  {  yybegin(YYINITIAL);}
[^\n] {  /*Se ignora */}
}

<STRING>{
 [\"]  {  
            String temporal=cadena; 
            cadena =""; 
            yybegin(YYINITIAL);   
            return new Symbol(sym.cadena,yyline, yycolumn,temporal);           }
 [^\"]  {     
            cadena+=yytext(); 
        }
}



