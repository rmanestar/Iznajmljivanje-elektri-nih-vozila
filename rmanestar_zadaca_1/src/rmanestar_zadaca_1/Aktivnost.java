/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmanestar_zadaca_1;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Mane
 */

public class Aktivnost {

 
    private int id;
    private Date vrijeme;
    private int korisnik;
    private int lokacija;
    private int vrstaVozila;
    private int brojKM;

    public Aktivnost(int id, Date vrijeme) {
        this.id = id;
        this.vrijeme = vrijeme;
    }

    public Aktivnost(int id, Date vrijeme, int korisnik, int lokacija, int vrstaVozila, int brojKM) {
        this.id = id;
        this.vrijeme = vrijeme;
        this.korisnik = korisnik;
        this.lokacija = lokacija;
        this.vrstaVozila = vrstaVozila;
        this.brojKM = brojKM;
    }

    public Aktivnost(int id, Date vrijeme, int korisnik, int lokacija, int vrstaVozila) {
        this.id = id;
        this.vrijeme = vrijeme;
        this.korisnik = korisnik;
        this.lokacija = lokacija;
        this.vrstaVozila = vrstaVozila;
    }
    
    
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getVrijeme() {
        return vrijeme;
    }

    public void setVrijeme(Date vrijeme) {
        this.vrijeme = vrijeme;
    }

    public int getKorisnik() {
        return korisnik;
    }

    public void setKorisnik(int korisnik) {
        this.korisnik = korisnik;
    }

    public int getLokacija() {
        return lokacija;
    }

    public void setLokacija(int lokacija) {
        this.lokacija = lokacija;
    }

    public int getVrstaVozila() {
        return vrstaVozila;
    }

    public void setVrstaVozila(int vrstaVozila) {
        this.vrstaVozila = vrstaVozila;
    }

    public int getBrojKM() {
        return brojKM;
    }

    public void setBrojKM(int brojKM) {
        this.brojKM = brojKM;
    }
    
    public static void dohvatiBrojVozilaVrsteNaLokaciji(Osoba osoba, String vrijeme, Lokacija lokacijaSifra, ElektricnoVozilo vrstaVozila){
        if(osoba==null || vrijeme==null || lokacijaSifra==null || vrstaVozila==null){
            PostavkeSingleton.ps.println("Ne postoje raspoloziva vozila na lokaciji na temelju danih parametara\n");
            return;
        }
        
        if(osoba.isAktivniNajam())
            PostavkeSingleton.ps.println("Obavijest: Osoba ima aktivni najam i ne može iznajmiti drugo vozilo\n");
        Date novoVirtualnoVrijeme =null;
        try{
            novoVirtualnoVrijeme = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(vrijeme);
        }catch(Exception e){
            return;
        }
        
        if(novoVirtualnoVrijeme.compareTo(PostavkeSingleton.virtualnoVrijeme)<=0){
            PostavkeSingleton.ps.println("Akcija ne smije imati vrijeme izvrsavanja koje se odvija prije trenutnog virtualnog vremena\n");
            return;
        } else PostavkeSingleton.virtualnoVrijeme=novoVirtualnoVrijeme;
        
        PostavkeSingleton.ps.println("U \u201e" + vrijeme+ "\u201c korisnik "+osoba.getImePrezime()+" traži na lokaciji "+ lokacijaSifra.getNazivLokacije()
                                            +" broj raspoloživih vozila vrste: "+vrstaVozila.getNaziv());
                                  
        
        int trenutnoRaspolozivo=0;
        for(ElektricnoVozilo ev : KapacitetLokacije.listaRaspoloziveVrsteVozilaNaLokaciji(lokacijaSifra.getId(),vrstaVozila.getId()))
            if(ev.baterija==100) trenutnoRaspolozivo++;
        
        PostavkeSingleton.ps.println("Broj raspoloživih vozila na lokaciji: "+ trenutnoRaspolozivo +"\n");
                                     
        return;
    }
    
       public static void brojRaspolozivihMjestaVrsteVozilaNaLokaciji(Osoba osoba, String vrijeme, Lokacija lokacijaSifra, ElektricnoVozilo vrstaVozila) {
           
        if(osoba==null || vrijeme==null || lokacijaSifra==null || vrstaVozila==null){
            PostavkeSingleton.ps.println("Ne postoje raspoloziva vozila na lokaciji na temelju danih parametara\n");
            return;
        }
        
        Date novoVirtualnoVrijeme =null;
        try{
            novoVirtualnoVrijeme = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(vrijeme);
        }catch(Exception e){
            return;
        }
        
        if(novoVirtualnoVrijeme.compareTo(PostavkeSingleton.virtualnoVrijeme)<=0){
            PostavkeSingleton.ps.println("Akcija ne smije imati vrijeme izvrsavanja koje se odvija prije trenutnog virtualnog vremena\n");
            return;
        } else PostavkeSingleton.virtualnoVrijeme=novoVirtualnoVrijeme;
                  
           KapacitetLokacije kl = KapacitetLokacije.dohvatiKapacitetVrsteVozilaZaLokaciju(lokacijaSifra.getId(), vrstaVozila.getId());

           PostavkeSingleton.ps.println("U \u201e" + vrijeme+ "\u201c korisnik "+osoba.getImePrezime()+" traži na lokaciji "+ lokacijaSifra.getNazivLokacije()+" "
                   + "broj raspoloživih mjesta za vrstu vozila "+vrstaVozila.getNaziv());
           PostavkeSingleton.ps.println("Ukupan broj mjesta za punjenje za vrstu: " + kl.getMjestaZaPunjenje() + "\nBroj slobodnih mjesta: " 
                   + (kl.getMjestaZaPunjenje()-KapacitetLokacije.listaRaspoloziveVrsteVozilaNaLokaciji(lokacijaSifra.getId(), vrstaVozila.getId()).size())+"\n");
           
           return;
       }

}
