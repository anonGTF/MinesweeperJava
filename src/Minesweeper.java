import javax.swing.*;
import javax.swing.text.BoxView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Minesweeper  implements ActionListener, GameListener {
    private JFrame frame;
    private Menu menu;
    private Board board;
    private Header header;
    private JPanel mainPanel;

    public Minesweeper(){
        menu = new Menu(this);
        board = new Board();
        header = new Header(3);
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

        header.getTicker().pause();

        if(e.getSource() == menu.getEasy()){
            cellRow = 5;
            cellCol = 5;
            sumMines = 3;
        }
        else if(e.getSource() == menu.getMedium()){
            cellRow = 10;
            cellCol = 10;
            sumMines = 15;
        }
        else if(e.getSource() == menu.getHard()){
            cellRow = 10;
            cellCol = 15;
            sumMines = 30;
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
    }

    @Override
    public void onWon() {
        header.getTicker().pause();
        String[] messages = {"Your score is " + header.getTicker().getSeconds(), "Want to restart the game?"};
        Notification notification = new Notification("You Won!", messages);
        notificationHandler(notification);
    }

    @Override
    public void onGameOver() {
        header.getTicker().pause();
        String[] messages = {"Want to try again?"};
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
