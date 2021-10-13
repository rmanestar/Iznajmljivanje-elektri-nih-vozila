/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmanestar_zadaca_2.FactoryMethod;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import rmanestar_zadaca_2.Singleton.PostavkeSingleton;

/**
 *
 * @author Mane
 */
public class KonfiguracijaFileLoader extends FileLoader{

    @Override
    public void parseLine(String linija) {
        try{
            String[] lista = linija.split("=");
            if(lista.length==2){
                String key = lista[0];
                String value = lista[1];
                PostavkeSingleton.konfiguracija.setProperty(key,value);
                
            }else{
                PostavkeSingleton.ps.println("Datoteka Konfiguracije - Pogresan format linija: " + linija);                  
                return;
            }
            
        }catch(Exception e){
        
        }
    }
  
    @Override
    public void parseFile(String putanja){
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
            //e.printStackTrace();
        }       
    }
    
    
}
