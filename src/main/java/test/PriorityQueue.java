package test;

import java.util.Comparator;

/**
 * Created by jarek on 2/2/17.
 */
public class PriorityQueue <T> {

    private T[] array;
    private int index;
    private Comparator<T> comparator;

    public PriorityQueue (int size, Comparator<T> comparator) {
        array =  (T[]) new Object[size];
        this.comparator = comparator;
    }

    public PriorityQueue () {
        this((Comparator<T>) Comparator.naturalOrder());
    }

    public PriorityQueue (Comparator<T> comparator) {
        this(10, comparator);
    }

    public static void main(final String ... args ) {
        PriorityQueue<Integer> pw = new PriorityQueue<Integer>();
        System.out.println("oo");
    }
}
