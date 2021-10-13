/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmanestar_zadaca_2.FactoryMethod;

import rmanestar_zadaca_2.Singleton.PostavkeSingleton;
import rmanestar_zadaca_2.Lokacija;

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
            PostavkeSingleton.ps.println("Datoteka LOKACIJE  - Pogresan format linija: " + linija);
            String[] lista = linija.split("; ");

            switch (smCounter) {
                case 3:
                    if (!lista[0].matches("^([1-9]+[0-9]*|[0])$")) 
                        PostavkeSingleton.ps.println("Atribut ID nije ispravan. Mora biti broj veci ili jednak 0");                  
                    if (!lista[1].matches("^.+$")) 
                        PostavkeSingleton.ps.println("Atribut NAZIV LOKACIJE nije ispravan. Mora biti niz znakova.");                  
                    if (!lista[2].matches("^.+$")) 
                        PostavkeSingleton.ps.println("Atribut ADRESA LOKACIJE nije ispravan. Mora biti broj veci ili jednak 0");                  
                    if (!lista[3].matches("^((\\d+\\.\\d+\\,\\d+\\.\\d+)|(\\d+\\.\\d+\\,\\ \\d+\\.\\d+))$")) 
                        PostavkeSingleton.ps.println("Atribut GPS nije ispravan. Mora biti broj veci ili jednak 0");                  
                    
                    break;           
                default:
                    PostavkeSingleton.ps.println("Neispravan broj argumenata!");
            }
        }
    }

}
