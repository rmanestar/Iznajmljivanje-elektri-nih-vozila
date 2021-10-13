/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmanestar_zadaca_2.FactoryMethod;

import rmanestar_zadaca_2.Singleton.PostavkeSingleton;
import rmanestar_zadaca_2.Kapacitet;

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
                    PostavkeSingleton.ps.println("Datoteka kapaciteti: Linija: " + linija +" - Broj raspolozivih vozila ne smije biti veci od broj mjesta za punjenje LINIJA PRESKOCENA");
                }
            } catch (Exception e) {

            }
        } else {
            PostavkeSingleton.ps.println("Datoteka KAPACITETI  - Pogresan format linija: " + linija);
            String[] lista = linija.split("; ");

             switch (smCounter) {
                case 3:
                    if (!lista[0].matches("^([1-9]+[0-9]*|[0])$")) 
                        PostavkeSingleton.ps.println("Atribut ID LOKACIJE nije ispravan. Mora biti broj veci ili jednak 0");                  
                    if (!lista[1].matches("^([1-9]+[0-9]*|[0])$")) 
                        PostavkeSingleton.ps.println("Atribut ID VRSTE VOZILA nije ispravan. Mora biti broj veci ili jednak 0");                  
                    if (!lista[2].matches("^([1-9]+[0-9]*|[0])$")) 
                        PostavkeSingleton.ps.println("Atribut BROJ MJESTA ZA VRSTU VOZILA nije ispravan. Mora biti broj veci ili jednak 0");                  
                    if (!lista[3].matches("^([1-9]+[0-9]*|[0])$")) 
                        PostavkeSingleton.ps.println("Atribut RASPOLOZIVI BROJ VOZILA nije ispravan. Mora biti broj veci ili jednak 0");                  
                    break;           
                default:
                    PostavkeSingleton.ps.println("Neispravan broj argumenata!");
            }
        }

    }

}
