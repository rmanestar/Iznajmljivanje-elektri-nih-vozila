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
public class RadController {
    PostavkeSingleton model;
    ViewInterface view;

    public RadController() {
        model=PostavkeSingleton.getInstance();
    }
    
    public void updateView(ViewInterface view,FileLoader aktivnostiLoader) throws ParseException{    
        this.view = view;
        System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
        System.out.println("Pokrecem: "+ view.toString());
        
        view.postaviVrstuIspisa();
        view.pokreni(model, aktivnostiLoader);
    }
    
}
