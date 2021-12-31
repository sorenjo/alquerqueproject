public class Minimax{

    /*
     * Creates a minimax tree for the given board with the given depth and returns the best move for that board
     */
    public static Move nextMove(Board board, int depth, boolean isWhite){
        MinimaxTree boardTree = new MinimaxTree(board, depth, isWhite);
        return boardTree.nextMove();
    }
}
