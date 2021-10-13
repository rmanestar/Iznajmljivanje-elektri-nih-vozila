/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmanestar_zadaca_2;

import rmanestar_zadaca_2.FactoryMethod.ElektricnoVoziloFileLoader;
import rmanestar_zadaca_2.FactoryMethod.OsobaFileLoader;
import rmanestar_zadaca_2.FactoryMethod.KapacitetFileLoader;
import rmanestar_zadaca_2.FactoryMethod.CjenikFileLoader;
import rmanestar_zadaca_2.FactoryMethod.LokacijaFileLoader;
import rmanestar_zadaca_2.FactoryMethod.FileLoader;
import rmanestar_zadaca_2.FactoryMethod.AktivnostFileLoader;
import rmanestar_zadaca_2.FactoryMethod.OrganizacijskaJedinicaFileLoader;
import rmanestar_zadaca_2.Singleton.PostavkeSingleton;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import rmanestar_zadaca_2.Builder.StrukturaBuilder;
import rmanestar_zadaca_2.Builder.StrukturaBuilderImplementacija;
import rmanestar_zadaca_2.Composite.Struktura;
import rmanestar_zadaca_2.FactoryMethod.KonfiguracijaFileLoader;
import rmanestar_zadaca_2.Iterator.Iterator;
import rmanestar_zadaca_2.Iterator.StrukturaRepozitorij;

/**
 *
 * @author Mane
 */
// interaktivni nacin regex
// (0; [0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2})|([1-3]; [0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2}; ([1-9]+[0-9]*|[0]); ([1-9]+[0-9]*|[0]); ([1-9]+[0-9]*|[0]))|(4; [0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2}; ([1-9]+[0-9]*|[0]); ([1-9]+[0-9]*|[0]); ([1-9]+[0-9]*|[0]); ([1-9]+[0-9]*|[0]); .+)|(4; [0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2}; ([1-9]+[0-9]*|[0]); ([1-9]+[0-9]*|[0]); ([1-9]+[0-9]*|[0]); ([1-9]+[0-9]*|[0]))|(5; ([\w]|[:]|[\\]|[\.]|[-])+.txt)
/* interaktivni stari (0; [0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2})|([1-3]; "
                        + "[0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2}; ([1-9]+[0-9]*|[0]); "
                        + "([1-9]+[0-9]*|[0]); ([1-9]+[0-9]*|[0]))|(4; [0-9]{4}-[0-9]{2}-[0-9]{2} "
                        + "[0-9]{2}:[0-9]{2}:[0-9]{2}; ([1-9]+[0-9]*|[0]); ([1-9]+[0-9]*|[0]); ([1-9]+[0-9]*|[0]); "
                        + "([1-9]+[0-9]*|[0]); .+)|(4; [0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2}; ([1-9]+[0-9]*|[0]); "
                        + "([1-9]+[0-9]*|[0]); ([1-9]+[0-9]*|[0]); ([1-9]+[0-9]*|[0]))|(5; ([\\w]|[:]|[\\\\]|[\\.]|[-])+.txt)*/
//TO DO - atribut vracena vozila u neispravnom stanju osoba
//TO DO - vozilo neispravno stanje atribut - ne smije se daljnje izanajmljivati
//TO DO - dodati broj najmova za pojedino vozilo
//TO DO - interaktivni rad regexovi za aktivnosti
//TO DO - dodane aktivnosti skupni nacin - opcionalno
//TO DO - dodati klasu račun
//TO DO - odabir vozila kod iznajmljivanja - uvjeti
//TO DO - zarada za lokaciju ažuriranje s odnosnom na račune
//TO DO - ispis zasto pojedina aktivnost nije ispravna


public class Rmanestar_zadaca_2 {

