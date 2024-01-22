package bomb;

import common.IntList;

public class BombMain {
    public static void answers(String[] args) {
        int phase = 2;
        if (args.length > 0) {
            phase = Integer.parseInt(args[0]);
        }
        // TODO: Find the correct inputs (passwords) to each phase using debugging techniques
        Bomb b = new Bomb();
        if (phase >= 0) {
            b.phase0("Figure this out. I wonder where the phases are defined...");
        }
        if (phase >= 1) {
            b.phase1(null); // Figure this out too
        }
        if (phase >= 2) {
            b.phase2("Figure this out. I wonder where the phases are defined...");
        }
    }
}
