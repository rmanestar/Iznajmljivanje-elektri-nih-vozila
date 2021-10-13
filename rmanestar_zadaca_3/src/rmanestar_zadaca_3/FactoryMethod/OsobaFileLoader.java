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
import rmanestar_zadaca_3.Osoba;

/**
 *
 * @author Mane
 */
// cjelokupni ^(([1-9]+[0-9]*)|[0]); .+$

public class OsobaFileLoader extends FileLoader{
     
    @Override
    public void parseLine(String linija) {
        int smCounter = 0;
        for (int i = 0; i < linija.length(); i++) 
            if (linija.charAt(i) == ';') 
                smCounter++;
            
        if (linija.matches("^(([1-9]+[0-9]*)|[0]); .+; ([0]|[1])$") && smCounter == 2) {
            try {
                String[] lista = linija.split("; ");

                int id = Integer.parseInt(lista[0].replace(" ", ""));
                String imePrezime = lista[1];
                int ugovor = Integer.parseInt(lista[2].replace(" ", ""));
                PostavkeSingleton.getOsobaLista().add(new Osoba(id, imePrezime, ugovor));

            } catch (Exception e) {

            }
        } else {
            String temp="";
            if(!PostavkeSingleton.konfiguracija.containsKey("izlaz")){
                temp=PostavkeSingleton.ANSI_RED;
            }
            
            System.out.println(temp+"Datoteka OSOBE  - Pogresan format linija: " + linija);
            
            if(PostavkeSingleton.konfiguracija.containsKey("izlaz")){
                try {     
                    System.setOut(new PrintStream(new FileOutputStream(PostavkeSingleton.konfiguracija.getProperty("izlaz"))));
                } catch (FileNotFoundException ex) {
                }
                System.out.println("Datoteka OSOBE  - Pogresan format linija: " + linija);

                for(int i=0; i<("Datoteka OSOBE - Pogresan format linija: " + linija).length();i++){
                    System.out.print("-");
                }
                System.out.println();
                try { 
                System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
                }catch(Exception e){}
            }
            
            String[] lista = linija.split("; ");

            switch (smCounter) {
                case 2:
                    if (!lista[0].matches("^([1-9]+[0-9]*|[0])$")) 
                        System.out.println(temp+"Atribut ID nije ispravan. Mora biti broj veci ili jednak 0");                  
                    if (!lista[1].matches("^.+$")) 
                        System.out.println(temp+"Atribut IME I PREZIME nije ispravan. Mora biti niz znakova.");     
                    if (!lista[2].matches("^([0]|[1])$")) 
                        System.out.println(temp+"Atribut UGOVOR nije ispravan. Mora biti 0 ili 1.");     
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
