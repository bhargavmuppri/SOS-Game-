import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.*;
import java.util.List;

public class GUI extends JFrame {

    private static String RECORDING_DIRECTORY_NAME = "sos_recordings";
    private static String RECORDING_TEXT_FILE = "sos_recordings.txt";
    private static String RECORDING_OBJ_FILE = "sos_recordings_obj.txt";

    private JRadioButton redCompRadioButton;
    private JRadioButton redHumanRadioButton;
    private JRadioButton blueHumanRadioButton;
    private JRadioButton blueCompRadioButton;
    private JRadioButton redORadioButton;
    private JRadioButton redSRadioButton;
    private JRadioButton blueORadioButton;
    private JRadioButton blueSRadioButton;
    private JTextField boardSizeTextField;
    private JRadioButton simpleGameRadioButton;
    private JRadioButton generalGameRadioButton;
    private JPanel centerPanel;
    private JPanel boardPanel;
    private JLabel currentTurnLabel;
    private JButton newGameButton;
    private JButton replayButton;
    private boolean gameSettingsChanged = false;
    private boolean boardSizeChanged = false;
    private boolean gameOver = false;
    private Game game;
    private boolean isReplay = false;
    private File recordingObjFile;
    private File recordingTextFile;
    private CustomButton[][] buttons;
    private JCheckBox recordGameCheckBox;

