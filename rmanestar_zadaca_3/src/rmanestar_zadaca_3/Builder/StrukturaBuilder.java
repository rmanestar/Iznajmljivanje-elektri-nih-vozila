/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmanestar_zadaca_3.Builder;

import rmanestar_zadaca_3.Composite.Struktura;
import rmanestar_zadaca_3.Lokacija;
import rmanestar_zadaca_3.OrganizacijskaJedinica;

/**
 *
 * @author Mane
 */
public interface StrukturaBuilder {
    Struktura build();
    
    StrukturaBuilder saPodredenomOrganizacijskomJedinicom(final Struktura s);
    StrukturaBuilder dodajLokaciju(final Lokacija l);
    StrukturaBuilder definirajIzvorisnuOj(final Struktura oj);

}
