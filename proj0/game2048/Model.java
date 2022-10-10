package game2048;

import java.util.ArrayList;
import java.util.Formatter;
import java.util.Observable;

import static game2048.TestAtLeastOneMoveExists.b;


/** The state of a game of 2048.
 *  @author Kevin Vong
 */
public class Model extends Observable {
    /** Current contents of the board. */
    private Board board;
    /** Current score. */
    private int score;
    /** Maximum score so far.  Updated when game ends. */
    private int maxScore;
    /** True iff game is ended. */
    private boolean gameOver;

    /* Coordinate System: column C, row R of the board (where row 0,
     * column 0 is the lower-left corner of the board) will correspond
     * to board.tile(c, r).  Be careful! It works like (x, y) coordinates.
     */

    /** Largest piece value. */
    public static final int MAX_PIECE = 2048;

    /** A new 2048 game on a board of size SIZE with no pieces
     *  and score 0. */
    public Model(int size) {
        board = new Board(size);
        score = maxScore = 0;
        gameOver = false;
    }

    /** A new 2048 game where RAWVALUES contain the values of the tiles
     * (0 if null). VALUES is indexed by (row, col) with (0, 0) corresponding
     * to the bottom-left corner. Used for testing purposes. */
    public Model(int[][] rawValues, int score, int maxScore, boolean gameOver) {
        int size = rawValues.length;
        board = new Board(rawValues, score);
        this.score = score;
        this.maxScore = maxScore;
        this.gameOver = gameOver;
    }

    /** Return the current Tile at (COL, ROW), where 0 <= ROW < size(),
     *  0 <= COL < size(). Returns null if there is no tile there.
     *  Used for testing. Should be deprecated and removed.
     *  */
    public Tile tile(int col, int row) {
        return board.tile(col, row);
    }

    /** Return the number of squares on one side of the board.
     *  Used for testing. Should be deprecated and removed. */
    public int size() {
        return board.size();
    }

    /** Return true iff the game is over (there are no moves, or
     *  there is a tile with value 2048 on the board). */
    public boolean gameOver() {
        checkGameOver();
        if (gameOver) {
            maxScore = Math.max(score, maxScore);
        }
        return gameOver;
    }

    /** Return the current score. */
    public int score() {
        return score;
    }

    /** Return the current maximum game score (updated at end of game). */
    public int maxScore() {
        return maxScore;
    }

    /** Clear the board to empty and reset the score. */
    public void clear() {
        score = 0;
        gameOver = false;
        board.clear();
        setChanged();
    }

    /** Add TILE to the board. There must be no Tile currently at the
     *  same position. */
    public void addTile(Tile tile) {
        board.addTile(tile);
        checkGameOver();
        setChanged();
    }

