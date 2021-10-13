/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmanestar_zadaca_3.Iterator;

import java.util.ArrayList;
import rmanestar_zadaca_3.Composite.Struktura;

/**
 *
 * @author Mane
 */
public class StrukturaRepozitorij implements Containter{
    Struktura s = null;

    public Struktura getS() {
        return s;
    }

    public void setS(Struktura s) {
        this.s = s;
    }
    public StrukturaRepozitorij(Struktura s) {
        this.s=s;
    }
    
    @Override
    public Iterator getIterator() {
        return new StrukturaIterator();
    }
    
    private class StrukturaIterator implements Iterator{

        @Override
        public boolean hasNext() {
            try{
            if(!s.dohvatiDjecu().isEmpty()){
                return true;
            }
            return false;
            }catch(Exception e){}
            return false;
        }

        @Override
        public Object next() {
            if(this.hasNext())
                return s.dohvatiDjecu();          
            return null;
        }
    }
    
}
