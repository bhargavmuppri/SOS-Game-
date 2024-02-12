import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class Game implements Serializable {
    private int boardSize;
    private String[][] gameBoard;
    private boolean isBluePlayersTurn;

    private int bluePlayerSOSCount;

    private int redPlayerSOSCount;

    private Player bluePlayer;

    private Player redPlayer;

    private List<Move> moves;


    public Game(int boardSize) {
        reset(boardSize);
    }

    public Player getBluePlayer() {
        return bluePlayer;
    }

    public void setBluePlayer(Player bluePlayer) {
        this.bluePlayer = bluePlayer;
    }

    public Player getRedPlayer() {
        return redPlayer;
    }

    public void setRedPlayer(Player redPlayer) {
        this.redPlayer = redPlayer;
    }

    public int getBluePlayerSOSCount() {
        return bluePlayerSOSCount;
    }


    public int getRedPlayerSOSCount() {
        return redPlayerSOSCount;
    }


    public void reset(int boardSize) {
        this.boardSize = boardSize;
        this.gameBoard = new String[boardSize][boardSize];
        this.isBluePlayersTurn = true;
        bluePlayerSOSCount = 0;
        redPlayerSOSCount = 0;
        initializeBoard();
        moves = new ArrayList<>();
    }

    public void initializeBoard() {
        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                gameBoard[row][col] = "";
            }
        }
    }

    public abstract void makeMove(int row, int col, String symbol);

    public boolean isBluePlayersTurn() {
        return isBluePlayersTurn;
    }

    public String[][] getGameBoard() {
        return gameBoard;
    }


    public void setBoardSize(int boardSize) {
        this.boardSize = boardSize;
    }

    public int getBoardSize() {
        return boardSize;
    }

    public void setBluePlayersTurn(boolean bluePlayersTurn) {
        isBluePlayersTurn = bluePlayersTurn;
    }


    boolean isBoardSizeTextNumeric(String txtBoardSize) {
        try {
            Integer.parseInt(txtBoardSize);
        } catch (NumberFormatException ex) {
            if (txtBoardSize.length() > 0) {
                return false;
            }
        }
        return true;
    }

    boolean isBoardSizeGreaterThanTwo(int newSize) {
        return newSize > 2;
    }

    boolean checkSequenceAt(int row, int col) {

        boolean foundSOS = false;

        if (checkForwardHorizontalSOS(row, col) ||
                checkBackwardHorizontalSOS(row, col) ||
                checkBottomVerticalSOS(row, col) ||
                checkTopVerticalSOS(row, col) ||
                checkDiagonalSOSFromTopRight(row, col) ||
                checkDiagonalSOSFromTopLeft(row, col) ||
                checkDiagonalSOSFromBottomRight(row, col) ||
                checkDiagonalSOSFromBottomLeft(row, col)
        ) {
            foundSOS = true;
        }

        return foundSOS;

    }


    public boolean checkForwardHorizontalSOS(int row, int col) {
        if (col >= 0 && col + 2 < boardSize &&
                gameBoard[row][col].equals("S") &&
                gameBoard[row][col + 1].equals("O") &&
                gameBoard[row][col + 2].equals("S")) {
            incrementSOSCount();
            return true;
        }


        return false;
    }


    public boolean checkBackwardHorizontalSOS(int row, int col) {

        if (col < boardSize && col - 2 >= 0 &&
                gameBoard[row][col].equals("S") &&
                gameBoard[row][col - 1].equals("O") &&
                gameBoard[row][col - 2].equals("S")) {
            incrementSOSCount();
            return true;
        }


        return false;
    }


    public boolean checkTopVerticalSOS(int row, int col) {

        if (row < boardSize && row - 2 >= 0 &&
                gameBoard[row][col].equals("S") &&
                gameBoard[row - 1][col].equals("O") &&
                gameBoard[row - 2][col].equals("S")) {
            incrementSOSCount();
            return true;
        }

        return false;
    }

    public boolean checkBottomVerticalSOS(int row, int col) {
        if (row >= 0 && row + 2 < boardSize &&
                gameBoard[row][col].equals("S") &&
                gameBoard[row + 1][col].equals("O") &&
                gameBoard[row + 2][col].equals("S")) {
            incrementSOSCount();
            return true;
        }

        return false;
    }

    private void incrementSOSCount() {
        if (isBluePlayersTurn) {
            bluePlayerSOSCount++;
        } else {
            redPlayerSOSCount++;
        }
    }

    public boolean checkDiagonalSOSFromTopLeft(int row, int col) {
        return checkDiagonalSOSDirection(row, col, 1, 1);
    }

    public boolean checkDiagonalSOSFromTopRight(int row, int col) {
        return checkDiagonalSOSDirection(row, col, 1, -1);
    }

    public boolean checkDiagonalSOSFromBottomLeft(int row, int col) {
        return checkDiagonalSOSDirection(row, col, -1, 1);
    }

    public boolean checkDiagonalSOSFromBottomRight(int row, int col) {
        return checkDiagonalSOSDirection(row, col, -1, -1);
    }

    public boolean checkDiagonalSOSDirection(int row, int col, int rowIncrement, int colIncrement) {
        int currentRow = row;
        int currentCol = col;

        String sequence = "";

        for (int i = 0; i < 3; i++) {
            if (currentRow < 0 || currentRow >= boardSize || currentCol < 0 || currentCol >= boardSize)
                return false;

            sequence += gameBoard[currentRow][currentCol];

            currentRow += rowIncrement;
            currentCol += colIncrement;
        }

        if (sequence.equals("SOS")) {
            incrementSOSCount();
            return true;
        }
        return false;
    }

    public abstract boolean isGameOver();

    public boolean isBoardFull() {

        for (int row = 0; row < boardSize; row++) {

            for (int col = 0; col < boardSize; col++) {
                if (gameBoard[row][col].equals(""))
                    return false;
            }
        }
        return true;
    }

    public List<Move> getMoves() {
        return moves;
    }

    public void setMoves(List<Move> moves) {
        this.moves = moves;
    }

    @Override
    public String toString() {
        return "SOS Game{" +
                "\nmode=" + (this instanceof SimpleGame ? "simple" : "general") +
                "\nboardSize=" + boardSize +
                "\nbluePlayer=" + bluePlayer +
                "\nredPlayer=" + redPlayer +
                "\nmoves=" + moves +
                '}';
    }
}
