package tetris;

import java.util.ArrayList;
import java.util.Random;

/**
 *  A Tetris-accurate Tetromino randomizer.
 *  This prevents the same Tetromino shape from appearing multiple times in succession.
 *
 *  @author Erik Nelson
 */

public class BagRandomizer {

  private Random random;

  // A list of current values in the "bag".
  ArrayList<Integer> values;

  // The total capacity of the bag.
  int capacity;

  public BagRandomizer(Random r, int n) {
    this.random = r;
    this.capacity = n;

    refillValues();
  }

  /**
   * Resets the values of the bag to contain integers from 0 (inclusive) to capacity (exclusive).
   */
  private void refillValues() {
    ArrayList<Integer> newValues = new ArrayList<>();
    for (int i = 0; i < this.capacity; i++) {
      newValues.add(i);
    }
    values = newValues;
  }

  /**
   * Grabs and removes a random item from the bag. If the bag is empty, refill it.
   * @return the removed integer
   */
  public int getValue() {
    if (values.isEmpty()) {
      refillValues();
    }

    int randomIndex = random.nextInt(values.size());
    int randomValue = values.get(randomIndex);

    values.remove(randomIndex);
    return randomValue;
  }
}
