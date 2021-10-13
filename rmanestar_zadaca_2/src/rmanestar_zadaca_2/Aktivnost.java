/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmanestar_zadaca_2;

import rmanestar_zadaca_2.Singleton.PostavkeSingleton;
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
    private String opis;

    public String getParametar1() {
        return parametar1;
    }

    public void setParametar1(String parametar1) {
        this.parametar1 = parametar1;
    }

    public String getParametar2() {
        return parametar2;
    }

    public void setParametar2(String parametar2) {
        this.parametar2 = parametar2;
    }

    public String getParametar3() {
        return parametar3;
    }

    public void setParametar3(String parametar3) {
        this.parametar3 = parametar3;
    }

    public int getIdOrgJedinice() {
        return idOrgJedinice;
    }

    public void setIdOrgJedinice(int idOrgJedinice) {
        this.idOrgJedinice = idOrgJedinice;
    }

    public Date getPocetniDatum() {
        return pocetniDatum;
    }

    public void setPocetniDatum(Date pocetniDatum) {
        this.pocetniDatum = pocetniDatum;
    }

    public Date getKrajnjiDatum() {
        return krajnjiDatum;
    }

    public void setKrajnjiDatum(Date krajnjiDatum) {
        this.krajnjiDatum = krajnjiDatum;
    }
    
    private String parametar1;
    private String parametar2;
    private String parametar3;
    private int idOrgJedinice =-1;
    private Date pocetniDatum;
    private Date krajnjiDatum;
    
    public Aktivnost(int id, Date vrijeme) {
        this.id = id;
        this.vrijeme = vrijeme;
    }

    public Aktivnost(int id, String parametar1){
        this.id = id;
        this.parametar1 = parametar1;
    }
    
    public Aktivnost(int id, String parametar1, String parametar2, int idOJ){
        this.id = id;
        this.parametar1 = parametar1;
        this.parametar2 = parametar2;
        this.idOrgJedinice = idOJ;
    }
    
    public Aktivnost(int id, String parametar1, String parametar2, String parametar3){
        this.id = id;
        this.parametar1 = parametar1;
        this.parametar2 = parametar2;
        this.parametar3 = parametar3;
    }
    
    public Aktivnost(int id, String parametar1, Date pocetniDatum, Date krajnjiDatum){
        this.id = id;
        this.parametar1 = parametar1;
        this.pocetniDatum = pocetniDatum;
        this.krajnjiDatum = krajnjiDatum;
    }
    
    public Aktivnost(int id, String parametar1,String parametar2, Date pocetniDatum, Date krajnjiDatum, int idOrgJedinice){
        this.id = id;
        this.parametar1 = parametar1;
        this.parametar2 = parametar2;
        this.pocetniDatum = pocetniDatum;
        this.krajnjiDatum = krajnjiDatum;
        this.idOrgJedinice = idOrgJedinice;
    }
    
    public Aktivnost(int id, String parametar1,String parametar2,String parametar3, Date pocetniDatum, Date krajnjiDatum, int idOrgJedinice){
        this.id = id;
        this.parametar1 = parametar1;
        this.parametar2 = parametar2;
        this.parametar3 = parametar3;
        this.pocetniDatum = pocetniDatum;
        this.krajnjiDatum = krajnjiDatum;
        this.idOrgJedinice = idOrgJedinice;
    }
    
    public Aktivnost(int id, Date vrijeme, int korisnik, int lokacija, int vrstaVozila, int brojKM) {
        this.id = id;
        this.vrijeme = vrijeme;
        this.korisnik = korisnik;
        this.lokacija = lokacija;
        this.vrstaVozila = vrstaVozila;
        this.brojKM = brojKM;
    }

    public Aktivnost(int id, Date vrijeme, int korisnik, int lokacija, int vrstaVozila, int brojKM, String opis) {
        this.id = id;
        this.vrijeme = vrijeme;
        this.korisnik = korisnik;
        this.lokacija = lokacija;
        this.vrstaVozila = vrstaVozila;
        this.brojKM = brojKM;
        this.opis = opis;
    }
    
    public Aktivnost(int id, Date vrijeme, int korisnik, int lokacija, int vrstaVozila) {
        this.id = id;
        this.vrijeme = vrijeme;
        this.korisnik = korisnik;
        this.lokacija = lokacija;
        this.vrstaVozila = vrstaVozila;
    }
    
    public static void dohvatiBrojVozilaVrsteNaLokaciji(Osoba osoba, String vrijeme, Lokacija lokacijaSifra, ElektricnoVozilo vrstaVozila){
        if(osoba==null || vrijeme==null || lokacijaSifra==null || vrstaVozila==null){
            PostavkeSingleton.ps.println("Ne postoje raspoloziva vozila na lokaciji na temelju danih parametara\n");
            return;
        }
        
        if(osoba.isAktivniNajam())
            PostavkeSingleton.ps.println("Obavijest: Osoba ima aktivni najam i ne moze iznajmiti drugo vozilo\n");
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
        
        PostavkeSingleton.ps.println("U \u201e" + vrijeme+ "\u201c korisnik "+osoba.getImePrezime()+" trazi na lokaciji "+ lokacijaSifra.getNazivLokacije()
                                            +" broj raspolozivih vozila vrste: "+vrstaVozila.getNaziv());
                                       
        int trenutnoRaspolozivo=0;
        for(ElektricnoVozilo ev : KapacitetLokacije.listaRaspoloziveVrsteVozilaNaLokaciji(lokacijaSifra.getId(),vrstaVozila.getId()))
            if(ev.baterija==100) trenutnoRaspolozivo++;
        
        PostavkeSingleton.ps.println("Broj raspolozivih vozila na lokaciji: "+ trenutnoRaspolozivo +"\n");
                                     
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

       PostavkeSingleton.ps.println("U \u201e" + vrijeme+ "\u201c korisnik "+osoba.getImePrezime()+" trazi na lokaciji "+ lokacijaSifra.getNazivLokacije()+" "
               + "broj raspolozivih mjesta za vrstu vozila "+vrstaVozila.getNaziv());
       PostavkeSingleton.ps.println("Ukupan broj mjesta za punjenje za vrstu: " + kl.getMjestaZaPunjenje() + "\nBroj slobodnih mjesta: " 
               + (kl.getMjestaZaPunjenje()-KapacitetLokacije.listaRaspoloziveVrsteVozilaNaLokaciji(lokacijaSifra.getId(), vrstaVozila.getId()).size())+"\n");

       return;
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
    
    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }
    
}
