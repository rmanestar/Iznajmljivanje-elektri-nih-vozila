/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmanestar_zadaca_3;

import java.util.ArrayList;
import java.util.Date;
import rmanestar_zadaca_3.Singleton.PostavkeSingleton;

/**
 *
 * @author Mane
 */
public class Najam {

    private long JIDnajam;
    private Date pocetakNajma=null;
    private Date krajNajma=null;
    private int voziloVrstaId;
    private int ososbaId;

    public int getOsosbaId() {
        return ososbaId;
    }

    public void setOsosbaId(int ososbaId) {
        this.ososbaId = ososbaId;
    }
    private int lokacijaId;
    private float zarada;
    private static long brojacId = 0;

    public static long getBrojacId() {
        return brojacId;
    }

    public static void setBrojacId(long brojacId) {
        Najam.brojacId = brojacId;
    }
    
    public Najam(Date pocetakNajma, int voziloVrstaId, int osobaId, int lokacijaId) {
        this.JIDnajam = brojacId++;
        this.pocetakNajma = pocetakNajma;
        this.voziloVrstaId = voziloVrstaId;
        this.ososbaId = osobaId;
        this.lokacijaId = lokacijaId;
    }
      
    public Date getPocetakNajma() {
        return pocetakNajma;
    }

    public void setPocetakNajma(Date pocetakNajma) {
        this.pocetakNajma = pocetakNajma;
    }

    public Date getKrajNajma() {
        return krajNajma;
    }

    public void setKrajNajma(Date krajNajma) {
        this.krajNajma = krajNajma;
    }

    public int getVoziloVrstaId() {
        return voziloVrstaId;
    }

    public void setVoziloVrstaId(int voziloVrstaId) {
        this.voziloVrstaId = voziloVrstaId;
    }

    public float getZarada() {
        return zarada;
    }

    public void setZarada(float zarada) {
        this.zarada = zarada;
    }
    
    public long getJIDnajam() {
        return JIDnajam;
    }

    public void setJIDnajam(long JIDnajam) {
        this.JIDnajam = JIDnajam;
    }
    
    public int getLokacijaId() {
        return lokacijaId;
    }

    public void setLokacijaId(int lokacijaId) {
        this.lokacijaId = lokacijaId;
    }
    
    public static Najam dohavatiNajamSPocetnimDatumom(int osobaId, int vrstaVozilaId){
        ArrayList<Najam> lista = PostavkeSingleton.getNajamLista();
        for(Najam n : lista ){
            if(n.getOsosbaId()==osobaId && n.getVoziloVrstaId()==vrstaVozilaId && n.getKrajNajma()==null){
                return n;
            }
        }
        return null;
    }
    
    public String ispisiTrajanje(Date pocetniDatum, Date krajnjiDatum){

    long different = krajnjiDatum.getTime() - pocetniDatum.getTime();
    long secondsInMilli = 1000;
    long minutesInMilli = secondsInMilli * 60;
    long hoursInMilli = minutesInMilli * 60;
    long daysInMilli = hoursInMilli * 24;

    long elapsedHours = different / hoursInMilli;
    different = different % hoursInMilli;

    long elapsedMinutes = different / minutesInMilli;
    different = different % minutesInMilli;

    long elapsedSeconds = different / secondsInMilli;

    return elapsedHours + "h "+elapsedMinutes+" m "+elapsedSeconds+" s";
    }
    
    
    public static ArrayList<Najam> dohvatiListuNajmovaFiltriranuPoDatumima(ArrayList<Najam> listaNajmova, Date pocetniDatum, Date krajnjiDatum){
        ArrayList<Najam> lista = new ArrayList<Najam>();
        for(Najam n : PostavkeSingleton.getNajamLista()){
            // n.getPocetakNajma() mora se dogoditi nakon pocetnogdatuma n.getKrajNajma mora se dogoditi prije krajnji datum
            try{
            if(n.getPocetakNajma().compareTo(pocetniDatum)>=0 && n.getKrajNajma().compareTo(krajnjiDatum)<=0)
                lista.add(n);
            }catch(Exception e){}
        }
        return lista;
    }

}
