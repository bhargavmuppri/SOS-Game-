import org.junit.Test;

import static org.junit.Assert.*;

public class GameTest {

    @Test
    public void testChooseBoardSize() {
        Game game = new SimpleGame(8);
        game.setBoardSize(10);
        assertEquals(10, game.getBoardSize());
    }

    @Test
    public void testBoardSizeEnteredIsNumeric() {
        Game game = new SimpleGame(8);
        assertEquals(false, game.isBoardSizeTextNumeric("a"));
        assertEquals(true, game.isBoardSizeTextNumeric("5"));

    }

    @Test
    public void testBoardSizeEnteredIsGreaterThanTwo() {
        Game game = new SimpleGame(8);
        assertEquals(false, game.isBoardSizeGreaterThanTwo(2));
        assertEquals(true, game.isBoardSizeGreaterThanTwo(5));
    }

    @Test
    public void testSimpleGameMode() {
        Game game = new SimpleGame(8);
        assertTrue(game instanceof SimpleGame);
    }

    @Test
    public void testGeneralGameMode() {
        Game game = new GeneralGame(8);
        assertTrue(game instanceof GeneralGame);

    }

    @Test
    public void testStartGameWithChosenSizeAndMode() {
        Game game = new SimpleGame(8);
        int boardSize = 9;
        game.reset(boardSize);
        assertEquals(boardSize, game.getBoardSize());
        assertEquals(false, game instanceof GeneralGame);
        assertEquals("", game.getGameBoard()[0][0]);
    }


    @Test
    public void testMakeSMoveInSimpleGame() {
        Game game = new SimpleGame(8);
        game.reset(8);
        game.makeMove(5, 0, "S");
        assertEquals("S", game.getGameBoard()[5][0]);
    }


    @Test
    public void testMakeOMoveInSimpleGame() {
        Game game = new SimpleGame(8);
        game.reset(8);
        game.makeMove(0, 2, "O");
        assertEquals("O", game.getGameBoard()[0][2]);
    }

    @Test
    public void testMakeSMoveInGeneralGame() {
        Game game = new GeneralGame(8);
        game.reset(8);
        game.makeMove(5, 0, "S");
        assertEquals("S", game.getGameBoard()[5][0]);
    }


    @Test
    public void testMakeOMoveInGeneralGame() {
        Game game = new GeneralGame(8);
        game.reset(8);
        game.makeMove(0, 2, "O");
        assertEquals("O", game.getGameBoard()[0][2]);
    }


    @Test
    public void testInitialBoardSize() {
        Game game = new SimpleGame(8);
        assertEquals(8, game.getBoardSize());
    }

    @Test
    public void testInitializeBoard() {
        Game game = new SimpleGame(8);
        game.initializeBoard();
        assertEquals("", game.getGameBoard()[0][0]);
    }

    @Test
    public void testInitialPlayerTurn() {
        Game game = new SimpleGame(8);
        assertTrue(game.isBluePlayersTurn());
    }


    @Test
    public void testResetGame() {
        Game game = new SimpleGame(8);
        game.makeMove(0, 0, "S");
        game.reset(10);
        assertEquals(10, game.getBoardSize());
        assertEquals("", game.getGameBoard()[0][0]);
    }


    // test game over in simple game mode

    @Test
    public void testIsSimpleGameOver_SOSWinner() {
        Game game = new SimpleGame(8);

        game.makeMove(0, 0, "S");
        game.makeMove(0, 1, "O");
        game.makeMove(0, 2, "S");

        boolean result = game.isGameOver();

        assertTrue(result);
    }


    @Test
    public void testIsSimpleGameOver_NotOver() {
        Game game = new SimpleGame(8);

        game.makeMove(0, 0, "S");
        game.makeMove(0, 1, "S");
        game.makeMove(0, 2, "S");

        boolean result = game.isGameOver();

        assertFalse(result);
    }


    @Test
    public void testIsSimpleGameOver_BoardFull() {
        Game game = new SimpleGame(3);

        game.makeMove(0, 0, "S");
        game.makeMove(0, 1, "S");
        game.makeMove(0, 2, "S");
        game.makeMove(1, 0, "S");
        game.makeMove(1, 1, "S");
        game.makeMove(1, 2, "S");
        game.makeMove(2, 0, "S");
        game.makeMove(2, 1, "S");
        game.makeMove(2, 2, "S");

        boolean result = game.isGameOver();

        assertTrue(result);
    }

