import javax.swing.*;
import java.awt.*;

public class Header extends JPanel {

    private int sumMines;
    private JLabel mines;
    Ticker ticker;

    public Header(int sumMines){
        this.sumMines = sumMines;

        ticker = new Ticker();
        mines = new JLabel("Bombs: " + this.sumMines);

        this.setLayout(new BorderLayout());
        this.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        this.add(mines, BorderLayout.LINE_START);
        this.add(ticker, BorderLayout.LINE_END);
    }

    public void setSumMines(int sumMines){
        this.sumMines = sumMines;
        mines.setText("Bombs: " + this.sumMines);
    }

    public Ticker getTicker(){
        return this.ticker;
    }
}
