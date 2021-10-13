/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmanestar_zadaca_2.Builder;

import java.util.ArrayList;
import rmanestar_zadaca_2.Composite.Struktura;
import rmanestar_zadaca_2.Lokacija;
import rmanestar_zadaca_2.OrganizacijskaJedinica;
import rmanestar_zadaca_2.Singleton.PostavkeSingleton;

/**
 *
 * @author Mane
 */
public class StrukturaBuilderImplementacija implements StrukturaBuilder{
    private Struktura struktura;
    
    public StrukturaBuilderImplementacija(){
        struktura = new Struktura();
    }
    
    @Override
    public Struktura build() {
        return struktura;
    }

    @Override
    public StrukturaBuilder saPodredenomOrganizacijskomJedinicom(final Struktura s) {
        struktura.dodajKomponentu(s);
        return this;
    }

    @Override
    public StrukturaBuilder dodajLokaciju(Lokacija l) {
        struktura.dodijeliLokaciju(l);
        return this;
    }

    @Override
    public StrukturaBuilder definirajIzvorisnuOj(Struktura s) {
        struktura.setId(s.getId());
        struktura.setNaziv(s.getNaziv());
        struktura.setLokacijeTemp(s.getLokacijeTemp());
        try{
            ArrayList<OrganizacijskaJedinica> listaPodredisnihOj = OrganizacijskaJedinica.dohvatiListuPodredisnihOJ(PostavkeSingleton.getOrganizacijskaJedinicaLista(),struktura.getId());
            if(listaPodredisnihOj!=null){
                for(OrganizacijskaJedinica oj : listaPodredisnihOj){
                    saPodredenomOrganizacijskomJedinicom(new Struktura(oj));
                }
            } 
        }catch(Exception e){
            
        }
       // struktura.setLokacije(oj.);
        return this;
    }
    
}
