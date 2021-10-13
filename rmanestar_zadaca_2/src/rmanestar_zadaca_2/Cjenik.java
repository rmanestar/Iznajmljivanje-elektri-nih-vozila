/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmanestar_zadaca_2;

import rmanestar_zadaca_2.Singleton.PostavkeSingleton;

/**
 *
 * @author Mane
 */

public class Cjenik {
    private int vrstaVozila;
    private float najam = 0.00f;
    private float poSatu = 0.00f;
    private float poKm = 0.00f;

    public Cjenik(int vrstaVozila, float najam, float poSatu, float poKM) {
        this.vrstaVozila = vrstaVozila;
        this.najam = najam;
        this.poSatu = poSatu;
        this.poKm = poKM;
    }
    
    public static Cjenik dohvatiCjenikZaVozilo(int vrstaVozilaId){
        for(Cjenik c:PostavkeSingleton.getCjenikLista()){
            if(c.vrstaVozila==vrstaVozilaId)
                return c;
        }
        return null;
    }
    
    public int getVrstaVozila() {
        return vrstaVozila;
    }

    public void setVrstaVozila(int vrstaVozila) {
        this.vrstaVozila = vrstaVozila;
    }

    public float getNajam() {
        return najam;
    }

    public void setNajam(float najam) {
        this.najam = najam;
    }

    public float getPoSatu() {
        return poSatu;
    }

    public void setPoSatu(float poSatu) {
        this.poSatu = poSatu;
    }

    public float getPoKM() {
        return poKm;
    }

    public void setPoKM(float poKM) {
        this.poKm = poKM;
    }
    
}
