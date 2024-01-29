package adventure;

import edu.princeton.cs.algs4.In;

import java.util.Map;
import java.util.TreeMap;

import static adventure.AdventureUtils.isInt;

public class MachineStage implements AdventureStage {
    private final In in;
    private final Map<String, AdventureStage> responses;

    public MachineStage(In in) {
        this.in = in;
        this.responses = new TreeMap<>(Map.of(
                "li ka shing", new FillerStage("You head to Li Ka Shing 245, and sit in your favorite seat."),
                "zoom", new FillerStage("You find an open seat, set up your laptop, and join the Zoom.")
        ));
    }

    @Override
    public String nextStagePrompt() {
        return "It's almost time for lecture! How are you attending?";
    }

    @Override
    public void playStage() {
        String msg = """
                On the first (zeroth?) floor of Soda, below the labs, you find a mysterious machine.
                It has holes for two lists of ints of the same length, and a third hole that looks
                like it would output a number. The label reads:

                    'SumOfElementWiseMax-inator'

                ... Huh. You decide to experiment with the machine for a bit.
                    """;

        System.out.println(msg);

        outer:
        while (true) {
            System.out.println("Enter a sequence of ints, separated by commas:");
            String[] listOne = in.readLine().split("[\\s,]+");
            System.out.println("Enter a second sequence, with the same number of ints:");
            String[] listTwo = in.readLine().split("[\\s,]+");
            if (listOne.length != listTwo.length) {
                System.out.println("The provided lists aren't of the same length!");
                continue;
            }
            int[] arrOne = new int[listOne.length];
            int[] arrTwo = new int[listTwo.length];
            for (int i = 0; i < listOne.length; i++) {
                if (!isInt(listOne[i]) || !isInt(listTwo[i])) {
                    System.out.println("Hmm, are you sure you typed a sequence of integers?");
                    continue outer; // This is to continue from the while loop rather than the for loop.
                }
                arrOne[i] = Integer.parseInt(listOne[i]);
                arrTwo[i] = Integer.parseInt(listTwo[i]);
            }

            int machineResult = sumOfElementwiseMax(arrOne, arrTwo);

            System.out.println("The machine whirrs briefly before outputting a slip of paper, reading " + machineResult);
            System.out.println("Does that seem right to you?");
            System.out.println("Enter [y] if you want to move on, and anything else to try again.");
            String response = in.readLine().toLowerCase();
            if (response.equals("y")) {
                System.out.println("Satisfied with your tinkering, you leave the machine behind.");
                break;
            }
        }
    }

    @Override
    public Map<String, AdventureStage> getResponses() {
        return responses;
    }

    public static int mysteryMax(int a, int b) {
        int w = (b - a) >> 31;
        int z = ~(b - a) >> 31;

        int max = b & w | a & z;
        return max;
    }

    public static int mysteryAdd(int a, int b) {
        int x = a, y = b;
        int xor, and, temp;
        and = x & y;
        xor = x ^ y;

        while (and != 0) {
            and <<= 1;
            temp = xor ^ and;
            and &= xor;
            xor = temp;
        }
        return xor;
    }

    /**
     * Returns a new array where entry i is the max of
     * a[i] and b[i]. For example, if a = {1, -10, 3}
     * and b = {0, 20, 5}, this function will return {1, 20, 5}.
     */
    public static int[] arrayMax(int[] a, int[] b) {
        if (a.length != b.length) {
            System.out.println("ERROR! Array lengths don't match");
            return null;
        }
        int[] returnArray = new int[a.length];
        for (int i = 0; i < a.length; i += 1) {
            int biggerValue = mysteryMax(a[i], b[i]);
            returnArray[i] = biggerValue;
        }

        return returnArray;
    }

    /**
     * Returns the sum of all elements in x.
     */
    public static int arraySum(int[] x) {
        int i = 0;
        int sum = 0;
        while (i < x.length) {
            sum = sum + mysteryAdd(sum, x[i]);
            i = i + 1;
        }
        return sum;
    }

    /**
     * Returns the sum of the element-wise max of a and b.
     * For example if a = {1, -10, 3} and b = {0, 20, 5},
     * the elementwise max is {1, 20, 5}, which sums to 26.
     */
    public static int sumOfElementwiseMax(int[] a, int[] b) {
        int[] maxes = arrayMax(a, b);
        int sumofMaxes = arraySum(maxes);
        return sumofMaxes;
    }
}
