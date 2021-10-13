/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmanestar_zadaca_2.FactoryMethod;

import rmanestar_zadaca_2.Singleton.PostavkeSingleton;
import rmanestar_zadaca_2.Osoba;

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
            
        if (linija.matches("^(([1-9]+[0-9]*)|[0]); .+$") && smCounter == 1) {
            try {
                String[] lista = linija.split("; ");

                int id = Integer.parseInt(lista[0].replace(" ", ""));
                String imePrezime = lista[1];

                PostavkeSingleton.getOsobaLista().add(new Osoba(id, imePrezime));

            } catch (Exception e) {

            }
        } else {
            PostavkeSingleton.ps.println("Datoteka OSOBE  - Pogresan format linija: " + linija);
            String[] lista = linija.split("; ");

            switch (smCounter) {
                case 1:
                    if (!lista[0].matches("^([1-9]+[0-9]*|[0])$")) 
                        PostavkeSingleton.ps.println("Atribut ID nije ispravan. Mora biti broj veci ili jednak 0");                  
                    if (!lista[1].matches("^.+$")) 
                        PostavkeSingleton.ps.println("Atribut IME I PREZIME nije ispravan. Mora biti niz znakova.");                                     
                    break;           
                default:
                    PostavkeSingleton.ps.println("Neispravan broj argumenata!");
            }
        }
    }

}
