import java.util.ArrayList;

public class Board{
    private piece[] board;
    private int whiteCount, blackCount;
    private piece turn;
    private static int finishedGames = 0;
    public enum piece {
        BLACK,
        WHITE,
        FREE
    }

    /*
    * Setup a new game by initializing class attributes
    */
    public Board() {
        board = new piece[26];
        board[13] = piece.FREE;

        for (int i = 1; i <=12; i++)
            board[i] = piece.BLACK;
        for (int i = 14; i <=25; i++)
            board[i] = piece.WHITE;

        whiteCount = 12;
        blackCount = 12;
        turn = piece.WHITE;
    }

    /*
    * Create a new board with positions given in arrays and turn
    */
    public Board(int[] whites, int[] blacks, piece turn) {
      this.whiteCount = whites.length;
      this.blackCount = blacks.length;
      this.turn = turn;

      this.board = new piece[26];
      for (int i = 1; i <= 25; i++)
        this.board[i] = piece.FREE;
      for (int i = 0; i < whites.length; i++)
        this.board[whites[i]] = piece.WHITE;
      for (int i = 0; i < blacks.length; i++)
        this.board[blacks[i]] = piece.BLACK;
    }

    /*
    * Check if game is over
    */
    public boolean isGameOver() {
      Board.finishedGames++;
      return legalMoves().length == 0;
    }

    private piece negate(piece piece) {
      if (piece == piece.WHITE)
        return piece.BLACK;
      else if (piece == piece.BLACK)
        return piece.WHITE;
      else
        return piece.FREE;
    }

    private int abs(int x) {
      return (x >= 0 ? x : -x);
    }

    private boolean isLegalDiagonalRight(int from, int to){
      return (from - 8 == to || from + 12 == to);
    }

    private boolean isLegalDiagonalLeft(int from, int to){
      return (from + 8 == to || from - 12 == to);
    }

    private boolean isLegalVertical(int from, int to) {
      return (abs(from - to) == 10);
    }

    private boolean isLegalHorizontal(int from, int to) {
      return (from - to == 2);
    }

    private void kill(int index) {
      if (board[index] == piece.WHITE) {
        whiteCount--;
        System.out.println("White killed");
      }
      else {
        blackCount--;
        System.out.println("Black killed");
      }
      board[index] = piece.FREE;
    }

    /*
    * Checks if move is valid on the current board
    */
    public boolean isLegal(Move move) {
      boolean isLegal = false;
      int originalFrom = move.from(), from = move.from();
      int originalTo = move.to(), to = move.to();
      int column;

      // Return early if 'from' is not current players piece, or 'to' is not free
      if ((this.board[from] != this.turn) || (this.board[to] != piece.FREE))
        return false;

      if (this.turn == piece.WHITE) {
        from = 26 - from;
        to = 26 - to;
        column = from % 5;
      }
      else
        column = originalFrom % 5;

      // Forward move (up for white, down for black)
      if (from + 5 == to)
        isLegal = true;

      else {
        switch (column) {
          // First column (left side)
          case 1:
            // Check for right diagonal move
            if ((from % 2 == 1) && (from + 6 == to))
              isLegal = true;
            break;

          // Three middle columns
          case 2:
          case 3:
          case 4:
            // Check for left and right diagonal moves
            if ((from % 2 == 1) && (from + 4 == to || from + 6 == to))
              isLegal = true;
            break;

          // Last column (right side)
          case 0:
            // Check for left diagonal move
            if ((from % 2 == 1) && (from + 4 == to))
              isLegal = true;

            break;
        }

        if (board[(originalFrom+originalTo)/2] == negate(this.turn)) {
          switch (column) {
            case 1:
            case 2:
              if ((from % 2 == 1 && isLegalDiagonalRight(from, to)) || abs(from - to) == 10 || from + 2 == to) {
                kill((originalFrom+originalTo)/2);
                isLegal = true;
              }
              break;
            case 3:
              if ((from % 2 == 1 && (isLegalDiagonalLeft(from, to) || isLegalDiagonalRight(from, to))) || abs(from - to) == 10 || abs(from - to) == 2) {
                kill((originalFrom+originalTo)/2);
                isLegal = true;
              }
              break;
            case 4:
            case 0:
              if ((from % 2 == 1 && isLegalDiagonalLeft(from, to)) || abs(from - to) == 10 || from - 2 == to) {
                kill((originalFrom+originalTo)/2);
                isLegal = true;
              }
              break;
          }
        }
      }
      return isLegal;
    }

    public Move[] legalMoves() {
      ArrayList<Move> legalMoves = new ArrayList<Move>();

      int[] possibleTos = {1, 2, 9, 10, 11, 18, 20, 22};

      for (int i = 1; i <= 25; i++)
        for (int j = -1; j <= 1; j = j + 2)
          for (int to : possibleTos){
            System.out.println(i + " " + (i + j*to));
            int t = i+j*i;
            if (t >= 1 && t <= 25) {
              System.out.println("sådan");
              Move move = new Move(i, t);
              if (isLegal(move))
                legalMoves.add(move);
            }
          }

      //Move[] moves = [legalMoves.size()]
      return legalMoves.toArray(new Move[0]);
    }

    public void move(Move move) {
        board[move.from()] = piece.FREE;
        board[move.to()] = turn;
        turn = negate(this.turn);
    }

    public int[] black() {
        int[] blacks = new int[blackCount];
        int k = 0;
        for (int i = 1; i <= 25; i++)
            if (board[i] == piece.BLACK){
                blacks[k] = i;
                k = k + 1;
            }
        return blacks;
    }

    public int[] white() {
      int[] whites = new int[whiteCount];
      int k = 0;
      for (int i = 1; i <= 25; i++)
          if (board[i] == piece.WHITE){
              whites[k] = i;
              k = k + 1;
          }
      return whites;
    }
}
