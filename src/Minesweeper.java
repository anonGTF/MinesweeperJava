import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Minesweeper  implements ActionListener, GameListener {
    private static final String EASY = "easy";
    private static final String MEDIUM = "medium";
    private static final String HARD = "hard";
    private static final String CUSTOM = "custom";
    private static final int CELL_ROW_EASY = 5;
    private static final int CELL_COL_EASY = 5;
    private static final int SUM_MINES_EASY = 3;
    private static final int CELL_ROW_MEDIUM = 10;
    private static final int CELL_COL_MEDIUM = 10;
    private static final int SUM_MINES_MEDIUM = 15;
    private static final int CELL_ROW_HARD = 10;
    private static final int CELL_COL_HARD = 15;
    private static final int SUM_MINES_HARD = 30;
    private JFrame frame;
    private Menu menu;
    private Board board;
    private Header header;
    private JPanel mainPanel;
    private HighScoreManager highScoreManager;
    private HashMap<String, Integer> highScore;
    private String difficulty;

    public Minesweeper(){

        highScoreManager = new HighScoreManager();
        highScore = highScoreManager.getHighScore();
        difficulty = EASY;
        menu = new Menu(this);
        board = new Board();
        header = new Header(3, "easy", highScore);
        mainPanel = new JPanel();

        board.addListener(this);

        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.add(header);
        mainPanel.add(board);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        frame = new JFrame("Minesweeper");
        frame.setJMenuBar(menu);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(mainPanel);
        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int cellRow = 0;
        int cellCol = 0;
        int sumMines = 0;
        boolean isCanceled = false;
        String highScoreLabelText;

        header.getTicker().pause();

        if(e.getSource() == menu.getEasy()){
            difficulty = EASY;
            cellRow = CELL_ROW_EASY;
            cellCol = CELL_COL_EASY;
            sumMines = SUM_MINES_EASY;
        }
        else if(e.getSource() == menu.getMedium()){
            difficulty = MEDIUM;
            cellRow = CELL_ROW_MEDIUM;
            cellCol = CELL_COL_MEDIUM;
            sumMines = SUM_MINES_MEDIUM;
        }
        else if(e.getSource() == menu.getHard()){
            difficulty = HARD;
            cellRow = CELL_ROW_HARD;
            cellCol = CELL_COL_HARD;
            sumMines = SUM_MINES_HARD;
        }
        else if(e.getSource() == menu.getSoundOn()){
            isCanceled = true;
            board.setSoundOn(true);
        }
        else if(e.getSource() == menu.getSoundOff()){
            isCanceled = true;
            board.setSoundOn(false);
        }
        else if(e.getSource() == menu.getCustom()){
            difficulty = CUSTOM;
            CustomForm customForm = new CustomForm(board.getCellRow(), board.getCellCol(), board.getSumMines());
            cellRow = customForm.getCellRow();
            cellCol = customForm.getCellCol();
            sumMines = customForm.getSumMines();
            isCanceled = customForm.isCanceled();
        }

        header.getTicker().unpause();

        if (!isCanceled){
            header.setSumMines(sumMines);
            board.setCellCol(cellCol);
            board.setCellRow(cellRow);
            board.setSumMines(sumMines);

            header.getTicker().reset();
            header.getTicker().start();
            board.initGame();
            frame.pack();
            frame.setVisible(true);
        }

        if (difficulty.equals(CUSTOM)) {
            highScoreLabelText = "Customed";
        } else if (highScore.get(difficulty) == 0) {
            highScoreLabelText = "Highest: -";
        } else {
            highScoreLabelText = "Highest: " + highScore.get(difficulty);
        }
        header.getHighScoreLabel().setText(highScoreLabelText);
    }

    @Override
    public void onWon() {
        int score = Math.max(header.getTicker().getSeconds(), 1); // set highest score to 1
        List<String> messages = new ArrayList<>();
        messages.add("Your score is " + score);
        if (!difficulty.equals(CUSTOM) && (score < highScore.get(difficulty) || highScore.get(difficulty) == 0)) {
            highScore.replace(difficulty, score);
            header.getHighScoreLabel().setText("Highest: " + highScore.get(difficulty));
            highScoreManager.updateHighScore();
            messages.add("This is a new high score!");
        }
        messages.add("Want to restart the game?");
        header.getTicker().pause();
        Notification notification = new Notification("You Won!", messages);
        notificationHandler(notification);
    }

    @Override
    public void onGameOver() {
        header.getTicker().pause();
        List<String> messages = new ArrayList<>();
        messages.add("Want to try again?");
        Notification notification = new Notification("You Lose!", messages);
        notificationHandler(notification);
    }

    private void notificationHandler(Notification notification) {
        if(notification.getResult() == JOptionPane.YES_OPTION){
            header.getTicker().unpause();
            header.getTicker().reset();
            header.getTicker().start();
            board.initGame();
        }else if (notification.getResult() == JOptionPane.NO_OPTION){
            System.exit(0);
        }else {
            System.exit(0);
        }
    }
}
