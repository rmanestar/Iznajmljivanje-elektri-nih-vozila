/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmanestar_zadaca_3;

import rmanestar_zadaca_3.Singleton.PostavkeSingleton;
import java.util.ArrayList;
/**
 *
 * @author Mane
 */

public class Lokacija {
    private int id;
    private String nazivLokacije;
    private String adresaLokacije;
    private String gps;
  
    private ArrayList<KapacitetLokacije> kapacitetLokacije= new ArrayList<KapacitetLokacije>();

    private boolean jeSastavniDioOJ = false;

   
    public Lokacija(int id, String nazivLokacije, String adresaLokacije, String gps) {
        this.id = id;
        this.nazivLokacije = nazivLokacije;
        this.adresaLokacije = adresaLokacije;
        this.gps = gps;
    }
    
    public void popuniKapacitete(){
        for(ElektricnoVozilo vozilo : PostavkeSingleton.getElektricnoVoziloLista()){
            KapacitetLokacije noviKapacitetLokacije = new KapacitetLokacije();
            noviKapacitetLokacije.setVozilo(vozilo);
            for(Kapacitet kapacitet : PostavkeSingleton.getKapacitetLista()){
                if(this.id==kapacitet.getLokacija() && vozilo.getId()==kapacitet.getVoziloVrsta()){
                    noviKapacitetLokacije.setMjestaZaPunjenje(kapacitet.getBrMjestaZaPunjenje());
                    noviKapacitetLokacije.setRaspolozivo(kapacitet.getRaspolozivo());
                    for(int i=0; i<noviKapacitetLokacije.getRaspolozivo();i++){
                        try{
                            noviKapacitetLokacije.getListaRaspolozivihVozila().add(new ElektricnoVozilo(ElektricnoVozilo.kreirajID(),vozilo));
                        }
                        catch(Exception e){                            
                        }
                    }
                    kapacitetLokacije.add(noviKapacitetLokacije);
                }
            }      
        }
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNazivLokacije() {
        return nazivLokacije;
    }

    public void setNazivLokacije(String nazivLokacije) {
        this.nazivLokacije = nazivLokacije;
    }

    public String getAdresaLokacije() {
        return adresaLokacije;
    }

    public void setAdresaLokacije(String adresaLokacije) {
        this.adresaLokacije = adresaLokacije;
    }

    public String getGps() {
        return gps;
    }

    public void setGps(String gps) {
        this.gps = gps;
    }
    
    public ArrayList<KapacitetLokacije> getKapacitetLokacije() {
        return kapacitetLokacije;
    }

    public void setKapacitetLokacije(ArrayList<KapacitetLokacije> kapacitetLokacije) {
        this.kapacitetLokacije = kapacitetLokacije;
    }
    
    public boolean isJeSastavniDioOJ() {
        return jeSastavniDioOJ;
    }

    public void setJeSastavniDioOJ(boolean jeSastavniDioOrganizacijskeJedinice) {
        this.jeSastavniDioOJ = jeSastavniDioOrganizacijskeJedinice;
    }
    
    public static Lokacija dohvatiLokacijuIzListe(ArrayList<Lokacija> listaLokacija, int id){
        for(Lokacija lokacija : listaLokacija)
            if(lokacija.getId()==id)
                return lokacija;        
        return null;
    }
    
}
