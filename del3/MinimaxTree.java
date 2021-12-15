import java.util.Iterator;

public class MinimaxTree implements Iterable<Board> {
    private Node root;
    boolean isWhite;


    public MinimaxTree(Board board, int depth, boolean isWhite){
        root = new Node(board.copy(), depth - 1, true); 
        this.isWhite = isWhite;

    }

    private static class Node{
        private Board board;
        private Node[] children;
        private int depth;
        private boolean isMax;

        /*
         * constructs a new node 
         */
        private Node(Board board, int depth, boolean isMax){
            this.depth = depth;
            this.board = board;
            this.isMax = isMax;
            if (depth > 0){
                Move[] legalMoves = board.legalMoves();
                children = new Node[legalMoves.length];
                for (int i = 0; i < children.length; i++){
                    Board newBoard = board.copy();
                    newBoard.move(legalMoves[i]);
                    children[i] = new Node(newBoard, depth - 1, !isMax);
                }
            }
            else {
                children = null;
            }
        }

        private int minimax(){
            int[] scores = new int[children.length];
            for (int i = 0; i < children.length; i++)
                scores[i] = children[i].minimax();
            int max = 0;
            if (isMax)
                return max(scores, true);
            else 
                return max(scores, false);
                
        }

        /*
         * Returns the maximum of an array if sign == true, otherwise the minumum.
         */
        private static int max(int[] v, boolean sign){
            int max = v[0];
            int s = sign? 1: -1;
            for (int i = 1; i < v.length; i++)
                if (max < s*v[i])
                    max = v[i];
            return max;
        }
    }

    public Iterator<Board> iterator(){
        return new TreeIterator<Board>();
    }

    private class TreeIterator<Board> implements Iterator<Board>{

        private TreeIterator(){
        }
        public boolean hasNext(){
            return true;
        }

        public Board next(){
            return null;
        }

    }

    public Move next(){
        return new Move(-1,-1);
        
    }

    private static int heuristic(Board board, Move move, boolean isWhite){
        return isWhite ? board.white().length - board.black().length: board.black().length - board.white().length;
    }
}
