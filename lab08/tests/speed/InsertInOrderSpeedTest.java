package speed;

import java.util.HashMap;
import java.util.Scanner;
import edu.princeton.cs.algs4.Stopwatch;

import hashmap.Map61B;
import hashmap.ULLMap;
import hashmap.MyHashMap;

import static speed.InsertRandomSpeedTest.waitForPositiveInt;

/**
 * Performs a timing test on three different set implementations.
 * For hashmap.MyHashMap purposes assumes that <K,V> are <String, Integer> pairs.
 * @author Josh Hug 
 * @author Brendan Hu
 */
public class InsertInOrderSpeedTest {
    /**
     * Requests user input and performs tests of three different set
     * implementations. ARGS is unused. 
     */
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);


        System.out.println("\n This program inserts lexicographically increasing Strings"
                + "into Maps as <String, Integer> pairs.");

        String repeat;
        do {
            System.out.print("\nEnter # strings to insert into ULLMap: ");
            timeInOrderMap61B(new ULLMap<>(),
                    waitForPositiveInt(input));

            System.out.print("\nEnter # strings to insert into MyHashMap: ");
            timeInOrderMap61B(new MyHashMap<>(),
                    waitForPositiveInt(input));

            System.out.print("\nEnter # strings to insert into Java's HashMap: ");
            timeInOrderHashMap(new HashMap<>(),
                    waitForPositiveInt(input));

            System.out.print("\nWould you like to try more timed-tests? (y/n): ");
            repeat = input.nextLine();
        } while (!repeat.equalsIgnoreCase("n") && !repeat.equalsIgnoreCase("no"));
        input.close();
    }

    /**
     * Returns time needed to put N strings into a hashmap.Map61B in increasing order.
     * makes use of speed.StringUtils.nextString(String s)
     */
    public static double insertInOrder(Map61B<String, Integer> map61B, int N) {
        Stopwatch sw = new Stopwatch();
        String s = "cat";
        for (int i = 0; i < N; i++) {
            s = StringUtils.nextString(s);
            map61B.put(s, i);
        }
        return sw.elapsedTime();
    }

    /**
     * Returns time needed to put N strings into HashMap in increasing order.
     */
    public static double insertInOrder(HashMap<String, Integer> ts, int N) {
        Stopwatch sw = new Stopwatch();
        String s = "cat";
        for (int i = 0; i < N; i++) {
            s = StringUtils.nextString(s);
            ts.put(s, i);
        }
        return sw.elapsedTime();
    }

    /**
     * Attempts to insert N in-order strings of length L into map,
     * Prints time of the N insert calls, otherwise
     * Prints a nice message about the error
     */
    public static void timeInOrderMap61B(Map61B<String, Integer> map, int N) {
        try {
            double mapTime = insertInOrder(map, N);
            System.out.printf(map.getClass().getSimpleName() + ": %.2f sec\n", mapTime);
        } catch (StackOverflowError e) {
            printInfoOnStackOverflow(N);
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    /**
     * Attempts to insert N in-order strings of length L into HashMap,
     * Prints time of the N insert calls, otherwise
     * Prints a nice message about the error
     */
    public static void timeInOrderHashMap(HashMap<String, Integer> hashMap, int N) {
        try {
            double javaTime = insertInOrder(hashMap, N);
            System.out.printf("Java's Built-in HashMap: %.2f sec\n", javaTime);
        } catch (StackOverflowError e) {
            printInfoOnStackOverflow(N);
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    /* ------------------------------- Private methods ------------------------------- */

    /**
     * To be called after catching a StackOverflowError
     * Prints the error with corresponding N and L
     */
    private static void printInfoOnStackOverflow(int N) {
        System.out.println("--Stack Overflow -- couldn't add " + N + " strings.");
    }
}

