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
import java.text.SimpleDateFormat;
import java.util.Date;
import rmanestar_zadaca_3.Aktivnost;

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

//novi (0; [0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2})|([1-3]; [0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2}; ([1-9]+[0-9]*|[0]); ([1-9]+[0-9]*|[0]); ([1-9]+[0-9]*|[0]))|(4; [0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2}; ([1-9]+[0-9]*|[0]); ([1-9]+[0-9]*|[0]); ([1-9]+[0-9]*|[0]); ([1-9]+[0-9]*|[0]); .+)|(4; [0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2}; ([1-9]+[0-9]*|[0]); ([1-9]+[0-9]*|[0]); ([1-9]+[0-9]*|[0]); ([1-9]+[0-9]*|[0]))
// old (0; [0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2})|([1-3]; [0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2}; ([1-9]+[0-9]*|[0]); ([1-9]+[0-9]*|[0]); ([1-9]+[0-9]*|[0]))|(4; [0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2}; ([1-9]+[0-9]*|[0]); ([1-9]+[0-9]*|[0]); ([1-9]+[0-9]*|[0]); ([1-9]+[0-9]*|[0]); .+)|(4; [0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2}; ([1-9]+[0-9]*|[0]); ([1-9]+[0-9]*|[0]); ([1-9]+[0-9]*|[0]); ([1-9]+[0-9]*|[0]))
public class AktivnostFileLoader extends FileLoader {

