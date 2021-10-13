/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmanestar_zadaca_3.MVC;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import rmanestar_zadaca_3.Aktivnost;
import rmanestar_zadaca_3.Composite.Struktura;
import rmanestar_zadaca_3.ElektricnoVozilo;
import rmanestar_zadaca_3.FactoryMethod.FileLoader;
import rmanestar_zadaca_3.Lokacija;
import rmanestar_zadaca_3.Osoba;
import rmanestar_zadaca_3.Singleton.PostavkeSingleton;

/**
 *
 * @author Mane
 */
//old reg  (0; [0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2})|([1-3]; [0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2}; ([1-9]+[0-9]*|[0]); ([1-9]+[0-9]*|[0]); ([1-9]+[0-9]*|[0]))|(4; [0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2}; ([1-9]+[0-9]*|[0]); ([1-9]+[0-9]*|[0]); ([1-9]+[0-9]*|[0]); ([1-9]+[0-9]*|[0]); .+)|(4; [0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2}; ([1-9]+[0-9]*|[0]); ([1-9]+[0-9]*|[0]); ([1-9]+[0-9]*|[0]); ([1-9]+[0-9]*|[0]))|(5; ([\\w]|[:]|[\\\\]|[\\/]|[\\.]|[-])+.txt)|(6; struktura stanje ([1-9]+[0-9]*|[0]))|(6; struktura)|(7; struktura najam zarada ([0-9][0-9])\\.([0-9][0-9])\\.([0-9][0-9][0-9][0-9]) ([0-9][0-9])\\.([0-9][0-9])\\.([0-9][0-9][0-9][0-9]) ([1-9]+[0-9]*|[0]))|(7; struktura najam ([0-9][0-9])\\.([0-9][0-9])\\.([0-9][0-9][0-9][0-9]) ([0-9][0-9])\\.([0-9][0-9])\\.([0-9][0-9][0-9][0-9]) ([1-9]+[0-9]*|[0]))|(7; struktura ([0-9][0-9])\\.([0-9][0-9])\\.([0-9][0-9][0-9][0-9]) ([0-9][0-9])\\.([0-9][0-9])\\.([0-9][0-9][0-9][0-9])|(8; struktura rauni ([0-9][0-9])\\.([0-9][0-9])\\.([0-9][0-9][0-9][0-9]) ([0-9][0-9])\\.([0-9][0-9])\\.([0-9][0-9][0-9][0-9]) ([1-9]+[0-9]*|[0]))|(8; struktura ([0-9][0-9])\\.([0-9][0-9])\\.([0-9][0-9][0-9][0-9]) ([0-9][0-9])\\.([0-9][0-9])\\.([0-9][0-9][0-9][0-9])))

