public class Board{
    public piece[] board;
    private int whiteCount, blackCount;
    private piece turn;
    public enum piece {
        BLACK,
        WHITE,
        FREE
    }

    public static void main(String[] args) {

    }

    public Board() {
        board = new piece[26];
        for (int i = 1; i <=12; i++)
            board[i] = piece.WHITE;
        board[13] = piece.FREE;
        for (int i = 14; i <=25; i++)
            board[i] = piece.BLACK;
        turn = piece.WHITE;
    }

    public boolean isGameOver() {
        return legalMoves().length == 0;
    }

    public boolean isLegal(Move move) {
      boolean isLegal = false;
      int from = move.from();
      int to = move.to();
      int direction;

      if (this.turn == piece.WHITE)
        direction = -1;
      else
        direction = 1;

      if (board[to] == piece.FREE || board[from] == this.turn)
        isLegal = true;
      else {
        // Forward move (up for white, down for black)
        if (from + 5*direction == to)
          isLegal = true;

        // Left side
        if (from % 5 == 1) {
          // Check for right diagonal move
          if ((from % 2 == 1) && (from + 6*direction == to))
              isLegal = true;
        }

        // Right side
        else if (from % 5 == 0) {
          // Check for left diagonal move
          if ((from % 2 == 1) && (from + 4*direction == to))
              isLegal = true;
        }

        // Three middle columns
        else {
          // Check for left and right diagonal moves
          if ((from % 2 == 1) && (from + 4*direction == to || from + 6*direction == to))
            isLegal = true;
        }
      }
      return isLegal;
    }

    public Move[] legalMoves() {
      return new Move[1];
    }

    public void move(Move move){
        board[move.from()] = piece.FREE;
        board[move.to()] = turn;
        turn = turn == piece.WHITE ? piece.BLACK : piece.WHITE;
    }

    public int[] black(){
        int[] blacks = new int[12];
        int k = 0;
        for (int i = 1; i <= 25; i++)
            if (board[i] == piece.BLACK){
                blacks[k] = i;
                k = k + 1;
            }
        return blacks;
    }

    public int[] white(){
      return new int[1];
    }
}
