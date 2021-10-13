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
//  ([1-9]+[0-9]*|[0]); .+; ([1-9]+[0-9]*|[0]); ([1-9]+[0-9]*|[0]) 
// cijelokupni format vozilo
public class ElektricnoVoziloFileLoader extends FileLoader {

    @Override
    void parseLine(String linija) {
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
            
            PostavkeSingleton.ps.println("Datoteka VOZILA  - Pogrešan format linija: " + linija);
            String[] lista = linija.split("; ");

            switch (smCounter) {
                case 3:
                    if (!lista[0].matches("^([1-9]+[0-9]*|[0])$")) 
                        PostavkeSingleton.ps.println("Atribut ID VOZILA nije ispravan. Mora biti broj veći ili jednak 0");                  
                    if (!lista[1].matches("^.+$")) 
                        PostavkeSingleton.ps.println("Atribut NAZIV VOZILA nije ispravan. Mora biti niz znakova.");                  
                    if (!lista[2].matches("^([1-9]+[0-9]*|[0])$")) 
                        PostavkeSingleton.ps.println("Atribut VRIJEME PUNJENJA BATERIJE nije ispravan. Mora biti broj veći ili jednak 0");                  
                    if (!lista[3].matches("^([1-9]+[0-9]*|[0])$")) 
                        PostavkeSingleton.ps.println("Atribut DOMET nije ispravan. Mora biti broj veći ili jednak 0");                  
                    
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