    // test game over in General game mode

    @Test
    public void testIsGeneralGameOver_SOSSequence() {
        Game game = new GeneralGame(8);

        game.makeMove(0, 0, "S");
        game.makeMove(0, 1, "O");
        game.makeMove(0, 2, "S");

        boolean result = game.isGameOver();

        assertFalse(result);
    }


    @Test
    public void testIsGeneralGameOver_BoardFull() {
        Game game = new GeneralGame(3);

        game.makeMove(0, 0, "S");
        boolean sequence = game.checkSequenceAt(0, 0);
        assertFalse(sequence);
        game.makeMove(0, 1, "S");
        sequence = game.checkSequenceAt(0, 1);
        assertFalse(sequence);
        game.makeMove(0, 2, "S");
        sequence = game.checkSequenceAt(0, 2);
        assertFalse(sequence);
        game.makeMove(1, 0, "S");
        sequence = game.checkSequenceAt(1, 0);
        assertFalse(sequence);
        game.makeMove(1, 1, "S");
        sequence = game.checkSequenceAt(1, 1);
        assertFalse(sequence);
        game.makeMove(1, 2, "S");
        sequence = game.checkSequenceAt(1, 2);
        assertFalse(sequence);
        game.makeMove(2, 0, "S");
        sequence = game.checkSequenceAt(2, 0);
        assertFalse(sequence);
        game.makeMove(2, 1, "S");
        sequence = game.checkSequenceAt(2, 1);
        assertFalse(sequence);
        game.makeMove(2, 2, "S");
        sequence = game.checkSequenceAt(2, 2);
        assertFalse(sequence);


        boolean result = game.isGameOver();
        assertTrue(result);

        assertTrue(game.getBluePlayerSOSCount() == game.getRedPlayerSOSCount());

    }


    @Test
    public void testIsGeneralGameOver_BluePlayerWins() {
        Game game = new GeneralGame(3);

        game.makeMove(0, 0, "S");
        boolean sequence = game.checkSequenceAt(0, 0);
        assertFalse(sequence);
        game.makeMove(0, 1, "O");
        sequence = game.checkSequenceAt(0, 1);
        assertFalse(sequence);
        game.makeMove(0, 2, "S");
        sequence = game.checkSequenceAt(0, 2);
        assertTrue(sequence);
        game.makeMove(1, 0, "S");
        sequence = game.checkSequenceAt(1, 0);
        assertFalse(sequence);
        game.makeMove(1, 1, "S");
        sequence = game.checkSequenceAt(1, 1);
        assertFalse(sequence);
        game.makeMove(1, 2, "S");
        sequence = game.checkSequenceAt(1, 2);
        assertFalse(sequence);
        game.makeMove(2, 0, "S");
        sequence = game.checkSequenceAt(2, 0);
        assertFalse(sequence);
        game.makeMove(2, 1, "S");
        sequence = game.checkSequenceAt(2, 1);
        assertFalse(sequence);
        game.makeMove(2, 2, "S");
        sequence = game.checkSequenceAt(2, 2);
        assertFalse(sequence);


        boolean result = game.isGameOver();
        assertTrue(result);

        assertTrue(game.getBluePlayerSOSCount() > game.getRedPlayerSOSCount());

    }


    // check sos sequences in a simple game
    @Test
    public void testCheckSimpleGameForwardHorizontalSOS() {
        Game game = new SimpleGame(8);

        game.makeMove(0, 1, "S");
        game.makeMove(0, 2, "O");
        game.makeMove(0, 3, "S");
        boolean result = game.checkForwardHorizontalSOS(0, 1);

        assertTrue(result);

    }

    @Test
    public void testCheckSimpleGameBackwardHorizontalSOS() {
        Game game = new SimpleGame(8);

        game.makeMove(0, 3, "S");
        game.makeMove(0, 2, "O");
        game.makeMove(0, 1, "S");
        boolean result = game.checkBackwardHorizontalSOS(0, 3);

        assertTrue(result);

    }

    @Test
    public void testCheckSimpleGameTopVerticalSOS() {
        Game game = new SimpleGame(8);

        game.makeMove(2, 0, "S");
        game.makeMove(1, 0, "O");
        game.makeMove(0, 0, "S");
        boolean result = game.checkTopVerticalSOS(2, 0);

        assertTrue(result);

    }

