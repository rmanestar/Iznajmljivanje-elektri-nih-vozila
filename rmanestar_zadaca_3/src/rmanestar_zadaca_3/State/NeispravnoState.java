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
public class NeispravnoState implements VoziloState{

    public NeispravnoState() {
    }

    @Override
    public void doAction(VoziloContext context) {
        context.setState(this);
    }
    
    @Override
    public String toString(){
        return "NEISPRAVNO";
    }

    
}
