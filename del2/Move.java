public class Move {
  private int from, to;

  public Move(int from, int to) {
    this.from = from;
    this.to = to;
  }

  public int from() {
    return this.from;
  }

  public int to() {
    return this.to;
  }

  public String toString() {
    return ("(" + this.from + ", " + this.to + ")");
  }
}