    /** Tilt the board toward SIDE. Return true iff this changes the board.
     *
     * 1. If two Tile objects are adjacent in the direction of motion and have
     *    the same value, they are merged into one Tile of twice the original
     *    value and that new value is added to the score instance variable
     * 2. A tile that is the result of a merge will not merge again on that
     *    tilt. So each move, every tile will only ever be part of at most one
     *    merge (perhaps zero).
     * 3. When three adjacent tiles in the direction of motion have the same
     *    value, then the leading two tiles in the direction of motion merge,
     *    and the trailing tile does not.
     * */
    public boolean tilt(Side side) {
        boolean changed;
        changed = false;

        // TODO: Modify this.board (and perhaps this.score) to account
        // for the tilt to the Side SIDE. If the board changed, set the
        // changed local variable to true.

        int r = 2;
        int maxCol = 4;
        int maxRow = 4;
        ArrayList<ArrayList<Integer>> bigList = new ArrayList<>();
        if (side == Side.WEST) {
            board.setViewingPerspective(Side.WEST);
        } else if (side == Side.EAST) {
            board.setViewingPerspective(Side.EAST);
        } else if (side == Side.SOUTH) {
            board.setViewingPerspective(Side.SOUTH);
        }

        while (r != -1) {
            /* row 2 rules */
            if (r == 2) {
                for (int c = 0; c < maxCol; c += 1) {

                    /* skip null values */
                    if (checkIfSpaceIsNull(board, c, r)) {
                        continue;

                        /* This location has a tile */
                    } else {
                        if (checkAboveNull(board, c, r)) {
                            board.move(c, r + 1, board.tile(c, r));
                            changed = true;
                        }

                        /* check if value of 1 row above is the same */
                        if (checkValue(board, c, r) && !checkTileChange(c,3,bigList)) {
                            board.move(c, r + 1, board.tile(c, r));
                            score += board.tile(c, r + 1).value();
                            changed = true;
                            tileChange(c, r + 1, bigList);
                        }
                    }
                }
            }
            /* row 1 rules */
            if (r == 1) {
                for (int c = 0; c < maxCol; c += 1) {

                    /* TRUE if the current tile has a value */
                    if (board.tile(c, r) != null) {

                        /* case 1 */
                        if (board.tile(c,2) == null && board.tile(c,3) == null) {
                            board.move(c,3,tile(c,r));
                            continue;

                            /* case 2
                            * row 2 has a value
                            * */
                        } else if (board.tile(c, 2) != null){

                            /* check if row 2 value is the same */
                            if (checkValue(board,c,r) && !checkTileChange(c, r, bigList)){
                                board.move(c,2, tile(c,r));
                                score += board.tile(c, r + 1).value();
                                changed = true;
                                tileChange(c, 2, bigList);

                            /* row 2 value is not the same */
                            } else {
                                board.tile(c,r);
                            }
                        /*case 4 */
                        } else if (board.tile(c,3).value() == board.tile(c,r).value() && board.tile(c,2) == null) {
                            board.move(c,3,tile(c,r));
                            score += board.tile(c, 3).value();
                            changed = true;
                            tileChange(c, 2, bigList);

                        } else if (board.tile(c,2) == null && board.tile(c,3).value() != board.tile(c,r).value()) {
                            board.move(c,2, tile(c,r));
                            changed = true;
                        }

                    }

                }
            }
            if (r == 0) {
                for (int c = 0; c < maxCol; c += 1) {

                    /* TRUE if the current tile has a value */
                    if (board.tile(c,r) != null) {

                        /* case 1 */
                        if (board.tile(c, 1) == null && board.tile(c, 2) == null && board.tile(c, 3) == null) {
                            board.move(c,3, tile(c,r));

                            /* case 2 */
                        } else if (board.tile(c, 1) == null && board.tile(c, 2) == null && board.tile(c, 3) != null) {

                            /* check if row 3 has a value */
                            if (board.tile(c,r).value() == board.tile(c,3).value() && !checkTileChange(c,3, bigList)) {
                                board.move(c, 3, tile(c, r));
                                score += board.tile(c, 3).value();
                                changed = true;
                                tileChange(c, 3, bigList);
                            } else {
                                board.move(c,2, tile(c,r));
                            }
                        /* case 3 */
                        } else if (board.tile(c,3) != null && board.tile(c,2) != null && board.tile(c,1) != null) {
                            if (board.tile(c,r) != null && board.tile(c,1).value() == board.tile(c,r).value()) {
                                board.move(c, 1, tile(c, r));
                                score += board.tile(c, 1).value();
                                changed = true;
                                tileChange(c, 1, bigList);
                            }
                        } else if (board.tile(c,1) == null && board.tile(c,2) != null){
                            if (board.tile(c,2).value() == board.tile(c,r).value()) {
                                board.move(c, 2, tile(c, r));
                                score += board.tile(c, 2).value();
                                changed = true;
                                tileChange(c, 3, bigList);
                            } else {
                                board.move(c,1, tile(c,r));
                                changed = true;
                            }

                        }
                    }

                }
            }
            r -= 1;
        }
        if (side != Side.NORTH) {
            board.setViewingPerspective(Side.NORTH);
        }

        changed = true;
        checkGameOver();
        if (changed) {
            setChanged();
        }
        return changed;
    }


    public static void tileChange (int c, int r, ArrayList j) {
        ArrayList<Integer> number = new ArrayList<>();
        number.add(c);
        number.add(r);

        j.add(number);

    }
    /* returns TRUE if a tile has already been merged */
    public static boolean checkTileChange(int c, int r, ArrayList j) {
        ArrayList<Integer> number = new ArrayList<>();
        number.add(c);
        number.add(r);

        for (int i = 0; i < j.size(); i += 1) {
            if (j.get(i).equals(number)) {
                return true;
            }
        }
        return false;

    }
    public static boolean checkAboveNull(Board b, int c, int r) {
        return b.tile(c, r + 1) == null;
    }
    /* Check the value of the 1 tile above */
    public static boolean checkValue(Board board, int c, int r) {
        if (board.tile(c,r+1) == null) {
            return false;
        }
        if (board.tile(c,r) != null) {
            return board.tile(c, r).value() == board.tile(c, r + 1).value();
        }
        return false;
    }


    /** Checks if the game is over and sets the gameOver variable
     *  appropriately.
     */
    private void checkGameOver() {
        gameOver = checkGameOver(board);
    }

    /** Determine whether game is over. */
    private static boolean checkGameOver(Board b) {
        return maxTileExists(b) || !atLeastOneMoveExists(b);
    }

