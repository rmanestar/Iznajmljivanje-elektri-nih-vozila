/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmanestar_zadaca_1;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

/**
 *
 * @author Mane
 */
// cjelokupni ^(0; \„[0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2}\“)|([1-3]; \„[0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2}\“; ([1-9]+[0-9]*|[0]); ([1-9]+[0-9]*|[0]); ([1-9]+[0-9]*|[0]))|(4; \„[0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2}\“; ([1-9]+[0-9]*|[0]); ([1-9]+[0-9]*|[0]); ([1-9]+[0-9]*|[0]); ([1-9]+[0-9]*|[0]))$
// id - [0-4]
// vrijeme - \„[0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2}\“
// korisnikid - ([1-9]+[0-9]*|[0])
// lokacija id - ([1-9]+[0-9]*|[0])
// vrsta vozila id - ([1-9]+[0-9]*|[0])
// brojKm - ([1-9]+[0-9]*|[0])
public class AktivnostFileLoader extends FileLoader {

    @Override
    void parseLine(String linija) {
        int smCounter = 0;
        for (int i = 0; i < linija.length(); i++) {
            if (linija.charAt(i) == ';') {
                smCounter++;
            }
        }

        String novaLinija = linija.replaceAll("[^\\x20-\\x7f]", "");

        if (novaLinija.matches("^(0; [0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2})|([1-3]; [0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2}; ([1-9]+[0-9]*|[0]); ([1-9]+[0-9]*|[0]); ([1-9]+[0-9]*|[0]))|(4; [0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2}; ([1-9]+[0-9]*|[0]); ([1-9]+[0-9]*|[0]); ([1-9]+[0-9]*|[0]); ([1-9]+[0-9]*|[0]))$")) {
            try {
                String[] lista = novaLinija.split("; ");
                //for (String rijec : lista) 
                //  System.out.println(rijec);

                int id = Integer.parseInt(lista[0].replace(" ", ""));
                String sdfVrijeme = lista[1];
                Date vrijeme = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(sdfVrijeme);
                int korisnikId = 0;
                int lokacijaId = 0;
                int voziloId = 0;
                int brojKm = 0;

                if (id == 1 || id == 2 || id == 3 || id == 4) {
                    korisnikId = Integer.parseInt(lista[2].replace(" ", ""));
                    lokacijaId = Integer.parseInt(lista[3].replace(" ", ""));
                    voziloId = Integer.parseInt(lista[4].replace(" ", ""));
                }

                if (id == 4) {
                    brojKm = Integer.parseInt(lista[5].replace(" ", ""));
                }

                if (id == 0) {
                    PostavkeSingleton.getAktivnostLista().add(new Aktivnost(id, vrijeme));
                } else if (id == 1 || id == 2 || id == 3) {
                    PostavkeSingleton.getAktivnostLista().add(new Aktivnost(id, vrijeme, korisnikId, lokacijaId, voziloId));
                } else if (id == 4) {
                    PostavkeSingleton.getAktivnostLista().add(new Aktivnost(id, vrijeme, korisnikId, lokacijaId, voziloId, brojKm));
                }

            } catch (Exception e) {

            }
        } else {

            PostavkeSingleton.ps.println("Datoteka Aktivnosti - Pogrešan format linija: " + linija);
            String[] lista = novaLinija.split("; ");

            switch (smCounter) {
                case 1:
                    if (!lista[0].matches("^[0-4]$")) 
                        PostavkeSingleton.ps.println("Atribut ID AKTIVNOSTI nije u rasponu [0-4]");                  
                    if (!lista[1].matches("^[0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2}$")) 
                        PostavkeSingleton.ps.println("Atribut VRIJEME nije ispravan. Ispravan format: yyyy-MM-dd");                  
                    break;
                case 4:
                    if (!lista[0].matches("^[0-4]$")) 
                        PostavkeSingleton.ps.println("Atribut ID AKTIVNOSTI nije u rasponu [0-4]");
                    if (!lista[1].matches("^[0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2}$"))
                        PostavkeSingleton.ps.println("Atribut VRIJEME nije ispravan. Ispravan format: yyyy-MM-dd");
                    if (!lista[2].matches("^([1-9]+[0-9]*|[0])$")) 
                        PostavkeSingleton.ps.println("Atribut ID KORISNIKA nije ispravan. Mora biti broj veći ili jednak 0");
                    if (!lista[3].matches("^([1-9]+[0-9]*|[0])$"))
                        PostavkeSingleton.ps.println("Atribut ID LOKACIJE nije ispravan. Mora biti broj veći ili jednak 0");
                    if (!lista[4].matches("^([1-9]+[0-9]*|[0])$")) 
                        PostavkeSingleton.ps.println("Atribut ID VRSTE VOZILA nije ispravan. Mora biti broj veći ili jednak 0");                   
                    break;
                case 5:
                    if (!lista[0].matches("^[0-4]$")) 
                        PostavkeSingleton.ps.println("Atribut ID AKTIVNOSTI nije u rasponu [0-4]");
                    if (!lista[1].matches("^[0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2}$")) 
                        PostavkeSingleton.ps.println("Atribut VRIJEME nije ispravan. Ispravan format: yyyy-MM-dd");
                    if (!lista[2].matches("^([1-9]+[0-9]*|[0])$")) 
                        PostavkeSingleton.ps.println("Atribut ID KORISNIKA nije ispravan. Mora biti broj veći ili jednak 0");
                    if (!lista[3].matches("^([1-9]+[0-9]*|[0])$"))
                        PostavkeSingleton.ps.println("Atribut ID LOKACIJE nije ispravan. Mora biti broj veći ili jednak 0");
                    if (!lista[4].matches("^([1-9]+[0-9]*|[0])$")) 
                        PostavkeSingleton.ps.println("Atribut ID VRSTE VOZILA nije ispravan. Mora biti broj veći ili jednak 0");
                    if (!lista[5].matches("^([1-9]+[0-9]*|[0])$")) 
                        PostavkeSingleton.ps.println("Atribut BROJ KM nije ispravan. Mora biti broj veći ili jednak 0");                
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
