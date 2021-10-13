/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmanestar_zadaca_2.Composite;

import java.util.ArrayList;
import rmanestar_zadaca_2.Iterator.Iterator;
import rmanestar_zadaca_2.Iterator.StrukturaRepozitorij;
import rmanestar_zadaca_2.Lokacija;
import rmanestar_zadaca_2.OrganizacijskaJedinica;
import rmanestar_zadaca_2.Singleton.PostavkeSingleton;

/**
 *
 * @author Mane
 */

public class Struktura{
    private ArrayList<Struktura> podredeneOJ = new ArrayList<Struktura>();
    private ArrayList<Lokacija> lokacije = new ArrayList<Lokacija>();
    private ArrayList<Lokacija> lokacijeTemp = new ArrayList<Lokacija>();

    private int nadredenaJedinica;
    private int strukturnaRazina=0;

    public int getStrukturnaRazina() {
        return strukturnaRazina;
    }

    public void setStrukturnaRazina(int strukturnaRazina) {
        this.strukturnaRazina = strukturnaRazina;
    }
    public static ArrayList<Struktura> strukturePoRazinama = new ArrayList<Struktura>();
    
    public int getNadredenaJedinica() {
        return nadredenaJedinica;
    }

    public void setNadredenaJedinica(int nadredenaJedinica) {
        this.nadredenaJedinica = nadredenaJedinica;
    }
    private int id;
    private String naziv;
    
    public Struktura(OrganizacijskaJedinica oj) {
        this.id = oj.getId();
        this.naziv = oj.getNaziv();
        this.nadredenaJedinica = oj.getNadredenaJedinicaId();
        this.lokacijeTemp = oj.getListaLokacija();
    }
    
    public Struktura(){};
    
    public void dodajKomponentu(Struktura s) {
        podredeneOJ.add(s);
        int indeksNovoDodaneStrukture = podredeneOJ.indexOf(s);
        try{
            ArrayList<OrganizacijskaJedinica> listaPodredisnihOj = OrganizacijskaJedinica.dohvatiListuPodredisnihOJ(PostavkeSingleton.getOrganizacijskaJedinicaLista(),s.getId());
            if(listaPodredisnihOj!=null){
                for(OrganizacijskaJedinica oj : listaPodredisnihOj){
                    podredeneOJ.get(indeksNovoDodaneStrukture).dodajKomponentu(new Struktura(oj));
                }
            }   
        }catch(Exception e){
            
        }
    }
    public void obrisiKomponentu(Struktura s) {
        podredeneOJ.remove(s);
    }

    
    public ArrayList<Struktura> getPodredeneOJ(){
        return podredeneOJ;
    }
    public void dodijeliLokaciju(Lokacija l){
        lokacije.add(l);
    }
    
    public Struktura dohvatiDijete(){
        try{
        if(podredeneOJ.get(0)!=null)
            return podredeneOJ.get(0);       
        else return null;
        }catch(Exception e){}
        return null;
    }
    
    public ArrayList<Struktura> dohvatiDjecu(){
        try{
        if(!podredeneOJ.isEmpty())
            return podredeneOJ;       
        else return null;
        }catch(Exception e){}
        return null;
    }
    
    public static void provjeriLokacije(Struktura s, ArrayList<Lokacija> lokacijaProvjeraLista){
        for(Lokacija l : lokacijaProvjeraLista){
            for(Lokacija ll : PostavkeSingleton.getLokacijaLista()){
                if(l.getId()==ll.getId() && !ll.isJeSastavniDioOJ()){
                    s.lokacije.add(ll);
                    ll.setJeSastavniDioOJ(true);
                }
            }
        }
    }
            
    public static Struktura dohvatiStrukturuPoId(Struktura s, int id){      
        if(s.getId()==id)
            return s;
        
        for(Struktura struktura : s.getPodredeneOJ()){
           
            StrukturaRepozitorij sr = new StrukturaRepozitorij(struktura);
            
            if(struktura.getId()==id)
                return struktura;
            
            Iterator iter = sr.getIterator();
            while(iter.hasNext()){
                for(Struktura sa : (ArrayList<Struktura>)iter.next()){
                        sr.setS(sa);
                        iter = sr.getIterator();
                        try{
                            if(sr.getS().getId()==id)
                                return sr.getS();
                        
                        }catch(Exception e){};
                    }
                
            }    
        }
        return null;
    }
    
    public ArrayList<Lokacija> getLokacije() {
        return lokacije;
    }

    public void setLokacije(ArrayList<Lokacija> lokacije) {
        this.lokacije = lokacije;
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
   
     public ArrayList<Lokacija> getLokacijeTemp() {
        return lokacijeTemp;
    }

    public void setLokacijeTemp(ArrayList<Lokacija> lokacijeTemp) {
        this.lokacijeTemp = lokacijeTemp;
    }
    /*
    @Override
    public String toString(){
      return ("Employee :[ Name : " + name + ", dept : " + dept + ", salary :" + salary+" ]");
    }
    */
}
