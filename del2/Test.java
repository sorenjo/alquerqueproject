public class Test {
  public static void main(String[] args) {
    int[] blacks = {5, 9, 18};
    int[] whites = {11, 13};
    Board.piece turn = Board.piece.BLACK;

    Board board = new Board(whites, blacks, turn);
    Move move = new Move(Integer.parseInt(args[0]), Integer.parseInt(args[1]));

    Move[] moves = board.legalMoves();
    for (Move m : moves)
      System.out.println(m.toString());

    System.out.println(board.isLegal(move));
  }
}
