/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmanestar_zadaca_3;

import rmanestar_zadaca_3.Singleton.PostavkeSingleton;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import rmanestar_zadaca_3.ChainOfResponsibility.AbstractUpraviteljRacuna;
import rmanestar_zadaca_3.State.NeispravnoState;
import rmanestar_zadaca_3.State.PunjenjeState;
import rmanestar_zadaca_3.State.UnajmljenoState;
import rmanestar_zadaca_3.State.VoziloState;

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
    private int ugovor=-1;
    private float dugovanje = 0.00f;
    private float ostatak =0.00f;
    private Date datumVrijemeZadnjegNajma=null;
    private int vracenaVozilaNeispravnoStanje = 0;  

    public Osoba(int id, String imePrezime, int ugovor) {
        this.id = id;
        this.imePrezime = imePrezime;
        this.ugovor = ugovor;
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

    public int getUgovor() {
        return ugovor;
    }

    public void setUgovor(int ugovor) {
        this.ugovor = ugovor;
    }
    
    public float getDugovanje() {
        return dugovanje;
    }

    public void setDugovanje(float dugovanje) {
        this.dugovanje = dugovanje;
    }

    public Date getDatumVrijemeZadnjegNajma() {
        return datumVrijemeZadnjegNajma;
    }

    public void setDatumVrijemeZadnjegNajma(Date datumVrijemeZadnjegNajma) {
        this.datumVrijemeZadnjegNajma = datumVrijemeZadnjegNajma;
    }

    public float getOstatak() {
        return ostatak;
    }

    public void setOstatak(float ostatak) {
        this.ostatak = ostatak;
    }
    
    public static Osoba dohvatiOsobuIzListe(ArrayList<Osoba> listaOsoba, int id){
        for(Osoba osoba : listaOsoba)
            if(osoba.getId()==id)
                return osoba;        
        return null;
    }
      
    public static int unajmiVozilo(Osoba osoba, String vrijeme, ElektricnoVozilo vrstaVozila, Lokacija lokacijaSifra, Date datum){
        if(osoba==null || vrstaVozila==null || lokacijaSifra==null){
            System.out.println("ERROR ne moze se unajmiti vozilo na temelju danih parametara\n");
            return -1;         
        }
        
        for(ElektricnoVozilo ev : osoba.iznajmljenaVozila){
            if(ev.getId()==vrstaVozila.getId()){
                System.out.println("Osoba je vec unajmiti vozilo ove vrste!\n");
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
            System.out.println("Akcija ne smije imati vrijeme izvrsavanja koje se odvija prije trenutnog virtualnog vremena\n");
            return -1;
        } else PostavkeSingleton.virtualnoVrijeme=novoVirtualnoVrijeme;
        
        float maxDugovanje=-1.00f;
        
        if(PostavkeSingleton.konfiguracija.containsKey("dugovanje"))
            maxDugovanje = Float.parseFloat(PostavkeSingleton.konfiguracija.getProperty("dugovanje").replace(" ", "").replace(",", "."));
               
        if(osoba.getUgovor()==1){
            if(osoba.getDugovanje()>maxDugovanje){
                System.out.println("Osoba ne smije unajmiti vozilo jer je u sustavu ugovora i dugovanje joj prekoracuje maksimalno dopusteno dugovanje."+"Dugovanje osobe:"+osoba.getDugovanje()+"Maksimalno dugovanje u ugovoru:"+maxDugovanje+"\n");
                return -1;
            }        
        }
        
        int idVoziloZaBrisanje =0;
        ElektricnoVozilo evZaIznajmiti = null;
        ArrayList<ElektricnoVozilo> raspolozivaVozilaSPunomBaterijomNaLokaciji = new ArrayList<ElektricnoVozilo>();
              
        for(Lokacija l : PostavkeSingleton.getLokacijaLista()){
            for(KapacitetLokacije kl: l.getKapacitetLokacije()){
                for(ElektricnoVozilo rv: kl.getListaRaspolozivihVozila()){
                    if(vrstaVozila.idVrstaVozila==rv.idVrstaVozila && "SLOBODNO".equals(rv.getContext().getState().toString()) && l.getId()==lokacijaSifra.getId()){
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
            //nadodano broj najmova fix i state
            UnajmljenoState us = new UnajmljenoState();
            us.doAction(evZaIznajmiti.getContext());
            evZaIznajmiti.setBrojNajmova(evZaIznajmiti.getBrojNajmova()+1);
            osoba.iznajmljenaVozila.add(evZaIznajmiti);            
            idVoziloZaBrisanje = KapacitetLokacije.listaRaspoloziveVrsteVozilaNaLokaciji(lokacijaSifra.getId(),vrstaVozila.getId()).indexOf(evZaIznajmiti);       
        }       
        
        System.out.println("U "+"\u201e" + vrijeme+ "\u201c korisnik "+osoba.imePrezime+" trazi na lokaciji "+lokacijaSifra.getNazivLokacije()+ " najam za vrstu vozila "+vrstaVozila.getNaziv());
        if(evZaIznajmiti!=null && !KapacitetLokacije.listaRaspoloziveVrsteVozilaNaLokaciji(lokacijaSifra.getId(),vrstaVozila.getId()).isEmpty()){
           ArrayList<ElektricnoVozilo> el = KapacitetLokacije.listaRaspoloziveVrsteVozilaNaLokaciji(lokacijaSifra.getId(),vrstaVozila.getId());
           PostavkeSingleton.getNajamLista().add(new Najam(datum,vrstaVozila.getId(),osoba.getId(),lokacijaSifra.getId()));
           el.remove(idVoziloZaBrisanje);
           osoba.setDatumVrijemeZadnjegNajma(novoVirtualnoVrijeme);
           System.out.println("Vozilo iznajmljeno!\n");
           return 1;
        } else {
            System.out.println("Vozilo nije raspolozivo\n");
            return -1;
        }        
    }
    
    public static void vratiVozilo(Osoba osoba, String vrijeme, ElektricnoVozilo vrstaVozila, Lokacija lokacijaSifra, int brojKm, Date datumIzdavanja){
        if(osoba==null || vrstaVozila==null || lokacijaSifra==null){
            System.out.println("ERROR ne moze se vratiti vozilo a temelju danih parametara\n");
            return;         
        }
        
        if(osoba.iznajmljenaVozila==null){
            System.out.println("Osoba nije iznajmila niti jedno vozilo!\n");
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
            System.out.println("Osoba nema iznajmljeno vozilo navedene vrste!\n");
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
            System.out.println("Akcija ne smije imati vrijeme izvrsavanja koje se odvija prije trenutnog virtualnog vremena\n");
            return;
        } else PostavkeSingleton.virtualnoVrijeme=novoVirtualnoVrijeme;
        
        if(brojKm>iznajmljenoVozilo.domet){
            System.out.println("Broj kilometara koje je vozilo preslo ne smije biti vece od maksimalnog dometa vozila\n!");
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
            
            Najam pocetniNajam = Najam.dohavatiNajamSPocetnimDatumom(osoba.getId(),vrstaVozila.getId());
            if(pocetniNajam!=null){
                PostavkeSingleton.getNajamLista().get(PostavkeSingleton.getNajamLista().indexOf(pocetniNajam)).setKrajNajma(datumIzdavanja);
                PostavkeSingleton.getNajamLista().get(PostavkeSingleton.getNajamLista().indexOf(pocetniNajam)).setZarada(najamUkupno);
            }else {
                System.out.println("Osoba nema iznajmljeno vozilo ove vrste!\n");
                return;
            }
            
            String opis ="Stavke racuna su: 1 najam "+vrstaVozila.getNaziv()+ 
                    " - "+cjenik.getNajam()+" kn, najam je bio " +brojSati+" sat/a - "+brojSati+" * "+cjenik.getPoSatu()+" = "+
                    brojSati*cjenik.getPoSatu()+" kn, prethodno stanje bilo je "+prethodnoStanjeKm+" km znaci da je prosao "+brojKm+" km - "+
                    brojKm+" * "+cjenik.getPoKM()+" = "+brojKm*cjenik.getPoKM()+" kn. Racun ukupno iznosi "+cjenik.getNajam()+" kn + "+
                    brojSati*cjenik.getPoSatu()+" kn + "+brojKm*cjenik.getPoKM()+" = "+(cjenik.getNajam()+(cjenik.getPoSatu()*brojSati)+(brojKm*cjenik.getPoKM()))+" kn";
            
            
            System.out.println("U "+ "\u201e" + vrijeme+ "\u201c korisnik "+osoba.getImePrezime()+" vraca unajmljeni "
                    +vrstaVozila.getNaziv()+" koji ima ukupno " +novoStanje+" km. \n"+opis);
            
            Racun noviRacun = new Racun();
            noviRacun.setIznos(najamUkupno);
            noviRacun.setOsobaId(osoba.getId());
            noviRacun.setLokacijaId(lokacijaSifra.getId());
            noviRacun.setDatumIzdavanja(datumIzdavanja);
            noviRacun.setOpis(opis);
            if(osoba.getUgovor()==0)
                noviRacun.setPlacen(true);
            
            PostavkeSingleton.getLanacUpraviteljaRacuna().pokreniIzvrsavanjeOdgovornosti(AbstractUpraviteljRacuna.OBRADA, noviRacun);
            
            //PostavkeSingleton.getRacunLista().add(new Racun(najamUkupno,osoba.getId(),lokacijaSifra.getId(),datumIzdavanja,opis));
                   
            iznajmljenoVozilo.setBrKm(novoStanje);
            float baterijaIzracun = iznajmljenoVozilo.getBaterija()-(((float)brojKm/(float)iznajmljenoVozilo.getDomet())*100);
            iznajmljenoVozilo.setBaterija((int)baterijaIzracun);         
            System.out.println("Stanje baterije: "+iznajmljenoVozilo.getBaterija() + " %");
            float potrebneSekundeDoPuneBaterije = ((((float)iznajmljenoVozilo.vrijemePunjenja*3600000)*((float)(100-iznajmljenoVozilo.getBaterija())/100)));
            long voziloPunoNakonMiliSec = (novoVirtualnoVrijeme.getTime()+ (long)potrebneSekundeDoPuneBaterije);
            Date voziloPunoNakon = new Date(voziloPunoNakonMiliSec);
            iznajmljenoVozilo.puniSeDo=voziloPunoNakon;
            System.out.println("Vozilo ce biti upotpunosti napunjeno u: "+vrijemeFormat.format(iznajmljenoVozilo.puniSeDo));
            PunjenjeState ps = new PunjenjeState();
            ps.doAction(iznajmljenoVozilo.getContext());         
            KapacitetLokacije.listaRaspoloziveVrsteVozilaNaLokaciji(lokacijaSifra.getId(),iznajmljenoVozilo.getId()).add(iznajmljenoVozilo);
            System.out.println("Vozilo vraceno na lokaciju: "+lokacijaSifra.getId()+" - "+lokacijaSifra.getNazivLokacije()+"\n");
            if(osoba.getUgovor()==1)
                osoba.setDugovanje(osoba.getDugovanje()+najamUkupno);
            osoba.iznajmljenaVozila.remove(evIndexLista);
           // osoba.iznajmljenoVozilo=null;
           // osoba.aktivniNajam=false;
        } else System.out.println("Vozilo se ne moze vratiti na lokaciju - nedovoljan broj mjesta za punjenje\n");
                                   
    }
    
    public static void vratiNeispravnoVozilo(Osoba osoba, String vrijeme, ElektricnoVozilo vrstaVozila, Lokacija lokacijaSifra, int brojKm, String opisNeispravnosti, Date datumIzdavanja){
        if(osoba==null || vrstaVozila==null || lokacijaSifra==null){
            System.out.println("ERROR ne moze se vratiti vozilo a temelju danih parametara\n");
            return;         
        }
        
        if(osoba.iznajmljenaVozila==null){
            System.out.println("Osoba nije iznajmila niti jedno vozilo!\n");
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
            System.out.println("Osoba nema iznajmljeno vozilo navedene vrste!\n");
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
            System.out.println("Akcija ne smije imati vrijeme izvrsavanja koje se odvija prije trenutnog virtualnog vremena\n");
            return;
        } else PostavkeSingleton.virtualnoVrijeme=novoVirtualnoVrijeme;
        
        if(brojKm>iznajmljenoVozilo.domet){
            System.out.println("Broj kilometara koje je vozilo preslo ne smije biti vece od maksimalnog dometa vozila\n!");
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
            Najam pocetniNajam = Najam.dohavatiNajamSPocetnimDatumom(osoba.getId(),vrstaVozila.getId() );
            if(pocetniNajam!=null){
                PostavkeSingleton.getNajamLista().get(PostavkeSingleton.getNajamLista().indexOf(pocetniNajam)).setKrajNajma(datumIzdavanja);
                PostavkeSingleton.getNajamLista().get(PostavkeSingleton.getNajamLista().indexOf(pocetniNajam)).setZarada(najamUkupno);
            }else {
                System.out.println("Osoba nema iznajmljeno vozilo ove vrste!\n");
                return;
            }
            String opis ="Stavke racuna su: 1 najam "+vrstaVozila.getNaziv()+ 
                    " - "+cjenik.getNajam()+" kn, najam je bio " +brojSati+" sat/a - "+brojSati+" * "+cjenik.getPoSatu()+" = "+
                    brojSati*cjenik.getPoSatu()+" kn, prethodno stanje bilo je "+prethodnoStanjeKm+" km znaci da je prosao "+brojKm+" km - "+
                    brojKm+" * "+cjenik.getPoKM()+" = "+brojKm*cjenik.getPoKM()+" kn. Racun ukupno iznosi "+cjenik.getNajam()+" kn + "+
                    brojSati*cjenik.getPoSatu()+" kn + "+brojKm*cjenik.getPoKM()+" = "+(cjenik.getNajam()+(cjenik.getPoSatu()*brojSati)+(brojKm*cjenik.getPoKM()))+" kn";
            
            System.out.println("U "+ "\u201e" + vrijeme+ "\u201c korisnik "+osoba.getImePrezime()+" vraca unajmljeni "
                    +vrstaVozila.getNaziv()+" koji ima ukupno " +novoStanje+" km \nte prijavljuje da vozilo ima problem '"+opisNeispravnosti+"'."+opis);
               
            Racun noviRacun = new Racun();
            noviRacun.setIznos(najamUkupno);
            noviRacun.setOsobaId(osoba.getId());
            noviRacun.setLokacijaId(lokacijaSifra.getId());
            noviRacun.setDatumIzdavanja(datumIzdavanja);
            noviRacun.setOpis(opis);
            
            if(osoba.getUgovor()==0)
                noviRacun.setPlacen(true);
            
            PostavkeSingleton.getLanacUpraviteljaRacuna().pokreniIzvrsavanjeOdgovornosti(AbstractUpraviteljRacuna.OBRADA, noviRacun);
                      
            //PostavkeSingleton.getRacunLista().add(new Racun(najamUkupno,osoba.getId(),lokacijaSifra.getId(),datumIzdavanja,opis));
    
            iznajmljenoVozilo.setBrKm(novoStanje);
            float baterijaIzracun = iznajmljenoVozilo.getBaterija()-(((float)brojKm/(float)iznajmljenoVozilo.getDomet())*100);
            iznajmljenoVozilo.setBaterija((int)baterijaIzracun);         
            System.out.println("Stanje baterije: "+iznajmljenoVozilo.getBaterija() + " %");
            float potrebneSekundeDoPuneBaterije = ((((float)iznajmljenoVozilo.vrijemePunjenja*3600000)*((float)(100-iznajmljenoVozilo.getBaterija())/100)));
            long voziloPunoNakonMiliSec = (novoVirtualnoVrijeme.getTime()+ (long)potrebneSekundeDoPuneBaterije);
            Date voziloPunoNakon = new Date(voziloPunoNakonMiliSec);
            iznajmljenoVozilo.puniSeDo=voziloPunoNakon;
            System.out.println("Vozilo ce biti upotpunosti napunjeno u: "+vrijemeFormat.format(iznajmljenoVozilo.puniSeDo));
            
            NeispravnoState ns = new NeispravnoState();
            ns.doAction(iznajmljenoVozilo.getContext());
            
            KapacitetLokacije kpTemp = KapacitetLokacije.dohvatiKapacitetVrsteVozilaZaLokaciju(lokacijaSifra.getId(), iznajmljenoVozilo.getId());
            kpTemp.setBrojNeispravnih(kpTemp.getBrojNeispravnih()+1);
            
            KapacitetLokacije.listaRaspoloziveVrsteVozilaNaLokaciji(lokacijaSifra.getId(),iznajmljenoVozilo.getId()).add(iznajmljenoVozilo);
            System.out.println("Vozilo vraceno na lokaciju: "+lokacijaSifra.getId()+" - "+lokacijaSifra.getNazivLokacije()+"\n");
            if(osoba.getUgovor()==1)
                osoba.setDugovanje(osoba.getDugovanje()+najamUkupno);
            osoba.setVracenaVozilaNeispravnoStanje(osoba.getVracenaVozilaNeispravnoStanje()+1);
            osoba.iznajmljenaVozila.remove(evIndexLista);
           // osoba.iznajmljenoVozilo=null;
           // osoba.aktivniNajam=false;
        } else System.out.println("Vozilo se ne moze vratiti na lokaciju - nedovoljan broj mjesta za punjenje\n");
                                   
    }
    
}
