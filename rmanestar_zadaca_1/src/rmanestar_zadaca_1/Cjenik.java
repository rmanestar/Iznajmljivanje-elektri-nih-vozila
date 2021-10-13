/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmanestar_zadaca_1;

/**
 *
 * @author Mane
 */


public class Cjenik {
    private int vrstaVozila;
    private int najam;
    private int poSatu;
    private int poKm;

    public Cjenik(int vrstaVozila, int najam, int poSatu, int poKM) {
        this.vrstaVozila = vrstaVozila;
        this.najam = najam;
        this.poSatu = poSatu;
        this.poKm = poKM;
    }
    
    public int getVrstaVozila() {
        return vrstaVozila;
    }

    public void setVrstaVozila(int vrstaVozila) {
        this.vrstaVozila = vrstaVozila;
    }

    public int getNajam() {
        return najam;
    }

    public void setNajam(int najam) {
        this.najam = najam;
    }

    public int getPoSatu() {
        return poSatu;
    }

    public void setPoSatu(int poSatu) {
        this.poSatu = poSatu;
    }

    public int getPoKM() {
        return poKm;
    }

    public void setPoKM(int poKM) {
        this.poKm = poKM;
    }
    
    public static Cjenik dohvatiCjenikZaVozilo(int vrstaVozilaId){
        for(Cjenik c:PostavkeSingleton.getCjenikLista()){
            if(c.vrstaVozila==vrstaVozilaId)
                return c;
        }
        return null;
    }
    
}
