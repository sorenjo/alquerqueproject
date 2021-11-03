import java.util.Scanner;

public class Alquerque{

    private String blackName;
    private String whiteName;
    private Board board;
    private Scanner reader;

    public static void main(String[] args) {
        init();
        boolean whitesTurn = true;

        while (!board.isGameOver()){
            boolean isLegal = false;
            Move move;
            printBoard();
            String currentPlayer = whitesTurn ? whiteName : blackName;
            System.out.println(currentPlayer + "'s turn. Please enter your move,");
            do{
                System.out.println("Where do you want to move from?");
                int from = reader.nextInt();
                System.out.println("Where do you want to move to?");
                int to = reader.nextInt();

                move = new Move(from, to);

                if (!(from >= 1 && from <= 25) || !(to >= 1 && to <= 25))
                    System.out.println("You've unfortunately entered a move outside the playing board. Please enter a new move");
                else {
                    isLegal = board.isLegal(move);
                    if (!isLegal)
                        System.out.println("You've entered an illegal move");
                }

            } while (!isLegal);
            board.move(move);
            whitesTurn = !whitesTurn;
        }
    }

    /*
     * Initializations.
     * Names of players.
     */
    public static void init(){
        reader = new Scanner(System.in);
        board = new Board();
        System.out.println("Please enter name of black player: ");
        blackName = reader.nextLine().trim();

        System.out.println("Please enter name of white player: ");
        whiteName = reader.nextLine().trim();

    }
    public static void printBoard() {
        char[] pieces = new char[25];
        for (int i = 0; i < 25; i++)
            pieces[i] = ' ';
        for (int e: board.white())
            pieces[e-1] = 'W';
        for (int e: board.black())
            pieces[e-1] = 'B';

        for (int j = 0; j < 25; j = j + 5){
            for (int i = j; i < j + 4; i++)
                System.out.print(pieces[i] + "-");
                //System.out.print(i + "-");
            //System.out.print(j+4 + "\n");
            System.out.print(pieces[j+4] + "\n");
            if (j < 20){
                if (j%2 == 0)
                    System.out.println("|\\|/|\\|/|");
                else 
                    System.out.println("|/|\\|/|\\|");
            }
        }
    }
}
