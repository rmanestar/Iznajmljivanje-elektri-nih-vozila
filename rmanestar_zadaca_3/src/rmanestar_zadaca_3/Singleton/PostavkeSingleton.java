/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmanestar_zadaca_3.Singleton;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;
import rmanestar_zadaca_3.Aktivnost;
import rmanestar_zadaca_3.ChainOfResponsibility.AbstractUpraviteljRacuna;
import rmanestar_zadaca_3.ChainOfResponsibility.EvidencijaRacuna;
import rmanestar_zadaca_3.ChainOfResponsibility.ObradaRacuna;
import rmanestar_zadaca_3.ChainOfResponsibility.PretrazivanjeRacuna;
import rmanestar_zadaca_3.Cjenik;
import rmanestar_zadaca_3.ElektricnoVozilo;
import rmanestar_zadaca_3.Kapacitet;
import rmanestar_zadaca_3.Lokacija;
import rmanestar_zadaca_3.MVC.RadController;
import rmanestar_zadaca_3.Najam;
import rmanestar_zadaca_3.Osoba;
import rmanestar_zadaca_3.OrganizacijskaJedinica;
import rmanestar_zadaca_3.Racun;

/**
 *
 * @author Mane
 */
public class PostavkeSingleton {

    private static final PostavkeSingleton instance = new PostavkeSingleton();

    private static ArrayList<Aktivnost> aktivnostLista = new ArrayList<Aktivnost>();
    private static ArrayList<Cjenik> cjenikLista = new ArrayList<Cjenik>();
    private static ArrayList<ElektricnoVozilo> elektricnoVoziloLista = new ArrayList<ElektricnoVozilo>();
    private static ArrayList<Kapacitet> kapacitetLista = new ArrayList<Kapacitet>();
    private static ArrayList<Lokacija> lokacijaLista = new ArrayList<Lokacija>();
    private static ArrayList<Osoba> osobaLista = new ArrayList<Osoba>();
    private static ArrayList<OrganizacijskaJedinica> organizacijskaJedinicaLista = new ArrayList<OrganizacijskaJedinica>();
    private static ArrayList<Racun> racunLista = new ArrayList<Racun>();
    private static ArrayList<Najam> najamLista = new ArrayList<Najam>();
    
    private static AbstractUpraviteljRacuna lanacUpraviteljaRacuna;
    
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    
    private boolean ucitanaVozila = false;
    private boolean ucitaneLokacije = false;
    private boolean ucitaniKapaciteti = false;
    private boolean ucitaneOsobe = false;
    private boolean ucitanCjenik = false;
    private boolean ucitaneAktivnosti = false;
    private boolean ucitaneOrganizacijskeJedinice = false;
    private boolean ucitanIzlaz = false;
    
    private boolean ucitanDD =false;
    private boolean ucitanDC = false;
    private boolean ucitanDT = false;
    
    public static Properties konfiguracija = new Properties();

    private boolean ucitanoVirtualnoVrijeme = false;
    
    boolean uspjesnoUcitanePostavke = false;

    public static Date virtualnoVrijeme = new Date();
    private boolean nacinRadaSkupni = false;
    
    public static RadController controller = new RadController();

    
    public int dt=30,dc=5,dd=2;
    public int razmakStupci=2;
    public int getDt() {
        return dt;
    }

    private PostavkeSingleton() {
        lanacUpraviteljaRacuna = dohvatiLanacUpravitelja();
    }
    
    public boolean provjeriPostavke(){
        if((ucitanaVozila && ucitaneLokacije && ucitaneOrganizacijskeJedinice && ucitaniKapaciteti && ucitaneOsobe && ucitanCjenik && ucitanoVirtualnoVrijeme) ||
                (ucitaneAktivnosti && ucitaneOrganizacijskeJedinice && ucitanaVozila && ucitaneLokacije && ucitaniKapaciteti && ucitaneOsobe && ucitanCjenik && ucitanoVirtualnoVrijeme))
            return true;
        else return false;
    }
    
    private static AbstractUpraviteljRacuna dohvatiLanacUpravitelja(){
        AbstractUpraviteljRacuna evidencijaUpravitelj = new EvidencijaRacuna();
        AbstractUpraviteljRacuna obradaUpravitelj = new ObradaRacuna();
        AbstractUpraviteljRacuna pretrazivanjeUpravitelj = new PretrazivanjeRacuna();       
        
        evidencijaUpravitelj.postaviSljedecegUpravitelja(obradaUpravitelj);
        obradaUpravitelj.postaviSljedecegUpravitelja(pretrazivanjeUpravitelj);
      
        return evidencijaUpravitelj;
    }
    
    public boolean isUcitanoVirtualnoVrijeme() {
        return ucitanoVirtualnoVrijeme;
    }

    public void setUcitanoVirtualnoVrijeme(boolean ucitanoVirtualnoVrijeme) {
        this.ucitanoVirtualnoVrijeme = ucitanoVirtualnoVrijeme;
    }

    public boolean isUcitanaVozila() {
        return ucitanaVozila;
    }

    public void setUcitanaVozila(boolean ucitanaVozila) {
        this.ucitanaVozila = ucitanaVozila;
    }

    public boolean isUcitaneLokacije() {
        return ucitaneLokacije;
    }

    public void setUcitaneLokacije(boolean ucitaneLokacije) {
        this.ucitaneLokacije = ucitaneLokacije;
    }

    public boolean isUcitaniKapaciteti() {
        return ucitaniKapaciteti;
    }

