/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmanestar_zadaca_3.Composite;

import java.util.Comparator;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;

/**
 *
 * @author Mane
 */
public class StrukturaComparator implements Comparator<Struktura>{
 
    @Override
    public int compare(Struktura o1, Struktura o2) {
        Integer i1 = new Integer(o1.getStrukturnaRazina());
        Integer i2 = new Integer(o2.getStrukturnaRazina());
        
        return i1.compareTo(i2);
    }

    @Override
    public Comparator<Struktura> reversed() {
        return Comparator.super.reversed(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Comparator<Struktura> thenComparing(Comparator<? super Struktura> other) {
        return Comparator.super.thenComparing(other); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <U> Comparator<Struktura> thenComparing(Function<? super Struktura, ? extends U> keyExtractor, Comparator<? super U> keyComparator) {
        return Comparator.super.thenComparing(keyExtractor, keyComparator); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <U extends Comparable<? super U>> Comparator<Struktura> thenComparing(Function<? super Struktura, ? extends U> keyExtractor) {
        return Comparator.super.thenComparing(keyExtractor); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Comparator<Struktura> thenComparingInt(ToIntFunction<? super Struktura> keyExtractor) {
        return Comparator.super.thenComparingInt(keyExtractor); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Comparator<Struktura> thenComparingLong(ToLongFunction<? super Struktura> keyExtractor) {
        return Comparator.super.thenComparingLong(keyExtractor); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Comparator<Struktura> thenComparingDouble(ToDoubleFunction<? super Struktura> keyExtractor) {
        return Comparator.super.thenComparingDouble(keyExtractor); //To change body of generated methods, choose Tools | Templates.
    }
}
