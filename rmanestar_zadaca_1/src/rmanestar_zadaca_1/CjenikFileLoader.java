/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmanestar_zadaca_1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

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
    void parseLine(String linija) {
        int smCounter = 0;
        for (int i = 0; i < linija.length(); i++) 
            if (linija.charAt(i) == ';') 
                smCounter++;               

        if (linija.matches("^([1-9]+[0-9]*|[0]); ([1-9]+[0-9]*|[0]); ([1-9]+[0-9]*|[0]); ([1-9]+[0-9]*|[0])$") && smCounter == 3) {
            try {
                String[] lista = linija.split("; ");

                int voziloId = Integer.parseInt(lista[0].replace(" ", ""));
                int najam = Integer.parseInt(lista[1].replace(" ", ""));
                int poSatu = Integer.parseInt(lista[2].replace(" ", ""));
                int poKm = Integer.parseInt(lista[3].replace(" ", ""));

                PostavkeSingleton.getCjenikLista().add(new Cjenik(voziloId, najam, poSatu, poKm));

            } catch (Exception e) {

            }
        } else {
            PostavkeSingleton.ps.println("Datoteka CJENIK - Pogrešan format linija: " + linija);
            String[] lista = linija.split("; ");

            switch (smCounter) {
                case 3:
                    if (!lista[0].matches("^([1-9]+[0-9]*|[0])$")) 
                        PostavkeSingleton.ps.println("Atribut ID VRSTE VOZILA nije ispravan. Mora biti broj veći ili jednak 0");                  
                    if (!lista[1].matches("^([1-9]+[0-9]*|[0])$")) 
                        PostavkeSingleton.ps.println("Atribut NAJAM nije ispravan. Mora biti broj veći ili jednak 0");                  
                    if (!lista[2].matches("^([1-9]+[0-9]*|[0])$")) 
                        PostavkeSingleton.ps.println("Atribut PO SATU nije ispravan. Mora biti broj veći ili jednak 0");                  
                    if (!lista[3].matches("^([1-9]+[0-9]*|[0])$")) 
                        PostavkeSingleton.ps.println("Atribut PO KM nije ispravan. Mora biti broj veći ili jednak 0");                  
                    
                    break;           
                default:
                    PostavkeSingleton.ps.println("Neispravan broj argumenata!");
            }
        }

    }

    @Override
    void parseFile(String putanja) {
        try {
            File fileObj = new File(putanja);
            Scanner myReader = new Scanner(fileObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                parseLine(data);
                //System.out.println(data);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("Nije pronađena datoteka na putanji: " + putanja);
            e.printStackTrace();
        }
    }
}
