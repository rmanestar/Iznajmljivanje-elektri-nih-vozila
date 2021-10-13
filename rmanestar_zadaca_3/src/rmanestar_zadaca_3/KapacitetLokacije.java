/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmanestar_zadaca_3;

import rmanestar_zadaca_3.Singleton.PostavkeSingleton;
import java.util.ArrayList;

/**
 *
 * @author Mane
 */
public class KapacitetLokacije {

    public KapacitetLokacije() {
    }

    public KapacitetLokacije(ElektricnoVozilo vozilo) {
        this.vozilo = vozilo;
    }
    private ElektricnoVozilo vozilo;
    private ArrayList<ElektricnoVozilo> listaRaspolozivihVozila = new ArrayList<ElektricnoVozilo>();
    private int mjestaZaPunjenje;
    private int raspolozivo;
    private int brojNeispravnih =0;
    private int brojNajmova =0;

    public int getBrojNajmova() {
        return brojNajmova;
    }

    public void setBrojNajmova(int brojNajmova) {
        this.brojNajmova = brojNajmova;
    }
    
    public int getBrojNeispravnih() {
        return brojNeispravnih;
    }

    public void setBrojNeispravnih(int brojNeispravnih) {
        this.brojNeispravnih = brojNeispravnih;
    }
    
    public static ArrayList<ElektricnoVozilo> listaRaspoloziveVrsteVozilaNaLokaciji(int lokacijaSifra, int sifraVrsteVozila){
        for(Lokacija l: PostavkeSingleton.getLokacijaLista()){
            for(KapacitetLokacije kl : l.getKapacitetLokacije()){
                if(l.getId()== lokacijaSifra && kl.getVozilo().idVrstaVozila==sifraVrsteVozila){
                    return kl.getListaRaspolozivihVozila();
                }
            }
        }
        return null;
    }
    
    public static KapacitetLokacije dohvatiKapacitetVrsteVozilaZaLokaciju(int lokacijaSifra, int sifraVrsteVozila){
        for(Lokacija l: PostavkeSingleton.getLokacijaLista()){
            for(KapacitetLokacije kl : l.getKapacitetLokacije()){
                if(l.getId()== lokacijaSifra && kl.getVozilo().idVrstaVozila==sifraVrsteVozila){
                    return kl;
                }
            }
        }
        return null;
    }
    
    public ElektricnoVozilo getVozilo() {
        return vozilo;
    }

    public void setVozilo(ElektricnoVozilo vozilo) {
        this.vozilo = vozilo;
    }

    public int getMjestaZaPunjenje() {
        return mjestaZaPunjenje;
    }

    public void setMjestaZaPunjenje(int mjestaZaPunjenje) {
        this.mjestaZaPunjenje = mjestaZaPunjenje;
    }

    public int getRaspolozivo() {
        return raspolozivo;
    }

    public void setRaspolozivo(int raspolozivo) {
        this.raspolozivo = raspolozivo;
    }
    
    public ArrayList<ElektricnoVozilo> getListaRaspolozivihVozila() {
        return listaRaspolozivihVozila;
    }

    public void setListaRaspolozivihVozila(ArrayList<ElektricnoVozilo> listaRaspolozivihVozila) {
        this.listaRaspolozivihVozila = listaRaspolozivihVozila;
    }
//promjena zad 3 stanja
    public static ArrayList<ElektricnoVozilo> dohvatiListuRaspolozivihVozilaOdredeneVrsteNaLokaciji(int vrstaVozila, int lokacijaId){
        ArrayList<ElektricnoVozilo> raspolozivaVozilaSPunomBaterijomNaLokaciji = new ArrayList<ElektricnoVozilo>();
              
        for(Lokacija l : PostavkeSingleton.getLokacijaLista()){
            for(KapacitetLokacije kl: l.getKapacitetLokacije()){
                for(ElektricnoVozilo rv: kl.getListaRaspolozivihVozila()){
                    if(vrstaVozila==rv.idVrstaVozila && "SLOBODNO".equals(rv.getContext().getState().toString()) && l.getId()==lokacijaId){
                        raspolozivaVozilaSPunomBaterijomNaLokaciji.add(rv);
                    }
                }
            }
        }
        return raspolozivaVozilaSPunomBaterijomNaLokaciji;
    }
    
}
