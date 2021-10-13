/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmanestar_zadaca_3.FactoryMethod;

import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import rmanestar_zadaca_3.Singleton.PostavkeSingleton;
import rmanestar_zadaca_3.Lokacija;

/**
 *
 * @author Mane
 */

// (([1-9]+[0-9]*)|[0]); .+; .+; ((\d+\.\d+\,\d+\.\d+)|(\d+\.\d+\,\ \d+\.\d+))

// id - [0-9]+[1-9]*
// naziv .+
// adresa .+
// gps \d+.\d+,\d+.\d+

public class LokacijaFileLoader extends FileLoader{
     
    @Override
    public void parseLine(String linija) {
        int smCounter = 0;
        for (int i = 0; i < linija.length(); i++) 
            if (linija.charAt(i) == ';') 
                smCounter++;      

        if (linija.matches("^(([1-9]+[0-9]*)|[0]); .+; .+; ((\\d+\\.\\d+\\,\\d+\\.\\d+)|(\\d+\\.\\d+\\,\\ \\d+\\.\\d+))$") && smCounter == 3) {
            try {
                String[] lista = linija.split("; ");

                int id = Integer.parseInt(lista[0].replace(" ", ""));
                String naziv = lista[1];
                String adresa = lista[2];
                String gps = lista[3];

                PostavkeSingleton.getLokacijaLista().add(new Lokacija(id, naziv, adresa, gps));

            } catch (Exception e) {

            }
        } else {
            
            String temp="";
            if(!PostavkeSingleton.konfiguracija.containsKey("izlaz")){
                temp=PostavkeSingleton.ANSI_RED;
            }
            
            System.out.println(temp+"Datoteka LOKACIJE  - Pogresan format linija: " + linija);
            
            if(PostavkeSingleton.konfiguracija.containsKey("izlaz")){
                try {     
                    System.setOut(new PrintStream(new FileOutputStream(PostavkeSingleton.konfiguracija.getProperty("izlaz"))));
                } catch (FileNotFoundException ex) {
                }
                System.out.println("Datoteka LOKACIJE  - Pogresan format linija: " + linija);

                for(int i=0; i<("Datoteka LOKACIJE - Pogresan format linija: " + linija).length();i++){
                    System.out.print("-");
                }
                System.out.println();
                try { 
                System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
                }catch(Exception e){}
            }
            
            String[] lista = linija.split("; ");
        
            switch (smCounter) {
                case 3:
                    if (!lista[0].matches("^([1-9]+[0-9]*|[0])$")) 
                        System.out.println(temp+"Atribut ID nije ispravan. Mora biti broj veci ili jednak 0");                  
                    if (!lista[1].matches("^.+$")) 
                        System.out.println(temp+"Atribut NAZIV LOKACIJE nije ispravan. Mora biti niz znakova.");                  
                    if (!lista[2].matches("^.+$")) 
                        System.out.println(temp+"Atribut ADRESA LOKACIJE nije ispravan. Mora biti broj veci ili jednak 0");                  
                    if (!lista[3].matches("^((\\d+\\.\\d+\\,\\d+\\.\\d+)|(\\d+\\.\\d+\\,\\ \\d+\\.\\d+))$")) 
                        System.out.println(temp+"Atribut GPS nije ispravan. Mora biti 2 decimalna broja koji su odvojeni zarezom npr. 43,345 43,546");                  
                    
                    break;           
                default:
                    System.out.println(temp+"Neispravan broj argumenata!");
            }
            
            if(!PostavkeSingleton.konfiguracija.containsKey("izlaz")){
                System.out.print(PostavkeSingleton.ANSI_RESET);
            }
        }
    }

}
