package test;

import java.util.Comparator;
import java.util.Random;

/**
 * Created by jarek on 2/1/17.
 */
public class Test {

    public static void main( final String ... args ) {
        for (int i=0; i< 500 ; i++) {
            final double ms = testIt();
            if ( i %100 == 0) {
                System.out.println("That took " + ms + " ms");
            }

        }
    }

    private static double testIt() {
        int count = 1000000;
        Random generator = new Random();
        int[] list1 = new int[count];
        for (int i = 0; i < count; i++) {
            list1[i] = generator.nextInt();
        }
        int[] list2 = new int[count];
        for (int i = 0; i < count; i++) {
            list2[i] = generator.nextInt();
        }
        long startTime = System.nanoTime();
        int[] list3 = new int[count];
        for (int i = 0; i < count; i++) {
            list3[i] = list2[i] + list1[i];
        }
        long endTime = System.nanoTime();
        long duration = (endTime - startTime);
        double ms = (double) duration / 1000000.0;
        return ms;

    }
}

