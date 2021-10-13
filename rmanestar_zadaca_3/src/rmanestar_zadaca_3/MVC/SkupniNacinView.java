/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmanestar_zadaca_3.MVC;

import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.text.ParseException;
import rmanestar_zadaca_3.FactoryMethod.FileLoader;
import rmanestar_zadaca_3.Singleton.PostavkeSingleton;

/**
 *
 * @author Mane
 */
public class SkupniNacinView extends SkupniNacinAbstract implements ViewInterface {
    @Override
    public String toString(){
        return "Skupni pogled";
    }
    @Override
    public void postaviVrstuIspisa(){
        System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out),true));
    }
    @Override
    public void pokreni(PostavkeSingleton postavkeSingleton, FileLoader aktivnostiLoader) throws ParseException{
        super.pokreni(postavkeSingleton, aktivnostiLoader);
    }
}
