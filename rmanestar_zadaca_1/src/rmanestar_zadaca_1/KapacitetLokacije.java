/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmanestar_zadaca_1;

import java.util.ArrayList;

/**
 *
 * @author Mane
 */
public class KapacitetLokacije {

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
    private ElektricnoVozilo vozilo;
    private ArrayList<ElektricnoVozilo> listaRaspolozivihVozila = new ArrayList<ElektricnoVozilo>();

    public ArrayList<ElektricnoVozilo> getListaRaspolozivihVozila() {
        return listaRaspolozivihVozila;
    }

    public void setListaRaspolozivihVozila(ArrayList<ElektricnoVozilo> listaRaspolozivihVozila) {
        this.listaRaspolozivihVozila = listaRaspolozivihVozila;
    }
    private int mjestaZaPunjenje;
    private int raspolozivo;
    
    public static ArrayList<ElektricnoVozilo> listaRaspoloziveVrsteVozilaNaLokaciji(int lokacijaSifra, int sifraVrsteVozila){
        for(Lokacija l: PostavkeSingleton.getLokacijaLista()){
            for(KapacitetLokacije kl : l.getKapacitetLokacije()){
                if(l.getId()== lokacijaSifra && kl.getVozilo().id==sifraVrsteVozila){
                    return kl.getListaRaspolozivihVozila();
                }
            }
        }
        return null;
    }
    
    public static KapacitetLokacije dohvatiKapacitetVrsteVozilaZaLokaciju(int lokacijaSifra, int sifraVrsteVozila){
        for(Lokacija l: PostavkeSingleton.getLokacijaLista()){
            for(KapacitetLokacije kl : l.getKapacitetLokacije()){
                if(l.getId()== lokacijaSifra && kl.getVozilo().id==sifraVrsteVozila){
                    return kl;
                }
            }
        }
        return null;
    }
    
}
