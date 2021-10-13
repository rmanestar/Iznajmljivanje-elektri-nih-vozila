/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmanestar_zadaca_3.MVC;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.text.ParseException;
import rmanestar_zadaca_3.FactoryMethod.FileLoader;
import rmanestar_zadaca_3.Singleton.PostavkeSingleton;

/**
 *
 * @author Mane
 */
public class SkupniNacinKonzolaView extends SkupniNacinAbstract implements ViewInterface {
    @Override
    public String toString(){
        return "Skupni pogled - konzola";
    } 
    @Override
    public void pokreni(PostavkeSingleton ps, FileLoader aktivnostiLoader) throws ParseException {
        super.pokreni(ps, aktivnostiLoader);
    }
    @Override
    public void postaviVrstuIspisa(){
        try {     
            System.setOut(new PrintStream(new FileOutputStream(PostavkeSingleton.konfiguracija.getProperty("izlaz"),true)));
        } catch (FileNotFoundException ex) {
        }
    }
}
