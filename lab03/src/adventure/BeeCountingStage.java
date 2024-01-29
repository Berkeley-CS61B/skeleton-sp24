package adventure;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdRandom;

import java.util.List;
import java.util.Map;

public class BeeCountingStage implements AdventureStage {
    private static final int[] SOME_NEAT_NUMBERS = {5, 3, 2, 6, 7};

    private final In in;
    private final Map<String, AdventureStage> responses;
    private List<String> input;

    public BeeCountingStage(In in) {
        this.in = in;
        this.responses = Map.of("go", new SpeciesListStage(in));
    }

    /**
     * Gives prompt for number of bees and prints out 3 sets of bees that the user
     * counts and inputs If wrong answer is given for number of bees, repeat with 3
     * new sets of bees.
     */
    @Override
    public void playStage() {
        while (true) {
            String msg = """
                    In Soda 326, you can find the computers known as "The Hive". It is a little-known fact that
                    they are called this because they are home to (friendly) robotic bees. How many bees do you see?
                    """;
            System.out.println(msg);
            int count = 0;
            int expectedSum = 0;

            while (count < 3) {
                int currNum = SOME_NEAT_NUMBERS[StdRandom.uniform(SOME_NEAT_NUMBERS.length)];
                for (int i = 0; i < currNum; i++) {
                    System.out.print("-.-");
                    if (i < currNum - 1) {
                        System.out.print(" ");
                    }
                }
                System.out.println();
                String input = this.in.readLine();
                while (!AdventureUtils.isInt(input)) {
                    System.out.println("Please enter a valid integer.");
                    input = this.in.readLine();
                }
                expectedSum += currNum;
                this.input.add(input);
                if (count < 2) {
                    System.out.println("How about now?");
                }
                count++;
            }
            if (this.sumInput() == expectedSum) {
                break;
            }
            System.out.println("You did not count the bees correctly. Let's try again!");
            this.input.clear();
        }
        System.out.println("Those sure were some bees!");
    }

    @Override
    public String nextStagePrompt() {
        return "Phew, that was a lot of counting! It's time for Professor Hug's office hours! " +
                "Let's head up to his office on the 7th floor.";
    }

    @Override
    public Map<String, AdventureStage> getResponses() {
        return this.responses;
    }

    /**
     * Uses this.input (the user's number inputs) to calculate the sum of
     * this.input.
     *
     * @return sum of elements in this.input.
     */
    private int sumInput() {
        int sum = 0;
        for (int i = 0; i <= this.input.size(); i++) {
            sum += Integer.parseInt(this.input.get(i));
        }
        return sum;
    }

}