    public static void main(String[] args) {

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
             
        try{
             postavkeSingleton.setPs(new PrintStream(System.out,true,"UTF-8"));
        }catch(Exception e){}

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
            
            //postavkeSingleton.ps.println(PostavkeSingleton.konfiguracija.getProperty("vozila"));
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
                PostavkeSingleton.ps.println("Neuspjesno ucitano vrijeme");
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
                        
                    } else PostavkeSingleton.ps.println("Provjera NOT OK - 0");                  
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
                    } else PostavkeSingleton.ps.println("Provjera NOT OK - 1");
                    
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
                    } else PostavkeSingleton.ps.println("Provjera NOT OK - 2");                
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
                        
                    } else PostavkeSingleton.ps.println("Provjera NOT OK - 3");
                    
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
                    } else PostavkeSingleton.ps.println("Provjera NOT OK - 4");
                    
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
                        } else PostavkeSingleton.ps.println("Provjera NOT OK - 5");                      
                    } catch (Exception e) {
                        postavkeSingleton.setUcitanoVirtualnoVrijeme(false);
                        //PostavkeSingleton.ps.println(e.toString());
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
                    } else PostavkeSingleton.ps.println("Provjera NOT OK - 6");                  
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
                    } else PostavkeSingleton.ps.println("Provjera NOT OK - 7");
                    break;    
                case "-dt":
                    if (provjera.matches(regExProvjera)) {
                        putanja = ulazniArgumentiLista[i + 1];
                        postavkeSingleton.dt=Integer.parseInt(putanja);
                        postavkeSingleton.setUcitanDT(true);
                    } else PostavkeSingleton.ps.println("Provjera NOT OK - 8");
                    break;  
                case "-dc":
                    if (provjera.matches(regExProvjera)) {
                        putanja = ulazniArgumentiLista[i + 1];
                        postavkeSingleton.dc=Integer.parseInt(putanja);
                        postavkeSingleton.setUcitanDC(true);
                    } else PostavkeSingleton.ps.println("Provjera NOT OK - 9");
                    break;  
                case "-dd":
                    if (provjera.matches(regExProvjera)) {
                        putanja = ulazniArgumentiLista[i + 1];
                        postavkeSingleton.dd=Integer.parseInt(putanja);
                        postavkeSingleton.setUcitanDD(true);
                    } else PostavkeSingleton.ps.println("Provjera NOT OK - 10");
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
                PostavkeSingleton.ps.println("Nisu ucitane datoteke potrebne za rad");
                System.exit(0);
            }
            
        if (!postavkeSingleton.isNacinRadaSkupni()){
            if(PostavkeSingleton.getCjenikLista().isEmpty() || PostavkeSingleton.getElektricnoVoziloLista().isEmpty() || PostavkeSingleton.getKapacitetLista().isEmpty() ||
                    PostavkeSingleton.getLokacijaLista().isEmpty() || PostavkeSingleton.getOrganizacijskaJedinicaLista().isEmpty() || PostavkeSingleton.getOsobaLista().isEmpty()){
                PostavkeSingleton.ps.println("Nisu ucitane datoteke potrebne za rad");
                System.exit(0);
            }
           zapocniInteraktivanNacinRada(postavkeSingleton, aktivnostiLoader);
        }
        else if (postavkeSingleton.isNacinRadaSkupni()) {
            if(PostavkeSingleton.getCjenikLista().isEmpty() || PostavkeSingleton.getElektricnoVoziloLista().isEmpty() || PostavkeSingleton.getKapacitetLista().isEmpty() ||
                    PostavkeSingleton.getLokacijaLista().isEmpty() || PostavkeSingleton.getOrganizacijskaJedinicaLista().isEmpty() || PostavkeSingleton.getOsobaLista().isEmpty() ||PostavkeSingleton.getAktivnostLista().isEmpty()){
                PostavkeSingleton.ps.println("Nisu ucitane datoteke potrebne za rad");
                System.exit(0);
            }
           zapocniSkupniNacinRada(postavkeSingleton, aktivnostiLoader);
        }
        }else{
            PostavkeSingleton.ps.println("Nisu ucitane datoteke potrebne za rad");
            System.exit(0);
        }
        
    }
    
    static void zapocniInteraktivanNacinRada(PostavkeSingleton postavkeSingleton, FileLoader aktivnostiLoader){         
        postavkeSingleton.getPs().println("\nZapocinje interaktivni nacin rada!\n");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        String unos = "";
        String[] unosLista;
        while (true) {
            try{
                unos = reader.readLine();
                unos = unos.replaceAll("[^\\x20-\\x7f]", "").replace("\"", "").replace("\'", "");
                if(unos.matches("^(0; [0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2})|([1-3]; [0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2}; ([1-9]+[0-9]*|[0]); ([1-9]+[0-9]*|[0]); ([1-9]+[0-9]*|[0]))|(4; [0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2}; ([1-9]+[0-9]*|[0]); ([1-9]+[0-9]*|[0]); ([1-9]+[0-9]*|[0]); ([1-9]+[0-9]*|[0]); .+)|(4; [0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2}; ([1-9]+[0-9]*|[0]); ([1-9]+[0-9]*|[0]); ([1-9]+[0-9]*|[0]); ([1-9]+[0-9]*|[0]))|(5; ([\\w]|[:]|[\\\\]|[\\/]|[\\.]|[-])+.txt)|(6; struktura stanje ([1-9]+[0-9]*|[0]))|(6; struktura)|(7; struktura najam zarada ([0-9][0-9])\\.([0-9][0-9])\\.([0-9][0-9][0-9][0-9]) ([0-9][0-9])\\.([0-9][0-9])\\.([0-9][0-9][0-9][0-9]) ([1-9]+[0-9]*|[0]))|(7; struktura najam ([0-9][0-9])\\.([0-9][0-9])\\.([0-9][0-9][0-9][0-9]) ([0-9][0-9])\\.([0-9][0-9])\\.([0-9][0-9][0-9][0-9]) ([1-9]+[0-9]*|[0]))|(7; struktura ([0-9][0-9])\\.([0-9][0-9])\\.([0-9][0-9][0-9][0-9]) ([0-9][0-9])\\.([0-9][0-9])\\.([0-9][0-9][0-9][0-9])|(8; struktura rauni ([0-9][0-9])\\.([0-9][0-9])\\.([0-9][0-9][0-9][0-9]) ([0-9][0-9])\\.([0-9][0-9])\\.([0-9][0-9][0-9][0-9]) ([1-9]+[0-9]*|[0]))|(8; struktura ([0-9][0-9])\\.([0-9][0-9])\\.([0-9][0-9][0-9][0-9]) ([0-9][0-9])\\.([0-9][0-9])\\.([0-9][0-9][0-9][0-9])))$")){
                    unosLista = unos.split("; ");
                    String vrijeme = unosLista[1];
                    Date datum = null;
                    String[] parametri;
                    SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");

                    if(Integer.parseInt(unosLista[0])>=0 && Integer.parseInt(unosLista[0])<=4)
                        datum = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(vrijeme);

                    switch(Integer.parseInt(unosLista[0])){
                        case 0:
                                PostavkeSingleton.ps.println("Program zavrsava u \u201e" + vrijeme+ "\u201c");
                                System.exit(0);
                            break;
                        case 1:  
                                ElektricnoVozilo.azurirajStanjeBaterija(vrijeme);

                                Aktivnost.dohvatiBrojVozilaVrsteNaLokaciji(Osoba.dohvatiOsobuIzListe(PostavkeSingleton.getOsobaLista(),
                                         Integer.parseInt(unosLista[2])), vrijeme, Lokacija.dohvatiLokacijuIzListe(PostavkeSingleton.getLokacijaLista(),Integer.parseInt(unosLista[3])), 
                                         ElektricnoVozilo.dohvatiVrstuVozilaIzListe(PostavkeSingleton.getElektricnoVoziloLista(), Integer.parseInt(unosLista[4])));
                                 ElektricnoVozilo.azurirajStanjeBaterija(vrijeme);
                            break;                                
                        case 2:
                                ElektricnoVozilo.azurirajStanjeBaterija(vrijeme);

                                Osoba.iznajmiVozilo(Osoba.dohvatiOsobuIzListe(PostavkeSingleton.getOsobaLista(),Integer.parseInt(unosLista[2])), vrijeme,
                                        ElektricnoVozilo.dohvatiVrstuVozilaIzListe(PostavkeSingleton.getElektricnoVoziloLista(), Integer.parseInt(unosLista[4])), 
                                        Lokacija.dohvatiLokacijuIzListe(PostavkeSingleton.getLokacijaLista(), Integer.parseInt(unosLista[3])),datum);
                                ElektricnoVozilo.azurirajStanjeBaterija(vrijeme);
                                break;                                
                        case 3:
                                ElektricnoVozilo.azurirajStanjeBaterija(vrijeme);

                                Aktivnost.brojRaspolozivihMjestaVrsteVozilaNaLokaciji(Osoba.dohvatiOsobuIzListe(PostavkeSingleton.getOsobaLista(),
                                         Integer.parseInt(unosLista[2])), vrijeme, Lokacija.dohvatiLokacijuIzListe(PostavkeSingleton.getLokacijaLista(),Integer.parseInt(unosLista[3])), 
                                         ElektricnoVozilo.dohvatiVrstuVozilaIzListe(PostavkeSingleton.getElektricnoVoziloLista(), Integer.parseInt(unosLista[4])));
                                ElektricnoVozilo.azurirajStanjeBaterija(vrijeme);
                                break;
                        case 4:
                                ElektricnoVozilo.azurirajStanjeBaterija(vrijeme);
                                //TODO - dodat slučaj s kvarom - neispravnosti po parametru
                                
                                if(unosLista.length==6){
                                    Osoba.vratiVozilo(Osoba.dohvatiOsobuIzListe(PostavkeSingleton.getOsobaLista(), Integer.parseInt(unosLista[2])), vrijeme,
                                        ElektricnoVozilo.dohvatiVrstuVozilaIzListe(PostavkeSingleton.getElektricnoVoziloLista(), Integer.parseInt(unosLista[4])), 
                                        Lokacija.dohvatiLokacijuIzListe(PostavkeSingleton.getLokacijaLista(), Integer.parseInt(unosLista[3])),Integer.parseInt(unosLista[5]),datum);
                                }else if(unosLista.length==7){
                                    Osoba.vratiNeispravnoVozilo(Osoba.dohvatiOsobuIzListe(PostavkeSingleton.getOsobaLista(), Integer.parseInt(unosLista[2])), vrijeme,
                                        ElektricnoVozilo.dohvatiVrstuVozilaIzListe(PostavkeSingleton.getElektricnoVoziloLista(), Integer.parseInt(unosLista[4])), 
                                        Lokacija.dohvatiLokacijuIzListe(PostavkeSingleton.getLokacijaLista(), Integer.parseInt(unosLista[3])),Integer.parseInt(unosLista[5]),unosLista[6],datum);
                                }                 
                                
                                 ElektricnoVozilo.azurirajStanjeBaterija(vrijeme);
                            break;                               
                        case 5:
                            PostavkeSingleton.ps.println("Izvrsavanje aktivnosti: " + Integer.parseInt(unosLista[0]) + "; "+unosLista[1]);
                            PostavkeSingleton.getAktivnostLista().clear();
                            File tmpDir = new File(unosLista[1]);
                            boolean exists = tmpDir.exists();
                            if(exists){
                                PostavkeSingleton.getAktivnostLista().clear();
                                aktivnostiLoader.parseFile(unosLista[1]);
                                postavkeSingleton.setNacinRadaSkupni(true);
                                zapocniSkupniNacinRada(postavkeSingleton, aktivnostiLoader);
                            }
                            else {
                                PostavkeSingleton.ps.println("Ne postoji datoteka na definiranoj lokaciji. Nastavak s interaktivnim radom");
                            }                          
                            break;                                
                        case 6:
                            parametri = unosLista[1].split(" ");
                            switch(parametri.length){
                                case 1:
                                    if("struktura".equals(parametri[0])){
                                        PostavkeSingleton.ps.println("Izvrsavanje aktivnosti: " + Integer.parseInt(unosLista[0]) + "; "+unosLista[1]);
                                        ispisiStrukturu(postavkeSingleton, -1, 0);
                                    }
                                    break;
                                case 3:
                                    if("struktura".equals(parametri[0]) &&"stanje".equals(parametri[1])){
                                        PostavkeSingleton.ps.println("Izvrsavanje aktivnosti: " + unosLista[0] + "; "+parametri[0]+" "+parametri[1]+" "+parametri[2]);
                                        ispisiStrukturu(postavkeSingleton, Integer.parseInt(parametri[2]), 1);
                                    }
                                    break;
                            }
                            break;                            
                        case 7:
                            parametri = unosLista[1].split(" ");
                            format = new SimpleDateFormat("dd.MM.yyyy");
                            switch(parametri.length){
                                case 3:
                                    if("struktura".equals(parametri[0]) && format.parse(parametri[1])!=null && format.parse(parametri[2])!=null){
                                        PostavkeSingleton.ps.println("Izvrsavanje aktivnosti: " + Integer.parseInt(unosLista[0]) + "; "+parametri[0] +" "+parametri[1] + " "+parametri[2]);
                                        ispisiNajmove(postavkeSingleton,-1,0,0,format.parse(parametri[1]),format.parse(parametri[2]));                   
                                    }
                                    break;
                                case 5:
                                    if("struktura".equals(parametri[0]) &&"najam".equals(parametri[1]) && Integer.parseInt(parametri[4])!=-1 && format.parse(parametri[2])!=null && format.parse(parametri[3])!=null){
                                        PostavkeSingleton.ps.println("Izvrsavanje aktivnosti: " + unosLista[0] + "; "+parametri[0]+" "+parametri[1]+" "+parametri[2]+" "+parametri[3]+" "+parametri[4]);
                                        ispisiNajmove(postavkeSingleton,Integer.parseInt(parametri[4]),1,0,format.parse(parametri[2]),format.parse(parametri[3]));
                                    }
                                    break;
                                case 6:
                                    if("struktura".equals(parametri[0]) &&"najam".equals(parametri[1]) && "zarada".equals(parametri[2]) && Integer.parseInt(parametri[5])!=-1 && format.parse(parametri[3])!=null && format.parse(parametri[4])!=null){
                                        PostavkeSingleton.ps.println("Izvrsavanje aktivnosti: " + unosLista[0] + "; "+parametri[0]+ " "+parametri[1]+ " "+parametri[2]+ " "+parametri[3]+" "+parametri[4]+" "+parametri[5]);
                                        ispisiNajmove(postavkeSingleton,Integer.parseInt(parametri[5]),1,1,format.parse(parametri[3]),format.parse(parametri[4]));
                                    }
                                    break;
                            }                          
                            break;                              
                        case 8:
                            parametri = unosLista[1].split(" ");
                            format = new SimpleDateFormat("dd.MM.yyyy");
                            switch(parametri.length){
                                case 3:
                                    if("struktura".equals(parametri[0]) && format.parse(parametri[1])!=null && format.parse(parametri[2])!=null){
                                        PostavkeSingleton.ps.println("Izvrsavanje aktivnosti: " + Integer.parseInt(unosLista[0]) + "; "+parametri[0] + " "+parametri[1] + " "+parametri[2]);
                                        ispisiRacune(postavkeSingleton,-1,0,format.parse(parametri[1]),format.parse(parametri[2]));
                                    }
                                    break;
                                case 5:
                                    if("struktura".equals(parametri[0]) &&"rauni".equals(parametri[1]) && Integer.parseInt(parametri[4])!=-1 && format.parse(parametri[2])!=null && format.parse(parametri[3])!=null){
                                        PostavkeSingleton.ps.println("Izvrsavanje aktivnosti: " + unosLista[0] + "; "+parametri[0]+" "+"racuni"+" "+parametri[2]+" "+parametri[3]+" "+parametri[4]);
                                        ispisiRacune(postavkeSingleton,Integer.parseInt(parametri[4]),1,format.parse(parametri[2]),format.parse(parametri[3]));
                                    }
                                    break;
                            }    
                            break;                               
                        default:
                            break;
                    }

                } else PostavkeSingleton.ps.println("Nepoznata naredba - provjerite argumente");

            }catch(Exception e){

            }
        }
    }
    
    static void zapocniSkupniNacinRada(PostavkeSingleton postavkeSingleton, FileLoader aktivnostiLoader){
        postavkeSingleton.getPs().println("\nZapocinje skupni nacin rada!\n");
        SimpleDateFormat vrijemeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
        for(Aktivnost a: PostavkeSingleton.getAktivnostLista()){
            String vrijeme ="";
            if(a.getId()<=4)
                vrijeme = vrijemeFormat.format(a.getVrijeme());
            
            switch(a.getId()){
                case 0:
                    PostavkeSingleton.ps.println("Izvrsavanje aktivnosti: " + a.getId() + "; " +vrijemeFormat.format(a.getVrijeme()));                                 
                    PostavkeSingleton.ps.println("Program zavrsava u \u201e" + vrijeme+ "\u201c");
                    System.exit(0);
                    break;
                case 1:         
                    ElektricnoVozilo.azurirajStanjeBaterija(vrijeme);

                    PostavkeSingleton.ps.println("Izvrsavanje aktivnosti: " + a.getId() + "; \u201e" +vrijemeFormat.format(a.getVrijeme())+"\u201c; "+a.getKorisnik()+"; "+a.getLokacija()+"; "+a.getVrstaVozila());

                    Aktivnost.dohvatiBrojVozilaVrsteNaLokaciji(Osoba.dohvatiOsobuIzListe(PostavkeSingleton.getOsobaLista(),
                             a.getKorisnik()), vrijeme, Lokacija.dohvatiLokacijuIzListe(PostavkeSingleton.getLokacijaLista(),a.getLokacija()), 
                             ElektricnoVozilo.dohvatiVrstuVozilaIzListe(PostavkeSingleton.getElektricnoVoziloLista(), a.getVrstaVozila()));
                    ElektricnoVozilo.azurirajStanjeBaterija(vrijeme);
                    break;
                case 2:
                    ElektricnoVozilo.azurirajStanjeBaterija(vrijeme);

                    PostavkeSingleton.ps.println("Izvrsavanje aktivnosti: " + a.getId() + "; \u201e" +vrijemeFormat.format(a.getVrijeme())+"\u201c; "+a.getKorisnik()+"; "+a.getLokacija()+"; "+a.getVrstaVozila());

                    Osoba.iznajmiVozilo(Osoba.dohvatiOsobuIzListe(PostavkeSingleton.getOsobaLista(),a.getKorisnik()), vrijeme,
                            ElektricnoVozilo.dohvatiVrstuVozilaIzListe(PostavkeSingleton.getElektricnoVoziloLista(), a.getVrstaVozila()), 
                            Lokacija.dohvatiLokacijuIzListe(PostavkeSingleton.getLokacijaLista(), a.getLokacija()),a.getVrijeme());
                    ElektricnoVozilo.azurirajStanjeBaterija(vrijeme);
                    break;
                case 3:
                    ElektricnoVozilo.azurirajStanjeBaterija(vrijeme);

                    PostavkeSingleton.ps.println("Izvrsavanje aktivnosti: " + a.getId() + "; \u201e" +vrijemeFormat.format(a.getVrijeme())+"\u201c; "+a.getKorisnik()+"; "+a.getLokacija()+"; "+a.getVrstaVozila());

                    Aktivnost.brojRaspolozivihMjestaVrsteVozilaNaLokaciji(Osoba.dohvatiOsobuIzListe(PostavkeSingleton.getOsobaLista(),
                             a.getKorisnik()), vrijeme, Lokacija.dohvatiLokacijuIzListe(PostavkeSingleton.getLokacijaLista(),a.getLokacija()), 
                             ElektricnoVozilo.dohvatiVrstuVozilaIzListe(PostavkeSingleton.getElektricnoVoziloLista(), a.getVrstaVozila()));
                    ElektricnoVozilo.azurirajStanjeBaterija(vrijeme);
                    break;
                case 4:
                    ElektricnoVozilo.azurirajStanjeBaterija(vrijeme);
                    //PostavkeSingleton.ps.println(a.getOpis());
                    if(a.getOpis()==""){
                        PostavkeSingleton.ps.println("Izvrsavanje aktivnosti: " + a.getId() + "; \u201e" +vrijemeFormat.format(a.getVrijeme())+"\u201c; "+a.getKorisnik()+"; "+a.getLokacija()+"; "+a.getVrstaVozila()+"; "+a.getBrojKM());
                        Osoba.vratiVozilo(Osoba.dohvatiOsobuIzListe(PostavkeSingleton.getOsobaLista(), a.getKorisnik()), vrijeme,
                        ElektricnoVozilo.dohvatiVrstuVozilaIzListe(PostavkeSingleton.getElektricnoVoziloLista(), a.getVrstaVozila()), 
                        Lokacija.dohvatiLokacijuIzListe(PostavkeSingleton.getLokacijaLista(), a.getLokacija()),a.getBrojKM(),a.getVrijeme());
                    }else if(a.getOpis().length()>0){
                        PostavkeSingleton.ps.println("Izvrsavanje aktivnosti: " + a.getId() + "; \u201e" +vrijemeFormat.format(a.getVrijeme())+"\u201c; "+a.getKorisnik()+"; "+a.getLokacija()+"; "+a.getVrstaVozila()+"; "+a.getBrojKM()+"; "+a.getOpis());
                        Osoba.vratiNeispravnoVozilo(Osoba.dohvatiOsobuIzListe(PostavkeSingleton.getOsobaLista(), a.getKorisnik()), vrijeme,
                        ElektricnoVozilo.dohvatiVrstuVozilaIzListe(PostavkeSingleton.getElektricnoVoziloLista(), a.getVrstaVozila()), 
                        Lokacija.dohvatiLokacijuIzListe(PostavkeSingleton.getLokacijaLista(), a.getLokacija()),a.getBrojKM(),a.getOpis(),a.getVrijeme());
                    } 
                    
                    ElektricnoVozilo.azurirajStanjeBaterija(vrijeme);
                    break;
                case 5:
                    System.out.println("Program se vec nalazi u skupnom nacinu rada!\n");
                    break;                               
                case 6:
                    if("struktura".equals(a.getParametar1()) &&"stanje".equals(a.getParametar2()) && a.getIdOrgJedinice()!=-1){
                        PostavkeSingleton.ps.println("Izvrsavanje aktivnosti: " + a.getId() + "; "+a.getParametar1()+" "+a.getParametar2()+" "+a.getIdOrgJedinice());
                        ispisiStrukturu(postavkeSingleton, a.getIdOrgJedinice(), 1);
                    }
                    else if("struktura".equals(a.getParametar1()) && a.getIdOrgJedinice()==-1){
                        PostavkeSingleton.ps.println("Izvrsavanje aktivnosti: " + a.getId() + "; "+a.getParametar1());
                        ispisiStrukturu(postavkeSingleton, -1, 0);
                    }
                    break;                              
                case 7:
                    if("struktura".equals(a.getParametar1()) &&"najam".equals(a.getParametar2()) && "zarada".equals(a.getParametar3()) && a.getIdOrgJedinice()!=-1 && a.getPocetniDatum()!=null && a.getKrajnjiDatum()!=null){
                        PostavkeSingleton.ps.println("Izvrsavanje aktivnosti: " + a.getId() + "; "+a.getParametar1()+ " "+a.getParametar2()+ " "+a.getParametar3()+ " "+new SimpleDateFormat("dd.MM.yyyy").format(a.getPocetniDatum())+" "+new SimpleDateFormat("dd.MM.yyyy").format(a.getKrajnjiDatum())+ " "+a.getIdOrgJedinice());
                        ispisiNajmove(postavkeSingleton,a.getIdOrgJedinice(),1,1,a.getPocetniDatum(),a.getKrajnjiDatum());
                    }
                    else if("struktura".equals(a.getParametar1()) &&"najam".equals(a.getParametar2()) && a.getIdOrgJedinice()!=-1 && a.getPocetniDatum()!=null && a.getKrajnjiDatum()!=null){
                        PostavkeSingleton.ps.println("Izvrsavanje aktivnosti: " + a.getId() + "; "+a.getParametar1()+ " "+a.getParametar2()+ " "+new SimpleDateFormat("dd.MM.yyyy").format(a.getPocetniDatum())+" "+new SimpleDateFormat("dd.MM.yyyy").format(a.getKrajnjiDatum())+ " "+a.getIdOrgJedinice());
                        ispisiNajmove(postavkeSingleton,a.getIdOrgJedinice(),1,0,a.getPocetniDatum(),a.getKrajnjiDatum());
                    }
                    else if("struktura".equals(a.getParametar1()) && a.getPocetniDatum()!=null && a.getKrajnjiDatum()!=null){
                        PostavkeSingleton.ps.println("Izvrsavanje aktivnosti: " + a.getId() + "; "+a.getParametar1()+ " "+new SimpleDateFormat("dd.MM.yyyy").format(a.getPocetniDatum())+" "+new SimpleDateFormat("dd.MM.yyyy").format(a.getKrajnjiDatum()));
                        ispisiNajmove(postavkeSingleton,-1,0,0,a.getPocetniDatum(),a.getKrajnjiDatum());                   
                    }
                    break;                              
                case 8:
                    if("struktura".equals(a.getParametar1()) &&"rauni".equals(a.getParametar2()) && a.getIdOrgJedinice()!=-1 && a.getPocetniDatum()!=null && a.getKrajnjiDatum()!=null){
                        PostavkeSingleton.ps.println("Izvrsavanje aktivnosti: " + a.getId() + "; "+a.getParametar1()+ " "+"racuni"+ " "+new SimpleDateFormat("dd.MM.yyyy").format(a.getPocetniDatum())+" "+new SimpleDateFormat("dd.MM.yyyy").format(a.getKrajnjiDatum()));
                        ispisiRacune(postavkeSingleton,a.getIdOrgJedinice(),1,a.getPocetniDatum(),a.getKrajnjiDatum());
                    }
                    else if("struktura".equals(a.getParametar1()) && a.getPocetniDatum()!=null && a.getKrajnjiDatum()!=null){
                        PostavkeSingleton.ps.println("Izvrsavanje aktivnosti: " + a.getId() + "; "+a.getParametar1()+ " "+new SimpleDateFormat("dd.MM.yyyy").format(a.getPocetniDatum())+" "+new SimpleDateFormat("dd.MM.yyyy").format(a.getKrajnjiDatum()));
                        ispisiRacune(postavkeSingleton,-1,0,a.getPocetniDatum(),a.getKrajnjiDatum()); 
                    }
                    break;
                default:
                    break;
            }
        }

        PostavkeSingleton.ps.println("Zavrsava skupni nacin rada!");
        postavkeSingleton.setNacinRadaSkupni(false);
        zapocniInteraktivanNacinRada(postavkeSingleton, aktivnostiLoader);

    }  
    
    public static void ispisiRacune(PostavkeSingleton postavkeSingleton, int idOrganizacijskeJedinice, int racuni, Date pocetni, Date krajnji){
        StrukturaBuilder sb = new StrukturaBuilderImplementacija();
        Struktura izvorisnaOJ = new Struktura(OrganizacijskaJedinica.dohvatiIzvorisnuOJ(PostavkeSingleton.getOrganizacijskaJedinicaLista()));
       
        if(pocetni.compareTo(krajnji)>0){
            PostavkeSingleton.ps.println("Pogreska aktivnost 8: Pocetni datum ne smije biti veci do krajnjeg datuma!");
            return;
        }
        
        if(izvorisnaOJ==null)
            PostavkeSingleton.ps.println("Nije moguce konstruirati strukturu jer ne postoji izvorisna organizacijska jedinica!");
        else{
           // sb.saPodredenomOrganizacijskomJedinicom(izvorisnaOJ);
            sb.definirajIzvorisnuOj(izvorisnaOJ);
            Struktura s = sb.build();

            if(idOrganizacijskeJedinice!=-1){
                s = Struktura.dohvatiStrukturuPoId(s, idOrganizacijskeJedinice);
                if(s==null){
                    System.out.println("Organizacijska jedinica s id " + idOrganizacijskeJedinice+ " nije dio strukture organizacije");
                    Struktura.strukturePoRazinama.clear();
                    for(Lokacija l : PostavkeSingleton.getLokacijaLista())
                        l.setJeSastavniDioOJ(false);
                    return;
                }
                 
            }
            
            System.out.println("\n{Prvi stupac: ID lokacije} {Drugi stupac: Naziv organizacijske jedinice} {Treci stupac: Nadredena organizacijska jedinica} {Cetvrti stupac: Strukturna razina (po hijerarhiji)}");

            int temp = 0;
            s.setStrukturnaRazina(temp);
            Struktura.strukturePoRazinama.add(s);
                    
            if(s.getNadredenaJedinica()!=0){
               System.out.format("%"+postavkeSingleton.dc+"s",String.format("%"+postavkeSingleton.dc+"d ", s.getId()).substring(0, postavkeSingleton.dc));
                System.out.format("%"+postavkeSingleton.razmakStupci+"s", " ");
                System.out.format("%-"+postavkeSingleton.dt+"s",String.format("%-"+postavkeSingleton.dt+"s ", s.getNaziv()).substring(0, postavkeSingleton.dt));
                System.out.format("%"+postavkeSingleton.razmakStupci+"s", " ");
                System.out.format("%"+postavkeSingleton.dc+"s",String.format("%"+postavkeSingleton.dc+"d ", s.getNadredenaJedinica()).substring(0, postavkeSingleton.dc));
                System.out.format("%"+postavkeSingleton.razmakStupci+"s", " ");
                System.out.format("%"+postavkeSingleton.dc+"s\n",String.format("%"+postavkeSingleton.dc+"d ", temp).substring(0, postavkeSingleton.dc));

            }else{
                System.out.format("%"+postavkeSingleton.dc+"s",String.format("%"+postavkeSingleton.dc+"d ", s.getId()).substring(0, postavkeSingleton.dc));
                System.out.format("%"+postavkeSingleton.razmakStupci+"s", " ");
                System.out.format("%-"+postavkeSingleton.dt+"s",String.format("%-"+postavkeSingleton.dt+"s ", s.getNaziv()).substring(0, postavkeSingleton.dt));
                System.out.format("%"+postavkeSingleton.razmakStupci+"s", " ");
                System.out.format("%"+postavkeSingleton.dc+"s\n",String.format("%"+postavkeSingleton.dc+"s ", "").substring(0, postavkeSingleton.dc));
            }
            
            for(Struktura struktura : s.getPodredeneOJ()){
                StrukturaRepozitorij sr = new StrukturaRepozitorij(struktura);
                temp = 1;
                sr.getS().setStrukturnaRazina(temp);
                Struktura.strukturePoRazinama.add(sr.getS());
                System.out.format("%"+postavkeSingleton.dc+"s",String.format("%"+postavkeSingleton.dc+"d ", sr.getS().getId()).substring(0, postavkeSingleton.dc));
                System.out.format("%"+postavkeSingleton.razmakStupci+"s", " ");
                System.out.format("%-"+postavkeSingleton.dt+"s",String.format("%-"+postavkeSingleton.dt+"s ", sr.getS().getNaziv()).substring(0, postavkeSingleton.dt));
                System.out.format("%"+postavkeSingleton.razmakStupci+"s", " ");
                System.out.format("%"+postavkeSingleton.dc+"s",String.format("%"+postavkeSingleton.dc+"d ", sr.getS().getNadredenaJedinica()).substring(0, postavkeSingleton.dc));
                System.out.format("%"+postavkeSingleton.razmakStupci+"s", " ");
                System.out.format("%"+postavkeSingleton.dc+"s\n",String.format("%"+postavkeSingleton.dc+"d ", temp).substring(0, postavkeSingleton.dc));

                Iterator iter = sr.getIterator();
                
                while(iter.hasNext()){
                    temp++;
                           
                    for(Struktura sa : (ArrayList<Struktura>)iter.next()){
                        
                        sr.setS(sa);
                        iter = sr.getIterator();
                        sr.getS().setStrukturnaRazina(temp);
                        Struktura.strukturePoRazinama.add(sr.getS());
                        System.out.format("%"+postavkeSingleton.dc+"s",String.format("%"+postavkeSingleton.dc+"d ", sr.getS().getId()).substring(0, postavkeSingleton.dc));
                        System.out.format("%"+postavkeSingleton.razmakStupci+"s", " ");
                        System.out.format("%-"+postavkeSingleton.dt+"s",String.format("%-"+postavkeSingleton.dt+"s ", sr.getS().getNaziv()).substring(0, postavkeSingleton.dt));
                        System.out.format("%"+postavkeSingleton.razmakStupci+"s", " ");
                        System.out.format("%"+postavkeSingleton.dc+"s",String.format("%"+postavkeSingleton.dc+"d ", sr.getS().getNadredenaJedinica()).substring(0, postavkeSingleton.dc));
                        System.out.format("%"+postavkeSingleton.razmakStupci+"s", " ");
                        System.out.format("%"+postavkeSingleton.dc+"s\n",String.format("%"+postavkeSingleton.dc+"d ", temp).substring(0, postavkeSingleton.dc));
                    }
                }    
            }
        }
        PostavkeSingleton.ps.println();

        for(Struktura s:Struktura.strukturePoRazinama){
            Struktura.provjeriLokacije(s, s.getLokacijeTemp());
        }
        
        
        for(Struktura s: Struktura.strukturePoRazinama){
            PostavkeSingleton.ps.println("Ispis lokacija za organizacijsku jedinicu "+ s.getNaziv());
             System.out.println("{Prvi stupac: ID} {Drugi stupac: Naziv lokacije} {Treci stupac: adresa} {Cetvrti stupac: GPS)}");
            for(Lokacija l : s.getLokacije()){    
                System.out.format("%"+postavkeSingleton.dc+"s",String.format("%"+postavkeSingleton.dc+"d ", l.getId()).substring(0, postavkeSingleton.dc));
                System.out.format("%"+postavkeSingleton.razmakStupci+"s", " ");
                System.out.format("%-"+postavkeSingleton.dt+"s",String.format("%-"+postavkeSingleton.dt+"s ", l.getNazivLokacije()).substring(0, postavkeSingleton.dt));
                System.out.format("%"+postavkeSingleton.razmakStupci+"s", " ");
                System.out.format("%-"+postavkeSingleton.dt+"s",String.format("%-"+postavkeSingleton.dt+"s ", l.getAdresaLokacije()).substring(0, postavkeSingleton.dt));
                System.out.format("%"+postavkeSingleton.razmakStupci+"s", " ");
                String[] gps = l.getGps().replace(" ", "").split(",");
                String[] prvi = gps[0].split("\\.");
                String[] drugi = gps[1].split("\\.");
                if(prvi[0].length()>postavkeSingleton.dc)
                    prvi[0]= prvi[0].substring(0,postavkeSingleton.dc);
                if(prvi[1].length()>postavkeSingleton.dd)
                    prvi[1]= prvi[1].substring(0,postavkeSingleton.dd);
                if(drugi[0].length()>postavkeSingleton.dc)
                    drugi[0]= drugi[0].substring(0,postavkeSingleton.dc);
                if(drugi[1].length()>postavkeSingleton.dd)
                    drugi[1]= drugi[1].substring(0,postavkeSingleton.dd);
                System.out.format("%"+postavkeSingleton.dt+"s\n",String.format("%-"+postavkeSingleton.dt+"s ", prvi[0]+"."+prvi[1]+","+drugi[0]+"."+drugi[1]).substring(0, postavkeSingleton.dt));

            }
            System.out.println();
        }
 
        
        if(racuni==1){
            String decimalFormatString= "";
            for(int i=0; i<postavkeSingleton.dc;i++){
                decimalFormatString+="#";
            }
            decimalFormatString+=".";
            for(int i=0; i<postavkeSingleton.dd;i++){
                decimalFormatString+="#";
            }
            DecimalFormat df = new DecimalFormat(decimalFormatString);
             
            SimpleDateFormat vrijemeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            for(Struktura s: Struktura.strukturePoRazinama){
                for(Lokacija l : s.getLokacije()){    
                    PostavkeSingleton.ps.println("Ispis najmova za lokaciju "+ l.getNazivLokacije()+ " u organizacijskoj jedinici "+s.getNaziv());
                    System.out.println("{Prvi stupac: ID racuna} {Drugi stupac: ID osobe} {Treci stupac: ID lokacije} {Cetvrti stupac: Ime i prezime} \n{Peti stupac: Datum izdavanja} {Sesti stupac: Iznos} {Sedmi stupac: Struktura racuna}");

                    for(Racun r : Racun.dohvatiListuRacunaFiltriranuPoDatumima(PostavkeSingleton.getRacunLista(), pocetni, krajnji)){
                        if(l.getId()==r.getLokacijaId()){
                            System.out.format("%"+postavkeSingleton.dc+"s",String.format("%"+postavkeSingleton.dc+"d ", r.getJIDracuna()).substring(0, postavkeSingleton.dc));
                            System.out.format("%"+postavkeSingleton.razmakStupci+"s", " ");                           
                            System.out.format("%"+postavkeSingleton.dc+"s",String.format("%"+postavkeSingleton.dc+"d ", r.getOsobaId()).substring(0, postavkeSingleton.dc));
                            System.out.format("%"+postavkeSingleton.razmakStupci+"s", " "); 
                            System.out.format("%"+postavkeSingleton.dc+"s",String.format("%"+postavkeSingleton.dc+"d ", r.getLokacijaId()).substring(0, postavkeSingleton.dc));
                            System.out.format("%"+postavkeSingleton.razmakStupci+"s", " "); 
                            System.out.format("%-"+postavkeSingleton.dt+"s",String.format("%-"+postavkeSingleton.dt+"s ", Osoba.dohvatiOsobuIzListe(PostavkeSingleton.getOsobaLista(), r.getOsobaId()).getImePrezime()).substring(0, postavkeSingleton.dt));
                            System.out.format("%"+postavkeSingleton.razmakStupci+"s", " ");
                            
                            if(r.getDatumIzdavanja()!=null){
                                System.out.format("%-"+postavkeSingleton.dt+"s",String.format("%-"+postavkeSingleton.dt+"s ", vrijemeFormat.format(r.getDatumIzdavanja())).substring(0, postavkeSingleton.dt));
                                System.out.format("%"+postavkeSingleton.razmakStupci+"s", " ");
                            }else {
                                System.out.format("%-"+postavkeSingleton.dt+"s",String.format("%-"+postavkeSingleton.dt+"s ","").substring(0, postavkeSingleton.dt));
                                System.out.format("%"+postavkeSingleton.razmakStupci+"s", " ");
                            }
                            
                            System.out.format("%"+(postavkeSingleton.dd+postavkeSingleton.dc)+"s",String.format("%"+(postavkeSingleton.dd+postavkeSingleton.dc)+"s ", df.format(r.getIznos())).substring(0, (postavkeSingleton.dd+postavkeSingleton.dc)));
                            System.out.format("%"+postavkeSingleton.razmakStupci+"s", " "); 

                            System.out.format("%-"+postavkeSingleton.dt+"s",String.format("%-"+postavkeSingleton.dt+"s ",r.getOpis()).substring(0, postavkeSingleton.dt));
                            System.out.format("%"+postavkeSingleton.razmakStupci+"s\n", " ");

                        }
                    }
                    System.out.println();
                }                 
                System.out.println();                        
            }
            
            PostavkeSingleton.ps.println("Kumulativni ispis racuna ");
            System.out.println("{Prvi stupac: ID racuna} {Drugi stupac: ID osobe} {Treci stupac: Ime i preizme osobe} {Cetvrti stupac: ID lokacije} \n{Peti stupac: Datum izdavanja} {Sesti stupac: Iznos} {Sedmi stupac: Struktura racuna}");

            float ukupnaZarada=0;
            for(Struktura s: Struktura.strukturePoRazinama){
                for(Lokacija l : s.getLokacije()){                      
                    for(Racun r : Racun.dohvatiListuRacunaFiltriranuPoDatumima(PostavkeSingleton.getRacunLista(), pocetni, krajnji)){
                        if(l.getId()==r.getLokacijaId()){
                           
                            System.out.format("%"+postavkeSingleton.dc+"s",String.format("%"+postavkeSingleton.dc+"d ", r.getJIDracuna()).substring(0, postavkeSingleton.dc));
                            System.out.format("%"+postavkeSingleton.razmakStupci+"s", " ");                           
                            System.out.format("%"+postavkeSingleton.dc+"s",String.format("%"+postavkeSingleton.dc+"d ", r.getOsobaId()).substring(0, postavkeSingleton.dc));
                            System.out.format("%"+postavkeSingleton.razmakStupci+"s", " "); 
                            System.out.format("%"+postavkeSingleton.dc+"s",String.format("%"+postavkeSingleton.dc+"d ", r.getLokacijaId()).substring(0, postavkeSingleton.dc));
                            System.out.format("%"+postavkeSingleton.razmakStupci+"s", " "); 
                            System.out.format("%-"+postavkeSingleton.dt+"s",String.format("%-"+postavkeSingleton.dt+"s ", Osoba.dohvatiOsobuIzListe(PostavkeSingleton.getOsobaLista(), r.getOsobaId()).getImePrezime()).substring(0, postavkeSingleton.dt));
                            System.out.format("%"+postavkeSingleton.razmakStupci+"s", " ");
                            
                            if(r.getDatumIzdavanja()!=null){
                                System.out.format("%-"+postavkeSingleton.dt+"s",String.format("%-"+postavkeSingleton.dt+"s ", vrijemeFormat.format(r.getDatumIzdavanja())).substring(0, postavkeSingleton.dt));
                                System.out.format("%"+postavkeSingleton.razmakStupci+"s", " ");
                            }else {
                                System.out.format("%-"+postavkeSingleton.dt+"s",String.format("%-"+postavkeSingleton.dt+"s ","").substring(0, postavkeSingleton.dt));
                                System.out.format("%"+postavkeSingleton.razmakStupci+"s", " ");
                            }
                            
                            System.out.format("%"+(postavkeSingleton.dd+postavkeSingleton.dc)+"s",String.format("%"+(postavkeSingleton.dd+postavkeSingleton.dc)+"s ", df.format(r.getIznos())).substring(0, (postavkeSingleton.dd+postavkeSingleton.dc)));
                            System.out.format("%"+postavkeSingleton.razmakStupci+"s", " "); 
                            ukupnaZarada+=r.getIznos();
                            System.out.format("%-"+postavkeSingleton.dt+"s",String.format("%-"+postavkeSingleton.dt+"s ",r.getOpis()).substring(0, postavkeSingleton.dt));
                            System.out.format("%"+postavkeSingleton.razmakStupci+"s\n", " ");
                        }
                    }                  
                }                                       
            }
            System.out.println("Ukupna zarada: "+df.format(ukupnaZarada) +" kn\n");
        }
        
        Struktura.strukturePoRazinama.clear();
        for(Lokacija l : PostavkeSingleton.getLokacijaLista())
            l.setJeSastavniDioOJ(false);
        //PostavkeSingleton.getRacunLista().clear();;
        //Racun.setIdBrojac(0);
    }
    
    public static void ispisiNajmove(PostavkeSingleton postavkeSingleton, int idOrganizacijskeJedinice, int najam, int zarada, Date pocetni, Date krajnji){
        StrukturaBuilder sb = new StrukturaBuilderImplementacija();
        Struktura izvorisnaOJ = new Struktura(OrganizacijskaJedinica.dohvatiIzvorisnuOJ(PostavkeSingleton.getOrganizacijskaJedinicaLista()));
       
        if(pocetni.compareTo(krajnji)>0){
            PostavkeSingleton.ps.println("Pogreska aktivnost 7: Pocetni datum ne smije biti veci do krajnjeg datuma!");
            return;
        }
        
        if(izvorisnaOJ==null)
            PostavkeSingleton.ps.println("Nije moguce konstruirati strukturu jer ne postoji izvorisna organizacijska jedinica!");
        else{
           // sb.saPodredenomOrganizacijskomJedinicom(izvorisnaOJ);
            sb.definirajIzvorisnuOj(izvorisnaOJ);
            Struktura s = sb.build();

            if(idOrganizacijskeJedinice!=-1){
                s = Struktura.dohvatiStrukturuPoId(s, idOrganizacijskeJedinice);
                if(s==null){
                    System.out.println("Organizacijska jedinica s id " + idOrganizacijskeJedinice+ " nije dio strukture organizacije");
                    Struktura.strukturePoRazinama.clear();
                    for(Lokacija l : PostavkeSingleton.getLokacijaLista())
                        l.setJeSastavniDioOJ(false);
                    return;
                }
                 
            }
            
            System.out.println("\n{Prvi stupac: ID lokacije} {Drugi stupac: Naziv organizacijske jedinice} {Treci stupac: Nadredena organizacijska jedinica} {Cetvrti stupac: Strukturna razina (po hijerarhiji)}");

            int temp = 0;
            s.setStrukturnaRazina(temp);
            Struktura.strukturePoRazinama.add(s);
                    
            if(s.getNadredenaJedinica()!=0){
                System.out.format("%"+postavkeSingleton.dc+"s",String.format("%"+postavkeSingleton.dc+"d ", s.getId()).substring(0, postavkeSingleton.dc));
                System.out.format("%"+postavkeSingleton.razmakStupci+"s", " ");
                System.out.format("%-"+postavkeSingleton.dt+"s",String.format("%-"+postavkeSingleton.dt+"s ", s.getNaziv()).substring(0, postavkeSingleton.dt));
                System.out.format("%"+postavkeSingleton.razmakStupci+"s", " ");
                System.out.format("%"+postavkeSingleton.dc+"s",String.format("%"+postavkeSingleton.dc+"d ", s.getNadredenaJedinica()).substring(0, postavkeSingleton.dc));
                System.out.format("%"+postavkeSingleton.razmakStupci+"s", " ");
                System.out.format("%"+postavkeSingleton.dc+"s\n",String.format("%"+postavkeSingleton.dc+"d ", temp).substring(0, postavkeSingleton.dc));
            }else{
                System.out.format("%"+postavkeSingleton.dc+"s",String.format("%"+postavkeSingleton.dc+"d ", s.getId()).substring(0, postavkeSingleton.dc));
                System.out.format("%"+postavkeSingleton.razmakStupci+"s", " ");
                System.out.format("%-"+postavkeSingleton.dt+"s",String.format("%-"+postavkeSingleton.dt+"s ", s.getNaziv()).substring(0, postavkeSingleton.dt));
                System.out.format("%"+postavkeSingleton.razmakStupci+"s", " ");
                System.out.format("%"+postavkeSingleton.dc+"s\n",String.format("%"+postavkeSingleton.dc+"s ", "").substring(0, postavkeSingleton.dc));
            }
            
            for(Struktura struktura : s.getPodredeneOJ()){
                StrukturaRepozitorij sr = new StrukturaRepozitorij(struktura);
                temp = 1;
                sr.getS().setStrukturnaRazina(temp);
                Struktura.strukturePoRazinama.add(sr.getS());
                System.out.format("%"+postavkeSingleton.dc+"s",String.format("%"+postavkeSingleton.dc+"d ", sr.getS().getId()).substring(0, postavkeSingleton.dc));
                System.out.format("%"+postavkeSingleton.razmakStupci+"s", " ");
                System.out.format("%-"+postavkeSingleton.dt+"s",String.format("%-"+postavkeSingleton.dt+"s ", sr.getS().getNaziv()).substring(0, postavkeSingleton.dt));
                System.out.format("%"+postavkeSingleton.razmakStupci+"s", " ");
                System.out.format("%"+postavkeSingleton.dc+"s",String.format("%"+postavkeSingleton.dc+"d ", sr.getS().getNadredenaJedinica()).substring(0, postavkeSingleton.dc));
                System.out.format("%"+postavkeSingleton.razmakStupci+"s", " ");
                System.out.format("%"+postavkeSingleton.dc+"s\n",String.format("%"+postavkeSingleton.dc+"d ", temp).substring(0, postavkeSingleton.dc));

                Iterator iter = sr.getIterator();
                
                while(iter.hasNext()){
                    temp++;
                           
                    for(Struktura sa : (ArrayList<Struktura>)iter.next()){
                        
                        sr.setS(sa);
                        iter = sr.getIterator();
                        sr.getS().setStrukturnaRazina(temp);
                        Struktura.strukturePoRazinama.add(sr.getS());
                        System.out.format("%"+postavkeSingleton.dc+"s",String.format("%"+postavkeSingleton.dc+"d ", sr.getS().getId()).substring(0, postavkeSingleton.dc));
                        System.out.format("%"+postavkeSingleton.razmakStupci+"s", " ");
                        System.out.format("%-"+postavkeSingleton.dt+"s",String.format("%-"+postavkeSingleton.dt+"s ", sr.getS().getNaziv()).substring(0, postavkeSingleton.dt));
                        System.out.format("%"+postavkeSingleton.razmakStupci+"s", " ");
                        System.out.format("%"+postavkeSingleton.dc+"s",String.format("%"+postavkeSingleton.dc+"d ", sr.getS().getNadredenaJedinica()).substring(0, postavkeSingleton.dc));
                        System.out.format("%"+postavkeSingleton.razmakStupci+"s", " ");
                        System.out.format("%"+postavkeSingleton.dc+"s\n",String.format("%"+postavkeSingleton.dc+"d ", temp).substring(0, postavkeSingleton.dc));
                    }
                }    
            }
        }
        PostavkeSingleton.ps.println();

        for(Struktura s:Struktura.strukturePoRazinama){
            Struktura.provjeriLokacije(s, s.getLokacijeTemp());
        }
        
        
        for(Struktura s: Struktura.strukturePoRazinama){
            PostavkeSingleton.ps.println("Ispis lokacija za organizacijsku jedinicu "+ s.getNaziv());
            System.out.println("{Prvi stupac: ID} {Drugi stupac: Naziv lokacije} {Treci stupac: adresa} {Cetvrti stupac: GPS)}");

            for(Lokacija l : s.getLokacije()){    
                System.out.format("%"+postavkeSingleton.dc+"s",String.format("%"+postavkeSingleton.dc+"d ", l.getId()).substring(0, postavkeSingleton.dc));
                System.out.format("%"+postavkeSingleton.razmakStupci+"s", " ");
                System.out.format("%-"+postavkeSingleton.dt+"s",String.format("%-"+postavkeSingleton.dt+"s ", l.getNazivLokacije()).substring(0, postavkeSingleton.dt));
                System.out.format("%"+postavkeSingleton.razmakStupci+"s", " ");
                System.out.format("%-"+postavkeSingleton.dt+"s",String.format("%-"+postavkeSingleton.dt+"s ", l.getAdresaLokacije()).substring(0, postavkeSingleton.dt));
                System.out.format("%"+postavkeSingleton.razmakStupci+"s", " ");

                String[] gps = l.getGps().replace(" ", "").split(",");
                String[] prvi = gps[0].split("\\.");
                String[] drugi = gps[1].split("\\.");
                if(prvi[0].length()>postavkeSingleton.dc)
                    prvi[0]= prvi[0].substring(0,postavkeSingleton.dc);
                if(prvi[1].length()>postavkeSingleton.dd)
                    prvi[1]= prvi[1].substring(0,postavkeSingleton.dd);
                if(drugi[0].length()>postavkeSingleton.dc)
                    drugi[0]= drugi[0].substring(0,postavkeSingleton.dc);
                if(drugi[1].length()>postavkeSingleton.dd)
                    drugi[1]= drugi[1].substring(0,postavkeSingleton.dd);
                System.out.format("%"+postavkeSingleton.dt+"s\n",String.format("%-"+postavkeSingleton.dt+"s ", prvi[0]+"."+prvi[1]+","+drugi[0]+"."+drugi[1]).substring(0, postavkeSingleton.dt));

            }
            System.out.println();
        }
        
        if(najam==1){
                      
             ArrayList<Lokacija> listaLokacijaTemp = new ArrayList<Lokacija>();
            for(Struktura s : Struktura.strukturePoRazinama){
                for(Lokacija l : s.getLokacije()){
                    listaLokacijaTemp.add(l);
                }
            }
            
            for(Najam n : Najam.dohvatiListuNajmovaFiltriranuPoDatumima(PostavkeSingleton.getNajamLista(), pocetni, krajnji)){
                for(Lokacija l : listaLokacijaTemp){
                    for(KapacitetLokacije kl : l.getKapacitetLokacije()){
                        if(l.getId()==n.getLokacijaId() && kl.getVozilo().getId()==n.getVoziloVrstaId()){
                            kl.setBrojNajmova(kl.getBrojNajmova()+1);
                        }
                    }
                }
            }

            for(Lokacija l : listaLokacijaTemp){
                PostavkeSingleton.ps.println("Ispis broja najmova po vozilu "+ l.getNazivLokacije()+ " na lokaciji "+l.getNazivLokacije());
                System.out.println("{Prvi stupac: ID vozila} {Drugi stupac: Naziv vozila} {Treci stupac: Broj najmova}");
                for(KapacitetLokacije kl : l.getKapacitetLokacije()){
                    System.out.format("%"+postavkeSingleton.dc+"s",String.format("%"+postavkeSingleton.dc+"d ", kl.getVozilo().getId()).substring(0, postavkeSingleton.dc));
                    System.out.format("%"+postavkeSingleton.razmakStupci+"s", " ");
                    System.out.format("%-"+postavkeSingleton.dt+"s",String.format("%-"+postavkeSingleton.dt+"s ", kl.getVozilo().getNaziv()).substring(0, postavkeSingleton.dt));
                    System.out.format("%"+postavkeSingleton.razmakStupci+"s", " ");
                    System.out.format("%"+postavkeSingleton.dc+"s\n",String.format("%"+postavkeSingleton.dc+"d ", kl.getBrojNajmova()).substring(0, postavkeSingleton.dc));
                }
                System.out.println();
            }
            
                         
            ArrayList<KapacitetLokacije> kumulativnaLista = new ArrayList<KapacitetLokacije>();
            for(ElektricnoVozilo ev : PostavkeSingleton.getElektricnoVoziloLista()){
                kumulativnaLista.add(new KapacitetLokacije(ev));
            }
            for(Struktura s : Struktura.strukturePoRazinama){
                for(Lokacija l : s.getLokacije()){
                    for(Najam n : Najam.dohvatiListuNajmovaFiltriranuPoDatumima(PostavkeSingleton.getNajamLista(), pocetni, krajnji)){
                        if(l.getId()==n.getLokacijaId()){
                            for(KapacitetLokacije kl : kumulativnaLista){
                                if(kl.getVozilo().getId()==n.getVoziloVrstaId()){
                                    kl.setBrojNajmova(kl.getBrojNajmova()+1);
                                }
                            }
                        }
                    }
                }             
            }
         
            PostavkeSingleton.ps.println("Kumulativni broja najmova: ");
            System.out.println("{Prvi stupac: ID vozila} {Drugi stupac: Naziv vozila} {Treci stupac: Broj najmova}");

            for(KapacitetLokacije kl : kumulativnaLista){        
                System.out.format("%"+postavkeSingleton.dc+"s",String.format("%"+postavkeSingleton.dc+"d ", kl.getVozilo().getId()).substring(0, postavkeSingleton.dc));
                System.out.format("%"+postavkeSingleton.razmakStupci+"s", " ");
                System.out.format("%-"+postavkeSingleton.dt+"s",String.format("%-"+postavkeSingleton.dt+"s ", kl.getVozilo().getNaziv()).substring(0, postavkeSingleton.dt));
                System.out.format("%"+postavkeSingleton.razmakStupci+"s", " ");
                System.out.format("%"+postavkeSingleton.dc+"s\n",String.format("%"+postavkeSingleton.dc+"d ", kl.getBrojNajmova()).substring(0, postavkeSingleton.dc));          
            }
            System.out.println();
                   
        }
        
        if(zarada==1){
           
            String decimalFormatString= "";
            for(int i=0; i<postavkeSingleton.dc;i++){
                decimalFormatString+="#";
            }
            decimalFormatString+=".";
            for(int i=0; i<postavkeSingleton.dd;i++){
                decimalFormatString+="#";
            }
            DecimalFormat df = new DecimalFormat(decimalFormatString);
             
            SimpleDateFormat vrijemeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            for(Struktura s: Struktura.strukturePoRazinama){
                for(Lokacija l : s.getLokacije()){    
                    PostavkeSingleton.ps.println("Ispis najmova za lokaciju "+ l.getNazivLokacije()+ " u organizacijskoj jedinici "+s.getNaziv());
                    System.out.println("{Prvi stupac: Jedinstveni ID najma} {Drugi stupac: ID vrste vozila} {Treci stupac: ID korisnika} {Cetvrti stupac: Ime i prezime korisnika} \n{Peti stupac: Pocetak najma} {Sesti stupac: Kraj najma} {Sedmi stupac: Trajanje najma} {Osmi stupac: zarada}");
                    for(Najam n : Najam.dohvatiListuNajmovaFiltriranuPoDatumima(PostavkeSingleton.getNajamLista(), pocetni, krajnji)){
                        if(l.getId()==n.getLokacijaId()){
                            System.out.format("%"+postavkeSingleton.dc+"s",String.format("%"+postavkeSingleton.dc+"d ", n.getJIDnajam()).substring(0, postavkeSingleton.dc));
                            System.out.format("%"+postavkeSingleton.razmakStupci+"s", " ");
                            System.out.format("%"+postavkeSingleton.dc+"s",String.format("%"+postavkeSingleton.dc+"d ", n.getVoziloVrstaId()).substring(0, postavkeSingleton.dc));
                            System.out.format("%"+postavkeSingleton.razmakStupci+"s", " ");
                            System.out.format("%"+postavkeSingleton.dc+"s",String.format("%"+postavkeSingleton.dc+"d ", n.getOsosbaId()).substring(0, postavkeSingleton.dc));
                            System.out.format("%"+postavkeSingleton.razmakStupci+"s", " ");
                            System.out.format("%-"+postavkeSingleton.dt+"s",String.format("%-"+postavkeSingleton.dt+"s ", Osoba.dohvatiOsobuIzListe(PostavkeSingleton.getOsobaLista(), n.getOsosbaId()).getImePrezime()).substring(0, postavkeSingleton.dt));
                            System.out.format("%"+postavkeSingleton.razmakStupci+"s", " ");
                            System.out.format("%-"+postavkeSingleton.dt+"s",String.format("%-"+postavkeSingleton.dt+"s ", vrijemeFormat.format(n.getPocetakNajma())).substring(0, postavkeSingleton.dt));
                            System.out.format("%"+postavkeSingleton.razmakStupci+"s", " ");
                            if(n.getKrajNajma()!=null){
                                System.out.format("%-"+postavkeSingleton.dt+"s",String.format("%-"+postavkeSingleton.dt+"s ", vrijemeFormat.format(n.getKrajNajma())).substring(0, postavkeSingleton.dt));
                                System.out.format("%"+postavkeSingleton.razmakStupci+"s", " ");
                            }
                            else{
                                System.out.format("%-"+postavkeSingleton.dt+"s",String.format("%-"+postavkeSingleton.dt+"s ", "").substring(0, postavkeSingleton.dt));
                                System.out.format("%"+postavkeSingleton.razmakStupci+"s", " ");
                            }
                            if(n.getKrajNajma()!=null && n.getPocetakNajma()!=null){
                                System.out.format("%-"+postavkeSingleton.dt+"s",String.format("%-"+postavkeSingleton.dt+"s ", n.ispisiTrajanje(n.getPocetakNajma(), n.getKrajNajma())).substring(0, postavkeSingleton.dt));
                                System.out.format("%"+postavkeSingleton.razmakStupci+"s", " ");
                            }
                            else{ 
                                System.out.format("%-"+postavkeSingleton.dt+"s",String.format("%-"+postavkeSingleton.dt+"s ", "").substring(0, postavkeSingleton.dt));
                                System.out.format("%"+postavkeSingleton.razmakStupci+"s", " ");                       
                            }
                            if(n.getKrajNajma()!=null && n.getPocetakNajma()!=null){
                                System.out.format("%"+(postavkeSingleton.dd+postavkeSingleton.dc)+"s",String.format("%"+(postavkeSingleton.dd+postavkeSingleton.dc)+"s ", df.format(n.getZarada())).substring(0, (postavkeSingleton.dd+postavkeSingleton.dc)));
                                System.out.format("%"+postavkeSingleton.razmakStupci+"s\n", " ");                       
                            }
                            else{
                                System.out.format("%-"+postavkeSingleton.dt+"s",String.format("%-"+postavkeSingleton.dt+"s ", "Nije definirano").substring(0, postavkeSingleton.dt));
                                System.out.format("%"+postavkeSingleton.razmakStupci+"s\n", " ");
                            }
                        }
                    }
                    System.out.println();
                }                 
                System.out.println();                        
            }
            
            PostavkeSingleton.ps.println("Kumulativni ispis najmova ");
            System.out.println("{Prvi stupac: Jedinstveni ID najma} {Drugi stupac: ID vrste vozila} {Treci stupac: ID korisnika} {Cetvrti stupac: Ime i prezime korisnika} \n{Peti stupac: Pocetak najma} {Sesti stupac: Kraj najma} {Sedmi stupac: Trajanje najma} {Osmi stupac: zarada}");

            float ukupnaZarada=0;
            for(Struktura s: Struktura.strukturePoRazinama){
                for(Lokacija l : s.getLokacije()){                      
                    for(Najam n : Najam.dohvatiListuNajmovaFiltriranuPoDatumima(PostavkeSingleton.getNajamLista(), pocetni, krajnji)){
                        if(l.getId()==n.getLokacijaId()){
                                                        
                            System.out.format("%"+postavkeSingleton.dc+"s",String.format("%"+postavkeSingleton.dc+"d ", n.getJIDnajam()).substring(0, postavkeSingleton.dc));
                            System.out.format("%"+postavkeSingleton.razmakStupci+"s", " ");
                            System.out.format("%"+postavkeSingleton.dc+"s",String.format("%"+postavkeSingleton.dc+"d ", n.getVoziloVrstaId()).substring(0, postavkeSingleton.dc));
                            System.out.format("%"+postavkeSingleton.razmakStupci+"s", " ");
                            System.out.format("%"+postavkeSingleton.dc+"s",String.format("%"+postavkeSingleton.dc+"d ", n.getOsosbaId()).substring(0, postavkeSingleton.dc));
                            System.out.format("%"+postavkeSingleton.razmakStupci+"s", " ");
                            System.out.format("%-"+postavkeSingleton.dt+"s",String.format("%-"+postavkeSingleton.dt+"s ", Osoba.dohvatiOsobuIzListe(PostavkeSingleton.getOsobaLista(), n.getOsosbaId()).getImePrezime()).substring(0, postavkeSingleton.dt));
                            System.out.format("%"+postavkeSingleton.razmakStupci+"s", " ");
                            System.out.format("%-"+postavkeSingleton.dt+"s",String.format("%-"+postavkeSingleton.dt+"s ", vrijemeFormat.format(n.getPocetakNajma())).substring(0, postavkeSingleton.dt));
                            System.out.format("%"+postavkeSingleton.razmakStupci+"s", " ");
                            if(n.getKrajNajma()!=null){
                                System.out.format("%-"+postavkeSingleton.dt+"s",String.format("%-"+postavkeSingleton.dt+"s ", vrijemeFormat.format(n.getKrajNajma())).substring(0, postavkeSingleton.dt));
                                System.out.format("%"+postavkeSingleton.razmakStupci+"s", " ");
                            }
                            else{
                                System.out.format("%-"+postavkeSingleton.dt+"s",String.format("%-"+postavkeSingleton.dt+"s ", "").substring(0, postavkeSingleton.dt));
                                System.out.format("%"+postavkeSingleton.razmakStupci+"s", " ");
                            }
                            if(n.getKrajNajma()!=null && n.getPocetakNajma()!=null){
                                System.out.format("%-"+postavkeSingleton.dt+"s",String.format("%-"+postavkeSingleton.dt+"s ", n.ispisiTrajanje(n.getPocetakNajma(), n.getKrajNajma())).substring(0, postavkeSingleton.dt));
                                System.out.format("%"+postavkeSingleton.razmakStupci+"s", " ");
                            }
                            else{ 
                                System.out.format("%-"+postavkeSingleton.dt+"s",String.format("%-"+postavkeSingleton.dt+"s ", "").substring(0, postavkeSingleton.dt));
                                System.out.format("%"+postavkeSingleton.razmakStupci+"s", " ");                       
                            }
                            if(n.getKrajNajma()!=null && n.getPocetakNajma()!=null){
                                System.out.format("%"+(postavkeSingleton.dd+postavkeSingleton.dc)+"s",String.format("%"+(postavkeSingleton.dd+postavkeSingleton.dc)+"s ", df.format(n.getZarada())).substring(0, (postavkeSingleton.dd+postavkeSingleton.dc)));
                                System.out.format("%"+postavkeSingleton.razmakStupci+"s\n", " ");      
                                ukupnaZarada+=n.getZarada();
                            }
                            else{
                                System.out.format("%-"+postavkeSingleton.dt+"s",String.format("%-"+postavkeSingleton.dt+"s ", "Nije definirano").substring(0, postavkeSingleton.dt));
                                System.out.format("%"+postavkeSingleton.razmakStupci+"s\n", " ");
                            }
                 
                        }
                    }                  
                }                                       
            }
            System.out.println("Ukupna zarada: "+df.format(ukupnaZarada) +" kn\n");
        }
        
        Struktura.strukturePoRazinama.clear();
        for(Lokacija l : PostavkeSingleton.getLokacijaLista())
            l.setJeSastavniDioOJ(false);
        //PostavkeSingleton.getNajamLista().clear();
        //Najam.setBrojacId(0);
    }
    
    public static void ispisiStrukturu(PostavkeSingleton postavkeSingleton, int idOrganizacijskeJedinice, int stanje){
        StrukturaBuilder sb = new StrukturaBuilderImplementacija();
        Struktura izvorisnaOJ = new Struktura(OrganizacijskaJedinica.dohvatiIzvorisnuOJ(PostavkeSingleton.getOrganizacijskaJedinicaLista()));
       
        if(izvorisnaOJ==null)
            PostavkeSingleton.ps.println("Nije moguce konstruirati strukturu jer ne postoji izvorisna organizacijska jedinica!");
        else{
           // sb.saPodredenomOrganizacijskomJedinicom(izvorisnaOJ);
            sb.definirajIzvorisnuOj(izvorisnaOJ);
            Struktura s = sb.build();

            if(idOrganizacijskeJedinice!=-1){
                s = Struktura.dohvatiStrukturuPoId(s, idOrganizacijskeJedinice);
                if(s==null){
                    System.out.println("Organizacijska jedinica s id " + idOrganizacijskeJedinice+ " nije dio strukture organizacije");
                    Struktura.strukturePoRazinama.clear();
                    for(Lokacija l : PostavkeSingleton.getLokacijaLista())
                        l.setJeSastavniDioOJ(false);
                    return;
                }
                 
            }
            

            System.out.println("\n{Prvi stupac: ID lokacije} {Drugi stupac: Naziv organizacijske jedinice} {Treci stupac: Nadredena organizacijska jedinica} {Cetvrti stupac: Strukturna razina (po hijerarhiji)}");

            int temp = 0;
            s.setStrukturnaRazina(temp);
            Struktura.strukturePoRazinama.add(s);
                    
            if(s.getNadredenaJedinica()!=0){
                System.out.format("%"+postavkeSingleton.dc+"s",String.format("%"+postavkeSingleton.dc+"d ", s.getId()).substring(0, postavkeSingleton.dc));
                System.out.format("%"+postavkeSingleton.razmakStupci+"s", " ");
                System.out.format("%-"+postavkeSingleton.dt+"s",String.format("%-"+postavkeSingleton.dt+"s ", s.getNaziv()).substring(0, postavkeSingleton.dt));
                System.out.format("%"+postavkeSingleton.razmakStupci+"s", " ");
                System.out.format("%"+postavkeSingleton.dc+"s",String.format("%"+postavkeSingleton.dc+"d ", s.getNadredenaJedinica()).substring(0, postavkeSingleton.dc));
                System.out.format("%"+postavkeSingleton.razmakStupci+"s", " ");
                System.out.format("%"+postavkeSingleton.dc+"s\n",String.format("%"+postavkeSingleton.dc+"d ", temp).substring(0, postavkeSingleton.dc));

            }else{
                System.out.format("%"+postavkeSingleton.dc+"s",String.format("%"+postavkeSingleton.dc+"d ", s.getId()).substring(0, postavkeSingleton.dc));
                System.out.format("%"+postavkeSingleton.razmakStupci+"s", " ");
                System.out.format("%-"+postavkeSingleton.dt+"s",String.format("%-"+postavkeSingleton.dt+"s ", s.getNaziv()).substring(0, postavkeSingleton.dt));
                System.out.format("%"+postavkeSingleton.razmakStupci+"s", " ");
                System.out.format("%"+postavkeSingleton.dc+"s\n",String.format("%"+postavkeSingleton.dc+"s ", "").substring(0, postavkeSingleton.dc));
            }
            
            for(Struktura struktura : s.getPodredeneOJ()){
                StrukturaRepozitorij sr = new StrukturaRepozitorij(struktura);
                temp = 1;
                sr.getS().setStrukturnaRazina(temp);
                Struktura.strukturePoRazinama.add(sr.getS());
                System.out.format("%"+postavkeSingleton.dc+"s",String.format("%"+postavkeSingleton.dc+"d ", sr.getS().getId()).substring(0, postavkeSingleton.dc));
                System.out.format("%"+postavkeSingleton.razmakStupci+"s", " ");
                System.out.format("%-"+postavkeSingleton.dt+"s",String.format("%-"+postavkeSingleton.dt+"s ", sr.getS().getNaziv()).substring(0, postavkeSingleton.dt));
                System.out.format("%"+postavkeSingleton.razmakStupci+"s", " ");
                System.out.format("%"+postavkeSingleton.dc+"s",String.format("%"+postavkeSingleton.dc+"d ", sr.getS().getNadredenaJedinica()).substring(0, postavkeSingleton.dc));
                System.out.format("%"+postavkeSingleton.razmakStupci+"s", " ");
                System.out.format("%"+postavkeSingleton.dc+"s\n",String.format("%"+postavkeSingleton.dc+"d ", temp).substring(0, postavkeSingleton.dc));

                Iterator iter = sr.getIterator();
                
                while(iter.hasNext()){
                    temp++;
                           
                    for(Struktura sa : (ArrayList<Struktura>)iter.next()){
                        
                        sr.setS(sa);
                        iter = sr.getIterator();
                        sr.getS().setStrukturnaRazina(temp);
                        Struktura.strukturePoRazinama.add(sr.getS());
                        System.out.format("%"+postavkeSingleton.dc+"s",String.format("%"+postavkeSingleton.dc+"d ", sr.getS().getId()).substring(0, postavkeSingleton.dc));
                        System.out.format("%"+postavkeSingleton.razmakStupci+"s", " ");
                        System.out.format("%-"+postavkeSingleton.dt+"s",String.format("%-"+postavkeSingleton.dt+"s ", sr.getS().getNaziv()).substring(0, postavkeSingleton.dt));
                        System.out.format("%"+postavkeSingleton.razmakStupci+"s", " ");
                        System.out.format("%"+postavkeSingleton.dc+"s",String.format("%"+postavkeSingleton.dc+"d ", sr.getS().getNadredenaJedinica()).substring(0, postavkeSingleton.dc));
                        System.out.format("%"+postavkeSingleton.razmakStupci+"s", " ");
                        System.out.format("%"+postavkeSingleton.dc+"s\n",String.format("%"+postavkeSingleton.dc+"d ", temp).substring(0, postavkeSingleton.dc));
                    }
                }    
            }
        }
        PostavkeSingleton.ps.println();
        //StrukturaComparator sc = new StrukturaComparator();
        /*
        for(Struktura s : Struktura.strukturePoRazinama){
            System.out.println(s.getNaziv());
        }

        Collections.sort(Struktura.strukturePoRazinama,sc);
        /*
        for(Struktura sa : Struktura.strukturePoRazinama){
            System.out.println(sa.getStrukturnaRazina());
        }
        */
        for(Struktura s:Struktura.strukturePoRazinama){
            Struktura.provjeriLokacije(s, s.getLokacijeTemp());
        }
        
        for(Struktura s: Struktura.strukturePoRazinama){
            PostavkeSingleton.ps.println("Ispis lokacija za organizacijsku jedinicu "+ s.getNaziv());
            System.out.println("{Prvi stupac: ID} {Drugi stupac: Naziv lokacije} {Treci stupac: adresa} {Cetvrti stupac: GPS)}");

            for(Lokacija l : s.getLokacije()){
                System.out.format("%"+postavkeSingleton.dc+"s",String.format("%"+postavkeSingleton.dc+"d ", l.getId()).substring(0, postavkeSingleton.dc));
                System.out.format("%"+postavkeSingleton.razmakStupci+"s", " ");
                System.out.format("%-"+postavkeSingleton.dt+"s",String.format("%-"+postavkeSingleton.dt+"s ", l.getNazivLokacije()).substring(0, postavkeSingleton.dt));
                System.out.format("%"+postavkeSingleton.razmakStupci+"s", " ");
                System.out.format("%-"+postavkeSingleton.dt+"s",String.format("%-"+postavkeSingleton.dt+"s ", l.getAdresaLokacije()).substring(0, postavkeSingleton.dt));
                System.out.format("%"+postavkeSingleton.razmakStupci+"s", " ");

                String[] gps = l.getGps().replace(" ", "").split(",");
                String[] prvi = gps[0].split("\\.");
                String[] drugi = gps[1].split("\\.");
                if(prvi[0].length()>postavkeSingleton.dc)
                    prvi[0]= prvi[0].substring(0,postavkeSingleton.dc);
                if(prvi[1].length()>postavkeSingleton.dd)
                    prvi[1]= prvi[1].substring(0,postavkeSingleton.dd);
                if(drugi[0].length()>postavkeSingleton.dc)
                    drugi[0]= drugi[0].substring(0,postavkeSingleton.dc);
                if(drugi[1].length()>postavkeSingleton.dd)
                    drugi[1]= drugi[1].substring(0,postavkeSingleton.dd);
                System.out.format("%"+postavkeSingleton.dt+"s\n",String.format("%-"+postavkeSingleton.dt+"s ", prvi[0]+"."+prvi[1]+","+drugi[0]+"."+drugi[1]).substring(0, postavkeSingleton.dt));

            }
            System.out.println();
        }
        
        if(stanje==1){
            ArrayList<KapacitetLokacije> kumulativnaLista = new ArrayList<KapacitetLokacije>();
            for(ElektricnoVozilo ev : PostavkeSingleton.getElektricnoVoziloLista()){
                kumulativnaLista.add(new KapacitetLokacije(ev));
            }
            for(Struktura s: Struktura.strukturePoRazinama){
                for(Lokacija l : s.getLokacije()){    
                    PostavkeSingleton.ps.println("Ispis stanja za lokaciju "+ l.getNazivLokacije()+ " u organizacijskoj jedinici "+s.getNaziv());
                    System.out.println("{Prvi stupac: ID vozila} {Drugi stupac: Naziv vozila} {Treci stupac: Broj raspolozivih vozila} {Cetvrti stupac: Broj punjenja za vrstu vozila)} {Peti stupac: Broj neispravnih vozila)");

                    for(KapacitetLokacije kl : l.getKapacitetLokacije()){
                        for(KapacitetLokacije k : kumulativnaLista){
                            if(k.getVozilo().idVrstaVozila==kl.getVozilo().getId()){
                                k.setRaspolozivo(k.getRaspolozivo()+KapacitetLokacije.dohvatiListuRaspolozivihVozilaOdredeneVrsteNaLokaciji(kl.getVozilo().getId(),l.getId()).size());
                                k.setMjestaZaPunjenje(k.getMjestaZaPunjenje()+kl.getMjestaZaPunjenje());
                                k.setBrojNeispravnih(k.getBrojNeispravnih()+kl.getBrojNeispravnih());
                            }
                        }
                        System.out.format("%"+postavkeSingleton.dc+"s",String.format("%"+postavkeSingleton.dc+"d ", kl.getVozilo().getId()).substring(0, postavkeSingleton.dc));
                        System.out.format("%"+postavkeSingleton.razmakStupci+"s", " ");
                        System.out.format("%-"+postavkeSingleton.dt+"s",String.format("%-"+postavkeSingleton.dt+"s ", kl.getVozilo().getNaziv()).substring(0, postavkeSingleton.dt));
                        System.out.format("%"+postavkeSingleton.razmakStupci+"s", " ");
                        System.out.format("%"+postavkeSingleton.dc+"s",String.format("%"+postavkeSingleton.dc+"d ", KapacitetLokacije.dohvatiListuRaspolozivihVozilaOdredeneVrsteNaLokaciji(kl.getVozilo().getId(),l.getId()).size()).substring(0, postavkeSingleton.dc));
                        System.out.format("%"+postavkeSingleton.razmakStupci+"s", " ");
                        System.out.format("%"+postavkeSingleton.dc+"s",String.format("%"+postavkeSingleton.dc+"d ", kl.getMjestaZaPunjenje()).substring(0, postavkeSingleton.dc));
                        System.out.format("%"+postavkeSingleton.razmakStupci+"s", " ");
                        System.out.format("%"+postavkeSingleton.dc+"s\n",String.format("%"+postavkeSingleton.dc+"d ", kl.getBrojNeispravnih()).substring(0, postavkeSingleton.dc));
                    }                 
                System.out.println();

                }           
            }
            System.out.println("Kumulativan ispis podataka za trazene organizacijske jedinice");
            System.out.println("{Prvi stupac: ID vozila} {Drugi stupac: Naziv vozila} {Treci stupac: Broj raspolozivih vozila} {Cetvrti stupac: Broj punjenja za vrstu vozila)} {Peti stupac: Broj neispravnih vozila)");

            for(KapacitetLokacije k : kumulativnaLista){
                System.out.format("%"+postavkeSingleton.dc+"s",String.format("%"+postavkeSingleton.dc+"d ", k.getVozilo().getId()).substring(0, postavkeSingleton.dc));
                System.out.format("%"+postavkeSingleton.razmakStupci+"s", " ");
                System.out.format("%-"+postavkeSingleton.dt+"s",String.format("%-"+postavkeSingleton.dt+"s ", k.getVozilo().getNaziv()).substring(0, postavkeSingleton.dt));
                System.out.format("%"+postavkeSingleton.razmakStupci+"s", " ");
                System.out.format("%"+postavkeSingleton.dc+"s",String.format("%"+postavkeSingleton.dc+"d ", k.getRaspolozivo()).substring(0, postavkeSingleton.dc));
                System.out.format("%"+postavkeSingleton.razmakStupci+"s", " ");
                System.out.format("%"+postavkeSingleton.dc+"s",String.format("%"+postavkeSingleton.dc+"d ", k.getMjestaZaPunjenje()).substring(0, postavkeSingleton.dc));
                System.out.format("%"+postavkeSingleton.razmakStupci+"s", " ");
                System.out.format("%"+postavkeSingleton.dc+"s\n",String.format("%"+postavkeSingleton.dc+"d ", k.getBrojNeispravnih()).substring(0, postavkeSingleton.dc));
            }          
        }
        Struktura.strukturePoRazinama.clear();
        for(Lokacija l : PostavkeSingleton.getLokacijaLista())
            l.setJeSastavniDioOJ(false);
  
    }
}
