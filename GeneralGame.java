public class GeneralGame extends Game {

    private boolean isGameOver;

    public GeneralGame(int boardSize) {
        super(boardSize);
    }

    @Override
    public void makeMove(int row, int col, String symbol) {
        super.getGameBoard()[row][col] = symbol;

    }

    @Override
    public boolean isGameOver() {
        isGameOver = isBoardFull();
        return isGameOver;
    }
}
