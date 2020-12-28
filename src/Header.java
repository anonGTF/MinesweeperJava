import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class Header extends JPanel {

    private int sumMines;
    private JLabel mines, highScoreLabel;
    Ticker ticker;

    public Header(int sumMines, String difficulty, HashMap<String, Integer> highScore){
        this.sumMines = sumMines;

        ticker = new Ticker();
        mines = new JLabel("Bombs: " + this.sumMines);
        highScoreLabel = (highScore.get(difficulty) == 0) ?
                new JLabel("Highest: -") :
                new JLabel("Highest: " + highScore.get(difficulty));
        highScoreLabel.setHorizontalAlignment(JLabel.CENTER);

        this.setLayout(new BorderLayout());
        this.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        this.add(mines, BorderLayout.LINE_START);
        this.add(highScoreLabel, BorderLayout.CENTER);
        this.add(ticker, BorderLayout.LINE_END);
    }

    public void setSumMines(int sumMines){
        this.sumMines = sumMines;
        mines.setText("Bombs: " + this.sumMines);
    }

    public Ticker getTicker(){
        return this.ticker;
    }

    public JLabel getHighScoreLabel() {
        return highScoreLabel;
    }
}