public class InteraktivniNacinView implements ViewInterface {
    @Override
    public String toString(){
        return "Interaktivni pogled";
    }
    @Override
    public void postaviVrstuIspisa(){
        System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
    }
    public void pokreni(PostavkeSingleton postavkeSingleton, FileLoader aktivnostiLoader) {
        System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
        
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String unos = "";
        String[] unosLista;
        while (true) {
            try{
                unos = reader.readLine();
                unos = unos.replaceAll("[^\\x20-\\x7f]", "").replace("\"", "").replace("\'", "");
                if(unos.matches("^(0; [0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2})|([1-3]; [0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2}; ([1-9]+[0-9]*|[0]); ([1-9]+[0-9]*|[0]); ([1-9]+[0-9]*|[0]))|(4; [0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2}; ([1-9]+[0-9]*|[0]); ([1-9]+[0-9]*|[0]); ([1-9]+[0-9]*|[0]); ([1-9]+[0-9]*|[0]); .+)|(4; [0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2}; ([1-9]+[0-9]*|[0]); ([1-9]+[0-9]*|[0]); ([1-9]+[0-9]*|[0]); ([1-9]+[0-9]*|[0]))|(5; ([\\w]|[:]|[\\\\]|[\\/]|[\\.]|[-])+.txt)|(6; struktura stanje ([1-9]+[0-9]*|[0]))|(6; struktura)|(7; struktura najam zarada ([0-9][0-9])\\.([0-9][0-9])\\.([0-9][0-9][0-9][0-9]) ([0-9][0-9])\\.([0-9][0-9])\\.([0-9][0-9][0-9][0-9]) ([1-9]+[0-9]*|[0]))|(7; struktura najam ([0-9][0-9])\\.([0-9][0-9])\\.([0-9][0-9][0-9][0-9]) ([0-9][0-9])\\.([0-9][0-9])\\.([0-9][0-9][0-9][0-9]) ([1-9]+[0-9]*|[0]))|(7; struktura ([0-9][0-9])\\.([0-9][0-9])\\.([0-9][0-9][0-9][0-9]) ([0-9][0-9])\\.([0-9][0-9])\\.([0-9][0-9][0-9][0-9])|(8; struktura rauni ([0-9][0-9])\\.([0-9][0-9])\\.([0-9][0-9][0-9][0-9]) ([0-9][0-9])\\.([0-9][0-9])\\.([0-9][0-9][0-9][0-9]) ([1-9]+[0-9]*|[0]))|(8; struktura ([0-9][0-9])\\.([0-9][0-9])\\.([0-9][0-9][0-9][0-9]) ([0-9][0-9])\\.([0-9][0-9])\\.([0-9][0-9][0-9][0-9]))|(9)|(10; ([1-9]+[0-9]*|[0]) ([0-9][0-9])\\.([0-9][0-9])\\.([0-9][0-9][0-9][0-9]) ([0-9][0-9])\\.([0-9][0-9])\\.([0-9][0-9][0-9][0-9]))|(11; ([1-9]+[0-9]*|[0]) ([1-9]+[0-9]*|[0]),([0-9][0-9])))$")){
                    unosLista = unos.split("; ");
                    String vrijeme="";
                    try{
                     vrijeme = unosLista[1];
                    }catch(Exception e){}
                    Date datum = null;
                    String[] parametri;
                    SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");

                    if(Integer.parseInt(unosLista[0])>=0 && Integer.parseInt(unosLista[0])<=4)
                        datum = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(vrijeme);

                    switch(Integer.parseInt(unosLista[0])){
                        case 0:
                                System.out.println("Program zavrsava u \u201e" + vrijeme+ "\u201c");
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

                                Osoba.unajmiVozilo(Osoba.dohvatiOsobuIzListe(PostavkeSingleton.getOsobaLista(),Integer.parseInt(unosLista[2])), vrijeme,
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
                            System.out.println("Izvrsavanje aktivnosti: " + Integer.parseInt(unosLista[0]) + "; "+unosLista[1]);
                            PostavkeSingleton.getAktivnostLista().clear();
                            File tmpDir = new File(unosLista[1]);
                            boolean exists = tmpDir.exists();
                            if(exists){
                                PostavkeSingleton.getAktivnostLista().clear();
                                aktivnostiLoader.parseFile(unosLista[1]);
                                postavkeSingleton.setNacinRadaSkupni(true);
                                //zapocniSkupniNacinRada(postavkeSingleton, aktivnostiLoader);
                                if(PostavkeSingleton.konfiguracija.containsKey("izlaz"))
                                    PostavkeSingleton.controller.updateView(new SkupniNacinKonzolaView(), aktivnostiLoader);
                                else PostavkeSingleton.controller.updateView(new SkupniNacinView(), aktivnostiLoader);

                            }
                            else {
                                System.out.println("Ne postoji datoteka na definiranoj lokaciji. Nastavak s interaktivnim radom");
                            }                          
                            break;                                
                        case 6:
                            parametri = unosLista[1].split(" ");
                            switch(parametri.length){
                                case 1:
                                    if("struktura".equals(parametri[0])){
                                        System.out.println("Izvrsavanje aktivnosti: " + Integer.parseInt(unosLista[0]) + "; "+unosLista[1]);
                                        Aktivnost.ispisiStrukturu(postavkeSingleton, -1, 0);
                                        Struktura.strukturePoRazinama.clear();
                                    }
                                    break;
                                case 3:
                                    if("struktura".equals(parametri[0]) &&"stanje".equals(parametri[1])){
                                        System.out.println("Izvrsavanje aktivnosti: " + unosLista[0] + "; "+parametri[0]+" "+parametri[1]+" "+parametri[2]);
                                        Aktivnost.ispisiStrukturu(postavkeSingleton, Integer.parseInt(parametri[2]), 1);
                                        Struktura.strukturePoRazinama.clear();
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
                                        System.out.println("Izvrsavanje aktivnosti: " + Integer.parseInt(unosLista[0]) + "; "+parametri[0] +" "+parametri[1] + " "+parametri[2]);
                                        Aktivnost.ispisiNajmove(postavkeSingleton,-1,0,0,format.parse(parametri[1]),format.parse(parametri[2]));                   
                                    }
                                    break;
                                case 5:
                                    if("struktura".equals(parametri[0]) &&"najam".equals(parametri[1]) && Integer.parseInt(parametri[4])!=-1 && format.parse(parametri[2])!=null && format.parse(parametri[3])!=null){
                                        System.out.println("Izvrsavanje aktivnosti: " + unosLista[0] + "; "+parametri[0]+" "+parametri[1]+" "+parametri[2]+" "+parametri[3]+" "+parametri[4]);
                                        Aktivnost.ispisiNajmove(postavkeSingleton,Integer.parseInt(parametri[4]),1,0,format.parse(parametri[2]),format.parse(parametri[3]));
                                    }
                                    break;
                                case 6:
                                    if("struktura".equals(parametri[0]) &&"najam".equals(parametri[1]) && "zarada".equals(parametri[2]) && Integer.parseInt(parametri[5])!=-1 && format.parse(parametri[3])!=null && format.parse(parametri[4])!=null){
                                        System.out.println("Izvrsavanje aktivnosti: " + unosLista[0] + "; "+parametri[0]+ " "+parametri[1]+ " "+parametri[2]+ " "+parametri[3]+" "+parametri[4]+" "+parametri[5]);
                                        Aktivnost.ispisiNajmove(postavkeSingleton,Integer.parseInt(parametri[5]),1,1,format.parse(parametri[3]),format.parse(parametri[4]));
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
                                        System.out.println("Izvrsavanje aktivnosti: " + Integer.parseInt(unosLista[0]) + "; "+parametri[0] + " "+parametri[1] + " "+parametri[2]);
                                        Aktivnost.ispisiRacune(postavkeSingleton,-1,0,format.parse(parametri[1]),format.parse(parametri[2]));
                                    }
                                    break;
                                case 5:
                                    if("struktura".equals(parametri[0]) &&"rauni".equals(parametri[1]) && Integer.parseInt(parametri[4])!=-1 && format.parse(parametri[2])!=null && format.parse(parametri[3])!=null){
                                        System.out.println("Izvrsavanje aktivnosti: " + unosLista[0] + "; "+parametri[0]+" "+"racuni"+" "+parametri[2]+" "+parametri[3]+" "+parametri[4]);
                                        Aktivnost.ispisiRacune(postavkeSingleton,Integer.parseInt(parametri[4]),1,format.parse(parametri[2]),format.parse(parametri[3]));
                                    }
                                    break;
                            }    
                            break;            
                        case 9:
                            System.out.println("Izvrsavanje aktivnosti: " + unosLista[0]);
                            Aktivnost.ispisiStanjeOsobe(postavkeSingleton);
                            break;
                        case 10:
                            parametri = unosLista[1].split(" ");
                            System.out.println("Izvrsavanje aktivnosti: " + unosLista[0]+"; "+parametri[0]+" "+parametri[1]+" "+parametri[2]);
                            Aktivnost.ispisiPodatkeRacuniKorisnika(postavkeSingleton, Integer.parseInt(parametri[0]), format.parse(parametri[1]), format.parse(parametri[2]));
                            break;

                        case 11:
                            parametri = unosLista[1].split(" ");
                            System.out.println("Izvrsavanje aktivnosti: " + unosLista[0]+"; "+parametri[0]+" "+Float.toString(Float.parseFloat(parametri[1].replace(",", "."))).replace(".",","));
                            Aktivnost.platiDugovanje(postavkeSingleton, Integer.parseInt(parametri[0]), Float.parseFloat(parametri[1].replace(",", ".")));
                            break;
                        default:
                            break;
                    }

                } else System.out.println("Nepoznata naredba - provjerite argumente");

            }catch(Exception e){

            }
        }
    }
}
