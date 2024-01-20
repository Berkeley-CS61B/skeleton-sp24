package game2048rendering;

/** Represents the image of a numbered tile on a 2048 board.
 *  @author P. N. Hilfinger.
 */
public class Tile {

    /** A new tile with VALUE as its value at (x, y).  This
     *  constructor is private, so all tiles are created by the
     *  factory method create. */
    private Tile(int value, int x, int y) {
        this._value = value;
        this._x = x;
        this._y = y;
        this._next = null;
        this._merged  = false;
    }

    /** Return whether this tile was already merged. */
    public boolean wasMerged() {
        return _merged;
    }

    void setMerged(boolean merged) {
        this._merged = merged;
    }

    /** Return my current y-coordinate. */
    int y() {
        return _y;
    }

    /** Return my current x-coordinate. */
    int x() {
        return _x;
    }

    /** Return the value supplied to my constructor. */
    public int value() {
        return _value;
    }

    /** Return my next state.  Before I am moved or merged, I am my
     *  own successor. */
    Tile next() {
        return _next == null ? this : _next;
    }

    /** Set my next state when I am moved or merged. */
    void setNext(Tile otherTile) {
        _next = otherTile;
    }

    /** Return a new tile at (x, y) with value VALUE. */
    public static Tile create(int value, int x, int y) {
        return new Tile(value, x, y);
    }

    /** Return the distance in rows or columns between me and my successor
     *  tile (0 if I have no successor). */
    int distToNext() {
        if (_next == null) {
            return 0;
        } else {
            return Math.max(Math.abs(_y - _next.y()),
                            Math.abs(_x - _next.x()));
        }
    }

    @Override
    public String toString() {
        return String.format("Tile %d at position (%d, %d)", value(), x(), y());
    }

    /** My value. */
    private final int _value;

    /** My last position on the board. */
    private final int _x;
    private final int _y;

    /** Whether I have merged. */
    private boolean _merged;

    /** Successor tile: one I am moved to or merged with. */
    private Tile _next;
}
