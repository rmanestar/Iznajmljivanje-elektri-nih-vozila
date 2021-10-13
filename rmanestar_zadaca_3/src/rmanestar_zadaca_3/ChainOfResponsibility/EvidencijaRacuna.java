/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmanestar_zadaca_3.ChainOfResponsibility;

import rmanestar_zadaca_3.Racun;
import rmanestar_zadaca_3.Singleton.PostavkeSingleton;

/**
 *
 * @author Mane
 */
public class EvidencijaRacuna extends AbstractUpraviteljRacuna{

    public EvidencijaRacuna() {
    }
    
    public EvidencijaRacuna(int razina) {
        this.razina = razina;
    }

    @Override
    protected Racun izvrsiOdgovornost(Racun racun) {
        //Evidentiranje placanja racuna
        for(Racun tempRacun : PostavkeSingleton.getRacunLista())
            if(tempRacun.getJIDracuna()==racun.getJIDracuna())
                tempRacun.setPlacen(true);
        
        return null;
    }
    
}
