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
import rmanestar_zadaca_3.ElektricnoVozilo;

/**
 *
 * @author Mane
 */
//  ([1-9]+[0-9]*|[0]); .+; ([1-9]+[0-9]*|[0]); ([1-9]+[0-9]*|[0]) 
// cijelokupni format vozilo
public class ElektricnoVoziloFileLoader extends FileLoader {

    @Override
    public void parseLine(String linija) {
        int smCounter = 0;
        for(int i=0; i<linija.length();i++)
            if(linija.charAt(i)==';') smCounter++;
      
        if (linija.matches("^([1-9]+[0-9]*|[0]); .+; ([1-9]+[0-9]*|[0]); ([1-9]+[0-9]*|[0])$") && smCounter==3) {
            try {
                String[] lista = linija.split("; ");
                //for (String rijec : lista) 
                  //  System.out.println(rijec);
                
                int id = Integer.parseInt(lista[0].replace(" ", ""));
                String naziv = lista[1];
                int vrijemePunjenja = Integer.parseInt(lista[2].replace(" ", ""));
                int domet = Integer.parseInt(lista[3].replace(" ", ""));

                PostavkeSingleton.getElektricnoVoziloLista().add(new ElektricnoVozilo(id, naziv, vrijemePunjenja, domet));

            } catch (Exception e) {

            }
        }else { 
            String temp="";
            if(!PostavkeSingleton.konfiguracija.containsKey("izlaz")){
                temp=PostavkeSingleton.ANSI_RED;
            }
            
            System.out.println(temp+"Datoteka VOZILA  - Pogresan format linija: " + linija);
            
            if(PostavkeSingleton.konfiguracija.containsKey("izlaz")){
                try {     
                    System.setOut(new PrintStream(new FileOutputStream(PostavkeSingleton.konfiguracija.getProperty("izlaz"))));
                } catch (FileNotFoundException ex) {
                }
                System.out.println("Datoteka VOZILA  - Pogresan format linija: " + linija);

                for(int i=0; i<("Datoteka VOZILA - Pogresan format linija: " + linija).length();i++){
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
                        System.out.println(temp+"Atribut ID VOZILA nije ispravan. Mora biti broj veci ili jednak 0");                  
                    if (!lista[1].matches("^.+$")) 
                        System.out.println(temp+"Atribut NAZIV VOZILA nije ispravan. Mora biti niz znakova.");                  
                    if (!lista[2].matches("^([1-9]+[0-9]*|[0])$")) 
                        System.out.println(temp+"Atribut VRIJEME PUNJENJA BATERIJE nije ispravan. Mora biti broj veci ili jednak 0");                  
                    if (!lista[3].matches("^([1-9]+[0-9]*|[0])$")) 
                        System.out.println(temp+"Atribut DOMET nije ispravan. Mora biti broj veci ili jednak 0");                  
                    
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
