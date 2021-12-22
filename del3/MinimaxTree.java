import java.util.Iterator;
import java.util.Stack;
import java.util.NoSuchElementException;
import java.util.Objects;

public class MinimaxTree implements Iterable<Board> {
    private Node root;
    private int[] scores;
    boolean isWhite;


    public MinimaxTree(Board board, int depth, boolean isWhite){
        root = new Node(board.copy(), depth, false); 
        this.isWhite = isWhite;
        scores = new int[root.children.length];
        for (int i = 0; i < scores.length; i++)
            scores[i] = root.children[i].minimax();
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
            Move[] legalMoves = board.legalMoves();
            if (depth > 0){ // edgecase: if there are no legal moves for a board it's children will point to null instead of an empty children array.
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

        /*
         * Assign scores for each of the leaves.
         */
        private int minimax(){
            if (children == null)
                return heuristic();
            else{ // children != null
                int[] scores = new int[children.length];
                for (int i = 0; i < children.length; i++)
                    scores[i] = children[i].minimax();
                if (isMax)
                    return extremum(scores, true);
                else 
                    return extremum(scores, false);
            }
        }

        /*
         * compute a rating for a given board
         */
        private int heuristic(){
            System.out.println(" " + (board.white().length - board.black().length));
            return isMax ? board.white().length - board.black().length: board.black().length - board.white().length; //TODO: ikke sikker på det her virker som det burde
        }
    }

    public Iterator<Board> iterator(){
        return new TreeIterator();
    }

    /*
     * Returns the maximum of an array if sign == true, otherwise the minimum.
     */
    private static int extremum(int[] v, boolean sign){
        int max = v[0];
        int s = sign? 1: -1;
        for (int i = 1; i < v.length; i++)
            if (max < s*v[i])
                max = v[i];
        return max;
    }

    private class TreeIterator implements Iterator<Board>{
        
        private Stack<Node> nextNodes;

        /*
         * Create a new tree iterator
         */
        private TreeIterator(){
            nextNodes = new Stack<Node>();
            if (root != null)
                nextNodes.add(root);
        }

        /*
         * determines whether this iterator has an element to return.
         */
        public boolean hasNext(){
            return (!nextNodes.empty());
        }

        /*
         * returns the next board of this tree iterator
         */
        public Board next(){
            if (nextNodes.empty())
                throw new NoSuchElementException("No more boards in this minimax tree");
            Node next = nextNodes.pop();
            Board board = next.board;
            if (next.children != null)
                for (Node child: next.children)
                    nextNodes.push(child);

            return board;
        }
    }

    public Move nextMove(){
        System.out.println("Score of best move: " + root.minimax());
        return new Move(-1,-1);
    }


    /*
     * Test of methods.
     */
    /*
    public static void main(String[] args){
        Board board = new Board();
        MinimaxTree boardTree = new MinimaxTree(board, 5, true);
        for (Board b: boardTree){
            System.out.println("-------------");
            MinimaxTest.printAlquerque(b);
        }

        System.out.println(root.minimax());
    }
    */
}
