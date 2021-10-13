/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmanestar_zadaca_3.State;

/**
 *
 * @author Mane
 */
public class VoziloContext {
    private VoziloState state;
    public VoziloContext(){
        state = new SlobodnoState();
    }

    public VoziloState getState() {
        return state;
    }

    public void setState(VoziloState state) {
        this.state = state;
    }   
}
