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
public class Racun {

    private static long idBrojac = 0;

    public static long getIdBrojac() {
        return idBrojac;
    }

    public static void setIdBrojac(long idBrojac) {
        Racun.idBrojac = idBrojac;
    }
    private long JIDracuna;
    private float iznos;
    private int osobaId;
    private int lokacijaId;
    private Date datumIzdavanja;
    private String opis;
    private boolean placen=false;

    public Racun(){
        this.JIDracuna=-1;
    }
    
    public Racun(float iznos, int osobaId, int lokacijaId, Date datumIzdavanja,String opis) {
        this.JIDracuna = Racun.idBrojac++;
        this.iznos = iznos;
        this.osobaId = osobaId;
        this.lokacijaId = lokacijaId;
        this.datumIzdavanja = datumIzdavanja;
        this.opis=opis;
    }

    Racun(float iznos, int osobaId, int lokacijaId, Date datumIzdavanja, String opis, boolean placen) {
        this.JIDracuna = Racun.idBrojac++;
        this.iznos = iznos;
        this.osobaId = osobaId;
        this.lokacijaId = lokacijaId;
        this.datumIzdavanja = datumIzdavanja;
        this.opis = opis;
        this.placen=placen;
    }
   
    
    public long getJIDracuna() {
        return JIDracuna;
    }

    public void setJIDracuna(long redniBroj) {
        this.JIDracuna = redniBroj;
    }

    public float getIznos() {
        return iznos;
    }

    public void setIznos(float iznos) {
        this.iznos = iznos;
    }

    public int getOsobaId() {
        return osobaId;
    }

    public void setOsobaId(int osobaId) {
        this.osobaId = osobaId;
    }

    public int getLokacijaId() {
        return lokacijaId;
    }

    public void setLokacijaId(int lokacijaId) {
        this.lokacijaId = lokacijaId;
    }
  
    public Date getDatumIzdavanja() {
        return datumIzdavanja;
    }

    public void setDatumIzdavanja(Date datumIzdavanja) {
        this.datumIzdavanja = datumIzdavanja;
    }
    
    public boolean isPlacen() {
        return placen;
    }

    public void setPlacen(boolean placen) {
        this.placen = placen;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }  
    
    public static ArrayList<Racun> dohvatiListuRacunaFiltriranuPoDatumima(ArrayList<Racun> listaRacuna, Date pocetniDatum, Date krajnjiDatum){
        ArrayList<Racun> lista = new ArrayList<Racun>();
        for(Racun r : PostavkeSingleton.getRacunLista()){
            // n.getPocetakNajma() mora se dogoditi nakon pocetnogdatuma n.getKrajNajma mora se dogoditi prije krajnji datum
            if(r.getDatumIzdavanja().compareTo(pocetniDatum)>=0 && r.getDatumIzdavanja().compareTo(krajnjiDatum)<=0)
                lista.add(r);
        }
        return lista;
    }
        
}
