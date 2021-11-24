public class Board{
    private piece[] board;
    enum piece {
        BLACK,
        WHITE,
        FREE
    }

    public Board() {
        board = new piece[26];
        for (int i = 1; i <=12; i++)
            board[i] = piece.WHITE;
        board[13] = piece.FREE;
        for (int i = 14; i <=25; i++)
            board[i] = piece.BLACK;
    }

    public boolean isGameOver() {
        if (!legalMoves.length == 0)
            return false;
        else
            return true;
    }
    
    public boolean isLegal() {
    }

    public Move[] legalMoves() {
    }

    public void move(Move move){
        board[move.from()] = piece.FREE;
        board[move.to()] = 
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
    }
}
