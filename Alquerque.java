import java.util.Scanner;

public class Alquerque{

    private static final int WHITEWON = 1;
    private static final int BLACKWON = 2;
    private static final int DRAW = 3;
    private static String blackName;
    private static String whiteName;
    private static Board board;
    private static Scanner reader;
    private static boolean isBlackComputer;
    private static boolean isWhiteComputer;

    public static void main(String[] args) {
        init();
        boolean whitesTurn = true;
        Move move = new Move(100, 100);
        // Main game loop
        while (!board.isGameOver()){
            boolean isLegal = false;

            printBoard();
            String currentPlayer = whitesTurn ? whiteName : blackName;

            // Check for computer players
            if (whitesTurn && isWhiteComputer){
                System.out.println("White computer's turn");
                move = Minimax.nextMove(board, 4, true);
                isLegal = true;
            }
            else if (!whitesTurn && isBlackComputer){
                System.out.println("Black computer's turn");
                move = Minimax.nextMove(board, 4, false);
                isLegal = true;
            }
            else // Human player
                System.out.print(currentPlayer + "'s turn. ");

            // Get some input representing a valid move
            while(!isLegal) {
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
            }
            board.move(move);
            whitesTurn = !whitesTurn;
        }
        System.out.println("Game is over. Final game state:");
        printBoard();
        if (whoWon() == WHITEWON)
            System.out.println("White has won the game");
        else if (whoWon() == BLACKWON)
            System.out.println("Black has won the game");
        else
            System.out.println("The game is a draw");
    }

    /*
     * Initializations.
     * Names of players.
     */
    private static void init(){
        reader = new Scanner(System.in);
        board = new Board();
        System.out.println("Please enter name of white player: (blank for computer)");
        whiteName = reader.nextLine().trim();

        if (whiteName.equals(""))
          isWhiteComputer = true;

        else
          isWhiteComputer = false;

        System.out.println("Please enter name of black player: (blank for computer)");
        blackName = reader.nextLine().trim();

        if (blackName.equals(""))
          isBlackComputer = true;

        else
          isBlackComputer = false;
    }

    /*
     * Pretty prints the board
     */
    private static void printBoard() {
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

    /*
     * Dertermines who has won or if the game is a draw.
     */
    private static int whoWon() {
        if (board.black().length == 0)
            return WHITEWON;
        else if (board.white().length == 0)
            return BLACKWON;
        else
            return DRAW;
    }
}
