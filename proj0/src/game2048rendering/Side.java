package game2048rendering;

/** Symbolic names for the four sides of a board.
 *  @author P. N. Hilfinger */
public enum Side {
    /** The parameters (COL0, ROW0, DCOL, and DROW) for each of the
     *  symbolic directions, D, below are to be interpreted as follows:
     *     The board's standard orientation has the top of the board
     *     as NORTH, and rows and columns (see Model) are numbered
     *     from its lower-left corner. Consider the board oriented
     *     so that side D of the board is farthest from you. Then
     *        * (COL0*s, ROW0*s) are the standard coordinates of the
     *          lower-left corner of the reoriented board (where s is the
     *          board size), and
     *        * If (x, y) are the standard coordinates of a certain
     *          square on the reoriented board, then (x+DCOL, y+DROW)
     *          are the standard coordinates of the squares immediately
     *          above it on the reoriented board.
     *  The idea behind going to this trouble is that by using the
     *  x() and y() methods below to translate from reoriented to
     *  standard coordinates, one can arrange to use exactly the same code
     *  to compute the result of tilting the board in any particular
     *  direction. */

    NORTH(0, 0, 0, 1),
    EAST(0, 1, 1, 0),
    SOUTH(1, 1, 0, -1),
    WEST(1, 0, -1, 0);

    /** The side that is in the direction (DCOL, DROW) from any square
     *  of the board.  Here, "direction (DCOL, DROW) means that to
     *  move one space in the direction of this Side increases the row
     *  by DROW and the colunn by DCOL.  (COL0, ROW0) are the row and
     *  column of the lower-left square when sitting at the board facing
     *  towards this Side. */
    Side(int col0, int row0, int dcol, int drow) {
        this._row0 = row0;
        this._col0 = col0;
        this._drow = drow;
        this._dcol = dcol;
    }

    /** Return the standard x-coordinate for square (x, y) on a board
     *  of size SIZE oriented with this Side on top. */
    int x(int x, int y, int size) {
        return _col0 * (size - 1) + x * _drow + y * _dcol;
    }

    /** Return the standard y-coordinate for square (x, y) on a board
     *  of size SIZE oriented with this Side on top. */
    int y(int x, int y, int size) {
        return _row0 * (size - 1) - x * _dcol + y * _drow;
    }

    /** Parameters describing this Side, as documented in the comment at the
     *  start of this class. */
    private final int _row0, _col0, _drow, _dcol;
}
