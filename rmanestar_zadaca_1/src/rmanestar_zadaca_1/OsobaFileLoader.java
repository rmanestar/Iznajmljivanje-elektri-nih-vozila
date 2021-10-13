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
// cjelokupni ^(([1-9]+[0-9]*)|[0]); .+$

public class OsobaFileLoader extends FileLoader{
     

    @Override
    void parseLine(String linija) {
        int smCounter = 0;
        for (int i = 0; i < linija.length(); i++) 
            if (linija.charAt(i) == ';') 
                smCounter++;
            
        if (linija.matches("^(([1-9]+[0-9]*)|[0]); .+$") && smCounter == 1) {
            try {
                String[] lista = linija.split("; ");

                int id = Integer.parseInt(lista[0].replace(" ", ""));
                String imePrezime = lista[1];

                PostavkeSingleton.getOsobaLista().add(new Osoba(id, imePrezime));

            } catch (Exception e) {

            }
        } else {
            PostavkeSingleton.ps.println("Datoteka OSOBE  - Pogrešan format linija: " + linija);
            String[] lista = linija.split("; ");

            switch (smCounter) {
                case 1:
                    if (!lista[0].matches("^([1-9]+[0-9]*|[0])$")) 
                        PostavkeSingleton.ps.println("Atribut ID nije ispravan. Mora biti broj veći ili jednak 0");                  
                    if (!lista[1].matches("^.+$")) 
                        PostavkeSingleton.ps.println("Atribut IME I PREZIME nije ispravan. Mora biti niz znakova.");                                     
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
            PostavkeSingleton.ps.println("Nije pronađena datoteka na putanji: " + putanja);
            e.printStackTrace();
          }   
    }
}
