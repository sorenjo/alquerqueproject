public class Test {
  public static void main(String[] args) {
    Board board = new Board();
    Board.piece test;
    for (Board.piece p : board.board) {
      test = p;
      System.out.println(p);
    }
    test = Board.piece.FREE;
    for (Board.piece p : board.board)
      System.out.println(p);

    System.out.print(test);
  }
}