    /** Returns true if at least one space on the Board is empty.
     *  Empty spaces are stored as null.
     * */
    public static boolean emptySpaceExists(Board b) {
        // TODO: Fill in this function.

        int c = 0;
        int r = 0;

        while (c != 3 || r != 3){

            if (checkIfSpaceIsNull(b, c, r)) {
                return true;
            }
            c += 1;
            if (c == 3 && r == 3) {
                return checkIfSpaceIsNull(b, c, r);
            }

            if (c > 3) {
                c = 0;
                r += 1;
            }

        }
        return false;
    }

    public static boolean checkIfSpaceIsNull(Board b,int c, int r) {
        return b.tile(c, r) == null;
    }

    /**
     * Returns true if any tile is equal to the maximum valid value.
     * Maximum valid value is given by MAX_PIECE. Note that
     * given a Tile object t, we get its value with t.value().
     */
    public static boolean maxTileExists(Board b) {
        // TODO: Fill in this function.

        int c = 0;
        int r = 0;

        while (c != 3 || r != 3){

            if (maxTileHelper(b, c, r)) {
                return true;
            }
            c += 1;
            if (c == 3 && r == 3) {
                return maxTileHelper(b, c, r);
            }
            if (c > 3) {
                c = 0;
                r += 1;
            }
        }
        return false;
    }
    public static boolean maxTileHelper(Board b,int c, int r) {
        if (b.tile(c,r) != null){
            return b.tile(c,r).value() == MAX_PIECE;
        }
        return false;
    }
    /**
     * Returns true if there are any valid moves on the board.
     * There are two ways that there can be valid moves:
     * 1. There is at least one empty space on the board.
     * 2. There are two adjacent tiles with the same value.
     */
    public static boolean atLeastOneMoveExists(Board b) {
        // TODO: Fill in this function.

        /* Check for empty spaces, if there are any, return true */
        if (emptySpaceExists(b)) {
            return true;
        }

        /* check for adjacent tiles with the same value */
        int c = 0;
        int r = 0;


        while (c < 3 || r < 3) {
            if (Top(b, c, r)) {
                return true;
            }
            if (Bottom(b, c,r)) {
                return true;
            }
            if (Right(b, c, r)) {
                return true;
            }
            if (Left(b, c, r)) {
                return true;
            }
            c += 1;
            if (c == 3 && r == 3) {
                if (Top(b, c, r)) {
                    return true;
                }
                if (Bottom(b, c,r)) {
                    return true;
                }
                if (Right(b, c, r)) {
                    return true;
                }
                if (Left(b, c, r)) {
                    return true;
                }
            }
            if (c > 3) {
                c = 0;
                r += 1;
            }
        }
        return false;
    }
        public static boolean outOfBound(int c, int r) {
            int maxC = 3;
            int maxR = 3;

            int minC = 0;
            int minR = 0;


            return c > maxC || r > maxR || c < minC || r < minR;
        }
        public static boolean Top(Board b, int c, int r) {
            if (!outOfBound(c, r + 1)) {
                return oneMoveHelper(b, c, r, c,r + 1);
            }
            return false;
        }
        public static boolean Bottom(Board b, int c, int r) {
            if (!outOfBound(c, r - 1)) {
                return oneMoveHelper(b, c, r, c, r - 1);
            }
            return false;
        }

        public static boolean Right(Board b,int c, int r) {
            if (!outOfBound(c+1, r)) {
                return oneMoveHelper(b, c, r, c+1, r);
            }
            return false;
        }

        public static boolean Left(Board b,int c, int r) {
            if (!outOfBound(c-1, r)) {
                return oneMoveHelper(b, c, r, c-1, r );
            }
            return false;
        }
    public static boolean oneMoveHelper(Board b, int c, int r, int nextC, int nextR) {
        return b.tile(c, r).value() == b.tile(nextC, nextR).value();
    }

    @Override
     /** Returns the model as a string, used for debugging. */
    public String toString() {
        Formatter out = new Formatter();
        out.format("%n[%n");
        for (int row = size() - 1; row >= 0; row -= 1) {
            for (int col = 0; col < size(); col += 1) {
                if (tile(col, row) == null) {
                    out.format("|    ");
                } else {
                    out.format("|%4d", tile(col, row).value());
                }
            }
            out.format("|%n");
        }
        String over = gameOver() ? "over" : "not over";
        out.format("] %d (max: %d) (game is %s) %n", score(), maxScore(), over);
        return out.toString();
    }

    @Override
    /** Returns whether two models are equal. */
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        } else if (getClass() != o.getClass()) {
            return false;
        } else {
            return toString().equals(o.toString());
        }
    }

    @Override
    /** Returns hash code of Model’s string. */
    public int hashCode() {
        return toString().hashCode();
    }
}
