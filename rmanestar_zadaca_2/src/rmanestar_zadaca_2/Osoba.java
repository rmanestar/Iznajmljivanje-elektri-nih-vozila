/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmanestar_zadaca_2;

import rmanestar_zadaca_2.Singleton.PostavkeSingleton;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Mane
 */

//popravit iznajmljivanje s listom vozila

public class Osoba {
    private int id;
    private String imePrezime;
    private boolean aktivniNajam = false;
    private ArrayList<ElektricnoVozilo> iznajmljenaVozila = new ArrayList<ElektricnoVozilo>();

    private int vracenaVozilaNeispravnoStanje = 0;
    
    public static int iznajmiVozilo(Osoba osoba, String vrijeme, ElektricnoVozilo vrstaVozila, Lokacija lokacijaSifra, Date datum){
        if(osoba==null || vrstaVozila==null || lokacijaSifra==null){
            PostavkeSingleton.ps.println("ERROR ne moze se iznajmiti vozilo na temelju danih parametara\n");
            return -1;         
        }
        
        for(ElektricnoVozilo ev : osoba.iznajmljenaVozila){
            if(ev.getId()==vrstaVozila.getId()){
                PostavkeSingleton.ps.println("Osoba je vec iznajmila vozilo ove vrste!\n");
                return -1;
            }               
        }
        
        Date novoVirtualnoVrijeme =null;
        try{
            novoVirtualnoVrijeme = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(vrijeme);
        }catch(Exception e){
            return -1;
        }
        
        if(novoVirtualnoVrijeme.compareTo(PostavkeSingleton.virtualnoVrijeme)<=0){
            PostavkeSingleton.ps.println("Akcija ne smije imati vrijeme izvrsavanja koje se odvija prije trenutnog virtualnog vremena\n");
            return -1;
        } else PostavkeSingleton.virtualnoVrijeme=novoVirtualnoVrijeme;
           
        int idVoziloZaBrisanje =0;
        ElektricnoVozilo evZaIznajmiti = null;
        ArrayList<ElektricnoVozilo> raspolozivaVozilaSPunomBaterijomNaLokaciji = new ArrayList<ElektricnoVozilo>();
              
        for(Lokacija l : PostavkeSingleton.getLokacijaLista()){
            for(KapacitetLokacije kl: l.getKapacitetLokacije()){
                for(ElektricnoVozilo rv: kl.getListaRaspolozivihVozila()){
                    if(vrstaVozila.idVrstaVozila==rv.idVrstaVozila && rv.baterija==100 && l.getId()==lokacijaSifra.getId() && rv.isJeNeispravno()==false){
                        raspolozivaVozilaSPunomBaterijomNaLokaciji.add(rv);

                    }
                }
            }
        }
        
        for(ElektricnoVozilo ev : raspolozivaVozilaSPunomBaterijomNaLokaciji){
            if(evZaIznajmiti==null)
                evZaIznajmiti=ev;
            if(ev.getBrojNajmova()<evZaIznajmiti.getBrojNajmova())
                evZaIznajmiti=ev;
            if(ev.getBrojNajmova()==evZaIznajmiti.getBrojNajmova()){
                if(ev.getBrKm()<evZaIznajmiti.getBrKm())
                    evZaIznajmiti=ev;
                if(ev.getBrKm()==evZaIznajmiti.getBrKm()){
                    if(ev.getJIDVozila()<evZaIznajmiti.getJIDVozila())
                        evZaIznajmiti=ev;
                }
            }                  
        }
        
        if(evZaIznajmiti!=null){
            osoba.iznajmljenaVozila.add(evZaIznajmiti);
            idVoziloZaBrisanje = KapacitetLokacije.listaRaspoloziveVrsteVozilaNaLokaciji(lokacijaSifra.getId(),vrstaVozila.getId()).indexOf(evZaIznajmiti);       
        }       
        
        PostavkeSingleton.ps.println("U "+"\u201e" + vrijeme+ "\u201c korisnik "+osoba.imePrezime+" trazi na lokaciji "+lokacijaSifra.getNazivLokacije()+ " najam za vrstu vozila "+vrstaVozila.getNaziv());
        if(evZaIznajmiti!=null && !KapacitetLokacije.listaRaspoloziveVrsteVozilaNaLokaciji(lokacijaSifra.getId(),vrstaVozila.getId()).isEmpty()){
           ArrayList<ElektricnoVozilo> el = KapacitetLokacije.listaRaspoloziveVrsteVozilaNaLokaciji(lokacijaSifra.getId(),vrstaVozila.getId());
           PostavkeSingleton.getNajamLista().add(new Najam(datum,vrstaVozila.getId(),osoba.getId(),lokacijaSifra.getId()));
           el.remove(idVoziloZaBrisanje);
           PostavkeSingleton.ps.println("Vozilo iznajmljeno!\n");
           return 1;
        } else {
            PostavkeSingleton.ps.println("Vozilo nije raspolozivo\n");
            return -1;
        }        
    }
    