    public GUI() {
        game = new SimpleGame(8);
        setTitle("SOS Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(700, 650));

        JPanel mainPanel = new JPanel(new BorderLayout());

        JPanel topPanel = new JPanel(new FlowLayout());
        JLabel sosLabel = new JLabel("SOS");
        simpleGameRadioButton = new JRadioButton("Simple Game");
        simpleGameRadioButton.setSelected(true);
        simpleGameRadioButton.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    game = new SimpleGame(Integer.parseInt(boardSizeTextField.getText()));
                    gameSettingsChanged = true;
                }

            }
        });

        generalGameRadioButton = new JRadioButton("General Game");
        generalGameRadioButton.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    game = new GeneralGame(Integer.parseInt(boardSizeTextField.getText()));
                    gameSettingsChanged = true;
                }

            }
        });

        ButtonGroup gameTypeGroup = new ButtonGroup();
        gameTypeGroup.add(simpleGameRadioButton);
        gameTypeGroup.add(generalGameRadioButton);

        JLabel boardSizeLabel = new JLabel("Board Size:");
        boardSizeLabel.setBorder(new EmptyBorder(0, 100, 0, 0));
        boardSizeTextField = new JTextField(String.valueOf(game.getBoardSize()));
        boardSizeTextField.setColumns(2);

        boardSizeTextField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                boardSizeChanged = true;
                handleBoardSizeTextChange(boardSizeTextField.getText());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {

            }

            @Override
            public void changedUpdate(DocumentEvent e) {

            }
        });


        topPanel.add(sosLabel);
        topPanel.add(simpleGameRadioButton);
        topPanel.add(generalGameRadioButton);
        topPanel.add(boardSizeLabel);
        topPanel.add(boardSizeTextField);

        topPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JPanel bluePlayerPanel = new JPanel(new BorderLayout());
        JLabel bluePlayerLabel = new JLabel("Blue Player");
        bluePlayerLabel.setBorder(new EmptyBorder(40, 20, 10, 20));

        ButtonGroup bluePlayerTypeRadioGroup = new ButtonGroup();
        blueHumanRadioButton = new JRadioButton("Human");
        blueHumanRadioButton.setSelected(true);
        bluePlayerTypeRadioGroup.add(blueHumanRadioButton);
        blueHumanRadioButton.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {

                    gameSettingsChanged = true;
                }

            }
        });


        JPanel bluePlayerLabelPanel = new JPanel(new BorderLayout());
        bluePlayerLabelPanel.add(bluePlayerLabel, BorderLayout.NORTH);
        bluePlayerLabelPanel.add(blueHumanRadioButton, BorderLayout.SOUTH);
        bluePlayerLabelPanel.setBorder(new EmptyBorder(0, 20, 0, 0));

        JPanel bluePlayerMovesRadioPanel = new JPanel(new BorderLayout());
        bluePlayerMovesRadioPanel.setBorder(new EmptyBorder(0, 40, 0, 0));
        bluePlayerPanel.add(bluePlayerLabelPanel, BorderLayout.NORTH);


        blueSRadioButton = new JRadioButton("S");
        blueSRadioButton.setSelected(true);
        blueORadioButton = new JRadioButton("O");
        bluePlayerMovesRadioPanel.add(blueSRadioButton, BorderLayout.NORTH);
        bluePlayerMovesRadioPanel.add(blueORadioButton, BorderLayout.SOUTH);
        ButtonGroup bluePlayerMovesRadioGroup = new ButtonGroup();
        bluePlayerMovesRadioGroup.add(blueSRadioButton);
        bluePlayerMovesRadioGroup.add(blueORadioButton);
        bluePlayerPanel.add(bluePlayerMovesRadioPanel, BorderLayout.CENTER);

        JPanel bluePlayerBottomPanel = new JPanel();
        blueCompRadioButton = new JRadioButton("Computer");
        blueCompRadioButton.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {

                    gameSettingsChanged = true;
                }

            }
        });


        bluePlayerTypeRadioGroup.add(blueCompRadioButton);
        bluePlayerBottomPanel.add(blueCompRadioButton);
        bluePlayerBottomPanel.setPreferredSize(new Dimension(50, 300));
        bluePlayerPanel.add(bluePlayerBottomPanel, BorderLayout.SOUTH);


        JPanel redPlayerPanel = new JPanel(new BorderLayout());
        JLabel redPlayerLabel = new JLabel("Red Player");
        redPlayerLabel.setBorder(new EmptyBorder(40, 20, 10, 20));

        ButtonGroup redPlayerTypeRadioGroup = new ButtonGroup();
        redHumanRadioButton = new JRadioButton("Human");
        redHumanRadioButton.setSelected(true);
        redPlayerTypeRadioGroup.add(redHumanRadioButton);

        redHumanRadioButton.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {

                    gameSettingsChanged = true;
                }

            }
        });


        JPanel redPlayerLabelPanel = new JPanel(new BorderLayout());
        redPlayerLabelPanel.add(redPlayerLabel, BorderLayout.NORTH);
        redPlayerLabelPanel.add(redHumanRadioButton, BorderLayout.SOUTH);
        redPlayerLabelPanel.setBorder(new EmptyBorder(0, 20, 0, 0));


        JPanel redPlayerMovesRadioPanel = new JPanel(new BorderLayout());
        redPlayerMovesRadioPanel.setBorder(new EmptyBorder(0, 40, 0, 0));
        redPlayerPanel.add(redPlayerLabelPanel, BorderLayout.NORTH);


        redSRadioButton = new JRadioButton("S");
        redSRadioButton.setSelected(true);
        redORadioButton = new JRadioButton("O");
        redPlayerMovesRadioPanel.add(redSRadioButton, BorderLayout.NORTH);
        redPlayerMovesRadioPanel.add(redORadioButton, BorderLayout.SOUTH);

        ButtonGroup redPlayerMovesRadioGroup = new ButtonGroup();
        redPlayerMovesRadioGroup.add(redSRadioButton);
        redPlayerMovesRadioGroup.add(redORadioButton);
        redPlayerPanel.add(redPlayerMovesRadioPanel, BorderLayout.CENTER);

        JPanel redPlayerBottomPanel = new JPanel();
        redCompRadioButton = new JRadioButton("Computer");
        redCompRadioButton.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {

                    gameSettingsChanged = true;
                }

            }
        });


        JPanel replayButtonPanel = new JPanel();
        replayButtonPanel.setBorder(new EmptyBorder(230, 0, 0, 0));

        replayButton = new JButton("Replay");
        replayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                replayButtonClicked();
            }
        });

        replayButtonPanel.add(replayButton);

        redPlayerTypeRadioGroup.add(redCompRadioButton);
        redPlayerBottomPanel.add(redCompRadioButton);
        redPlayerBottomPanel.add(replayButtonPanel);
        redPlayerBottomPanel.setPreferredSize(new Dimension(50, 300));
        redPlayerPanel.add(redPlayerBottomPanel, BorderLayout.SOUTH);

        centerPanel = new JPanel(new BorderLayout());
        updateBoard();

        centerPanel.add(bluePlayerPanel, BorderLayout.WEST);
        centerPanel.add(redPlayerPanel, BorderLayout.EAST);

        JPanel bottomPanel = new JPanel(new BorderLayout());

        recordGameCheckBox = new JCheckBox("Record game");
        recordGameCheckBox.setSelected(true);
        recordGameCheckBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {

                    gameSettingsChanged = true;
                }

            }
        });


        currentTurnLabel = new JLabel("Current Turn: blue");
        currentTurnLabel.setBorder(new EmptyBorder(0, 150, 0, 0));
        newGameButton = new JButton("New Game");
        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newGameButtonClicked();
            }
        });

        bottomPanel.add(recordGameCheckBox, BorderLayout.WEST);
        bottomPanel.add(currentTurnLabel, BorderLayout.CENTER);
        bottomPanel.add(newGameButton, BorderLayout.EAST);
        bottomPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        getContentPane().add(mainPanel);
        pack();
        setLocationRelativeTo(null);

    }

    private void replayButtonClicked() {
        isReplay = true;
        updateBoard();
        JOptionPane.showMessageDialog(GUI.this, "Replaying game ", "Replaying game", JOptionPane.INFORMATION_MESSAGE);
        readRecordFileAndReplay();
    }

    private void newGameButtonClicked() {
        gameSettingsChanged = false;
        boardSizeChanged = false;
        gameOver = false;
        blueSRadioButton.setSelected(true);
        redSRadioButton.setSelected(true);
        isReplay = false;

        boolean isValidBoardSize = handleBoardSizeTextChange(boardSizeTextField.getText());

        if (isValidBoardSize) {

            if (redHumanRadioButton.isSelected()) {
                game.setRedPlayer(new Player());

            } else {
                game.setRedPlayer(new ComputerPlayer());
            }

            if (blueHumanRadioButton.isSelected()) {
                game.setBluePlayer(new Player());
            } else {
                game.setBluePlayer(new ComputerPlayer());
            }


            if (game instanceof SimpleGame) {
                game.reset(Integer.parseInt(boardSizeTextField.getText()));

                if (recordGameCheckBox.isSelected()) {
                    createRecordFile();
                    JOptionPane.showMessageDialog(GUI.this, "Starting and Recording new Simple Game with board size " + game.getBoardSize(), "Starting New Game with Recording", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(GUI.this, "Starting new Simple Game with board size " + game.getBoardSize(), "Starting New Game", JOptionPane.INFORMATION_MESSAGE);
                }


            } else {


                game.reset(Integer.parseInt(boardSizeTextField.getText()));

                if (recordGameCheckBox.isSelected()) {
                    createRecordFile();
                    JOptionPane.showMessageDialog(GUI.this, "Starting and Recording new General Game with board size " + game.getBoardSize(), "Starting New Game with Recording", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(GUI.this, "Starting new General Game with board size " + game.getBoardSize(), "Starting New Game", JOptionPane.INFORMATION_MESSAGE);
                }
            }
            currentTurnLabel.setText("Current Turn: blue");
            nextPlayersTurn();
        }
    }

    private void createRecordFile() {

        try {
            File directory = new File(RECORDING_DIRECTORY_NAME);
            if (!directory.exists()) {
                directory.mkdir();
            }

            recordingObjFile = new File(RECORDING_DIRECTORY_NAME + "/" + RECORDING_OBJ_FILE);

            try (ObjectOutputStream objectOut = new ObjectOutputStream(new FileOutputStream(recordingObjFile))) {

                objectOut.writeObject(game);
            }

            recordingTextFile = new File(RECORDING_DIRECTORY_NAME + "/" + RECORDING_TEXT_FILE);
            try (PrintWriter writer = new PrintWriter(new FileWriter(recordingTextFile))) {
                writer.println(game.toString());
            }


        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }


    boolean handleBoardSizeTextChange(String txtBoardSize) {
        boolean isSuccess = false;

        if (game.isBoardSizeTextNumeric(txtBoardSize)) {
            int newSize = Integer.parseInt(txtBoardSize);
            if (game.isBoardSizeGreaterThanTwo(newSize)) {
                game.setBoardSize(newSize);
                updateBoard();
                isSuccess = true;
            } else {
                JOptionPane.showMessageDialog(GUI.this, "Board size must be greater than 2.", "Invalid Board Size", JOptionPane.ERROR_MESSAGE);
            }

        } else {
            JOptionPane.showMessageDialog(GUI.this, "Invalid input for board size.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
        }

        return isSuccess;
    }

    private void updateBoard() {
        if (boardPanel != null) {
            centerPanel.remove(boardPanel);
        }
        int boardSize = game.getBoardSize();
        boardPanel = new JPanel(new GridLayout(boardSize, boardSize));
        buttons = new CustomButton[boardSize][boardSize];
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                CustomButton button = new CustomButton("");
                //JButton button = new JButton("");
                int finalI = i;
                int finalJ = j;

                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                        cellClicked(button, finalI, finalJ);
                    }
                });
                buttons[i][j] = button;
                boardPanel.add(button);
            }
        }
        centerPanel.add(boardPanel, BorderLayout.CENTER);
        centerPanel.revalidate();
        centerPanel.repaint();
        //  game.displayGameBoard();
    }

    private void cellClicked(CustomButton button, int finalI, int finalJ) {
        boolean sos;

        if (checkCannotPlay()) return;

        if (button.getText().contains("S") || button.getText().contains("O")) {
            JOptionPane.showMessageDialog(GUI.this, "Please click on an empty square", "Square already filled", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (game.isBluePlayersTurn()) {
            bluePlayersTurn(button, finalI, finalJ);
        } else {
            redPlayersTurn(button, finalI, finalJ);
        }
       
    }

    private void redPlayersTurn(CustomButton button, int finalI, int finalJ) {
        boolean sos;
        if (redSRadioButton.isSelected()) {
            button.setText("S");
            game.makeMove(finalI, finalJ, "S");
            sos = checkSequenceAndDrawLine(finalI, finalJ, "S", Color.RED);

            if (recordGameCheckBox.isSelected()) {
                addRecord(finalI, finalJ, "S");
            }
        } else {
            button.setText("O");
            game.makeMove(finalI, finalJ, "O");
            sos = checkSequenceAndDrawLine(finalI, finalJ, "O", Color.RED);

            if (recordGameCheckBox.isSelected()) {
                addRecord(finalI, finalJ, "O");
            }

        }
        if (game.isGameOver()) {
            String msg = game.getBluePlayerSOSCount() == game.getRedPlayerSOSCount() ? "Its a draw" : game.getBluePlayerSOSCount() > game.getRedPlayerSOSCount() ? "Blue player won!!" : "Red player won!!";
            JOptionPane.showMessageDialog(GUI.this, msg, "Game Over!!!", JOptionPane.INFORMATION_MESSAGE);
            gameOver = true;
        } else {
            if (!sos) {
                game.setBluePlayersTurn(true);
                currentTurnLabel.setText("Current Turn: blue");
                nextPlayersTurn();
            } else {
                if (game.getRedPlayer() instanceof ComputerPlayer && game instanceof GeneralGame) {
                    nextPlayersTurn();
                }
            }
        }
    }

    private void bluePlayersTurn(CustomButton button, int finalI, int finalJ) {
        boolean sos;
        if (blueSRadioButton.isSelected()) {
            button.setText("S");
            game.makeMove(finalI, finalJ, "S");
            sos = checkSequenceAndDrawLine(finalI, finalJ, "S", Color.BLUE);

            if (recordGameCheckBox.isSelected()) {
                addRecord(finalI, finalJ, "S");
            }

        } else {
            button.setText("O");
            game.makeMove(finalI, finalJ, "O");
            sos = checkSequenceAndDrawLine(finalI, finalJ, "O", Color.BLUE);

            if (recordGameCheckBox.isSelected()) {
                addRecord(finalI, finalJ, "O");
            }
        }

        if (game.isGameOver()) {
            String msg = game.getBluePlayerSOSCount() == game.getRedPlayerSOSCount() ? "Its a draw" : game.getBluePlayerSOSCount() > game.getRedPlayerSOSCount() ? "Blue player won!!" : "Red player won!!";
            JOptionPane.showMessageDialog(GUI.this, msg, "Game Over!!!", JOptionPane.INFORMATION_MESSAGE);
            gameOver = true;
        } else {
            if (!sos) {
                game.setBluePlayersTurn(false);
                currentTurnLabel.setText("Current Turn: red");
                nextPlayersTurn();
            } else {
                if (game.getBluePlayer() instanceof ComputerPlayer && game instanceof GeneralGame) {
                    nextPlayersTurn();
                }
            }
        }
    }

    private boolean checkCannotPlay() {
        if (boardSizeChanged) {
            JOptionPane.showMessageDialog(GUI.this, "Board size has been changed, please press New Game button to start the new game", "Start new game", JOptionPane.ERROR_MESSAGE);
            return true;
        }
        if (gameSettingsChanged) {
            JOptionPane.showMessageDialog(GUI.this, "Game settings has been changed, please press New Game button to start the new game", "Start new game", JOptionPane.ERROR_MESSAGE);
            return true;
        }

        if (gameOver) {
            JOptionPane.showMessageDialog(GUI.this, "Game over, please press New Game button to start the new game", "Start new game", JOptionPane.INFORMATION_MESSAGE);
            return true;

        }
        return false;
    }

    private void addRecord(int i, int j, String s) {

        try (ObjectInputStream objectIn = new ObjectInputStream(new FileInputStream(recordingObjFile))) {

            Game recordedGame = (Game) objectIn.readObject();
            recordedGame.getMoves().add(new Move(i, j, s));


            try (ObjectOutputStream objectOut = new ObjectOutputStream(new FileOutputStream(recordingObjFile))) {
                objectOut.writeObject(recordedGame);
            }

            recordingTextFile = new File(RECORDING_DIRECTORY_NAME + "/" + RECORDING_TEXT_FILE);
            try (PrintWriter writer = new PrintWriter(new FileWriter(recordingTextFile))) {
                writer.println(recordedGame);
            }

        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


    }

    private void readRecordFileAndReplay() {


        Game game = null;
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(recordingObjFile));
            game = (Game) in.readObject();
            //System.out.println(game);

            boardSizeTextField.setText(game.getBoardSize() + "");
            if (game instanceof SimpleGame) {
                simpleGameRadioButton.setSelected(true);
            } else {
                generalGameRadioButton.setSelected(true);
            }


            gameSettingsChanged = false;
            boardSizeChanged = false;
            gameOver = false;
            blueSRadioButton.setSelected(true);
            redSRadioButton.setSelected(true);
            recordGameCheckBox.setSelected(false);


            this.game = game;
            List<Move> moves = game.getMoves();
            for (Move move : moves) {

                if (game.isBluePlayersTurn()) {
                    if (move.getSymbol().equals("S")) {
                        blueSRadioButton.setSelected(true);
                    } else {
                        blueORadioButton.setSelected(true);
                    }
                } else {
                    if (move.getSymbol().equals("S")) {
                        redSRadioButton.setSelected(true);
                    } else {
                        redORadioButton.setSelected(true);
                    }
                }

                cellClicked(buttons[move.getRow()][move.getCol()], move.getRow(), move.getCol());

            }

        } catch (Exception e) {
            System.out.println(e);
        }

    }


    private void nextPlayersTurn() {
        if (game.isBluePlayersTurn()) {
            if (!isReplay && game.getBluePlayer() instanceof ComputerPlayer) {
                Move computerMove = ((ComputerPlayer) game.getBluePlayer()).getComputerMove(game);
                if (computerMove.getSymbol().equals("S")) {
                    blueSRadioButton.setSelected(true);
                } else {
                    blueORadioButton.setSelected(true);
                }
                cellClicked(buttons[computerMove.getRow()][computerMove.getCol()], computerMove.getRow(), computerMove.getCol());


            }
        } else {
            if (!isReplay && game.getRedPlayer() instanceof ComputerPlayer) {
                Move computerMove = ((ComputerPlayer) game.getRedPlayer()).getComputerMove(game);

                if (computerMove.getSymbol().equals("S")) {
                    redSRadioButton.setSelected(true);
                } else {
                    redORadioButton.setSelected(true);
                }
                cellClicked(buttons[computerMove.getRow()][computerMove.getCol()], computerMove.getRow(), computerMove.getCol());

            }
        }
    }


    private boolean checkSequenceAndDrawLine(int row, int col, String symbol, Color color) {
        boolean sos = false;
        if (symbol.equals("S")) {

            if (game.checkTopVerticalSOS(row, col)) {
                sos = true;
                drawVerticalLine(color, row - 2, col);

            } else if (game.checkBottomVerticalSOS(row, col)) {
                sos = true;
                drawVerticalLine(color, row, col);

            } else if (game.checkForwardHorizontalSOS(row, col)) {
                sos = true;
                drawHorizontalLine(color, row, col);

            } else if (game.checkBackwardHorizontalSOS(row, col)) {
                sos = true;
                drawHorizontalLine(color, row, col - 2);

            } else if (game.checkDiagonalSOSFromBottomLeft(row, col)) {
                sos = true;
                drawDiagonalLineFromTopRight(color, row - 2, col + 2);

            } else if (game.checkDiagonalSOSFromBottomRight(row, col)) {
                sos = true;
                drawDiagonalLineFromTopLeft(color, row - 2, col - 2);

            } else if (game.checkDiagonalSOSFromTopLeft(row, col)) {
                sos = true;
                drawDiagonalLineFromTopLeft(color, row, col);

            } else if (game.checkDiagonalSOSFromTopRight(row, col)) {
                sos = true;
                drawDiagonalLineFromTopRight(color, row, col);
            }


        } else if (symbol.equals("O")) {

            if (game.checkTopVerticalSOS(row + 1, col)) {
                sos = true;
                drawVerticalLine(color, row - 2 + 1, col);

            } else if (game.checkBottomVerticalSOS(row - 1, col)) {
                sos = true;
                drawVerticalLine(color, row - 1, col);

            } else if (game.checkForwardHorizontalSOS(row, col - 1)) {
                sos = true;
                drawHorizontalLine(color, row, col - 1);

            } else if (game.checkBackwardHorizontalSOS(row, col + 1)) {
                sos = true;
                drawHorizontalLine(color, row, col - 2 + 1);

            } else if (game.checkDiagonalSOSFromBottomLeft(row + 1, col - 1)) {
                sos = true;
                drawDiagonalLineFromTopRight(color, row - 2 + 1, col + 2 - 1);

            } else if (game.checkDiagonalSOSFromBottomRight(row + 1, col + 1)) {
                sos = true;
                drawDiagonalLineFromTopLeft(color, row - 2 + 1, col - 2 + 1);

            } else if (game.checkDiagonalSOSFromTopLeft(row - 1, col - 1)) {
                sos = true;
                drawDiagonalLineFromTopLeft(color, row - 1, col - 1);

            } else if (game.checkDiagonalSOSFromTopRight(row - 1, col + 1)) {
                sos = true;
                drawDiagonalLineFromTopRight(color, row - 1, col + 1);
            }
        }
        return sos;
    }

    public void drawVerticalLine(Color color, int startRow, int col) {

        for (int row = startRow; row < startRow + 3; row++) {
            buttons[row][col].addCenterVerticalLine(color);
        }
    }

    public void drawHorizontalLine(Color color, int row, int startCol) {

        for (int col = startCol; col < startCol + 3; col++) {
            buttons[row][col].addCenterHorizontalLine(color);
        }
    }

    public void drawDiagonalLineFromTopLeft(Color color, int startRow, int startCol) {

        for (int i = 0; i < 3; i++) {
            buttons[startRow++][startCol++].addTopLeftToBottomRightLine(color);
        }
    }

    public void drawDiagonalLineFromTopRight(Color color, int startRow, int startCol) {

        for (int i = 0; i < 3; i++) {
            buttons[startRow++][startCol--].addTopRightToBottomLeftLine(color);
        }
    }


    class CustomButton extends JButton {

        private boolean topLeftToBottomRightLine;
        private boolean topRightToBottomLeftLine;
        private boolean centerHorizontalLine;
        private boolean centerVerticalLine;
        private Color topLeftToBottomRightLineColor;
        private Color topRightToBottomLeftLineColor;
        private Color centerHorizontalLineColor;

        private Color centerVerticalLineColor;

        public CustomButton(String text) {
            super(text);
            topLeftToBottomRightLine = false;
            topRightToBottomLeftLine = false;
            centerHorizontalLine = false;
            centerVerticalLine = false;

        }

        public void addTopLeftToBottomRightLine(Color color) {
            topLeftToBottomRightLine = true;
            topLeftToBottomRightLineColor = color;
            repaint();

        }


        public void addTopRightToBottomLeftLine(Color color) {
            topRightToBottomLeftLine = true;
            topRightToBottomLeftLineColor = color;
            repaint();

        }


        public void addCenterHorizontalLine(Color color) {
            centerHorizontalLine = true;
            centerHorizontalLineColor = color;
            repaint();

        }


        public void addCenterVerticalLine(Color color) {
            centerVerticalLine = true;
            centerVerticalLineColor = color;
            repaint();

        }


        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            if (topLeftToBottomRightLine) {
                g.setColor(topLeftToBottomRightLineColor);
                g.drawLine(0, 0, getWidth(), getHeight());

            }

            if (topRightToBottomLeftLine) {
                g.setColor(topRightToBottomLeftLineColor);
                g.drawLine(getWidth(), 0, 0, getHeight());
            }

            if (centerHorizontalLine) {
                g.setColor(centerHorizontalLineColor);
                int y = getHeight() / 2;
                g.drawLine(0, y, getWidth(), y);
            }

            if (centerVerticalLine) {
                g.setColor(centerVerticalLineColor);
                int x = getWidth() / 2;
                g.drawLine(x, 0, x, getHeight());
            }

        }
    }
}
