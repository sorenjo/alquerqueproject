public class Minimax{
    public static Move nextMove(Board board, int depth, boolean isWhite){
        MinimaxTree boardTree = new MinimaxTree(board, depth, isWhite);
        return boardTree.nextMove();
    }
}
