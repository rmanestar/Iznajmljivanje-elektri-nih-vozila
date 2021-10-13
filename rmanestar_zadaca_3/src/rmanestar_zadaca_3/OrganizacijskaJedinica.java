/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmanestar_zadaca_3;

import java.util.ArrayList;
import rmanestar_zadaca_3.Singleton.PostavkeSingleton;

/**
 *
 * @author Mane
 */
public class OrganizacijskaJedinica {

    private int id;
    private String naziv;
    private int nadredenaJedinicaId;
    private String lokacije;
    private OrganizacijskaJedinica nadredenaJedinica;

    public OrganizacijskaJedinica getNadredenaJedinica() {
        return nadredenaJedinica;
    }

    public void setNadredenaJedinica(OrganizacijskaJedinica nadredenaJedinica) {
        this.nadredenaJedinica = nadredenaJedinica;
    }
    private ArrayList<Lokacija> listaLokacija = new ArrayList<Lokacija>();
    
    public OrganizacijskaJedinica(int id, String naziv, int nadredenaJedinica, String lokacije) {
        this.id = id;
        this.naziv = naziv;
        this.nadredenaJedinicaId = nadredenaJedinica;
        this.lokacije = lokacije;
    }
    
    public OrganizacijskaJedinica(){
        
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

    public int getNadredenaJedinicaId() {
        return nadredenaJedinicaId;
    }

    public void setNadredenaJedinicaId(int nadredenaJedinicaId) {
        this.nadredenaJedinicaId = nadredenaJedinicaId;
    }

    public String getLokacije() {
        return lokacije;
    }

    public void setLokacije(String lokacije) {
        this.lokacije = lokacije;
    }

    public ArrayList<Lokacija> getListaLokacija() {
        return listaLokacija;
    }

    public void setListaLokacija(ArrayList<Lokacija> listaLokacija) {
        this.listaLokacija = listaLokacija;
    }
    
    public static OrganizacijskaJedinica dohvatiIzvorisnuOJ(ArrayList<OrganizacijskaJedinica> listaOrganizacijskaJedinica){
        for(OrganizacijskaJedinica oj : listaOrganizacijskaJedinica)
            if(oj.getNadredenaJedinicaId()==-1)
                return oj;        
        return null;
    }
    
    public static ArrayList<OrganizacijskaJedinica> dohvatiListuPodredisnihOJ(ArrayList<OrganizacijskaJedinica> listaOrganizacijskaJedinica, int id){
        ArrayList<OrganizacijskaJedinica> listaOJ = new ArrayList<OrganizacijskaJedinica>();
        for(OrganizacijskaJedinica oj : listaOrganizacijskaJedinica)
            if(oj.getNadredenaJedinicaId()==id)
                listaOJ.add(oj);
        return listaOJ;
    }

    public static OrganizacijskaJedinica dohvatiOrganizacijskuJedinicuIzListe(ArrayList<OrganizacijskaJedinica> listaOrganizacijskaJedinica, int id){
        for(OrganizacijskaJedinica oj : listaOrganizacijskaJedinica)
            if(oj.getId()==id)
                return oj;        
        return null;
    }
    
    public static void popuniNadredenuJedinicu(){
        try{
            
       
        for(OrganizacijskaJedinica oj : PostavkeSingleton.getOrganizacijskaJedinicaLista()){
            OrganizacijskaJedinica dohvacenaNadredenaOj = dohvatiOrganizacijskuJedinicuIzListe(PostavkeSingleton.getOrganizacijskaJedinicaLista(),oj.nadredenaJedinicaId);
            if(dohvacenaNadredenaOj!=null){
                oj.nadredenaJedinica = dohvacenaNadredenaOj;                
            }//else System.out.println("Nije nadena odgovarajuca organizacijska jedinica");
        }
         }catch(Exception e){
             
         }
    }
    
    public static void popuniLokacijeOrganizacijskihJedinica(){
        for(OrganizacijskaJedinica oj : PostavkeSingleton.getOrganizacijskaJedinicaLista()){
            String[] listaLokacija = oj.lokacije.split(", ");
            
            for(String s : listaLokacija){
                Lokacija lokacija = null;
                try{
                    int id = Integer.parseInt(s.replace(" ", ""));                    
                    lokacija = Lokacija.dohvatiLokacijuIzListe(PostavkeSingleton.getLokacijaLista(), id);
                }catch(Exception e){
                    
                }
                if(lokacija!=null){
                oj.listaLokacija.add(lokacija);
                }//else System.out.println("Nije nadena odgovarajuca lokacija");
            }        
        }
    }
    
}
