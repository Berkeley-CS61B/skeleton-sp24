import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class InteractivePercolationVisualizer {
    private static final int DELAY = 20;

    public static void main(String[] args) {
        // N-by-N percolation system (read from command-line, default = 10)
        int N = 5;
        if (args.length == 1) {
            N = Integer.parseInt(args[0]);
        }

        // turn on animation mode
        PercolationPicture.show(0);

        // repeatedly open site specified my mouse click and draw resulting system
        StdOut.println(N);

        Percolation perc = new Percolation(N);
        PercolationPicture.draw(perc, N);
        PercolationPicture.show(DELAY);
        int lastClickedI = -1;
        int lastClickedJ = -1;
        while (true) {

            // detected mouse click
            if (StdDraw.isMousePressed()) {

                // screen coordinates
                double x = StdDraw.mouseX();
                double y = StdDraw.mouseY();

                // convert to row i, column j
                int i = (int) (N - Math.floor(y) - 1);
                int j = (int) (Math.floor(x));

                // open site (i, j) provided it's in bounds
                if (i >= 0 && i < N && j >= 0 && j < N) {
                    if (i != lastClickedI || j != lastClickedJ) {
                        StdOut.println(i + " " + j);
                        perc.open(i, j);
                        lastClickedI = i;
                        lastClickedJ = j;
                    }
                }

                // draw N-by-N percolation system
                PercolationPicture.draw(perc, N);
            } else {
                // if mouse is let go, allow re-clicking of same tile
                lastClickedI = -1;
                lastClickedJ = -1;
            }
            PercolationPicture.show(DELAY);
        }
    }
}