    @Test
    public void testCheckSimpleGameBottomVerticalSOS() {
        Game game = new SimpleGame(8);

        game.makeMove(0, 0, "S");
        game.makeMove(1, 0, "O");
        game.makeMove(2, 0, "S");
        boolean result = game.checkBottomVerticalSOS(0, 0);

        assertTrue(result);

    }

    @Test
    public void testCheckSimpleGameDiagonalSOSFromTopLeft() {
        Game game = new SimpleGame(5);

        game.makeMove(0, 0, "S");
        game.makeMove(1, 1, "O");
        game.makeMove(2, 2, "S");

        boolean result = game.checkDiagonalSOSFromTopLeft(0, 0);

        assertTrue(result);
    }


    @Test
    public void testCheckSimpleGameDiagonalSOSFromTopRight() {
        Game game = new SimpleGame(5);

        game.makeMove(0, 4, "S");
        game.makeMove(1, 3, "O");
        game.makeMove(2, 2, "S");

        boolean result = game.checkDiagonalSOSFromTopRight(0, 4);

        assertTrue(result);
    }

    @Test
    public void testCheckSimpleGameDiagonalSOSFromBottomLeft() {
        Game game = new SimpleGame(5);

        game.makeMove(4, 0, "S");
        game.makeMove(3, 1, "O");
        game.makeMove(2, 2, "S");

        boolean result = game.checkDiagonalSOSFromBottomLeft(4, 0);

        assertTrue(result);
    }

    @Test
    public void testCheckSimpleGameDiagonalSOSFromBottomRight() {
        Game game = new SimpleGame(5);

        game.makeMove(4, 4, "S");
        game.makeMove(3, 3, "O");
        game.makeMove(2, 2, "S");

        boolean result = game.checkDiagonalSOSFromBottomRight(4, 4);

        assertTrue(result);
    }

    // check sos sequences in a general game
    @Test
    public void testCheckGeneralGameForwardHorizontalSOS() {
        Game game = new GeneralGame(8);

        game.makeMove(0, 1, "S");
        game.makeMove(0, 2, "O");
        game.makeMove(0, 3, "S");
        boolean result = game.checkForwardHorizontalSOS(0, 1);

        assertTrue(result);

    }

    @Test
    public void testCheckGeneralGameBackwardHorizontalSOS() {
        Game game = new GeneralGame(8);

        game.makeMove(0, 3, "S");
        game.makeMove(0, 2, "O");
        game.makeMove(0, 1, "S");
        boolean result = game.checkBackwardHorizontalSOS(0, 3);

        assertTrue(result);

    }

    @Test
    public void testCheckGeneralGameTopVerticalSOS() {
        Game game = new GeneralGame(8);

        game.makeMove(2, 0, "S");
        game.makeMove(1, 0, "O");
        game.makeMove(0, 0, "S");
        boolean result = game.checkTopVerticalSOS(2, 0);

        assertTrue(result);

    }

    @Test
    public void testCheckGeneralGameBottomVerticalSOS() {
        Game game = new GeneralGame(8);

        game.makeMove(0, 0, "S");
        game.makeMove(1, 0, "O");
        game.makeMove(2, 0, "S");
        boolean result = game.checkBottomVerticalSOS(0, 0);

        assertTrue(result);

    }

    @Test
    public void testCheckGeneralGameDiagonalSOSFromTopLeft() {
        Game game = new GeneralGame(5);

        game.makeMove(0, 0, "S");
        game.makeMove(1, 1, "O");
        game.makeMove(2, 2, "S");

        boolean result = game.checkDiagonalSOSFromTopLeft(0, 0);

        assertTrue(result);
    }

    @Test
    public void testCheckGeneralGameDiagonalSOSFromTopRight() {
        Game game = new GeneralGame(5);

        game.makeMove(0, 4, "S");
        game.makeMove(1, 3, "O");
        game.makeMove(2, 2, "S");

        boolean result = game.checkDiagonalSOSFromTopRight(0, 4);

        assertTrue(result);
    }

    @Test
    public void testCheckGeneralGameDiagonalSOSFromBottomLeft() {
        Game game = new GeneralGame(5);

        game.makeMove(4, 0, "S");
        game.makeMove(3, 1, "O");
        game.makeMove(2, 2, "S");

        boolean result = game.checkDiagonalSOSFromBottomLeft(4, 0);

        assertTrue(result);
    }

