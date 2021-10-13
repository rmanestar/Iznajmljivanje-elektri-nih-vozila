/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmanestar_zadaca_2.FactoryMethod;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import rmanestar_zadaca_2.OrganizacijskaJedinica;
import rmanestar_zadaca_2.Singleton.PostavkeSingleton;


// reg ex  ^((([1-9]+[0-9]*)|[0]); .+;( (([1-9]+[0-9]*))|( [0])|( ));( (([1-9]+[0-9]*)|( [0])),)+ (([1-9]+[0-9]*)|[0]))|((([1-9]+[0-9]*)|[0]); .+; (([1-9]+[0-9]*)|[0]);)$
/**
 *
 * @author Mane
 */
public class OrganizacijskaJedinicaFileLoader extends FileLoader{
    
    @Override
    public void parseLine(String linija) {
        int smCounter = 0;
        for (int i = 0; i < linija.length(); i++) 
            if (linija.charAt(i) == ';') 
                smCounter++;
        
        if (linija.matches("^((([1-9]+[0-9]*)|[0]); .+;( (([1-9]+[0-9]*))|( [0])|( ));(( (([1-9]+[0-9]*)|( [0])),)+ (([1-9]+[0-9]*)|[0])|( (([1-9]+[0-9]*)|[0]))))|((([1-9]+[0-9]*)|[0]); .+; (([1-9]+[0-9]*)|[0]);)$") && smCounter == 3) {
            try {
                String[] lista = linija.split("; ");
                                
                int id = 0;
                String naziv = "";
                int nadredenaJedinica = -1;
                String lokacije = "";
                  
                switch(lista.length){
                    case 3:
                        id = Integer.parseInt(lista[0].replace(" ", ""));
                        naziv = lista[1];
                        if(lista[2].isEmpty()){
                            nadredenaJedinica=-1;
                        }else nadredenaJedinica = Integer.parseInt(lista[2].replace(" ", "").replace(";", ""));
                        break;                        
                    case 4:
                        id = Integer.parseInt(lista[0].replace(" ", ""));
                        naziv = lista[1];
                        if(lista[2].isEmpty()){
                            nadredenaJedinica=-1;
                        }else nadredenaJedinica = Integer.parseInt(lista[2].replace(" ", ""));                        
                        lokacije = lista[3];
                        break;
                    default:
                        
                        break;
                }
                
                PostavkeSingleton.getOrganizacijskaJedinicaLista().add(new OrganizacijskaJedinica(id,naziv,nadredenaJedinica,lokacije));

            } catch (Exception e) {
            }
        } else {           
            PostavkeSingleton.ps.println("Datoteka Tvrtka  - Pogresan format linija: " + linija);
            String[] lista = linija.split("; ");

            switch (smCounter) {
                case 3:
                    if(lista.length==3){
                        if (!lista[0].matches("^([1-9]+[0-9]*|[0])$")) 
                            PostavkeSingleton.ps.println("Atribut ID nije ispravan. Mora biti broj veci ili jednak 0");                  
                        if (!lista[1].matches("^.+$")) 
                            PostavkeSingleton.ps.println("Atribut NAZIV JEDINICE nije ispravan. Mora biti niz znakova.");
                        if (!lista[2].matches("^((([1-9]+[0-9]*))|([0])|( ))$")) 
                            PostavkeSingleton.ps.println("Atribut NADREDENA JEDINICA nije ispravan. Mora biti broj veci ili jednak 0 ili nedefiniran (prazan).");
                    }else if(lista.length==4){
                        if (!lista[0].matches("^([1-9]+[0-9]*|[0])$")) 
                            PostavkeSingleton.ps.println("Atribut ID nije ispravan. Mora biti broj veci ili jednak 0");                  
                        if (!lista[1].matches("^.+$")) 
                            PostavkeSingleton.ps.println("Atribut NAZIV JEDINICE nije ispravan. Mora biti niz znakova.");
                        if (!lista[2].matches("^((([1-9]+[0-9]*))|([0])|( ))$")) 
                            PostavkeSingleton.ps.println("Atribut NADREDENA JEDINICA nije ispravan. Mora biti broj veci ili jednak 0 ili nedefiniran.");
                        if (!lista[3].matches("^(((([1-9]+[0-9]*)|([0])), )+(([1-9]+[0-9]*)|([0])))|((([1-9]+[0-9]*)|([0])))$")) 
                            PostavkeSingleton.ps.println("Atribut LOKACIJE nije ispravan. Mora biti niz brojeva kod kojih je svaki broj u nizu odvojen zarezom i razmakom ili nedefinirana lista (prazna).");                      
                    }                  
                    break;           
                default:
                    PostavkeSingleton.ps.println("Neispravan broj argumenata!");
            } 
        }
    }

    @Override
    public void parseFile(String putanja) {
        try {
            File fileObj = new File(putanja);
            Scanner myReader = new Scanner(fileObj);
            myReader.nextLine();
            while (myReader.hasNextLine()) {
              String data = myReader.nextLine();
              parseLine(data);
              //System.out.println(data);
            }
            myReader.close();
            OrganizacijskaJedinica.popuniNadredenuJedinicu();
            OrganizacijskaJedinica.popuniLokacijeOrganizacijskihJedinica();
            
          } catch (FileNotFoundException e) {
            PostavkeSingleton.ps.println("Nije pronađena datoteka na putanji: " + putanja);
            //e.printStackTrace();
          } 
    }
    
}
