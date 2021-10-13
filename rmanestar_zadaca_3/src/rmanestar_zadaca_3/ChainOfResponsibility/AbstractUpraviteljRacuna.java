/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmanestar_zadaca_3.ChainOfResponsibility;

import rmanestar_zadaca_3.Racun;

/**
 *
 * @author Mane
 */
public abstract class AbstractUpraviteljRacuna {
    public static int EVIDENCIJA = 1;
    public static int OBRADA = 2;
    public static int PRETRAZIVANJE = 3;
    
    protected int razina;
    protected AbstractUpraviteljRacuna sljedeciUpravitelj;
    
    public void postaviSljedecegUpravitelja(AbstractUpraviteljRacuna sljedeciUpravitelj){
        this.sljedeciUpravitelj = sljedeciUpravitelj;
    }
    
    public Racun pokreniIzvrsavanjeOdgovornosti(int razina, Racun tempRacun){
        Racun racunZaVratiti = null;
        if(this.razina <= razina){
            racunZaVratiti = izvrsiOdgovornost(tempRacun);
        }  
        if(sljedeciUpravitelj !=null){
           sljedeciUpravitelj.pokreniIzvrsavanjeOdgovornosti(razina, tempRacun);
        }
        return racunZaVratiti;
    }
    
    abstract protected Racun izvrsiOdgovornost(Racun tempRacun);

}
