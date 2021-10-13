/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmanestar_zadaca_3.MVC;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
public class SkupniNacinAbstract {
     
    public void pokreni(PostavkeSingleton postavkeSingleton, FileLoader aktivnostiLoader) throws ParseException{       
        SimpleDateFormat vrijemeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
        for(Aktivnost a: PostavkeSingleton.getAktivnostLista()){
            String vrijeme ="";
            if(a.getId()<=4)
                vrijeme = vrijemeFormat.format(a.getVrijeme());
            
            switch(a.getId()){
                case 0:
                    System.out.println("Izvrsavanje aktivnosti: " + a.getId() + "; " +vrijemeFormat.format(a.getVrijeme()));                                 
                    System.out.println("Program zavrsava u \u201e" + vrijeme+ "\u201c");
                    System.exit(0);
                    break;
                case 1:         
                    ElektricnoVozilo.azurirajStanjeBaterija(vrijeme);

                    System.out.println("Izvrsavanje aktivnosti: " + a.getId() + "; \u201e" +vrijemeFormat.format(a.getVrijeme())+"\u201c; "+a.getKorisnik()+"; "+a.getLokacija()+"; "+a.getVrstaVozila());

                    Aktivnost.dohvatiBrojVozilaVrsteNaLokaciji(Osoba.dohvatiOsobuIzListe(PostavkeSingleton.getOsobaLista(),
                             a.getKorisnik()), vrijeme, Lokacija.dohvatiLokacijuIzListe(PostavkeSingleton.getLokacijaLista(),a.getLokacija()), 
                             ElektricnoVozilo.dohvatiVrstuVozilaIzListe(PostavkeSingleton.getElektricnoVoziloLista(), a.getVrstaVozila()));
                    ElektricnoVozilo.azurirajStanjeBaterija(vrijeme);
                    break;
                case 2:
                    ElektricnoVozilo.azurirajStanjeBaterija(vrijeme);

                    System.out.println("Izvrsavanje aktivnosti: " + a.getId() + "; \u201e" +vrijemeFormat.format(a.getVrijeme())+"\u201c; "+a.getKorisnik()+"; "+a.getLokacija()+"; "+a.getVrstaVozila());

                    Osoba.unajmiVozilo(Osoba.dohvatiOsobuIzListe(PostavkeSingleton.getOsobaLista(),a.getKorisnik()), vrijeme,
                            ElektricnoVozilo.dohvatiVrstuVozilaIzListe(PostavkeSingleton.getElektricnoVoziloLista(), a.getVrstaVozila()), 
                            Lokacija.dohvatiLokacijuIzListe(PostavkeSingleton.getLokacijaLista(), a.getLokacija()),a.getVrijeme());
                    ElektricnoVozilo.azurirajStanjeBaterija(vrijeme);
                    break;
                case 3:
                    ElektricnoVozilo.azurirajStanjeBaterija(vrijeme);

                    System.out.println("Izvrsavanje aktivnosti: " + a.getId() + "; \u201e" +vrijemeFormat.format(a.getVrijeme())+"\u201c; "+a.getKorisnik()+"; "+a.getLokacija()+"; "+a.getVrstaVozila());

                    Aktivnost.brojRaspolozivihMjestaVrsteVozilaNaLokaciji(Osoba.dohvatiOsobuIzListe(PostavkeSingleton.getOsobaLista(),
                             a.getKorisnik()), vrijeme, Lokacija.dohvatiLokacijuIzListe(PostavkeSingleton.getLokacijaLista(),a.getLokacija()), 
                             ElektricnoVozilo.dohvatiVrstuVozilaIzListe(PostavkeSingleton.getElektricnoVoziloLista(), a.getVrstaVozila()));
                    ElektricnoVozilo.azurirajStanjeBaterija(vrijeme);
                    break;
                case 4:
                    ElektricnoVozilo.azurirajStanjeBaterija(vrijeme);
                    //System.out.println(a.getOpis());
                    if(a.getOpis()==""){
                        System.out.println("Izvrsavanje aktivnosti: " + a.getId() + "; \u201e" +vrijemeFormat.format(a.getVrijeme())+"\u201c; "+a.getKorisnik()+"; "+a.getLokacija()+"; "+a.getVrstaVozila()+"; "+a.getBrojKM());
                        Osoba.vratiVozilo(Osoba.dohvatiOsobuIzListe(PostavkeSingleton.getOsobaLista(), a.getKorisnik()), vrijeme,
                        ElektricnoVozilo.dohvatiVrstuVozilaIzListe(PostavkeSingleton.getElektricnoVoziloLista(), a.getVrstaVozila()), 
                        Lokacija.dohvatiLokacijuIzListe(PostavkeSingleton.getLokacijaLista(), a.getLokacija()),a.getBrojKM(),a.getVrijeme());
                    }else if(a.getOpis().length()>0){
                        System.out.println("Izvrsavanje aktivnosti: " + a.getId() + "; \u201e" +vrijemeFormat.format(a.getVrijeme())+"\u201c; "+a.getKorisnik()+"; "+a.getLokacija()+"; "+a.getVrstaVozila()+"; "+a.getBrojKM()+"; "+a.getOpis());
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
                        System.out.println("Izvrsavanje aktivnosti: " + a.getId() + "; "+a.getParametar1()+" "+a.getParametar2()+" "+a.getIdOrgJedinice());
                        Aktivnost.ispisiStrukturu(postavkeSingleton, a.getIdOrgJedinice(), 1);
                        Struktura.strukturePoRazinama.clear();
                    }
                    else if("struktura".equals(a.getParametar1()) && a.getIdOrgJedinice()==-1){
                        System.out.println("Izvrsavanje aktivnosti: " + a.getId() + "; "+a.getParametar1());
                        Aktivnost.ispisiStrukturu(postavkeSingleton, -1, 0);
                        Struktura.strukturePoRazinama.clear();
                    }
                    break;                              
                case 7:
                    if("struktura".equals(a.getParametar1()) &&"najam".equals(a.getParametar2()) && "zarada".equals(a.getParametar3()) && a.getIdOrgJedinice()!=-1 && a.getPocetniDatum()!=null && a.getKrajnjiDatum()!=null){
                        System.out.println("Izvrsavanje aktivnosti: " + a.getId() + "; "+a.getParametar1()+ " "+a.getParametar2()+ " "+a.getParametar3()+ " "+new SimpleDateFormat("dd.MM.yyyy").format(a.getPocetniDatum())+" "+new SimpleDateFormat("dd.MM.yyyy").format(a.getKrajnjiDatum())+ " "+a.getIdOrgJedinice());
                        Aktivnost.ispisiNajmove(postavkeSingleton,a.getIdOrgJedinice(),1,1,a.getPocetniDatum(),a.getKrajnjiDatum());
                    }
                    else if("struktura".equals(a.getParametar1()) &&"najam".equals(a.getParametar2()) && a.getIdOrgJedinice()!=-1 && a.getPocetniDatum()!=null && a.getKrajnjiDatum()!=null){
                        System.out.println("Izvrsavanje aktivnosti: " + a.getId() + "; "+a.getParametar1()+ " "+a.getParametar2()+ " "+new SimpleDateFormat("dd.MM.yyyy").format(a.getPocetniDatum())+" "+new SimpleDateFormat("dd.MM.yyyy").format(a.getKrajnjiDatum())+ " "+a.getIdOrgJedinice());
                        Aktivnost.ispisiNajmove(postavkeSingleton,a.getIdOrgJedinice(),1,0,a.getPocetniDatum(),a.getKrajnjiDatum());
                    }
                    else if("struktura".equals(a.getParametar1()) && a.getPocetniDatum()!=null && a.getKrajnjiDatum()!=null){
                        System.out.println("Izvrsavanje aktivnosti: " + a.getId() + "; "+a.getParametar1()+ " "+new SimpleDateFormat("dd.MM.yyyy").format(a.getPocetniDatum())+" "+new SimpleDateFormat("dd.MM.yyyy").format(a.getKrajnjiDatum()));
                        Aktivnost.ispisiNajmove(postavkeSingleton,-1,0,0,a.getPocetniDatum(),a.getKrajnjiDatum());                   
                    }
                    break;                              
                case 8:
                    if("struktura".equals(a.getParametar1()) &&"rauni".equals(a.getParametar2()) && a.getIdOrgJedinice()!=-1 && a.getPocetniDatum()!=null && a.getKrajnjiDatum()!=null){
                        System.out.println("Izvrsavanje aktivnosti: " + a.getId() + "; "+a.getParametar1()+ " "+"racuni"+ " "+new SimpleDateFormat("dd.MM.yyyy").format(a.getPocetniDatum())+" "+new SimpleDateFormat("dd.MM.yyyy").format(a.getKrajnjiDatum()));
                        Aktivnost.ispisiRacune(postavkeSingleton,a.getIdOrgJedinice(),1,a.getPocetniDatum(),a.getKrajnjiDatum());
                    }
                    else if("struktura".equals(a.getParametar1()) && a.getPocetniDatum()!=null && a.getKrajnjiDatum()!=null){
                        System.out.println("Izvrsavanje aktivnosti: " + a.getId() + "; "+a.getParametar1()+ " "+new SimpleDateFormat("dd.MM.yyyy").format(a.getPocetniDatum())+" "+new SimpleDateFormat("dd.MM.yyyy").format(a.getKrajnjiDatum()));
                        Aktivnost.ispisiRacune(postavkeSingleton,-1,0,a.getPocetniDatum(),a.getKrajnjiDatum()); 
                    }
                    break;
                case 9:
                    System.out.println("Izvrsavanje aktivnosti: " + a.getId());

                    Aktivnost.ispisiStanjeOsobe(postavkeSingleton);
                    break;
                
                case 10:
                    System.out.println("Izvrsavanje aktivnosti: " + a.getId() + "; "+a.getKorisnik()+" " + new SimpleDateFormat("dd.MM.yyyy").format(a.getPocetniDatum())+" "+new SimpleDateFormat("dd.MM.yyyy").format(a.getKrajnjiDatum()));

                    Aktivnost.ispisiPodatkeRacuniKorisnika(postavkeSingleton,a.getKorisnik(),a.getPocetniDatum(),a.getKrajnjiDatum());
                    break;
                    
                case 11:
                    System.out.println("Izvrsavanje aktivnosti: " + a.getId() + "; "+a.getKorisnik()+" " + Float.toString(a.getIznos()).replace(".", ","));

                    Aktivnost.platiDugovanje(postavkeSingleton, a.getKorisnik(), a.getIznos());      
                    break;
                default:
                    break;
            }
        }

        postavkeSingleton.setNacinRadaSkupni(false);
        PostavkeSingleton.controller.updateView(new InteraktivniNacinView(), aktivnostiLoader);
    }
}
