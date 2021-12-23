import java.util.Iterator;
import java.util.Stack;
import java.util.NoSuchElementException;
import java.util.Objects;

public class MinimaxTree implements Iterable<Board> {
    private Node root;
    private int[] scores;
    private Board board;
    private boolean isWhite;


    public MinimaxTree(Board board, int depth, boolean isWhite) {
        root = new Node(board, depth, true);
        scores = new int[root.children.length];
        for (int i = 0; i < scores.length; i++)
            scores[i] = root.children[i].minimax();
        this.board = board;
        this.isWhite = isWhite;
    }

    private static class Node {
        private Board board;
        private Node[] children;
        private int depth;
        private boolean isMax;

        /*
         * Constructs a new node
         */
        private Node(Board board, int depth, boolean isMax) {
            this.depth = depth;
            this.board = board;
            this.isMax = isMax;
            Move[] legalMoves = board.legalMoves();
            if (depth > 0) { // edgecase: if there are no legal moves for a board it's children will point to null instead of an empty children array.
                children = new Node[legalMoves.length];
                for (int i = 0; i < children.length; i++) {
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
        private int minimax() {
            if (children == null)
                return heuristic();

            else { // children != null
                int[] scores = new int[children.length];
                for (int i = 0; i < children.length; i++)
                    scores[i] = children[i].minimax();

                int min = scores[0];
                int max = min;

                for (int i = 1; i < scores.length; i++) {
                    if (min > scores[i])
                        min = scores[i];
                    if (max < scores[i])
                        max = scores[i];
                }

                // Check if current node is max or min
                return isMax ? max : min;
            }
        }

        /*
         * Compute a rating for a given board
         */
        private int heuristic() {
            System.out.println(" " + (board.white().length - board.black().length));
            return isMax ? board.white().length - board.black().length : board.black().length - board.white().length; //TODO: ikke sikker på det her virker som det burde
        }
    }

    public Iterator<Board> iterator() {
        return new TreeIterator();
    }

    private class TreeIterator implements Iterator<Board> {

        private Stack<Node> nextNodes;

        /*
         * Create a new tree iterator
         */
        private TreeIterator() {
            nextNodes = new Stack<Node>();
            if (root != null)
                nextNodes.add(root);
        }

        /*
         * Determines whether this iterator has an element to return.
         */
        public boolean hasNext() {
            return (!nextNodes.empty());
        }

        /*
         * Returns the next board of this tree iterator
         */
        public Board next() {
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

    public Move nextMove() {
        System.out.println("Score of best move: " + root.minimax());
        int n = root.minimax();
        int i;

        // Skal på en eller anden måde få fat det rigtige move fra minimax, men den returnerer en score
        // 'i' skal sættes til det rigtige index i legalMoves ud fra scoren gemt i 'n'

        return board.legalMoves[i];
        //return new Move(-1,-1);
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
