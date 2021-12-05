import java.util.ArrayList;
import java.util.Arrays;

public class Board{
  private final piece[] board;
  private int whiteCount, blackCount;
  private piece turn;
  private static int finishedGames = 0;
  public static enum piece {
    BLACK,
    WHITE,
    FREE
  }

  /*
  * Setup a new board by initializing class attributes to Alquerques start values
  */
  public Board() {
    this.whiteCount = 12;
    this.blackCount = 12;
    this.turn = piece.WHITE;

    this.board = new piece[26];
    this.board[13] = piece.FREE;
    for (int i = 1; i <= 12; i++)
      this.board[i] = piece.BLACK;
    for (int i = 14; i <= 25; i++)
      this.board[i] = piece.WHITE;
  }

  /*
  * Setup a new board with positions given in arrays and set current turn
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
  * Checks if game is over and increments finishedGames if so
  */
  public boolean isGameOver() {
    boolean isGameOver = (black().length == 0 || white().length == 0 || legalMoves().length == 0);

    if (isGameOver)
      this.finishedGames++;

    return isGameOver;
  }

  /*
  * Returns opposite players piece
  */
  private piece oppositePlayer() {
    return (this.turn == piece.WHITE ? piece.BLACK : piece.WHITE);
  }

  /*
  * Returns absolute value of x
  */
  private int abs(int x) {
    return (x < 0 ? -x : x);
  }

  /*
  * Checks if move is a diagonal right attack
  */
  private boolean diagonalRight(int from, int to){
    return (from % 2 == 1 && (from - 8 == to || from + 12 == to));
  }

  /*
  * Checks if move is a diagonal left attack
  */
  private boolean diagonalLeft(int from, int to){
    return (from % 2 == 1 && (from + 8 == to || from - 12 == to));
  }

  /*
  * Sets killed piece's position to FREE and decreases its player's piece count by one
  */
  private void kill(int index) {
    if (this.board[index] == piece.WHITE)
      this.whiteCount--;
    else
      this.blackCount--;

    this.board[index] = piece.FREE;
  }

  /*
  * Checks if move is valid on the current board
  */
  public boolean isLegal(Move move) {
    boolean isLegal = false;
    int from = move.from(), to = move.to();
    final int positionBetween = (from + to) / 2;
    int column = from % 5;

    // Return early if 'from' is not current players piece, or 'to' is not free
    if ((this.board[from] != this.turn) || (this.board[to] != piece.FREE))
      return false;

    // Mirror logic if white player
    if (this.turn == piece.WHITE) {
      from = 26 - from;
      to = 26 - to;
      column = from % 5;
    }

    // Forward move (up for white, down for black)
    if (from + 5 == to)
      isLegal = true;

    // Check if move is diagonal
    else if (from + 4 == to || from + 6 == to) {
      // Switch case to handle logic in specific columns
      switch (column) {
        // First column
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

        // Last column
        case 0:
          // Check for left diagonal move
          if ((from % 2 == 1) && (from + 4 == to))
            isLegal = true;
          break;
      }
    }

    // Only enter attack logic if piece in between 'from' and 'to', is an enemy piece
    else if (this.board[positionBetween] == oppositePlayer()) {
      // Switch case to handle attack moves in specific columns
      switch (column) {
        // First and second columns
        case 1:
        case 2:
          if (diagonalRight(from, to) || abs(from - to) == 10 || from + 2 == to)
            isLegal = true;
          break;

        // Third column
        case 3:
          if (diagonalLeft(from, to) || diagonalRight(from, to) || abs(from - to) == 10 || abs(from - to) == 2)
            isLegal = true;
          break;

        // Fourth and fifth columns
        case 4:
        case 0:
          if (diagonalLeft(from, to) || abs(from - to) == 10 || from - 2 == to)
            isLegal = true;
          break;
      }
    }

    return isLegal;
  }

  /*
  * Returns an array of all current player's possible moves
  */
  public Move[] legalMoves() {
    final ArrayList<Move> legalMoves = new ArrayList<Move>();

    /*** int[] possibleTos = {1, 2, 9, 10, 11, 18, 20, 22};

    for (int i = 1; i <= 25; i++)
      for (int j = -1; j <= 1; j = j + 2)
        for (int to : possibleTos){
          int t = i+j*i;
          if (t >= 1 && t <= 25) {
            Move move = new Move(i, t);
            if (isLegal(move))
              legalMoves.add(move);
          }
        } ***/

    for (int i = 1; i <= 25; i++)
      if (this.board[i] == this.turn)
        for (int j = 1; j <= 25; j++) {
          Move move = new Move(i, j);
          if (isLegal(move))
            legalMoves.add(move);
        }

    final Move[] moves = new Move[legalMoves.size()];
    for (int i = 0; i < moves.length; i++)
      moves[i] = legalMoves.get(i);

    return moves;
  }

  /*
  * Moves piece using a Move-instance
  */
  public void move(Move move) {
    int from = move.from();
    int to = move.to();

    this.board[from] = piece.FREE;
    this.board[to] = this.turn;

    // Since move must be legal, if the absolute distance is 2, 8, 10 or 12 it must be an attack
    int distance = abs(from - to);
    if (distance == 2 || distance == 8 || distance == 10 || distance == 12)
      kill((from + to) / 2);

    this.turn = oppositePlayer();
  }

  /*
  * Returns an integer array of all black piece's positions
  */
  public int[] black() {
    final int[] blacks = new int[this.blackCount];
    int k = 0;
    for (int i = 1; i <= 25; i++)
      if (this.board[i] == piece.BLACK) {
        blacks[k] = i;
        k = k + 1;
      }
    return blacks;
  }

  /*
  * Returns an integer array of all white piece's positions
  */
  public int[] white() {
    final int[] whites = new int[this.whiteCount];
    int k = 0;
    for (int i = 1; i <= 25; i++)
      if (this.board[i] == piece.WHITE) {
        whites[k] = i;
        k = k + 1;
      }
    return whites;
  }

  /*
  * Getter method for 'finishedGames'
  */
  public int finishedGames() {
    return Board.finishedGames;
  }

  /*
  * Returns a copy of this board
  */
  public Board copy() {
    return new Board(white(), black(), this.turn);
  }

  /*
  * Checks wether this board is equal to another object
  */
  @Override
  public boolean equals(Object object) {
    if (object == this)
      return true;
    if (object == null)
      return false;
    if (!(object instanceof Board))
      return false;

    final Board board = (Board) object;
    return (Arrays.equals(board.board, this.board) && board.whiteCount == this.whiteCount &&
            board.blackCount == this.blackCount && board.turn == this.turn);
  }

  /*
  * Returns a hash of this board
  */
  @Override
  public int hashCode() {
    // Choose arbitrary prime and non-zero result
    final int prime = 31;
    int result = 17;

    result = prime * result + ((this.board) == null ? 0 : this.board.hashCode());
    result = prime * result + this.whiteCount;
    result = prime * result + this.blackCount;
    result = prime * result + ((this.turn) == null ? 0 : this.turn.hashCode());

    return result;
  }
}
