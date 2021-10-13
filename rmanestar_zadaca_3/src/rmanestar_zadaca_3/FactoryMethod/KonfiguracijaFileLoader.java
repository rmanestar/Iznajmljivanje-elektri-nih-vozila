/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmanestar_zadaca_3.FactoryMethod;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Scanner;
import rmanestar_zadaca_3.Singleton.PostavkeSingleton;

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
                String temp="";
                if(!PostavkeSingleton.konfiguracija.containsKey("izlaz")){
                    temp=PostavkeSingleton.ANSI_RED;
                }
            
                System.out.println(temp+"Datoteka KONFIGURACIJA - Pogresan format linija: \n" + linija);       
                
                if(PostavkeSingleton.konfiguracija.containsKey("izlaz")){
                try {     
                    System.setOut(new PrintStream(new FileOutputStream(PostavkeSingleton.konfiguracija.getProperty("izlaz"))));
                } catch (FileNotFoundException ex) {
                }
                System.out.println("Datoteka KONFIGURACIJA  - Pogresan format linija: " + linija);

                for(int i=0; i<("Datoteka KONFIGURACIJA - Pogresan format linija: " + linija).length();i++){
                    System.out.print("-");
                }
                System.out.println();
                try { 
                System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
                }catch(Exception e){}
                }
                
                if(!PostavkeSingleton.konfiguracija.containsKey("izlaz")){
                System.out.print(PostavkeSingleton.ANSI_RESET);
                }
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
            System.out.println("Nije pronađena datoteka na putanji: " + putanja);
            //e.printStackTrace();
        }       
    }
    
    
}
