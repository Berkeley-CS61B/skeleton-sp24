package game2048rendering;

import java.util.Arrays;
import java.util.Formatter;

/**
 * @author hug
 */
public class Board {
    /** Current contents of the board. */
    private final Tile[][] _values;
    /** Side that the board currently views as north. */
    private Side _viewPerspective;

    public Board(int size) {
        _values = new Tile[size][size];
        _viewPerspective = Side.NORTH;
    }

    /** Shifts the view of the board such that the board behaves as if side S is north. */
    public void setViewingPerspective(Side s) {
        _viewPerspective = s;
    }

    /** Create a board where RAWVALUES hold the values of the tiles on the board 
     * (0 is null) with a current score of SCORE and the viewing perspective set to north. */
    public Board(int[][] rawValues) {
        int size = rawValues.length;
        _values = new Tile[size][size];
        _viewPerspective = Side.NORTH;
        for (int x = 0; x < size; x += 1) {
            for (int y = 0; y < size; y += 1) {
                int value = rawValues[size - 1 - y][x];
                Tile tile;
                if (value == 0) {
                    tile = null;
                } else {
                    tile = Tile.create(value, x, y);
                }
                _values[x][y] = tile;
            }
        }
    }

    /** Returns the size of the board. */
    public int size() {
        return _values.length;
    }

    /** Return the current Tile at (x, y), when sitting with the board
     *  oriented so that SIDE is at the top (farthest) from you. */
    private Tile vtile(int x, int y, Side side) {
        return _values[side.x(x, y, size())][side.y(x, y, size())];
    }

    /** Return the current Tile at (x, y), where 0 <= x < size(),
     *  0 <= y < size(). Returns null if there is no tile there. */
    public Tile tile(int x, int y) {
        return vtile(x, y, _viewPerspective);
    }

    /** Clear the board to empty and reset the score. */
    public void clear() {
        for (Tile[] column : _values) {
            Arrays.fill(column, null);
        }
    }

    /** Adds the tile T to the board */
    public void addTile(Tile t) {
        _values[t.x()][t.y()] = t;
    }

    
    /** Places the Tile TILE at column x, y y where x and y are
     * treated as coordinates with respect to the current viewPerspective.
     *
     * (0, 0) is bottom-left corner.
     *
     * If the move is a merge, sets the tile's merged status to true.
     * */
    public void move(int x, int y, Tile tile) {
        int px = _viewPerspective.x(x, y, size());
        int py = _viewPerspective.y(x, y, size());

        Tile tile1 = vtile(x, y, _viewPerspective);
        _values[tile.x()][tile.y()] = null;

        // Move or merge the tile. It is important to call setNext
        // on the old tile(s) so they can be animated into position
        Tile next;
        if (tile1 == null) {
            next = Tile.create(tile.value(), px, py);
        } else {
            if (tile.value() != tile1.value()) {
                throw new IllegalArgumentException("Tried to merge two unequal tiles: " + tile + " and " + tile1);
            }
            next = Tile.create(2 * tile.value(), px, py);
            tile1.setNext(next);
        }
        tile.setMerged(tile1 != null);
        next.setMerged(tile.wasMerged());
        tile.setNext(next);
        _values[px][py] = next;
    }

    /** Resets all the merged booleans to false for every tile on the board. */
    public void resetMerged() {
        for (int x = 0; x < size(); x += 1) {
            for (int y = 0; y < size(); y += 1) {
                if (_values[x][y] != null){
                    _values[x][y].setMerged(false);
                }
            }
        }
    }

    /** Returns the board as a string, used for debugging. */
    @Override
    public String toString() {
        Formatter out = new Formatter();
        out.format("%n[%n");
        for (int y = size() - 1; y >= 0; y -= 1) {
            for (int x = 0; x < size(); x += 1) {
                if (tile(x, y) == null) {
                    out.format("|    ");
                } else {
                    out.format("|%4d", tile(x, y).value());
                }
            }
            out.format("|%n");
        }
        return out.toString();
    }
}
