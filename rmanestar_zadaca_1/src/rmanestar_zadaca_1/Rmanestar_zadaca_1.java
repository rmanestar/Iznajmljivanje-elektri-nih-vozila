/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmanestar_zadaca_1;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Mane
 */
public class Rmanestar_zadaca_1 {

    public static void main(String[] args) {

        System.setProperty("file.encoding","UTF-8");
        
        FileLoader aktivnostiLoader = new AktivnostFileLoader();
        FileLoader cjenikLoader = new CjenikFileLoader();
        FileLoader vozilaLoader = new ElektricnoVoziloFileLoader();
        FileLoader kapacitetiLoader = new KapacitetFileLoader();
        FileLoader lokacijeLoader = new LokacijaFileLoader();
        FileLoader osobeLoader = new OsobaFileLoader();
        PostavkeSingleton postavkeSingleton = PostavkeSingleton.getInstance();

        try{
             postavkeSingleton.setPs(new PrintStream(System.out,true,"UTF-8"));
        }catch(Exception e){}
        
        
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

        for (int i = 0; i < ulazniArgumentiLista.length; i++) {
            String regExProvjera = "(-[v|l|c|k|o|s] ([\\w]|[:]|[\\\\]|[\\.])+.txt)|(-t [0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2})";
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
                        vozilaLoader.parseFile(putanja);
                        postavkeSingleton.setUcitanaVozila(true);
                    } else {
                        PostavkeSingleton.ps.println("Provjera NOT OK - 0");
                    }

                    break;

                case "-l":
                    if (provjera.matches(regExProvjera)) {
                        putanja = ulazniArgumentiLista[i + 1];
                        lokacijeLoader.parseFile(putanja);
                        postavkeSingleton.setUcitaneLokacije(true);

                    } else {
                        PostavkeSingleton.ps.println("Provjera NOT OK - 1");
                    }

                    break;

                case "-c":
                    if (provjera.matches(regExProvjera)) {
                        putanja = ulazniArgumentiLista[i + 1];
                        cjenikLoader.parseFile(putanja);
                        postavkeSingleton.setUcitanCjenik(true);
                    } else {
                        PostavkeSingleton.ps.println("Provjera NOT OK - 2");
                    }

                    break;

                case "-k":
                    if (provjera.matches(regExProvjera)) {
                        putanja = ulazniArgumentiLista[i + 1];
                        kapacitetiLoader.parseFile(putanja);
                        postavkeSingleton.setUcitaniKapaciteti(true);
                    } else {
                        PostavkeSingleton.ps.println("Provjera NOT OK - 3");
                    }

                    break;

                case "-o":
                    if (provjera.matches(regExProvjera)) {
                        putanja = ulazniArgumentiLista[i + 1];
                        osobeLoader.parseFile(putanja);
                        postavkeSingleton.setUcitaneOsobe(true);
                    } else {
                        PostavkeSingleton.ps.println("Provjera NOT OK - 4");
                    }

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
                        } else {
                            PostavkeSingleton.ps.println("Provjera NOT OK - 5");
                        }
                    } catch (Exception e) {
                        PostavkeSingleton.ps.println(e.toString());
                    }
                    break;

                case "-s":
                    if (provjera.matches(regExProvjera)) {
                        putanja = ulazniArgumentiLista[i + 1];

                        aktivnostiLoader.parseFile(putanja);
                        postavkeSingleton.setUcitaneAktivnosti(true);
                    } else {
                        PostavkeSingleton.ps.println("Provjera NOT OK - 6");
                    }

                    break;
            }

        }
        
        if(postavkeSingleton.provjeriPostavke()){
            for(Lokacija lokacija : PostavkeSingleton.getLokacijaLista()){
            lokacija.popuniKapacitete();
            }           
        }
        
        if((postavkeSingleton.isUcitanCjenik()&&postavkeSingleton.isUcitanaVozila()&&postavkeSingleton.isUcitanaVozila()
                &&postavkeSingleton.isUcitaneLokacije()&&postavkeSingleton.isUcitaneOsobe()&&postavkeSingleton.isUcitaniKapaciteti()
                &&postavkeSingleton.isUcitanoVirtualnoVrijeme()&&postavkeSingleton.isUcitaneAktivnosti())||
                (postavkeSingleton.isUcitanCjenik()&&postavkeSingleton.isUcitanaVozila()&&postavkeSingleton.isUcitanaVozila()
                &&postavkeSingleton.isUcitaneLokacije()&&postavkeSingleton.isUcitaneOsobe()&&postavkeSingleton.isUcitaniKapaciteti()
                &&postavkeSingleton.isUcitanoVirtualnoVrijeme())){
        
        String unos = "";
        String[] unosLista;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        if (!postavkeSingleton.isNacinRadaSkupni()) {
            postavkeSingleton.getPs().println("\nZapočinje interaktivni način rada!\n");

            while (true) {
                try{
                    unos = reader.readLine();
                    unos = unos.replaceAll("[^\\x20-\\x7f]", "");
                    if(unos.matches("^(0; [0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2})|([1-3]; [0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2}; "
                            + "([1-9]+[0-9]*|[0]); ([1-9]+[0-9]*|[0]); ([1-9]+[0-9]*|[0]))|(4; [0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2}; "
                            + "([1-9]+[0-9]*|[0]); ([1-9]+[0-9]*|[0]); ([1-9]+[0-9]*|[0]); ([1-9]+[0-9]*|[0]))$")){
                        unosLista = unos.split("; ");
                        String vrijeme = unosLista[1];
                        switch(Integer.parseInt(unosLista[0])){
                            case 0:
                                    PostavkeSingleton.ps.println("Program završava u \u201e" + vrijeme+ "\u201c");
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
                                            Lokacija.dohvatiLokacijuIzListe(PostavkeSingleton.getLokacijaLista(), Integer.parseInt(unosLista[3])));
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

                                    Osoba.vratiVozilo(Osoba.dohvatiOsobuIzListe(PostavkeSingleton.getOsobaLista(), Integer.parseInt(unosLista[2])), vrijeme,
                                            ElektricnoVozilo.dohvatiVrstuVozilaIzListe(PostavkeSingleton.getElektricnoVoziloLista(), Integer.parseInt(unosLista[4])), 
                                            Lokacija.dohvatiLokacijuIzListe(PostavkeSingleton.getLokacijaLista(), Integer.parseInt(unosLista[3])),Integer.parseInt(unosLista[5]));
                                     ElektricnoVozilo.azurirajStanjeBaterija(vrijeme);

                                break;
                            default:
                                break;
                        }
                        
                    } else PostavkeSingleton.ps.println("Nepoznata naredba - provjerite argumente");
                    
                    
                }catch(Exception e){
                    
                }

            }
        } else if (postavkeSingleton.isNacinRadaSkupni()) {
            postavkeSingleton.getPs().println("\nZapočinje skupni način rada!\n");
            SimpleDateFormat vrijemeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            for(Aktivnost a: PostavkeSingleton.getAktivnostLista()){
                String vrijeme = vrijemeFormat.format(a.getVrijeme());
                switch(a.getId()){
                            case 0:
                                    PostavkeSingleton.ps.println("Izvršavanje aktivnosti: " + a.getId() + "; " +vrijemeFormat.format(a.getVrijeme()));                                 
                                    PostavkeSingleton.ps.println("Program završava u \u201e" + vrijeme+ "\u201c");
                                    System.exit(0);
                                break;
                            case 1:         
                                    ElektricnoVozilo.azurirajStanjeBaterija(vrijeme);

                                    PostavkeSingleton.ps.println("Izvršavanje aktivnosti: " + a.getId() + "; \u201e" +vrijemeFormat.format(a.getVrijeme())+"\u201c; "+a.getKorisnik()+"; "+a.getLokacija()+"; "+a.getVrstaVozila());

                                    
                                     Aktivnost.dohvatiBrojVozilaVrsteNaLokaciji(Osoba.dohvatiOsobuIzListe(PostavkeSingleton.getOsobaLista(),
                                             a.getKorisnik()), vrijeme, Lokacija.dohvatiLokacijuIzListe(PostavkeSingleton.getLokacijaLista(),a.getLokacija()), 
                                             ElektricnoVozilo.dohvatiVrstuVozilaIzListe(PostavkeSingleton.getElektricnoVoziloLista(), a.getVrstaVozila()));
                                    ElektricnoVozilo.azurirajStanjeBaterija(vrijeme);

                                     break;
                                
                            case 2:
                                    ElektricnoVozilo.azurirajStanjeBaterija(vrijeme);

                                    PostavkeSingleton.ps.println("Izvršavanje aktivnosti: " + a.getId() + "; \u201e" +vrijemeFormat.format(a.getVrijeme())+"\u201c; "+a.getKorisnik()+"; "+a.getLokacija()+"; "+a.getVrstaVozila());

                                    Osoba.iznajmiVozilo(Osoba.dohvatiOsobuIzListe(PostavkeSingleton.getOsobaLista(),a.getKorisnik()), vrijeme,
                                            ElektricnoVozilo.dohvatiVrstuVozilaIzListe(PostavkeSingleton.getElektricnoVoziloLista(), a.getVrstaVozila()), 
                                            Lokacija.dohvatiLokacijuIzListe(PostavkeSingleton.getLokacijaLista(), a.getLokacija()));
                                    ElektricnoVozilo.azurirajStanjeBaterija(vrijeme);

                                    break;
                                
                            case 3:
                                    ElektricnoVozilo.azurirajStanjeBaterija(vrijeme);

                                    PostavkeSingleton.ps.println("Izvršavanje aktivnosti: " + a.getId() + "; \u201e" +vrijemeFormat.format(a.getVrijeme())+"\u201c; "+a.getKorisnik()+"; "+a.getLokacija()+"; "+a.getVrstaVozila());

                                    Aktivnost.brojRaspolozivihMjestaVrsteVozilaNaLokaciji(Osoba.dohvatiOsobuIzListe(PostavkeSingleton.getOsobaLista(),
                                             a.getKorisnik()), vrijeme, Lokacija.dohvatiLokacijuIzListe(PostavkeSingleton.getLokacijaLista(),a.getLokacija()), 
                                             ElektricnoVozilo.dohvatiVrstuVozilaIzListe(PostavkeSingleton.getElektricnoVoziloLista(), a.getVrstaVozila()));
                                    ElektricnoVozilo.azurirajStanjeBaterija(vrijeme);

                                    break;
                                
                            case 4:
                                    ElektricnoVozilo.azurirajStanjeBaterija(vrijeme);

                                    PostavkeSingleton.ps.println("Izvršavanje aktivnosti: " + a.getId() + "; \u201e" +vrijemeFormat.format(a.getVrijeme())+"\u201c; "+a.getKorisnik()+"; "+a.getLokacija()+"; "+a.getVrstaVozila()+"; "+a.getBrojKM());
                                    Osoba.vratiVozilo(Osoba.dohvatiOsobuIzListe(PostavkeSingleton.getOsobaLista(), a.getKorisnik()), vrijeme,
                                            ElektricnoVozilo.dohvatiVrstuVozilaIzListe(PostavkeSingleton.getElektricnoVoziloLista(), a.getVrstaVozila()), 
                                            Lokacija.dohvatiLokacijuIzListe(PostavkeSingleton.getLokacijaLista(), a.getLokacija()),a.getBrojKM());
                                    ElektricnoVozilo.azurirajStanjeBaterija(vrijeme);
                                break;
                            default:
                                break;
                        
                }
                
            }
            Date trenutno = new Date();
            PostavkeSingleton.ps.println("Izvršavanje aktivnosti: 0; \u201e" +vrijemeFormat.format(trenutno)+"\u201c");
            PostavkeSingleton.ps.println("Program završava u \u201e" + vrijemeFormat.format(trenutno)+ "\u201c");
            System.exit(0);
            
        }

    }else PostavkeSingleton.ps.println("Nisu učitane datoteke potrebne za rad");
        
    }
}