    public static void vratiVozilo(Osoba osoba, String vrijeme, ElektricnoVozilo vrstaVozila, Lokacija lokacijaSifra, int brojKm, Date datumIzdavanja){
        if(osoba==null || vrstaVozila==null || lokacijaSifra==null){
            PostavkeSingleton.ps.println("ERROR ne moze se vratiti vozilo a temelju danih parametara\n");
            return;         
        }
        
        if(osoba.iznajmljenaVozila==null){
            PostavkeSingleton.ps.println("Osoba nije iznajmila niti jedno vozilo!\n");
            return;
        } 
        
        boolean imaAktivanNajamZaVrstu = false;
        ElektricnoVozilo iznajmljenoVozilo = null;
        int evIndexLista = -1;
        for(ElektricnoVozilo ev : osoba.iznajmljenaVozila){
            if(ev.getId()==vrstaVozila.getId()){
                imaAktivanNajamZaVrstu = true;
                iznajmljenoVozilo = ev;
                evIndexLista = osoba.iznajmljenaVozila.indexOf(ev);
                break;
            }                              
        }
        
        if(!imaAktivanNajamZaVrstu || iznajmljenoVozilo==null){
            PostavkeSingleton.ps.println("Osoba nema iznajmljeno vozilo navedene vrste!\n");
            return;
        }      
      
        Date novoVirtualnoVrijeme = null;
        Date staroVirtualnoVrijeme = PostavkeSingleton.virtualnoVrijeme;
        
  
        try{
            novoVirtualnoVrijeme = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(vrijeme);
        }catch(Exception e){
            return;
        }      
  
        if(novoVirtualnoVrijeme.compareTo(PostavkeSingleton.virtualnoVrijeme)<=0){
            PostavkeSingleton.ps.println("Akcija ne smije imati vrijeme izvrsavanja koje se odvija prije trenutnog virtualnog vremena\n");
            return;
        } else PostavkeSingleton.virtualnoVrijeme=novoVirtualnoVrijeme;
        
        if(brojKm>iznajmljenoVozilo.domet){
            PostavkeSingleton.ps.println("Broj kilometara koje je vozilo preslo ne smije biti vece od maksimalnog dometa vozila\n!");
            return;
        }
        
        SimpleDateFormat vrijemeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
        if(KapacitetLokacije.dohvatiKapacitetVrsteVozilaZaLokaciju(lokacijaSifra.getId(), iznajmljenoVozilo.getId()).getMjestaZaPunjenje() 
                > KapacitetLokacije.listaRaspoloziveVrsteVozilaNaLokaciji(lokacijaSifra.getId(),iznajmljenoVozilo.getId()).size()){
            Cjenik cjenik = Cjenik.dohvatiCjenikZaVozilo(vrstaVozila.idVrstaVozila);
            int prethodnoStanjeKm = iznajmljenoVozilo.getBrKm();
            int novoStanje = iznajmljenoVozilo.getBrKm()+ brojKm;
            float izracunSati =((float)novoVirtualnoVrijeme.getTime()-(float)staroVirtualnoVrijeme.getTime())/3600000;
            int brojSati = (int)Math.ceil(izracunSati);
            float najamUkupno =(cjenik.getNajam()+(cjenik.getPoSatu()*brojSati)+(brojKm*cjenik.getPoKM()));
            
            Najam pocetniNajam = Najam.dohavatiNajamSPocetnimDatumom(osoba.getId(),vrstaVozila.getId() ,lokacijaSifra.getId());
            if(pocetniNajam!=null){
                PostavkeSingleton.getNajamLista().get(PostavkeSingleton.getNajamLista().indexOf(pocetniNajam)).setKrajNajma(datumIzdavanja);
                PostavkeSingleton.getNajamLista().get(PostavkeSingleton.getNajamLista().indexOf(pocetniNajam)).setZarada(najamUkupno);
            }else {
                PostavkeSingleton.ps.println("Osoba nema iznajmljeno vozilo ove vrste!\n");
                return;
            }
            
            String opis ="Stavke racuna su: 1 najam "+vrstaVozila.getNaziv()+ 
                    " - "+cjenik.getNajam()+" kn, najam je bio " +brojSati+" sat/a - "+brojSati+" * "+cjenik.getPoSatu()+" = "+
                    brojSati*cjenik.getPoSatu()+" kn, prethodno stanje bilo je "+prethodnoStanjeKm+" km znaci da je prosao "+brojKm+" km - "+
                    brojKm+" * "+cjenik.getPoKM()+" = "+brojKm*cjenik.getPoKM()+" kn. Racun ukupno iznosi "+cjenik.getNajam()+" kn + "+
                    brojSati*cjenik.getPoSatu()+" kn + "+brojKm*cjenik.getPoKM()+" = "+(cjenik.getNajam()+(cjenik.getPoSatu()*brojSati)+(brojKm*cjenik.getPoKM()))+" kn";
            
            
            PostavkeSingleton.ps.println("U "+ "\u201e" + vrijeme+ "\u201c korisnik "+osoba.getImePrezime()+" vraca unajmljeni "
                    +vrstaVozila.getNaziv()+" koji ima ukupno " +novoStanje+" km. \n"+opis);
            
            
            PostavkeSingleton.getRacunLista().add(new Racun(najamUkupno,osoba.getId(),lokacijaSifra.getId(),datumIzdavanja,opis));
                   
            
            iznajmljenoVozilo.setBrKm(novoStanje);
            float baterijaIzracun = iznajmljenoVozilo.getBaterija()-(((float)brojKm/(float)iznajmljenoVozilo.getDomet())*100);
            iznajmljenoVozilo.setBaterija((int)baterijaIzracun);         
            PostavkeSingleton.ps.println("Stanje baterije: "+iznajmljenoVozilo.getBaterija() + " %");
            float potrebneSekundeDoPuneBaterije = ((((float)iznajmljenoVozilo.vrijemePunjenja*3600000)*((float)(100-iznajmljenoVozilo.getBaterija())/100)));
            long voziloPunoNakonMiliSec = (novoVirtualnoVrijeme.getTime()+ (long)potrebneSekundeDoPuneBaterije);
            Date voziloPunoNakon = new Date(voziloPunoNakonMiliSec);
            iznajmljenoVozilo.puniSeDo=voziloPunoNakon;
            PostavkeSingleton.ps.println("Vozilo ce biti upotpunosti napunjeno u: "+vrijemeFormat.format(iznajmljenoVozilo.puniSeDo));

            KapacitetLokacije.listaRaspoloziveVrsteVozilaNaLokaciji(lokacijaSifra.getId(),iznajmljenoVozilo.getId()).add(iznajmljenoVozilo);
            PostavkeSingleton.ps.println("Vozilo vraceno na lokaciju: "+lokacijaSifra.getId()+" - "+lokacijaSifra.getNazivLokacije()+"\n");
            osoba.iznajmljenaVozila.remove(evIndexLista);
           // osoba.iznajmljenoVozilo=null;
           // osoba.aktivniNajam=false;
        } else PostavkeSingleton.ps.println("Vozilo se ne moze vratiti na lokaciju - nedovoljan broj za punjenje");
                                   
    }
    
