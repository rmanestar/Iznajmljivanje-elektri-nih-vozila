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
public abstract class FileLoader {
    
    abstract void parseLine(String linija);
    abstract void parseFile(String putanja);
    
    public void readFile(String putanja){
        try {
            File fileObj = new File(putanja);
            Scanner myReader = new Scanner(fileObj);
            while (myReader.hasNextLine()) {
              String data = myReader.nextLine();
              //System.out.println(data);
            }
            myReader.close();
          } catch (FileNotFoundException e) {
            PostavkeSingleton.ps.println("Nije pronađena datoteka na putanji: " + putanja);
            e.printStackTrace();
          }
    }
    
}
