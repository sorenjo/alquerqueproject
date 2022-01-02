public class MinimaxTest{
    /*
     * Test of MinimaxTree public methods.
     * Using our own implementations of Board and Move.
     */
    public static void main(String[] args){
        Board board = new Board();
        //Test of iterator in for each loop.
        MinimaxTree boardTree = new MinimaxTree(board, 2, true);
        for (Board b: boardTree){
            System.out.println("---------------");
            printAlquerque(b);
        }
    
        //Test of nextMove()
        Move bestMove = Minimax.nextMove(board, 1, true);
        System.out.println( "(" + bestMove.from() + ", " + bestMove.to() + ")");
    }
    public static void printAlquerque(Board board){
        char[] pieces = new char[26];
        for (int i = 1; i <= 25; i++)
            pieces[i] = ' ';
        for (int e: board.white())
            pieces[e] = 'W';
        for (int e: board.black())
            pieces[e] = 'B';

        for (int j = 1; j <= 25; j = j+5){
            for (int i = j; i < j+4; i++)
                System.out.print(pieces[i] + "-");
                System.out.print(pieces[j+4] + "\n");
            // Print diagonal lines 4 times and change between direction every line
            if (j <= 20){
                if (j%2 == 0)
                    System.out.println("|/|\\|/|\\|");
                else
                    System.out.println("|\\|/|\\|/|");
            }
        }
    }
}