    public static void vratiNeispravnoVozilo(Osoba osoba, String vrijeme, ElektricnoVozilo vrstaVozila, Lokacija lokacijaSifra, int brojKm, String opisNeispravnosti, Date datumIzdavanja){
        if(osoba==null || vrstaVozila==null || lokacijaSifra==null){
            PostavkeSingleton.ps.println("ERROR ne moze se vratiti vozilo a temelju danih parametara\n");
            return;         
        }
        
        if(osoba.iznajmljenaVozila==null){
            PostavkeSingleton.ps.println("Osoba nije iznajmila niti jedno vozilo!\n");
            return;
        } 
        
        boolean imaAktivanNajamZaVrstu = false;
        ElektricnoVozilo iznajmljenoVozilo = null;
        int evIndexLista = -1;
        for(ElektricnoVozilo ev : osoba.iznajmljenaVozila){
            if(ev.getId()==vrstaVozila.getId()){
                imaAktivanNajamZaVrstu = true;
                iznajmljenoVozilo = ev;
                evIndexLista = osoba.iznajmljenaVozila.indexOf(ev);
                break;
            }                              
        }
        
        if(!imaAktivanNajamZaVrstu || iznajmljenoVozilo==null){
            PostavkeSingleton.ps.println("Osoba nema iznajmljeno vozilo navedene vrste!\n");
            return;
        }      
      
        Date novoVirtualnoVrijeme = null;
        Date staroVirtualnoVrijeme = PostavkeSingleton.virtualnoVrijeme;
        try{
            novoVirtualnoVrijeme = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(vrijeme);
        }catch(Exception e){
            return;
        }      
  
        if(novoVirtualnoVrijeme.compareTo(PostavkeSingleton.virtualnoVrijeme)<=0){
            PostavkeSingleton.ps.println("Akcija ne smije imati vrijeme izvrsavanja koje se odvija prije trenutnog virtualnog vremena\n");
            return;
        } else PostavkeSingleton.virtualnoVrijeme=novoVirtualnoVrijeme;
        
        if(brojKm>iznajmljenoVozilo.domet){
            PostavkeSingleton.ps.println("Broj kilometara koje je vozilo preslo ne smije biti vece od maksimalnog dometa vozila\n!");
            return;
        }
        
        SimpleDateFormat vrijemeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
        if(KapacitetLokacije.dohvatiKapacitetVrsteVozilaZaLokaciju(lokacijaSifra.getId(), iznajmljenoVozilo.getId()).getMjestaZaPunjenje() 
                > KapacitetLokacije.listaRaspoloziveVrsteVozilaNaLokaciji(lokacijaSifra.getId(),iznajmljenoVozilo.getId()).size()){
            Cjenik cjenik = Cjenik.dohvatiCjenikZaVozilo(vrstaVozila.idVrstaVozila);
            int prethodnoStanjeKm = iznajmljenoVozilo.getBrKm();
            int novoStanje = iznajmljenoVozilo.getBrKm()+ brojKm;
            float izracunSati =((float)novoVirtualnoVrijeme.getTime()-(float)staroVirtualnoVrijeme.getTime())/3600000;
            int brojSati = (int)Math.ceil(izracunSati);
            float najamUkupno =(cjenik.getNajam()+(cjenik.getPoSatu()*brojSati)+(brojKm*cjenik.getPoKM()));
            Najam pocetniNajam = Najam.dohavatiNajamSPocetnimDatumom(osoba.getId(),vrstaVozila.getId() ,lokacijaSifra.getId());
            if(pocetniNajam!=null){
                PostavkeSingleton.getNajamLista().get(PostavkeSingleton.getNajamLista().indexOf(pocetniNajam)).setKrajNajma(datumIzdavanja);
                PostavkeSingleton.getNajamLista().get(PostavkeSingleton.getNajamLista().indexOf(pocetniNajam)).setZarada(najamUkupno);
            }else {
                PostavkeSingleton.ps.println("Osoba nema iznajmljeno vozilo ove vrste!\n");
                return;
            }
            String opis ="Stavke racuna su: 1 najam "+vrstaVozila.getNaziv()+ 
                    " - "+cjenik.getNajam()+" kn, najam je bio " +brojSati+" sat/a - "+brojSati+" * "+cjenik.getPoSatu()+" = "+
                    brojSati*cjenik.getPoSatu()+" kn, prethodno stanje bilo je "+prethodnoStanjeKm+" km znaci da je prosao "+brojKm+" km - "+
                    brojKm+" * "+cjenik.getPoKM()+" = "+brojKm*cjenik.getPoKM()+" kn. Racun ukupno iznosi "+cjenik.getNajam()+" kn + "+
                    brojSati*cjenik.getPoSatu()+" kn + "+brojKm*cjenik.getPoKM()+" = "+(cjenik.getNajam()+(cjenik.getPoSatu()*brojSati)+(brojKm*cjenik.getPoKM()))+" kn";
            
            PostavkeSingleton.ps.println("U "+ "\u201e" + vrijeme+ "\u201c korisnik "+osoba.getImePrezime()+" vraca unajmljeni "
                    +vrstaVozila.getNaziv()+" koji ima ukupno " +novoStanje+" km \nte prijavljuje da vozilo ima problem '"+opisNeispravnosti+"'."+opis);
            
            PostavkeSingleton.getRacunLista().add(new Racun(najamUkupno,osoba.getId(),lokacijaSifra.getId(),datumIzdavanja,opis));
    
            iznajmljenoVozilo.setBrKm(novoStanje);
            float baterijaIzracun = iznajmljenoVozilo.getBaterija()-(((float)brojKm/(float)iznajmljenoVozilo.getDomet())*100);
            iznajmljenoVozilo.setBaterija((int)baterijaIzracun);         
            PostavkeSingleton.ps.println("Stanje baterije: "+iznajmljenoVozilo.getBaterija() + " %");
            float potrebneSekundeDoPuneBaterije = ((((float)iznajmljenoVozilo.vrijemePunjenja*3600000)*((float)(100-iznajmljenoVozilo.getBaterija())/100)));
            long voziloPunoNakonMiliSec = (novoVirtualnoVrijeme.getTime()+ (long)potrebneSekundeDoPuneBaterije);
            Date voziloPunoNakon = new Date(voziloPunoNakonMiliSec);
            iznajmljenoVozilo.puniSeDo=voziloPunoNakon;
            PostavkeSingleton.ps.println("Vozilo ce biti upotpunosti napunjeno u: "+vrijemeFormat.format(iznajmljenoVozilo.puniSeDo));
            iznajmljenoVozilo.setJeNeispravno(true);
            KapacitetLokacije kpTemp = KapacitetLokacije.dohvatiKapacitetVrsteVozilaZaLokaciju(lokacijaSifra.getId(), iznajmljenoVozilo.getId());
            kpTemp.setBrojNeispravnih(kpTemp.getBrojNeispravnih()+1);
            
            KapacitetLokacije.listaRaspoloziveVrsteVozilaNaLokaciji(lokacijaSifra.getId(),iznajmljenoVozilo.getId()).add(iznajmljenoVozilo);
            PostavkeSingleton.ps.println("Vozilo vraceno na lokaciju: "+lokacijaSifra.getId()+" - "+lokacijaSifra.getNazivLokacije()+"\n");
            osoba.setVracenaVozilaNeispravnoStanje(osoba.getVracenaVozilaNeispravnoStanje()+1);
            osoba.iznajmljenaVozila.remove(evIndexLista);
           // osoba.iznajmljenoVozilo=null;
           // osoba.aktivniNajam=false;
        } else PostavkeSingleton.ps.println("Vozilo se ne moze vratiti na lokaciju - nedovoljan broj za punjenje");
                                   
    }
    
