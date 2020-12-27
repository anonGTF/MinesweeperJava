import javax.swing.*;
import javax.swing.text.BoxView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Minesweeper  implements ActionListener {
    private JFrame frame;
    private Menu menu;
    private Board board;
    private JPanel mainPanel;

    public Minesweeper(){
        menu = new Menu(this);
        board = new Board();
        mainPanel = new JPanel();

        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
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
        if(e.getSource() == menu.getEasy()){
            board.setCellCol(5);
            board.setCellRow(5);
            board.setSumMines(3);
        }
        if(e.getSource() == menu.getMedium()){
            board.setCellCol(10);
            board.setCellRow(10);
            board.setSumMines(15);
        }
        if(e.getSource() == menu.getHard()){
            board.setCellCol(15);
            board.setCellRow(10);
            board.setSumMines(30);
        }
        if(e.getSource() == menu.getSoundOn()){
            board.setSoundOn(true);
        }
        if(e.getSource() == menu.getSoundOn()){
            board.setSoundOn(false);
        }
        if(e.getSource() == menu.getCustom()){
            CustomForm customForm = new CustomForm(board.getCellRow(), board.getCellCol(), board.getSumMines());
            int cellRow = customForm.getCellRow();
            int cellCol = customForm.getCellCol();
            int sumMines = customForm.getSumMines();
            board.setCellCol(cellCol);
            board.setCellRow(cellRow);
            board.setSumMines(sumMines);
        }
        board.initGame();
        frame.pack();
        frame.setVisible(true);
    }
}
