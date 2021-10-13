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
public class ObradaRacuna extends AbstractUpraviteljRacuna{

    public ObradaRacuna() {
    }
    
    public ObradaRacuna(int razina) {
        this.razina = razina;
    }
    
    @Override
    protected Racun izvrsiOdgovornost(Racun racun) {
        Racun racunZaObradu=null;
        for(Racun tempRacun : PostavkeSingleton.getRacunLista())
            if(tempRacun.getJIDracuna()==racun.getJIDracuna())              
                racunZaObradu=tempRacun;
        
        if(racunZaObradu==null){
            //ovdje se dogada dodavanje novog jid-a
            racunZaObradu = new Racun(racun.getIznos(),racun.getOsobaId(),racun.getLokacijaId(), racun.getDatumIzdavanja(), racun.getOpis());
            PostavkeSingleton.getRacunLista().add(racunZaObradu);
        }
        
        return racunZaObradu;
    }
    
}
