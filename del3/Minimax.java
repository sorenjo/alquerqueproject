public class Minimax{
    public static Move nextMove(Board board, int depth, boolean isWhite){
        MinimaxTree boardTree = new MinimaxTree(board, depth, isWhite);
        Move bestMove = boardTree.nextMove();
        return bestMove;
        //return new Move(-1, -1);
    }
}
