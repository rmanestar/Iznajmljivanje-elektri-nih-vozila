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
import rmanestar_zadaca_3.Cjenik;

/**
 *
 * @author Mane
 */
// ([1-9]+[0-9]*|[0]); ([1-9]+[0-9]*|[0]); ([1-9]+[0-9]*|[0]); ([1-9]+[0-9]*|[0])
// id vrvozila - ([1-9]+[0-9]*|[0])
// najam - ([1-9]+[0-9]*|[0])
//  po satu - ([1-9]+[0-9]*|[0])
//  po km - ([1-9]+[0-9]*|[0])
public class CjenikFileLoader extends FileLoader {

    @Override
    public void parseLine(String linija) {
        int smCounter = 0;
        for (int i = 0; i < linija.length(); i++) 
            if (linija.charAt(i) == ';') 
                smCounter++;               

        if (linija.matches("^([1-9]+[0-9]*|[0]); ([1-9]+[0-9]*|[0]),([0-9][0-9]); ([1-9]+[0-9]*|[0]),([0-9][0-9]); ([1-9]+[0-9]*|[0]),([0-9][0-9])$") && smCounter == 3) {
            try {
                String[] lista = linija.split("; ");

                int voziloId = Integer.parseInt(lista[0].replace(" ", ""));
                //DecimalFormat df = new DecimalFormat("0.00");
                //df.setMaximumFractionDigits(2);
                float najam = Float.parseFloat(lista[1].replace(" ", "").replace(",", "."));
                float poSatu = Float.parseFloat(lista[2].replace(" ", "").replace(",", "."));
                float poKm = Float.parseFloat(lista[3].replace(" ", "").replace(",", "."));
                //System.out.println(voziloId);
                //System.out.println(df.format(najam));
                //System.out.println(df.format(poSatu));
                //System.out.println(df.format(poKm));

                PostavkeSingleton.getCjenikLista().add(new Cjenik(voziloId, najam, poSatu, poKm));

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            
            String temp="";
            if(!PostavkeSingleton.konfiguracija.containsKey("izlaz")){
                temp=PostavkeSingleton.ANSI_RED;
            }
            
            System.out.println(temp+"Datoteka CJENIK - Pogresan format linija: " + linija);
            
            if(PostavkeSingleton.konfiguracija.containsKey("izlaz")){
                try {     
                    System.setOut(new PrintStream(new FileOutputStream(PostavkeSingleton.konfiguracija.getProperty("izlaz"))));
                } catch (FileNotFoundException ex) {
                }
                System.out.println("Datoteka CJENIK  - Pogresan format linija: " + linija);

                for(int i=0; i<("Datoteka CJENIK - Pogresan format linija: " + linija).length();i++){
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
                        System.out.println(temp+"Atribut ID VRSTE VOZILA nije ispravan. Mora biti broj veci ili jednak 0");                  
                    if (!lista[1].matches("^([1-9]+[0-9]*|[0]),([0-9][0-9])$")) 
                        System.out.println(temp+"Atribut NAJAM nije ispravan. Mora biti decimalan broj veci ili jednak 0.00");                  
                    if (!lista[2].matches("^([1-9]+[0-9]*|[0]),([0-9][0-9])$")) 
                        System.out.println(temp+"Atribut PO SATU nije ispravan. Mora biti decimalan broj veci ili jednak 0.00");                  
                    if (!lista[3].matches("^([1-9]+[0-9]*|[0]),([0-9][0-9])$")) 
                        System.out.println(temp+"Atribut PO KM nije ispravan. Mora biti decimalan broj veci ili jednak 0.00");                  
                    
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
