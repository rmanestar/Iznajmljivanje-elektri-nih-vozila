/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmanestar_zadaca_1;

/**
 *
 * @author Mane
 */
public class AktivnostFileLoaderCreator extends LoaderCreator{
    @Override
    protected FileLoader makeFileLoader() {
        return new AktivnostFileLoader();
    }
    
}