    @Test
    public void testCheckGeneralGameDiagonalSOSFromBottomRight() {
        Game game = new GeneralGame(5);

        game.makeMove(4, 4, "S");
        game.makeMove(3, 3, "O");
        game.makeMove(2, 2, "S");

        boolean result = game.checkDiagonalSOSFromBottomRight(4, 4);

        assertTrue(result);
    }

    //computer opponent
    @Test
    public void testGetComputerMoveSimpleGameEmptyBoard() {

        Game game = new SimpleGame(4);
        game.setBluePlayer(new ComputerPlayer());
        Move move = ((ComputerPlayer) game.getBluePlayer()).getComputerMove(game);

        assertNotNull(move);
        assertTrue(move.getRow() >= 0 && move.getRow() < game.getBoardSize());
        assertTrue(move.getCol() >= 0 && move.getCol() < game.getBoardSize());
        assertTrue(move.getSymbol().equals("S") || move.getSymbol().equals("O"));

    }

    @Test
    public void testGetComputerMoveGeneralGameEmptyBoard() {

        Game game = new GeneralGame(4);
        game.setBluePlayer(new ComputerPlayer());
        Move move = ((ComputerPlayer) game.getBluePlayer()).getComputerMove(game);

        assertNotNull(move);
        assertTrue(move.getRow() >= 0 && move.getRow() < game.getBoardSize());
        assertTrue(move.getCol() >= 0 && move.getCol() < game.getBoardSize());
        assertTrue(move.getSymbol().equals("S") || move.getSymbol().equals("O"));
    }

    @Test
    public void testSimpleGameGetComputerMoveWhenHorizontalSOSPossible() {

        Game game = new SimpleGame(4);
        game.makeMove(0, 0, "S");
        game.makeMove(0, 1, "O");
        game.setBluePlayer(new ComputerPlayer());
        Move move = ((ComputerPlayer) game.getBluePlayer()).getComputerMove(game);


        assertNotNull(move);
        assertTrue(move.getRow() == 0);
        assertTrue(move.getCol() == 2);
        assertTrue(game.getGameBoard()[move.getRow()][move.getCol()].equals(""));
        assertTrue(move.getSymbol().equals("S"));

    }

    @Test
    public void testGeneralGameGetComputerMoveWhenHorizontalSOSPossible() {

        Game game = new GeneralGame(4);
        game.makeMove(0, 0, "S");
        game.makeMove(0, 1, "O");
        game.setBluePlayer(new ComputerPlayer());
        Move move = ((ComputerPlayer) game.getBluePlayer()).getComputerMove(game);


        assertNotNull(move);
        assertTrue(move.getRow() == 0);
        assertTrue(move.getCol() == 2);
        assertTrue(game.getGameBoard()[move.getRow()][move.getCol()].equals(""));
        assertTrue(move.getSymbol().equals("S"));

    }


    @Test
    public void testGetSimpleComputerMoveWhenOnlyVerticalSOSPossible() {

        Game game = new SimpleGame(4);
        game.makeMove(0, 3, "S");
        game.makeMove(1, 3, "O");
        game.setBluePlayer(new ComputerPlayer());
        Move move = ((ComputerPlayer) game.getBluePlayer()).getComputerMove(game);


        assertNotNull(move);
        assertTrue(move.getRow() == 2);
        assertTrue(move.getCol() == 3);
        assertTrue(game.getGameBoard()[move.getRow()][move.getCol()].equals(""));
        assertTrue(move.getSymbol().equals("S"));

    }

    @Test
    public void testGetGeneralComputerMoveWhenOnlyVerticalSOSPossible() {

        Game game = new GeneralGame(4);
        game.makeMove(0, 3, "S");
        game.makeMove(1, 3, "O");
        game.setBluePlayer(new ComputerPlayer());
        Move move = ((ComputerPlayer) game.getBluePlayer()).getComputerMove(game);


        assertNotNull(move);
        assertTrue(move.getRow() == 2);
        assertTrue(move.getCol() == 3);
        assertTrue(game.getGameBoard()[move.getRow()][move.getCol()].equals(""));
        assertTrue(move.getSymbol().equals("S"));

    }
}




