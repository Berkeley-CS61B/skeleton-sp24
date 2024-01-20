package game2048rendering;

import game2048logic.Model;

import java.awt.event.KeyEvent;
import java.util.Random;

import static game2048rendering.Side.*;

/** The input/output and GUI controller for play of a game of 2048.
 *  @author P. N. Hilfinger. */
class Game {


    /** Controller for a game represented by MODEL, using GUI as the
     *  source of key inputs. Uses SEED as the random seed. */
    public Game(Model model, GUI gui, double tile2p, long seed) {
        _model = model;
        _playing = true;
        _gui = gui;
        _probOf2 = tile2p;

        if (seed == 0) {
            _random = new Random();
        } else {
            _random = new Random(seed);
        }
    }

    /** Return true iff we have not received a Quit command. */
    boolean playing() {
        return _playing;
    }

    /** Clear the board and play one game, until receiving a quit or
     *  new-game request.  Update the viewer with each added tile or
     *  change in the board from tilting. */
    void playGame(boolean hotStart) {

        if (!hotStart) {
            _model.clear();
            _model.addTile(getValidNewTile());
        }
        while (_playing) {
            if (!hotStart) {
                if (!_model.gameOver()) {
                    _model.addTile(getValidNewTile());
                    _gui.update();
                }
            }
            if (hotStart) {
                _gui.update();
                hotStart = false;
            }

            boolean moved;
            moved = false;
            while (!moved) {
                String cmnd = _gui.getKey();
                switch (cmnd) {
                    case "Quit":
                        _playing = false;
                        return;
                    case "New Game":
                        return;
                    case KeyEvent.VK_UP + "": case KeyEvent.VK_DOWN + "": case KeyEvent.VK_LEFT + "": case KeyEvent.VK_RIGHT+ "":
                    case "\u2190": case "\u2191": case "\u2192": case "\u2193":
                        if (!_model.gameOver()) {
                            _gui.update();
                            moved = false;
                        }

                        String stateBefore = _model.toString();
                        _model.tiltWrapper(keyToSide(cmnd));
                        String stateAfter = _model.toString();

                        if (!stateBefore.equals(stateAfter)) {
                            _gui.update();
                            moved = true;
                        }

                        break;
                    default:
                        break;
                }

            }
        }
    }

    /** Return the side indicated by KEY ("Up", "Down", "Left",
     *  or "Right"). */
    private Side keyToSide(String key) {
        return switch (key) {
            case KeyEvent.VK_UP + "", "\u2191" -> NORTH;
            case KeyEvent.VK_DOWN + "", "\u2193" -> SOUTH;
            case KeyEvent.VK_LEFT + "", "\u2190" -> WEST;
            case KeyEvent.VK_RIGHT+ "", "\u2192" -> EAST;
            default -> throw new IllegalArgumentException("unknown key designation");
        };
    }

    /** Return a valid tile, using our source's tile input until finding
     *  one that fits on the current board. Assumes there is at least one
     *  empty square on the board. */
    private Tile getValidNewTile() {
        while (true) {
            Tile tile = generateNewTile(_model.size());
            if (_model.tile(tile.x(), tile.y()) == null) {
                return tile;
            }
        }
    }

    /** Return a randomly positioned tile with either value of 2 with
     * probability _probOf2 or a value of 4 with probability 1 - _probOf2 in a
     * board with size SIZE. */
    private Tile generateNewTile(int size) {
        int c = _random.nextInt(size), r = _random.nextInt(size);
        int v = _random.nextDouble() <= _probOf2 ? 2 : 4;

        return Tile.create(v, c, r);
    }

    /** The playing board. */
    private final Model _model;

    /** GUI from which random commands are collected. */
    private final GUI _gui;

    /** Probability that the next tile is 2, rather than a 4. */
    private final double _probOf2;

    /** Source of random numbers. */
    private final Random _random;

    /** True while user is still willing to play. */
    private boolean _playing;

}
