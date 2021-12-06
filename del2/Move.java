public class Move {
  private int from, to;

  /*
  * Constructor to initialize move attributes
  */
  public Move(int from, int to) {
    this.from = from;
    this.to = to;
  }

  /*
  * Getter method for 'from'
  */
  public int from() {
    return this.from;
  }

  /*
  * Getter method for 'to'
  */
  public int to() {
    return this.to;
  }

  /*
  * Returns string representation of this move
  */
  @Override
  public String toString() {
    return ("(" + this.from + ", " + this.to + ")");
  }

  /*
  * Checks wether this move is equal to another object
  */
  @Override
  public boolean equals(Object object) {
    if (object == this)
      return true;
    if (object == null)
      return false;
    if (!(object instanceof Move))
      return false;

    final Move move = (Move) object;
    return ((move.from == this.from) && (move.to == this.to));
  }

  /*
  * Returns a hash of this board
  */
  @Override
  public int hashCode() {
    return this.from + 31 * this.to;
  }
}
