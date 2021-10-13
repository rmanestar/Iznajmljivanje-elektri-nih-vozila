/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmanestar_zadaca_1;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Date;

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

    private boolean  ucitanaVozila = false;
    private boolean  ucitaneLokacije = false;
    private boolean  ucitaniKapaciteti = false;
    private boolean  ucitaneOsobe = false;
    private boolean ucitanCjenik = false;
    private boolean ucitaneAktivnosti = false;
    private boolean ucitanoVirtualnoVrijeme = false;

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
    
    private boolean[] izvrsenaAktivnostSkupniNacin = new boolean[5];
    
    boolean uspjesnoUcitanePostavke = false;

    public static Date virtualnoVrijeme = new Date();
    private boolean nacinRadaSkupni = false;

    private PostavkeSingleton() {
    }

    public static PrintStream ps=null;
    
    public PrintStream getPs() {
        return ps;
    }

    public void setPs(PrintStream ps) {
        this.ps = ps;
    }
        
        
    private void uspjesnoUcitano(){
        if(!aktivnostLista.isEmpty())
            ucitaneAktivnosti=true;
        if(!cjenikLista.isEmpty())
            ucitanCjenik=true;
        if(!elektricnoVoziloLista.isEmpty())
            ucitanaVozila=true;
        if(!kapacitetLista.isEmpty())
            ucitaniKapaciteti=true;
        if(!lokacijaLista.isEmpty())
            ucitaneLokacije=true;
         if(!osobaLista.isEmpty())
            ucitaneOsobe=true;
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
    
    public boolean provjeriPostavke(){
        if((ucitanaVozila && ucitaneLokacije && ucitaniKapaciteti && ucitaneOsobe && ucitanCjenik && ucitanoVirtualnoVrijeme) ||
                (ucitaneAktivnosti && ucitanaVozila && ucitaneLokacije && ucitaniKapaciteti && ucitaneOsobe && ucitanCjenik && ucitanoVirtualnoVrijeme))
            return true;
        else return false;
    }
}
