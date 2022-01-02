import java.util.Iterator;
import java.util.Stack;
import java.util.NoSuchElementException;
import java.util.Objects;

public class MinimaxTree implements Iterable<Board> {
    private Node root;
    private int[] scores;

    /*
     * Construct a new minimax tree.
     * Precondition: depth > 0
     */
    public MinimaxTree(Board board, int depth, boolean isWhite) {
        root = new Node(board, depth, true);
        scores = new int[root.children.length];
        for (int i = 0; i < scores.length; i++)
            scores[i] = root.children[i].minimax(isWhite);
    }

    /*
     * Private inner class for representing nodes in a tree.
     */
    private static class Node {
        private Board board;
        private Node[] children;
        private int depth;
        private boolean isMax;

        /*
         * Constructs a new node.
         * precondition depth > 0
         */
        private Node(Board board, int depth, boolean isMax) {
            this.depth = depth;
            this.board = board;
            this.isMax = isMax;

            Move[] legalMoves = board.legalMoves();
            if (depth > 0 && legalMoves.length != 0) { // edgecase: if there are no legal moves for a board it's children will point to null instead of an empty children array.
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
         * Recursively compute the minimax value of this node, given as either the minimum or maximum of children minimax values.
         */
        private int minimax(boolean isWhite) {
            if (children == null)
                return heuristic(isWhite);

            else {
                int[] scores = new int[children.length];
                for (int i = 0; i < children.length; i++)
                    scores[i] = children[i].minimax(isWhite);

                int min = scores[0];
                int max = scores[0];

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
         * Compute a rating for this node's board.
         */
        private int heuristic(boolean isWhite) {
            return isWhite ? board.white().length - board.black().length :
                             board.black().length - board.white().length;
        }
    }

    /*
     * Returns best move for current board
     */
    public Move nextMove() {
        int index = 0;
        for (int i = 1; i < scores.length; i ++)
            if (scores[i] > scores[index]){
                index = i;
            }

        return root.board.legalMoves()[index];
    }

    /*
     * Returns an iterator object over this tree
     */
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
         * Returns the next board of this tree iterator.
         * Precondition: hasNext() == true
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
    /*
     * Test of private methods.
     * Using our implementations of Board and Move.
     */
    public static void main(String[] args){
        //test of heuristic method
        Node testNode = new Node(new Board(new int[]{24,25}, new int[]{1,2,3,4}, Board.piece.BLACK), 0, true);
        MinimaxTest.printAlquerque(testNode.board);
        System.out.println("heuristic rating (for black) of this board is: " + testNode.heuristic(false)); //should give 2
        System.out.println("heuristic rating (for white) of this board is: " + testNode.heuristic(true)); //should give -2
        System.out.println();
        //test of scores[] minimax method
        MinimaxTree testTree= new MinimaxTree(new Board(new int[]{7,8,9,12,14,17,18,19}, new int[]{13}, Board.piece.BLACK), 1, false);
        MinimaxTest.printAlquerque(testTree.root.board);
        for (int score: testTree.scores)
            System.out.println(score);//should all be -6
        /*
        for (Board b: testTree)
            MinimaxTest.printAlquerque(b);
        */





        /*for (int i = 1; i <= 7; i++){
            Node tree = new Node(new Board(), i, true);
            for (Node n: tree.children)
                System.out.println(tree.minimax(true));
        }
        */
    }
}
