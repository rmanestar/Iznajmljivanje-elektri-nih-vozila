/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmanestar_zadaca_1;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Mane
 */


public class Osoba {
    private int id;
    private String imePrezime;
    private boolean aktivniNajam = false;
    private ElektricnoVozilo iznajmljenoVozilo=null;

    public ElektricnoVozilo getIznajmljenoVozilo() {
        return iznajmljenoVozilo;
    }

    public void setIznajmljenoVozilo(ElektricnoVozilo iznajmljenoVozilo) {
        this.iznajmljenoVozilo = iznajmljenoVozilo;
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
    
    public static Osoba dohvatiOsobuIzListe(ArrayList<Osoba> listaOsoba, int id){
        for(Osoba osoba : listaOsoba)
            if(osoba.getId()==id)
                return osoba;        
        return null;
    }
    
    public static int iznajmiVozilo(Osoba osoba, String vrijeme, ElektricnoVozilo vrstaVozila, Lokacija lokacijaSifra){
        if(osoba==null || vrstaVozila==null || lokacijaSifra==null){
            PostavkeSingleton.ps.println("ERROR ne može se iznajmiti vozilo na temelju danih parametara\n");
            return -1;         
        }
        
        if(osoba.isAktivniNajam()){
            PostavkeSingleton.ps.println("Osoba je već iznajmila vozilo!\n");
            return -1;
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
        
        for(Lokacija l : PostavkeSingleton.getLokacijaLista()){
            for(KapacitetLokacije kl: l.getKapacitetLokacije()){
                for(ElektricnoVozilo rv: kl.getListaRaspolozivihVozila()){
                    if(vrstaVozila.id==rv.id && rv.baterija==100 && l.getId()==lokacijaSifra.getId()){
                        osoba.iznajmljenoVozilo=rv;
                        osoba.aktivniNajam=true;
                        idVoziloZaBrisanje = kl.getListaRaspolozivihVozila().indexOf(rv);
                        break;
                    }
                }
            }
        }
        
        PostavkeSingleton.ps.println("U "+"\u201e" + vrijeme+ "\u201c korisnik "+osoba.imePrezime+" traži na lokaciji "+lokacijaSifra.getNazivLokacije()+ " najam za vrstu vozila "+vrstaVozila.getNaziv());
        
        if(osoba.iznajmljenoVozilo!=null && !KapacitetLokacije.listaRaspoloziveVrsteVozilaNaLokaciji(lokacijaSifra.getId(),vrstaVozila.getId()).isEmpty()){
           ArrayList<ElektricnoVozilo> el = KapacitetLokacije.listaRaspoloziveVrsteVozilaNaLokaciji(lokacijaSifra.getId(),vrstaVozila.getId());
           el.remove(idVoziloZaBrisanje);
           PostavkeSingleton.ps.println("Vozilo iznajmljeno!\n");
           return 1;
        } else {
            PostavkeSingleton.ps.println("Vozilo nije raspoloživo\n");
            return -1;
        }        
    }
    
    public static void vratiVozilo(Osoba osoba, String vrijeme, ElektricnoVozilo vrstaVozila, Lokacija lokacijaSifra, int brojKm){
        if(osoba==null || vrstaVozila==null || lokacijaSifra==null){
            PostavkeSingleton.ps.println("ERROR ne može se vratiti vozilo a temelju danih parametara\n");
            return;         
        }
        
        if(!osoba.isAktivniNajam() || osoba.iznajmljenoVozilo==null){
            PostavkeSingleton.ps.println("Osoba nema aktivan najam!\n");
            return;
        }
        
        if(osoba.getIznajmljenoVozilo().id != vrstaVozila.id){
            PostavkeSingleton.ps.println("Osoba nema iznajmljeno vozilo navedene vrste\n");
            return;
        }
        
        Date novoVirtualnoVrijeme =null;
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
        
        if(brojKm>osoba.iznajmljenoVozilo.domet){
            PostavkeSingleton.ps.println("Broj kilometara koje je vozilo prešlo ne smije biti veće od maksimalnog dometa vozila\n!");
            return;
        }
        
        SimpleDateFormat vrijemeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
        if(KapacitetLokacije.dohvatiKapacitetVrsteVozilaZaLokaciju(lokacijaSifra.getId(), osoba.iznajmljenoVozilo.getId()).getMjestaZaPunjenje() 
                > KapacitetLokacije.listaRaspoloziveVrsteVozilaNaLokaciji(lokacijaSifra.getId(),osoba.iznajmljenoVozilo.getId()).size()){
            Cjenik cjenik = Cjenik.dohvatiCjenikZaVozilo(vrstaVozila.id);
            int prethodnoStanjeKm = osoba.iznajmljenoVozilo.getBrKm();
            int novoStanje = osoba.iznajmljenoVozilo.getBrKm()+ brojKm;
            float izracunSati =((float)novoVirtualnoVrijeme.getTime()-(float)staroVirtualnoVrijeme.getTime())/3600000;
            int brojSati = (int)Math.ceil(izracunSati);
            
            PostavkeSingleton.ps.println("U "+ "\u201e" + vrijeme+ "\u201c korisnik "+osoba.getImePrezime()+" vraća unajmljeni "
                    +vrstaVozila.getNaziv()+" koji ima ukupno " +novoStanje+" km. \nStavke računa su: 1 najam "+vrstaVozila.getNaziv()+ 
                    " - "+cjenik.getNajam()+" kn, najam je bio " +brojSati+" sat/a - "+brojSati+" * "+cjenik.getPoSatu()+" = "+
                    brojSati*cjenik.getPoSatu()+" kn, prethodno \nstanje bilo je "+prethodnoStanjeKm+" km znači da je prošao "+brojKm+" km - "+
                    brojKm+" * "+cjenik.getPoKM()+" = "+brojKm*cjenik.getPoKM()+" kn. Račun ukupno iznosi \n"+cjenik.getNajam()+" kn + "+
                    brojSati*cjenik.getPoSatu()+" kn + "+brojKm*cjenik.getPoKM()+" = "+(cjenik.getNajam()+(cjenik.getPoSatu()*brojSati)+(brojKm*cjenik.getPoKM()))+" kn");
            osoba.iznajmljenoVozilo.setBrKm(novoStanje);
            float baterijaIzracun = osoba.iznajmljenoVozilo.getBaterija()-(((float)brojKm/(float)osoba.iznajmljenoVozilo.getDomet())*100);
            osoba.iznajmljenoVozilo.setBaterija((int)baterijaIzracun);         
            PostavkeSingleton.ps.println("Stanje baterije: "+osoba.iznajmljenoVozilo.getBaterija() + " %");
            float potrebneSekundeDoPuneBaterije = ((((float)osoba.iznajmljenoVozilo.vrijemePunjenja*3600000)*((float)(100-osoba.iznajmljenoVozilo.getBaterija())/100)));
            long voziloPunoNakonMiliSec = (novoVirtualnoVrijeme.getTime()+ (long)potrebneSekundeDoPuneBaterije);
            Date voziloPunoNakon = new Date(voziloPunoNakonMiliSec);
            osoba.iznajmljenoVozilo.puniSeDo=voziloPunoNakon;
            PostavkeSingleton.ps.println("Vozilo će biti upotpunosti napunjeno u: "+vrijemeFormat.format(osoba.iznajmljenoVozilo.puniSeDo));

            KapacitetLokacije.listaRaspoloziveVrsteVozilaNaLokaciji(lokacijaSifra.getId(),osoba.iznajmljenoVozilo.getId()).add(osoba.iznajmljenoVozilo);
            PostavkeSingleton.ps.println("Vozilo vraćeno na lokaciju: "+lokacijaSifra.getId()+" - "+lokacijaSifra.getNazivLokacije()+"\n");

            osoba.iznajmljenoVozilo=null;
            osoba.aktivniNajam=false;
        } else PostavkeSingleton.ps.println("Vozilo se ne može vratiti na lokaciju - nedovoljan broj za punjenje");
                        
           
    }
    
}
