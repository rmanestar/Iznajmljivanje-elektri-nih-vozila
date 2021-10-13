/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmanestar_zadaca_3;

import java.io.BufferedOutputStream;
import rmanestar_zadaca_3.FactoryMethod.ElektricnoVoziloFileLoader;
import rmanestar_zadaca_3.FactoryMethod.OsobaFileLoader;
import rmanestar_zadaca_3.FactoryMethod.KapacitetFileLoader;
import rmanestar_zadaca_3.FactoryMethod.CjenikFileLoader;
import rmanestar_zadaca_3.FactoryMethod.LokacijaFileLoader;
import rmanestar_zadaca_3.FactoryMethod.FileLoader;
import rmanestar_zadaca_3.FactoryMethod.AktivnostFileLoader;
import rmanestar_zadaca_3.FactoryMethod.OrganizacijskaJedinicaFileLoader;
import rmanestar_zadaca_3.Singleton.PostavkeSingleton;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import rmanestar_zadaca_3.ChainOfResponsibility.AbstractUpraviteljRacuna;
import rmanestar_zadaca_3.ChainOfResponsibility.EvidencijaRacuna;
import rmanestar_zadaca_3.ChainOfResponsibility.ObradaRacuna;
import rmanestar_zadaca_3.ChainOfResponsibility.PretrazivanjeRacuna;
import rmanestar_zadaca_3.Composite.Struktura;
import rmanestar_zadaca_3.FactoryMethod.KonfiguracijaFileLoader;
import rmanestar_zadaca_3.MVC.InteraktivniNacinView;
import rmanestar_zadaca_3.MVC.RadController;
import rmanestar_zadaca_3.MVC.SkupniNacinKonzolaView;
import rmanestar_zadaca_3.MVC.SkupniNacinView;

/**
 *
 * @author Mane
 */


public class Rmanestar_zadaca_3 {

