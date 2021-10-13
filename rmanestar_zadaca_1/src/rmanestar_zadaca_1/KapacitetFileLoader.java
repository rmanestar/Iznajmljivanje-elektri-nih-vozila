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
// (([1-9]+[0-9]*)|[0]); (([1-9]+[0-9]*)|[0]); (([1-9]+[0-9]*)|[0]); (([1-9]+[0-9]*)|[0])

public class KapacitetFileLoader extends FileLoader {

    @Override
    void parseLine(String linija) {
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
                    PostavkeSingleton.ps.println("Datoteka kapaciteti: Linija: " + linija +" - Broj raspoloživih vozila ne smije biti veći od broj mjesta za punjenje LINIJA PRESKOČENA");
                }
            } catch (Exception e) {

            }
        } else {
            PostavkeSingleton.ps.println("Datoteka KAPACITETI  - Pogrešan format linija: " + linija);
            String[] lista = linija.split("; ");

             switch (smCounter) {
                case 3:
                    if (!lista[0].matches("^([1-9]+[0-9]*|[0])$")) 
                        PostavkeSingleton.ps.println("Atribut ID LOKACIJE nije ispravan. Mora biti broj veći ili jednak 0");                  
                    if (!lista[1].matches("^([1-9]+[0-9]*|[0])$")) 
                        PostavkeSingleton.ps.println("Atribut ID VRSTE VOZILA nije ispravan. Mora biti broj veći ili jednak 0");                  
                    if (!lista[2].matches("^([1-9]+[0-9]*|[0])$")) 
                        PostavkeSingleton.ps.println("Atribut BROJ MJESTA ZA VRSTU VOZILA nije ispravan. Mora biti broj veći ili jednak 0");                  
                    if (!lista[3].matches("^([1-9]+[0-9]*|[0])$")) 
                        PostavkeSingleton.ps.println("Atribut RASPOLOŽIVI BROJ VOZILA nije ispravan. Mora biti broj veći ili jednak 0");                  
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
