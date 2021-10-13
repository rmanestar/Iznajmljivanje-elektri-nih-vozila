/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmanestar_zadaca_1;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Mane
 */

public class ElektricnoVozilo {

    protected int id;
    protected String naziv;
    protected int vrijemePunjenja;
    protected int domet;
    protected int brKm = 0;
    protected int baterija = 100;
    protected Date puniSeDo=null;

    public Date getPuniSeDo() {
        return puniSeDo;
    }

    public void setPuniSeDo(Date puniSeDo) {
        this.puniSeDo = puniSeDo;
    }
    
    public ElektricnoVozilo(int id, String naziv, int vrijemePunjenja, int domet) {
        this.id = id;
        this.naziv = naziv;
        this.vrijemePunjenja = vrijemePunjenja;
        this.domet = domet;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public int getVrijemePunjenja() {
        return vrijemePunjenja;
    }

    public void setVrijemePunjenja(int vrijemePunjenja) {
        this.vrijemePunjenja = vrijemePunjenja;
    }

    public int getDomet() {
        return domet;
    }

    public void setDomet(int domet) {
        this.domet = domet;
    }

    public int getBaterija() {
        return baterija;
    }

    public void setBaterija(int baterija) {
        this.baterija = baterija;
    }
    
    public int getBrKm() {
        return brKm;
    }

    public void setBrKm(int brKm) {
        this.brKm = brKm;
    }
    
    public static ElektricnoVozilo dohvatiVrstuVozilaIzListe(ArrayList<ElektricnoVozilo> listaVozila, int id){
        for(ElektricnoVozilo vozilo : listaVozila)
            if(vozilo.getId()==id)
                return vozilo;        
        return null;
    }
    
    public static void azurirajStanjeBaterija(String vrijeme){
        Date novoVirtualnoVrijeme =null;
        try{
            novoVirtualnoVrijeme = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(vrijeme);
        }catch(Exception e){
            return;
        }
        for(Lokacija l: PostavkeSingleton.getLokacijaLista()){
            for(KapacitetLokacije kl: l.getKapacitetLokacije()){
                for(ElektricnoVozilo ev:kl.getListaRaspolozivihVozila()){
                    if(ev.baterija!=100 && ev.puniSeDo!=null){
                        if(ev.puniSeDo.compareTo(novoVirtualnoVrijeme)<=0){
                            ev.baterija=100;
                            ev.puniSeDo=null;
                        }
                    }
                }
            }
        }
        
    }

}
