/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmanestar_zadaca_3;

import rmanestar_zadaca_3.Singleton.PostavkeSingleton;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import rmanestar_zadaca_3.State.SlobodnoState;
import rmanestar_zadaca_3.State.VoziloContext;
import rmanestar_zadaca_3.State.VoziloState;

/**
 *
 * @author Mane
 */

public class ElektricnoVozilo {

    protected int idVrstaVozila;
    protected long JIDVozila;

    public long getJIDVozila() {
        return JIDVozila;
    }

    public void setJIDVozila(long JIDVozila) {
        this.JIDVozila = JIDVozila;
    }
    
    protected String naziv;
    protected int vrijemePunjenja;
    protected int domet;
    protected int brKm = 0;
    protected int baterija = 100;
    protected Date puniSeDo=null;
    private static long idBrojac = 0;
    private int brojNajmova=0;
    private VoziloContext context = new VoziloContext();

    
    public VoziloContext getContext() {
        return context;
    }

    public void setContext(VoziloContext context) {
        this.context = context;
    }

    //konstruktor za kreiranje instance vrste vozila
    public ElektricnoVozilo(int idVrstaVozila, String naziv, int vrijemePunjenja, int domet) {
        this.idVrstaVozila = idVrstaVozila;
        this.naziv = naziv;
        this.vrijemePunjenja = vrijemePunjenja;
        this.domet = domet;
    }
    
    //konstruktor za kreiranje vozila s jedinstvenim indentifikatorom
    public ElektricnoVozilo(long JIDVozila, ElektricnoVozilo vozilo) {
        this.JIDVozila = JIDVozila;
        this.idVrstaVozila = vozilo.idVrstaVozila;
        this.naziv = vozilo.naziv;
        this.vrijemePunjenja = vozilo.vrijemePunjenja;
        this.domet = vozilo.domet;
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
                            SlobodnoState ss = new SlobodnoState();
                            ss.doAction(ev.getContext());
                        }
                    }
                }
            }
        }     
    }
    
    public static synchronized long kreirajID()
    {
        return idBrojac++;
    }
    
    public Date getPuniSeDo() {
        return puniSeDo;
    }

    public void setPuniSeDo(Date puniSeDo) {
        this.puniSeDo = puniSeDo;
    }
    
    public int getId() {
        return idVrstaVozila;
    }

    public void setId(int id) {
        this.idVrstaVozila = id;
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
    
    public int getBrojNajmova() {
        return brojNajmova;
    }

    public void setBrojNajmova(int brojNajmova) {
        this.brojNajmova = brojNajmova;
    }

}
