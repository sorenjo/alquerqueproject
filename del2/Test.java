public class Test {
  public static void main(String[] args) {
    for (int i = 1; i <= 25; i++)
        for (int j = 1; j <= 25; j++)
            if (i != j)
                TestBoardMoves(new int[]{j}, new int[]{i}, Board.piece.BLACK);
  }


  /*
   * List legality of the moves for the turn on the board defined by the given arrays of positions for whites and blacks.
   * Precondition: no overlap of black's and white's positions, and 1 <= position <= 25.
   */
  private static void TestBoardMoves(int[] whites, int[] blacks, Board.piece turn){
    Board board = new Board(whites, blacks, turn);
    Move[] moves = board.legalMoves();
    PrintAlquerque(board);
    System.out.println("On this board, the following moves are legal");
    System.out.println("(from, to)");
      for (Move m : moves)
    System.out.println(m.toString());
    System.out.println();
  }

  /*
   * Determine legality of a move on the board defined by the given arrays of positions for whites and blacks.
   */
  private static void TestMove(int[] whites, int[] blacks, Board.piece turn, Move move){
    Board board = new Board(whites, blacks, turn);
    System.out.print("The legality of " + move + " is ");
    System.out.println(board.isLegal(move));
  }

 /*
  * pretty print given alquerque board.
  */
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
