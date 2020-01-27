
import java.util.ArrayList;

/**
 *
 * @author Pavel
 */
public class Ai {

    static int search_depth;
    static float win_val = 1f;
    static float lose_val = -1f;
    static float tie_val = 0f;

    Board board;
    int difficulty;

    public Ai(Board board, int difficulty) {
        this.board = board;
        this.difficulty = difficulty;
    }

    public int makeTurn(int lastmove) {
        double maxValue = 2. * Integer.MIN_VALUE;
        int move = 0;
        Heuristic heuristic = new Heuristic();
        search_depth = heuristic.setRecurDepth(difficulty);

        ArrayList<Integer> columns = new ArrayList<Integer>();
        columns = heuristic.getColumns(board.getWidth(), difficulty, lastmove);
        for (Integer column : columns) {
            if (board.isValidMove(column)) {

                double value = moveValue(column);
                if (value > maxValue) {
                    maxValue = value;
                    move = column;
                    if (value == win_val) {
                        break;
                    }
                }
            }
        }

        board.makeMoveAI(move);
        return move;
    }

    double moveValue(int column) {

        board.makeMoveAI(column);
        double val = abMinMax(search_depth, Integer.MIN_VALUE, Integer.MAX_VALUE, false);
        board.undoMoveAI(column);
        return val;
    }

    double abMinMax(int depth, double alpha, double beta, boolean maximizingPlayer) {
        boolean hasWinner = board.hasWinner();

        if (depth == 0 || hasWinner) {
            double score = 0;
            if (hasWinner) {
                score = board.playerIsWinner() ? lose_val : win_val;
            } else {
                score = tie_val;
            }

            return score / (search_depth - depth + 1);
        }

        if (maximizingPlayer) {
            for (int column = 0; column < board.getWidth(); column++) {
                if (board.isValidMove(column)) {
                    board.makeMoveAI(column);
                    alpha = Math.max(alpha, abMinMax(depth - 1, alpha, beta, false));
                    board.undoMoveAI(column);
                    if (beta <= alpha) {
                        break;
                    }
                }
            }
            return alpha;
        } else {
            for (int column = 0; column < board.getWidth(); column++) {
                if (board.isValidMove(column)) {
                    board.makeMovePlayer(column);
                    beta = Math.min(beta, abMinMax(depth - 1, alpha, beta, true));
                    board.undoMovePlayer(column);
                    if (beta <= alpha) {
                        break;
                    }
                }
            }
            return beta;
        }
    }
}