    public static void main(String[] args) throws FileNotFoundException, ParseException {
 
        
        System.setProperty("file.encoding","UTF-8");
        
        FileLoader aktivnostiLoader = new AktivnostFileLoader();
        FileLoader cjenikLoader = new CjenikFileLoader();
        FileLoader vozilaLoader = new ElektricnoVoziloFileLoader();
        FileLoader kapacitetiLoader = new KapacitetFileLoader();
        FileLoader lokacijeLoader = new LokacijaFileLoader();
        FileLoader osobeLoader = new OsobaFileLoader();
        FileLoader organizacijskaJedinicaLoader = new OrganizacijskaJedinicaFileLoader();
        FileLoader konfiguracijaLoader = new KonfiguracijaFileLoader();
        PostavkeSingleton postavkeSingleton = PostavkeSingleton.getInstance();
            
        //konfiguracijaLoader.parseFile("C:\\Users\\Mane\\Documents\\NetBeansProjects\\rmanestar_zadaca_1\\datoteke2\\DZ_2_konfiguracija_2.txt");

        String ulazniArgumenti = "";
        String[] ulazniArgumentiLista;
        for (int i = 0; i < args.length; i++) {
            ulazniArgumenti += args[i] + " ";
        }

        ulazniArgumenti = ulazniArgumenti.replaceAll("[^\\x20-\\x7f]", "");
        ulazniArgumentiLista = ulazniArgumenti.split(" ");
  
        for (String parametar : ulazniArgumentiLista) {
            if ("-s".equals(parametar)) {
                postavkeSingleton.setNacinRadaSkupni(true);
            }
        }
        
        //Ulazni if  za pokretanje s konfiguracijskom datotekom
        if(ulazniArgumentiLista.length==1){
            konfiguracijaLoader.parseFile(ulazniArgumentiLista[0]);
            
            //System.out.println(PostavkeSingleton.konfiguracija.getProperty("vozila"));
            vozilaLoader.parseFile(PostavkeSingleton.konfiguracija.getProperty("vozila"));
            if(PostavkeSingleton.konfiguracija.containsKey("vozila"))
                postavkeSingleton.setUcitanaVozila(true);         
            lokacijeLoader.parseFile(PostavkeSingleton.konfiguracija.getProperty("lokacije"));                            
            if(PostavkeSingleton.konfiguracija.containsKey("lokacije"))
                postavkeSingleton.setUcitaneLokacije(true);         
            cjenikLoader.parseFile(PostavkeSingleton.konfiguracija.getProperty("cjenik"));   
            if(PostavkeSingleton.konfiguracija.containsKey("cjenik"))
                postavkeSingleton.setUcitanCjenik(true);
            kapacitetiLoader.parseFile(PostavkeSingleton.konfiguracija.getProperty("kapaciteti"));
            if(PostavkeSingleton.konfiguracija.containsKey("kapaciteti"))
                postavkeSingleton.setUcitaniKapaciteti(true);
            osobeLoader.parseFile(PostavkeSingleton.konfiguracija.getProperty("osobe"));
            if(PostavkeSingleton.konfiguracija.containsKey("osobe"))
                postavkeSingleton.setUcitaneOsobe(true);
            organizacijskaJedinicaLoader.parseFile(PostavkeSingleton.konfiguracija.getProperty("struktura"));
            if(PostavkeSingleton.konfiguracija.containsKey("struktura"))
                postavkeSingleton.setUcitaneOrganizacijskeJedinice(true);
            if(PostavkeSingleton.konfiguracija.containsKey("izlaz")){
                postavkeSingleton.setUcitanIzlaz(true);
                //System.setOut(new PrintStream(new BufferedOutputStream(new FileOutputStream(PostavkeSingleton.konfiguracija.getProperty("izlaz")))));
            }
            
            try{
            postavkeSingleton.dt=Integer.parseInt(PostavkeSingleton.konfiguracija.getProperty("tekst"));
            postavkeSingleton.dc=Integer.parseInt(PostavkeSingleton.konfiguracija.getProperty("cijeli"));
            postavkeSingleton.dd=Integer.parseInt(PostavkeSingleton.konfiguracija.getProperty("decimala"));
            }catch(Exception e){};    
            
            try{
                aktivnostiLoader.parseFile(PostavkeSingleton.konfiguracija.getProperty("aktivnosti"));
                postavkeSingleton.setUcitaneAktivnosti(true);
                if(PostavkeSingleton.konfiguracija.containsKey("aktivnosti"))
                    postavkeSingleton.setNacinRadaSkupni(true);
            }
            catch(Exception e){                              
            }
                                  
            try{
                String a = PostavkeSingleton.konfiguracija.getProperty("vrijeme");
                Date vrijeme = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(a);
                postavkeSingleton.setVirtualnoVrijeme(vrijeme);
            }catch(Exception e){
                System.out.println("Neuspjesno ucitano vrijeme");
            }
            if(PostavkeSingleton.konfiguracija.containsKey("vrijeme"))
                postavkeSingleton.setUcitanoVirtualnoVrijeme(true);                   

        }
        else{
            //Inace standardno pokretanje opcijama i putanjama
            for (int i = 0; i < ulazniArgumentiLista.length; i++) {
            String regExProvjera = "(-([v|l|c|k|o|s]|(os)) ([\\w]|[:]|[\\\\]|[\\.]|[\\-])+.txt)|(-t [0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2})|(-((dd)|(dc)|(dt)) ([1-9]+[0-9]*|[0]))";
            String provjera = "";
            String putanja = "";
            try {
                provjera = ulazniArgumentiLista[i] + " " + ulazniArgumentiLista[i + 1];
            } catch (Exception e) {
            }
            switch (ulazniArgumentiLista[i]) {
                case "-v":
                    if (provjera.matches(regExProvjera)) {
                        putanja = ulazniArgumentiLista[i + 1];
                        postavkeSingleton.setUcitanaVozila(true);
                        try{
                        vozilaLoader.parseFile(putanja);
                        }catch(Exception e){
                            postavkeSingleton.setUcitanaVozila(false);
                        }
                        
                    } else System.out.println("Provjera NOT OK - 0");                  
                    break;
                case "-l":
                    if (provjera.matches(regExProvjera)) {
                        putanja = ulazniArgumentiLista[i + 1];
                        postavkeSingleton.setUcitaneLokacije(true);    
                        try{
                        lokacijeLoader.parseFile(putanja);        
                        }catch(Exception E){
                            postavkeSingleton.setUcitaneLokacije(false);
                        }
                    } else System.out.println("Provjera NOT OK - 1");
                    
                    break;
                case "-c":
                    if (provjera.matches(regExProvjera)) {
                        putanja = ulazniArgumentiLista[i + 1];
                        postavkeSingleton.setUcitanCjenik(true);
                        try{
                        cjenikLoader.parseFile(putanja);
                        }catch(Exception e){
                            postavkeSingleton.setUcitanCjenik(false);
                        }
                    } else System.out.println("Provjera NOT OK - 2");                
                    break;
                case "-k":
                    if (provjera.matches(regExProvjera)) {
                        putanja = ulazniArgumentiLista[i + 1];
                        postavkeSingleton.setUcitaniKapaciteti(true);
                        try{
                            kapacitetiLoader.parseFile(putanja);
                        }catch(Exception e){
                            postavkeSingleton.setUcitaniKapaciteti(false);
                        }
                        
                    } else System.out.println("Provjera NOT OK - 3");
                    
                    break;
                case "-o":
                    if (provjera.matches(regExProvjera)) {
                        putanja = ulazniArgumentiLista[i + 1];
                        postavkeSingleton.setUcitaneOsobe(true);
                        try{
                            osobeLoader.parseFile(putanja);
                        }catch(Exception e){
                            postavkeSingleton.setUcitaneOsobe(false);
                        }
                    } else System.out.println("Provjera NOT OK - 4");
                    
                    break;
                case "-t":
                    provjera = ulazniArgumentiLista[i] + " " + ulazniArgumentiLista[i + 1] + " " + ulazniArgumentiLista[i + 2];
                    try {
                        if (provjera.matches(regExProvjera)) {
                            provjera = ulazniArgumentiLista[i + 1] + " " + ulazniArgumentiLista[i + 2];
                            String a = provjera;
                            Date vrijeme = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(a);
                            postavkeSingleton.setVirtualnoVrijeme(vrijeme);
                            postavkeSingleton.setUcitanoVirtualnoVrijeme(true);
                        } else System.out.println("Provjera NOT OK - 5");                      
                    } catch (Exception e) {
                        postavkeSingleton.setUcitanoVirtualnoVrijeme(false);
                        //System.out.println(e.toString());
                    }
                    break;
                case "-s":
                    if (provjera.matches(regExProvjera)) {
                        putanja = ulazniArgumentiLista[i + 1];
                        postavkeSingleton.setUcitaneAktivnosti(true);
                        try{
                            aktivnostiLoader.parseFile(putanja);
                        }catch(Exception e){
                            postavkeSingleton.setUcitaneAktivnosti(false);
                        }
                    } else System.out.println("Provjera NOT OK - 6");                  
                    break;                   
                case "-os":
                    if (provjera.matches(regExProvjera)) {
                        putanja = ulazniArgumentiLista[i + 1];
                        postavkeSingleton.setUcitaneOrganizacijskeJedinice(true);
                        try{
                            organizacijskaJedinicaLoader.parseFile(putanja);
                        }catch(Exception e){
                            postavkeSingleton.setUcitaneOrganizacijskeJedinice(false);
                        }
                    } else System.out.println("Provjera NOT OK - 7");
                    break;    
                case "-dt":
                    if (provjera.matches(regExProvjera)) {
                        putanja = ulazniArgumentiLista[i + 1];
                        postavkeSingleton.dt=Integer.parseInt(putanja);
                        postavkeSingleton.setUcitanDT(true);
                    } else System.out.println("Provjera NOT OK - 8");
                    break;  
                case "-dc":
                    if (provjera.matches(regExProvjera)) {
                        putanja = ulazniArgumentiLista[i + 1];
                        postavkeSingleton.dc=Integer.parseInt(putanja);
                        postavkeSingleton.setUcitanDC(true);
                    } else System.out.println("Provjera NOT OK - 9");
                    break;  
                case "-dd":
                    if (provjera.matches(regExProvjera)) {
                        putanja = ulazniArgumentiLista[i + 1];
                        postavkeSingleton.dd=Integer.parseInt(putanja);
                        postavkeSingleton.setUcitanDD(true);
                    } else System.out.println("Provjera NOT OK - 10");
                    break;      
                }          
            }           
        }
 
        if(postavkeSingleton.provjeriPostavke()){
            for(Lokacija lokacija : PostavkeSingleton.getLokacijaLista()){
            lokacija.popuniKapacitete();
            }           
        }
        
        if((postavkeSingleton.isUcitanCjenik()&&postavkeSingleton.isUcitanaVozila()&&postavkeSingleton.isUcitanaVozila()
                &&postavkeSingleton.isUcitaneLokacije()&&postavkeSingleton.isUcitaneOsobe()&&postavkeSingleton.isUcitaniKapaciteti()
                &&postavkeSingleton.isUcitanoVirtualnoVrijeme()&&postavkeSingleton.isUcitaneAktivnosti()&&postavkeSingleton.isUcitaneOrganizacijskeJedinice())||
                (postavkeSingleton.isUcitanCjenik()&&postavkeSingleton.isUcitanaVozila()&&postavkeSingleton.isUcitanaVozila()
                &&postavkeSingleton.isUcitaneLokacije()&&postavkeSingleton.isUcitaneOsobe()&&postavkeSingleton.isUcitaniKapaciteti()
                &&postavkeSingleton.isUcitanoVirtualnoVrijeme() && postavkeSingleton.isUcitaneOrganizacijskeJedinice())){
               
            if(PostavkeSingleton.getCjenikLista().isEmpty() || PostavkeSingleton.getElektricnoVoziloLista().isEmpty() || PostavkeSingleton.getKapacitetLista().isEmpty() ||
                    PostavkeSingleton.getLokacijaLista().isEmpty() || PostavkeSingleton.getOrganizacijskaJedinicaLista().isEmpty() || PostavkeSingleton.getOsobaLista().isEmpty()){
                System.out.println("Nisu ucitane datoteke potrebne za rad");
                System.exit(0);
            }
            
            
        
        if (!postavkeSingleton.isNacinRadaSkupni()){
            if(PostavkeSingleton.getCjenikLista().isEmpty() || PostavkeSingleton.getElektricnoVoziloLista().isEmpty() || PostavkeSingleton.getKapacitetLista().isEmpty() ||
                    PostavkeSingleton.getLokacijaLista().isEmpty() || PostavkeSingleton.getOrganizacijskaJedinicaLista().isEmpty() || PostavkeSingleton.getOsobaLista().isEmpty()){
                System.out.println("Nisu ucitane datoteke potrebne za rad");
                System.exit(0);
            }
            PostavkeSingleton.controller.updateView(new InteraktivniNacinView(), aktivnostiLoader);

           //zapocniInteraktivanNacinRada(postavkeSingleton, aktivnostiLoader);
        }
        else if (postavkeSingleton.isNacinRadaSkupni()) {
            if(PostavkeSingleton.getCjenikLista().isEmpty() || PostavkeSingleton.getElektricnoVoziloLista().isEmpty() || PostavkeSingleton.getKapacitetLista().isEmpty() ||
                    PostavkeSingleton.getLokacijaLista().isEmpty() || PostavkeSingleton.getOrganizacijskaJedinicaLista().isEmpty() || PostavkeSingleton.getOsobaLista().isEmpty() ||PostavkeSingleton.getAktivnostLista().isEmpty()){
                System.out.println("Nisu ucitane datoteke potrebne za rad");
                System.exit(0);
            }
            if(PostavkeSingleton.konfiguracija.containsKey("izlaz"))
                PostavkeSingleton.controller.updateView(new SkupniNacinKonzolaView(), aktivnostiLoader);
            else
                PostavkeSingleton.controller.updateView(new SkupniNacinView(), aktivnostiLoader);
           //zapocniSkupniNacinRada(postavkeSingleton, aktivnostiLoader);
        }
        }else{
            System.out.println("Nisu ucitane datoteke potrebne za rad");
            System.exit(0);
        }
        
    }

    
    
}
