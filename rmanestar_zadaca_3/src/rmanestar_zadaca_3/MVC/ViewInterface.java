/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmanestar_zadaca_3.MVC;

import java.text.ParseException;
import rmanestar_zadaca_3.FactoryMethod.FileLoader;
import rmanestar_zadaca_3.Singleton.PostavkeSingleton;

/**
 *
 * @author Mane
 */
public interface ViewInterface {
    abstract void pokreni(PostavkeSingleton postavkeSingleton, FileLoader aktivnostiLoader) throws ParseException;
    void postaviVrstuIspisa();
}
