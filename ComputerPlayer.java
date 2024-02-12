import java.util.Random;

public class ComputerPlayer extends Player {

    private Move computerMove;

    Move getComputerMove(Game game) {

        for (int row = 0; row < game.getBoardSize(); row++) {
            for (int col = 0; col < game.getBoardSize(); col++) {
                if (game.getGameBoard()[row][col].equals("S")) {
                    if ((col + 1) < game.getBoardSize() && game.getGameBoard()[row][col + 1].equals("")) {
                        return computerMove = new Move(row, col + 1, "O");
                    } else if ((col + 2) < game.getBoardSize() && game.getGameBoard()[row][col + 2].equals("")) {
                        return computerMove = new Move(row, col + 2, "S");
                    } else if ((row + 1) < game.getBoardSize() && game.getGameBoard()[row + 1][col].equals("")) {
                        return computerMove = new Move(row + 1, col, "O");
                    } else if ((row + 2) < game.getBoardSize() && game.getGameBoard()[row + 2][col].equals("")) {
                        return computerMove = new Move(row + 2, col, "S");

                    }
                }
            }
        }

        Random random = new Random();
        int row, col;
        do {
            row = random.nextInt(game.getBoardSize());
            col = random.nextInt(game.getBoardSize());
        } while (!game.getGameBoard()[row][col].equals(""));
        int symbol = random.nextInt(2);
        return new Move(row, col, symbol == 0 ? "S" : "O");
    }

    @Override
    public String toString() {
        return "Computer Player";
    }
}
