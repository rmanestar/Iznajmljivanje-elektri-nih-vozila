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
import rmanestar_zadaca_3.Kapacitet;

/**
 *
 * @author Mane
 */
// (([1-9]+[0-9]*)|[0]); (([1-9]+[0-9]*)|[0]); (([1-9]+[0-9]*)|[0]); (([1-9]+[0-9]*)|[0])

public class KapacitetFileLoader extends FileLoader {

    @Override
    public void parseLine(String linija) {
        int smCounter = 0;
        for (int i = 0; i < linija.length(); i++) 
            if (linija.charAt(i) == ';') 
                smCounter++;
            
        if (linija.matches("^(([1-9]+[0-9]*)|[0]); (([1-9]+[0-9]*)|[0]); (([1-9]+[0-9]*)|[0]); (([1-9]+[0-9]*)|[0])$") && smCounter == 3) {
            try {
                String[] lista = linija.split("; ");

                int lokacijaId = Integer.parseInt(lista[0].replace(" ", ""));
                int voziloId = Integer.parseInt(lista[1].replace(" ", ""));
                int brMjestaZaPunjenje = Integer.parseInt(lista[2].replace(" ", ""));
                int raspolozivo = Integer.parseInt(lista[3].replace(" ", ""));

                if(raspolozivo<=brMjestaZaPunjenje)
                    PostavkeSingleton.getKapacitetLista().add(new Kapacitet(lokacijaId, voziloId, brMjestaZaPunjenje, raspolozivo));
                else{
                    System.out.println("Datoteka kapaciteti: Linija: " + linija +" - Broj raspolozivih vozila ne smije biti veci od broj mjesta za punjenje LINIJA PRESKOCENA");
                }
            } catch (Exception e) {

            }
        } else {
            String temp="";
            if(!PostavkeSingleton.konfiguracija.containsKey("izlaz")){
                temp=PostavkeSingleton.ANSI_RED;
            }
            
            System.out.println(temp+"Datoteka KAPACITETI  - Pogresan format linija: " + linija);
            
            if(PostavkeSingleton.konfiguracija.containsKey("izlaz")){
                try {     
                    System.setOut(new PrintStream(new FileOutputStream(PostavkeSingleton.konfiguracija.getProperty("izlaz"))));
                } catch (FileNotFoundException ex) {
                }
                System.out.println("Datoteka KAPACITETI  - Pogresan format linija: " + linija);

                for(int i=0; i<("Datoteka KAPACITETI - Pogresan format linija: " + linija).length();i++){
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
                        System.out.println(temp+"Atribut ID LOKACIJE nije ispravan. Mora biti broj veci ili jednak 0");                  
                    if (!lista[1].matches("^([1-9]+[0-9]*|[0])$")) 
                        System.out.println(temp+"Atribut ID VRSTE VOZILA nije ispravan. Mora biti broj veci ili jednak 0");                  
                    if (!lista[2].matches("^([1-9]+[0-9]*|[0])$")) 
                        System.out.println(temp+"Atribut BROJ MJESTA ZA VRSTU VOZILA nije ispravan. Mora biti broj veci ili jednak 0");                  
                    if (!lista[3].matches("^([1-9]+[0-9]*|[0])$")) 
                        System.out.println(temp+"Atribut RASPOLOZIVI BROJ VOZILA nije ispravan. Mora biti broj veci ili jednak 0");                  
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
