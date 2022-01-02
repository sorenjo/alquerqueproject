public class MinimaxTest{
    /*
     * Test of MinimaxTree public methods.
     * Using our own implementations of Board and Move.
     */
    public static void main(String[] args){
        //Test of iterator in for each loop.
        MinimaxTree boardTree = new MinimaxTree(new Board(), 1, true);
        for (Board b: boardTree){
            System.out.println("---------------");
            printAlquerque(b);
        }
        System.out.println();
    
        //Test of nextMove()
        Board board = new Board(new int[]{2}, new int[]{1}, Board.piece.BLACK);
        printAlquerque(board);
        System.out.print("Best move for black in above scenario: ");
        System.out.println(Minimax.nextMove(board, 2, false)); //should be (1,3)
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
