/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmanestar_zadaca_3;

/**
 *
 * @author Mane
 */


public class Kapacitet {
    private int lokacija;
    private int voziloVrsta;
    private int brMjestaZaPunjenje;
    private int raspolozivo;

    public Kapacitet(int lokacija, int voziloVrsta, int brMjestaZaPunjenje, int raspolozivo) {
        this.lokacija = lokacija;
        this.voziloVrsta = voziloVrsta;
        this.brMjestaZaPunjenje = brMjestaZaPunjenje;
        this.raspolozivo = raspolozivo;
    }
        
    public int getLokacija() {
        return lokacija;
    }

    public void setLokacija(int lokacija) {
        this.lokacija = lokacija;
    }

    public int getVoziloVrsta() {
        return voziloVrsta;
    }

    public void setVoziloVrsta(int voziloVrsta) {
        this.voziloVrsta = voziloVrsta;
    }

    public int getBrMjestaZaPunjenje() {
        return brMjestaZaPunjenje;
    }

    public void setBrMjestaZaPunjenje(int brMjestaZaPunjenje) {
        this.brMjestaZaPunjenje = brMjestaZaPunjenje;
    }

    public int getRaspolozivo() {
        return raspolozivo;
    }

    public void setRaspolozivo(int raspolozivo) {
        this.raspolozivo = raspolozivo;
    }
    
    
}