    public Osoba(int id, String imePrezime) {
        this.id = id;
        this.imePrezime = imePrezime;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImePrezime() {
        return imePrezime;
    }

    public void setImePrezime(String imePrezime) {
        this.imePrezime = imePrezime;
    }

    public boolean isAktivniNajam() {
        return aktivniNajam;
    }

    public void setAktivniNajam(boolean aktivniNajam) {
        this.aktivniNajam = aktivniNajam;
    }
    
    public ArrayList<ElektricnoVozilo> getIznajmljenaVozila() {
        return iznajmljenaVozila;
    }

    public void setIznajmljenaVozila(ArrayList<ElektricnoVozilo> iznajmljenaVozila) {
        this.iznajmljenaVozila = iznajmljenaVozila;
    }

    public int getVracenaVozilaNeispravnoStanje() {
        return vracenaVozilaNeispravnoStanje;
    }

    public void setVracenaVozilaNeispravnoStanje(int vracenaVozilaNeispravnoStanje) {
        this.vracenaVozilaNeispravnoStanje = vracenaVozilaNeispravnoStanje;
    }
    
    
    public static Osoba dohvatiOsobuIzListe(ArrayList<Osoba> listaOsoba, int id){
        for(Osoba osoba : listaOsoba)
            if(osoba.getId()==id)
                return osoba;        
        return null;
    }
      
}