    public void setUcitaniKapaciteti(boolean ucitaniKapaciteti) {
        this.ucitaniKapaciteti = ucitaniKapaciteti;
    }

    public boolean isUcitaneOsobe() {
        return ucitaneOsobe;
    }

    public void setUcitaneOsobe(boolean ucitaneOsobe) {
        this.ucitaneOsobe = ucitaneOsobe;
    }

    public boolean isUcitanCjenik() {
        return ucitanCjenik;
    }

    public void setUcitanCjenik(boolean ucitanCjenik) {
        this.ucitanCjenik = ucitanCjenik;
    }

    public boolean isUcitaneAktivnosti() {
        return ucitaneAktivnosti;
    }

    public void setUcitaneAktivnosti(boolean ucitaneAktivnosti) {
        this.ucitaneAktivnosti = ucitaneAktivnosti;
    }
    
    public static ArrayList<Aktivnost> getAktivnostLista() {
        return aktivnostLista;
    }

    public static void setAktivnostLista(ArrayList<Aktivnost> aktivnostLista) {
        PostavkeSingleton.aktivnostLista = aktivnostLista;
    }

    public static ArrayList<Cjenik> getCjenikLista() {
        return cjenikLista;
    }

    public static void setCjenikLista(ArrayList<Cjenik> cjenikLista) {
        PostavkeSingleton.cjenikLista = cjenikLista;
    }

    public static ArrayList<ElektricnoVozilo> getElektricnoVoziloLista() {
        return elektricnoVoziloLista;
    }

    public static void setElektricnoVoziloLista(ArrayList<ElektricnoVozilo> elektricnoVoziloLista) {
        PostavkeSingleton.elektricnoVoziloLista = elektricnoVoziloLista;
    }

    public static ArrayList<Kapacitet> getKapacitetLista() {
        return kapacitetLista;
    }

    public static void setKapacitetLista(ArrayList<Kapacitet> kapacitetLista) {
        PostavkeSingleton.kapacitetLista = kapacitetLista;
    }

    public static ArrayList<Lokacija> getLokacijaLista() {
        return lokacijaLista;
    }

    public static void setLokacijaLista(ArrayList<Lokacija> lokacijaLista) {
        PostavkeSingleton.lokacijaLista = lokacijaLista;
    }

    public static ArrayList<Osoba> getOsobaLista() {
        return osobaLista;
    }

    public static void setOsobaLista(ArrayList<Osoba> osobaLista) {
        PostavkeSingleton.osobaLista = osobaLista;
    }

    public static ArrayList<OrganizacijskaJedinica> getOrganizacijskaJedinicaLista() {
        return organizacijskaJedinicaLista;
    }

    public static void setOrganizacijskaJedinicaLista(ArrayList<OrganizacijskaJedinica> organizacijskaJedinicaLista) {
        PostavkeSingleton.organizacijskaJedinicaLista = organizacijskaJedinicaLista;
    }
    
    public Date getVirtualnoVrijeme() {
        return virtualnoVrijeme;
    }

    public void setVirtualnoVrijeme(Date virtualnoVrijeme) {
        this.virtualnoVrijeme = virtualnoVrijeme;
    }

    public boolean isNacinRadaSkupni() {
        return nacinRadaSkupni;
    }

    public void setNacinRadaSkupni(boolean nacinRadaSkupni) {
        this.nacinRadaSkupni = nacinRadaSkupni;
    }

    public static PostavkeSingleton getInstance() {
        return instance;
    }
    
    public boolean isUcitaneOrganizacijskeJedinice() {
        return ucitaneOrganizacijskeJedinice;
    }

    public void setUcitaneOrganizacijskeJedinice(boolean ucitaneOrganizacijskeJedinice) {
        this.ucitaneOrganizacijskeJedinice = ucitaneOrganizacijskeJedinice;
    }

    public static ArrayList<Racun> getRacunLista() {
        return racunLista;
    }

    public static void setRacunLista(ArrayList<Racun> listaRacuna) {
        PostavkeSingleton.racunLista = listaRacuna;
    }
    
    public static ArrayList<Najam> getNajamLista() {
        return najamLista;
    }

    public static void setNajamLista(ArrayList<Najam> najamLista) {
        PostavkeSingleton.najamLista = najamLista;
    }
    
    
    public boolean isUcitanDD() {
        return ucitanDD;
    }

    public void setUcitanDD(boolean ucitanDD) {
        this.ucitanDD = ucitanDD;
    }

    public boolean isUcitanDC() {
        return ucitanDC;
    }

    public void setUcitanDC(boolean ucitanDC) {
        this.ucitanDC = ucitanDC;
    }

    public boolean isUcitanDT() {
        return ucitanDT;
    }

    public void setUcitanDT(boolean ucitanDT) {
        this.ucitanDT = ucitanDT;
    }

    public boolean isUcitanTekst() {
        return ucitanIzlaz;
    }

    public void setUcitanIzlaz(boolean ucitanIzlaz) {
        this.ucitanIzlaz = ucitanIzlaz;
    }

    public static AbstractUpraviteljRacuna getLanacUpraviteljaRacuna() {
        return lanacUpraviteljaRacuna;
    }

    public static void setLanacUpraviteljaRacuna(AbstractUpraviteljRacuna lanacUpraviteljaRacuna) {
        PostavkeSingleton.lanacUpraviteljaRacuna = lanacUpraviteljaRacuna;
    }
    
    
}
