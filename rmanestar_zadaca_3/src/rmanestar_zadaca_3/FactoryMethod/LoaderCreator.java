/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmanestar_zadaca_3.FactoryMethod;

/**
 *
 * @author Mane
 */
public abstract class LoaderCreator {
    
    public LoaderCreator(){
        
    }
    
    abstract protected FileLoader makeFileLoader();
}
