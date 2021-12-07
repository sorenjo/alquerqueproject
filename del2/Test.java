public class Test {
  public static void main(String[] args) {

    // First column black move
    // first row 
    // single move
    FirstColumnFirstRowSingleMove();
    System.out.println();
    FirstColumnFirstRowAttack();
    System.out.println();


    //attack move
  }

    /*
     * Test legality of single move from first column first row
     */
    private static void FirstColumnFirstRowSingleMove(){
        Board board = new Board( new int[]{25}, new int[]{1}, Board.piece.BLACK);
        Move[] moves = board.legalMoves();
        PrintAlquerque(board);
        System.out.println("On this board, the following moves are legal");
        System.out.println("(from, to)");
        for (Move m : moves)
            System.out.println(m.toString());
        System.out.print("The legality of (1,4) is ");
        System.out.println(board.isLegal(new Move(1,4)));
    }

    /*
     * Test legality of attack from first column first row
     */
    private static void FirstColumnFirstRowAttack(){
        Board board = new Board( new int[]{2}, new int[]{1}, Board.piece.BLACK);
        Move[] moves = board.legalMoves();
        PrintAlquerque(board);
        System.out.println("On this board, the following moves are legal");
        System.out.println("(from, to)");
        for (Move m : moves)
            System.out.println(m.toString());
        System.out.print("The legality of (1,6) is ");
        System.out.println(board.isLegal(new Move(1,6)));
    }

    private static void PrintAlquerque(Board board){
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