    @Override
    public void parseLine(String linija) {
        int smCounter = 0;
        for (int i = 0; i < linija.length(); i++) {
            if (linija.charAt(i) == ';') {
                smCounter++;
            }
        }

        String novaLinija = linija.replaceAll("[^\\x20-\\x7f]", "");

        if (novaLinija.matches("^(0; [0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2})|([1-3]; [0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2}; ([1-9]+[0-9]*|[0]); ([1-9]+[0-9]*|[0]); ([1-9]+[0-9]*|[0]))|(4; [0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2}; ([1-9]+[0-9]*|[0]); ([1-9]+[0-9]*|[0]); ([1-9]+[0-9]*|[0]); ([1-9]+[0-9]*|[0]); .+)|(4; [0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2}; ([1-9]+[0-9]*|[0]); ([1-9]+[0-9]*|[0]); ([1-9]+[0-9]*|[0]); ([1-9]+[0-9]*|[0]))|(5; ([\\w]|[:]|[\\\\]|[\\/]|[\\.]|[-])+.txt)|(6; struktura stanje ([1-9]+[0-9]*|[0]))|(6; struktura)|(7; struktura najam zarada ([0-9][0-9])\\.([0-9][0-9])\\.([0-9][0-9][0-9][0-9]) ([0-9][0-9])\\.([0-9][0-9])\\.([0-9][0-9][0-9][0-9]) ([1-9]+[0-9]*|[0]))|(7; struktura najam ([0-9][0-9])\\.([0-9][0-9])\\.([0-9][0-9][0-9][0-9]) ([0-9][0-9])\\.([0-9][0-9])\\.([0-9][0-9][0-9][0-9]) ([1-9]+[0-9]*|[0]))|(7; struktura ([0-9][0-9])\\.([0-9][0-9])\\.([0-9][0-9][0-9][0-9]) ([0-9][0-9])\\.([0-9][0-9])\\.([0-9][0-9][0-9][0-9])|(8; struktura rauni ([0-9][0-9])\\.([0-9][0-9])\\.([0-9][0-9][0-9][0-9]) ([0-9][0-9])\\.([0-9][0-9])\\.([0-9][0-9][0-9][0-9]) ([1-9]+[0-9]*|[0]))|(8; struktura ([0-9][0-9])\\.([0-9][0-9])\\.([0-9][0-9][0-9][0-9]) ([0-9][0-9])\\.([0-9][0-9])\\.([0-9][0-9][0-9][0-9]))|(9)|(10; ([1-9]+[0-9]*|[0]) ([0-9][0-9])\\.([0-9][0-9])\\.([0-9][0-9][0-9][0-9]) ([0-9][0-9])\\.([0-9][0-9])\\.([0-9][0-9][0-9][0-9]))|(11; ([1-9]+[0-9]*|[0]) ([1-9]+[0-9]*|[0]),([0-9][0-9])))$")) {
            try {
                String[] lista = novaLinija.split("; ");
                //for (String rijec : lista) 
                //  System.out.println(rijec);

                int id = Integer.parseInt(lista[0].replace(" ", ""));
                String sdfVrijeme = "";
                Date vrijeme = null;
                int korisnikId = 0;
                int lokacijaId = 0;
                int voziloId = 0;
                int brojKm = 0;
                float iznos=0.0f;
                String opisProblema = "";
                
                String stringParametar1="";
                String stringParametar2="";
                String stringParametar3="";
                Date pocetni=null,krajnji=null;
                
                int idOrgJd;
                
                if (id == 1 || id == 2 || id == 3 || id == 4) {
                    sdfVrijeme = lista[1];
                    vrijeme = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(sdfVrijeme);
                    korisnikId = Integer.parseInt(lista[2].replace(" ", ""));
                    lokacijaId = Integer.parseInt(lista[3].replace(" ", ""));
                    voziloId = Integer.parseInt(lista[4].replace(" ", ""));
                }
                
                switch (id) {
                    case 4:
                        brojKm = Integer.parseInt(lista[5].replace(" ", ""));
                        try{
                            opisProblema=lista[6];
                        }catch(Exception e){
                        }   break;
                    case 5:
                        stringParametar1=lista[1]; 
                        break;
                    case 6:
                        {
                            String[] listaAtributa = lista[1].split(" ");
                            switch(listaAtributa.length){
                                case 1:
                                    stringParametar1 = listaAtributa[0];
                                    PostavkeSingleton.getAktivnostLista().add(new Aktivnost(id, stringParametar1));
                                    break;
                                case 3:
                                    stringParametar1 = listaAtributa[0];
                                    stringParametar2 = listaAtributa[1];
                                    idOrgJd = Integer.parseInt(listaAtributa[2]);
                                    PostavkeSingleton.getAktivnostLista().add(new Aktivnost(id, stringParametar1,stringParametar2,idOrgJd));
                                    break;
                            }       break;
                        }
                    case 7:
                        {
                            String[] listaAtributa = lista[1].split(" ");
                            switch(listaAtributa.length){
                                case 3:
                                    stringParametar1 = listaAtributa[0];
                                    try{
                                        pocetni = new SimpleDateFormat("dd.MM.yyyy").parse(listaAtributa[1]);
                                        krajnji = new SimpleDateFormat("dd.MM.yyyy").parse(listaAtributa[2]);
                                        
                                    }catch(Exception e){}
                                    PostavkeSingleton.getAktivnostLista().add(new Aktivnost(id, stringParametar1, pocetni,krajnji));
                                    
                                    break;
                                case 5:
                                    stringParametar1 = listaAtributa[0];
                                    stringParametar2 = listaAtributa[1];
                                    idOrgJd=-1;
                                    try{
                                        pocetni = new SimpleDateFormat("dd.MM.yyyy").parse(listaAtributa[2]);
                                        krajnji = new SimpleDateFormat("dd.MM.yyyy").parse(listaAtributa[3]);
                                        idOrgJd = Integer.parseInt(listaAtributa[4]);
                                    }catch(Exception e){}
                                    PostavkeSingleton.getAktivnostLista().add(new Aktivnost(id, stringParametar1,stringParametar2, pocetni,krajnji,idOrgJd));
                                    break;
                                case 6:
                                    stringParametar1 = listaAtributa[0];
                                    stringParametar2 = listaAtributa[1];
                                    stringParametar3 = listaAtributa[2];
                                    idOrgJd=-1;
                                    try{
                                        pocetni = new SimpleDateFormat("dd.MM.yyyy").parse(listaAtributa[3]);
                                        krajnji = new SimpleDateFormat("dd.MM.yyyy").parse(listaAtributa[4]);
                                        idOrgJd = Integer.parseInt(listaAtributa[5]);
                                    }catch(Exception e){}
                                    PostavkeSingleton.getAktivnostLista().add(new Aktivnost(id, stringParametar1,stringParametar2,stringParametar3, pocetni,krajnji,idOrgJd));
                                    break;
                            }       break;
                        }
                    case 8:
                        {
                            String[] listaAtributa = lista[1].split(" ");
                            switch(listaAtributa.length){
                                case 3:
                                    stringParametar1 = listaAtributa[0];
                                    try{
                                        pocetni = new SimpleDateFormat("dd.MM.yyyy").parse(listaAtributa[1]);
                                        krajnji = new SimpleDateFormat("dd.MM.yyyy").parse(listaAtributa[2]);
                                        
                                    }catch(Exception e){}
                                    PostavkeSingleton.getAktivnostLista().add(new Aktivnost(id, stringParametar1, pocetni,krajnji));
                                    
                                    break;
                                case 5:
                                    stringParametar1 = listaAtributa[0];
                                    stringParametar2 = listaAtributa[1].replaceAll("[^\\x20-\\x7f]", "");;
                                    idOrgJd=-1;
                                    try{
                                        pocetni = new SimpleDateFormat("dd.MM.yyyy").parse(listaAtributa[2]);
                                        krajnji = new SimpleDateFormat("dd.MM.yyyy").parse(listaAtributa[3]);
                                        idOrgJd = Integer.parseInt(listaAtributa[4]);
                                    }catch(Exception e){}
                                    PostavkeSingleton.getAktivnostLista().add(new Aktivnost(id, stringParametar1,stringParametar2, pocetni,krajnji,idOrgJd));
                                    break;
                            }       break;
                        }
                    case 9:
                        PostavkeSingleton.getAktivnostLista().add(new Aktivnost(id));
                        break;
                    case 10:
                        {
                            String[] listaAtributa = lista[1].split(" ");
                            korisnikId = Integer.parseInt(listaAtributa[0].replace(" ", ""));
                            pocetni = new SimpleDateFormat("dd.MM.yyyy").parse(listaAtributa[1]);
                            krajnji = new SimpleDateFormat("dd.MM.yyyy").parse(listaAtributa[2]);
                            PostavkeSingleton.getAktivnostLista().add(new Aktivnost(id,korisnikId,pocetni,krajnji));
                            break;
                        }
                    case 11:
                            String[] listaAtributa = lista[1].split(" ");
                            korisnikId = Integer.parseInt(listaAtributa[0].replace(" ", ""));
                            iznos = Float.parseFloat(listaAtributa[1].replace(" ", "").replace(",","."));
                            PostavkeSingleton.getAktivnostLista().add(new Aktivnost(id,korisnikId,iznos));
                        break;
                    default:
                        break;
                }
                
                if (id == 0) {
                    PostavkeSingleton.getAktivnostLista().add(new Aktivnost(id, vrijeme));
                } else if (id == 1 || id == 2 || id == 3) {
                    PostavkeSingleton.getAktivnostLista().add(new Aktivnost(id, vrijeme, korisnikId, lokacijaId, voziloId));
                } else if (id == 4) {
                    PostavkeSingleton.getAktivnostLista().add(new Aktivnost(id, vrijeme, korisnikId, lokacijaId, voziloId, brojKm, opisProblema));
                }else if(id == 5){
                    PostavkeSingleton.getAktivnostLista().add(new Aktivnost(id, stringParametar1));
                }

            } catch (Exception e) {

            }
        } else {
            String temp="";
            if(!PostavkeSingleton.konfiguracija.containsKey("izlaz")){
                temp=PostavkeSingleton.ANSI_RED;
            }
            
            System.out.println(temp+"Datoteka AKTIVNOSTI - Pogresan format linija: " + linija);
            
            if(PostavkeSingleton.konfiguracija.containsKey("izlaz")){
                try {     
                    System.setOut(new PrintStream(new FileOutputStream(PostavkeSingleton.konfiguracija.getProperty("izlaz"))));
                } catch (FileNotFoundException ex) {
                }
                System.out.println("Datoteka AKTIVNOSTI  - Pogresan format linija: " + linija);

                for(int i=0; i<("Datoteka AKTIVNOSTI - Pogresan format linija: " + linija).length();i++){
                    System.out.print("-");
                }
                System.out.println();
                try { 
                System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
                }catch(Exception e){}
            }
            
            String[] lista = novaLinija.split("; ");
                 
            switch (smCounter) {
                case 1:
                    if (!lista[0].matches("^[0-4]$")) 
                        System.out.println(temp+"Atribut ID AKTIVNOSTI nije u rasponu [0-4]");                  
                    if (!lista[1].matches("^[0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2}$")) 
                        System.out.println(temp+"Atribut VRIJEME nije ispravan. Ispravan format: yyyy-MM-dd");                  
                    break;
                case 4:
                    if (!lista[0].matches("^[0-4]$")) 
                        System.out.println(temp+"Atribut ID AKTIVNOSTI nije u rasponu [0-4]");
                    if (!lista[1].matches("^[0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2}$"))
                        System.out.println(temp+"Atribut VRIJEME nije ispravan. Ispravan format: yyyy-MM-dd");
                    if (!lista[2].matches("^([1-9]+[0-9]*|[0])$")) 
                        System.out.println(temp+"Atribut ID KORISNIKA nije ispravan. Mora biti broj veci ili jednak 0");
                    if (!lista[3].matches("^([1-9]+[0-9]*|[0])$"))
                        System.out.println(temp+"Atribut ID LOKACIJE nije ispravan. Mora biti broj veci ili jednak 0");
                    if (!lista[4].matches("^([1-9]+[0-9]*|[0])$")) 
                        System.out.println(temp+"Atribut ID VRSTE VOZILA nije ispravan. Mora biti broj veci ili jednak 0");                   
                    break;
                case 5:
                    if (!lista[0].matches("^[0-4]$")) 
                        System.out.println(temp+"Atribut ID AKTIVNOSTI nije u rasponu [0-4]");
                    if (!lista[1].matches("^[0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2}$")) 
                        System.out.println(temp+"Atribut VRIJEME nije ispravan. Ispravan format: yyyy-MM-dd");
                    if (!lista[2].matches("^([1-9]+[0-9]*|[0])$")) 
                        System.out.println(temp+"Atribut ID KORISNIKA nije ispravan. Mora biti broj veci ili jednak 0");
                    if (!lista[3].matches("^([1-9]+[0-9]*|[0])$"))
                        System.out.println(temp+"Atribut ID LOKACIJE nije ispravan. Mora biti broj veci ili jednak 0");
                    if (!lista[4].matches("^([1-9]+[0-9]*|[0])$")) 
                        System.out.println(temp+"Atribut ID VRSTE VOZILA nije ispravan. Mora biti broj veci ili jednak 0");
                    if (!lista[5].matches("^([1-9]+[0-9]*|[0])$")) 
                        System.out.println(temp+"Atribut BROJ KM nije ispravan. Mora biti broj veci ili jednak 0");                
                    break;
                case 6:
                    if (!lista[0].matches("^[0-4]$")) 
                        System.out.println(temp+"Atribut ID AKTIVNOSTI nije u rasponu [0-4]");
                    if (!lista[1].matches("^[0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2}$")) 
                        System.out.println(temp+"Atribut VRIJEME nije ispravan. Ispravan format: yyyy-MM-dd");
                    if (!lista[2].matches("^([1-9]+[0-9]*|[0])$")) 
                        System.out.println(temp+"Atribut ID KORISNIKA nije ispravan. Mora biti broj veci ili jednak 0");
                    if (!lista[3].matches("^([1-9]+[0-9]*|[0])$"))
                        System.out.println(temp+"Atribut ID LOKACIJE nije ispravan. Mora biti broj veci ili jednak 0");
                    if (!lista[4].matches("^([1-9]+[0-9]*|[0])$")) 
                        System.out.println(temp+"Atribut ID VRSTE VOZILA nije ispravan. Mora biti broj veci ili jednak 0");
                    if (!lista[5].matches("^([1-9]+[0-9]*|[0])$")) 
                        System.out.println(temp+"Atribut BROJ KM nije ispravan. Mora biti broj veci ili jednak 0");  
                    try{
                        if (!lista[6].matches("^([1-9]+[0-9]*|[0])$")) 
                            System.out.println(temp+"Atribut OPIS PROBLEMA nije ispravan. Mora biti niz znakova"); 
                    }
                    catch(Exception e){
                        
                    }
                    
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
