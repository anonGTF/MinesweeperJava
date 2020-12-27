import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Board extends JPanel implements MouseListener, ActionListener {

    private int cellRow = 5;
    private int cellCol = 5;
    private int sumMines = 3;
    private int frameWidth = 50 * cellCol;
    private int frameHeight = 50 * cellRow;
    private boolean showLog = true;
    private boolean isSoundOn = true;
    private int sisa = cellRow * cellCol;
    private Tile[][] board = new Tile[cellRow][cellCol];

    private JFrame frame;
    private JMenuBar menuBar;
    private JMenu difficulty, sound;
    private ButtonGroup difGroup, soundGroup;
    private JRadioButtonMenuItem easy, medium, hard, custom, soundOn, soundOff;

    public void generateMines() {

        int count = 0;
        while(count < sumMines) {

            //generate a random location (row, col)
            int row = (int)(Math.random()*board.length);
            int col = (int)(Math.random()*board[0].length);

            //If a generated location is already a mine, generate a new location
            // keep generating locations if the generated location is a mine!
            while(board[row][col].isMined()) {
                row = (int)(Math.random()*board.length);
                col = (int)(Math.random()*board[0].length);
            }

            board[row][col].setMine(); //set this tile to a Mine!
            count++;//increment mine count!

        }

    }

    public void updateCount(int r, int c) {

        //if no mine at location r, c exit!
        if(!board[r][c].isMined()) return;

        for(int row = r-1; row <= r+1; row++) {
            for(int col = c-1; col <= c+1; col++) {

                try {
                    board[row][col].incrementCount(); // add 1 to count
                }catch(Exception e) {
                    //do nothing! you went out of bounds
                }
            }
        }

    }

    public void checkWon(){
        if(this.showLog) System.out.println(this.sisa);

        if(this.sisa == sumMines) {
            System.out.println("Win!");
            showDialog("You Won!", "Want to restart the game?");
        }
    }


    /* count the mines per tile*/
    public void countMines() {

        for(int row = 0; row < board.length; row++) {
            for(int col = 0; col < board[0].length; col++) {
                //Call helper method to update the surrounding Tile
                updateCount(row,col);//update the count
            }
        }

    }

    public void displayMines() {
        for(int row = 0; row < board.length; row++) {
            for(int col = 0; col < board[0].length; col++) {
                if(board[row][col].isMined()) {
                    board[row][col].setIcon(new ImageIcon("mine.png"));
                    board[row][col].setText("*");
                }else{
                    board[row][col].setText(board[row][col].getCount()+"");
                }

            }
        }
        repaint();

    }


    public Board() {
        easy = new JRadioButtonMenuItem("easy");
        medium = new JRadioButtonMenuItem("medium");
        hard = new JRadioButtonMenuItem("hard");
        custom = new JRadioButtonMenuItem("Custom...");
        soundOn = new JRadioButtonMenuItem("Sound on");
        soundOff = new JRadioButtonMenuItem("sound off");

        easy.setSelected(true);
        soundOn.setSelected(true);

        easy.addActionListener(this);
        medium.addActionListener(this);
        hard.addActionListener(this);
        custom.addActionListener(this);
        soundOn.addActionListener(this);
        soundOff.addActionListener(this);

        menuBar = new JMenuBar();
        difficulty = new JMenu("Difficulty");
        sound = new JMenu("Sound");
        difGroup = new ButtonGroup();
        soundGroup = new ButtonGroup();

        difficulty.add(easy);
        difficulty.add(medium);
        difficulty.add(hard);
        difficulty.add(custom);
        sound.add(soundOn);
        sound.add(soundOff);

        difGroup.add(easy);
        difGroup.add(medium);
        difGroup.add(hard);
        difGroup.add(custom);
        soundGroup.add(soundOn);
        soundGroup.add(soundOff);

        menuBar.add(difficulty);
        menuBar.add(sound);

        frame = new JFrame("Minesweeper");
        frame.setJMenuBar(menuBar);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initGame();
    }

    public void initGame(){
        frameWidth = 50 * this.cellCol;
        frameHeight = Math.min(50 * this.cellRow, 750); // set max frame height to 750
        showLog = false;
        sisa = this.cellRow * this.cellCol;

        board = new Tile[cellRow][cellCol];

        frame.setSize(frameWidth, frameHeight);
        frame.setLayout(new GridLayout(this.cellRow, this.cellCol));
        frame.getContentPane().removeAll();

        for(int row = 0; row < board.length; row++) {
            for(int col = 0; col < board[0].length; col++) {
                Tile t = new Tile(row, col);
                t.addMouseListener(this);
                frame.add(t);
                board[row][col] = t;
            }
        }
        frame.validate();

        generateMines();
        countMines();
        frame.setVisible(true);
    }

    public void gameOver(){
        System.out.println("gameOver");
        for(int r = 0; r < board.length; r++){
            for(int c = 0; c < board[0].length; c++){
                if(board[r][c].isMined()){
                    board[r][c].setIcon(new ImageIcon("mine.png"));
                }
            }
        }
        showDialog("You Lose!", "Want to try again?");
    }

    public void showDialog(String title, String message){
        int result = JOptionPane.showConfirmDialog(null, message,title,
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
        if(result == JOptionPane.YES_OPTION){
            initGame();
        }else if (result == JOptionPane.NO_OPTION){
            System.exit(0);
        }else {
            System.exit(0);
        }
    }

    public void reveal(int r, int c){
        if( r<0 || r>=board.length || c<0|| c>=board[0].length ||
                board[r][c].getText().length()>0 || !board[r][c].isEnabled()){
            return;
        }else if(board[r][c].getCount()!=0){
            board[r][c].setText(board[r][c].getCount()+"");
            board[r][c].setEnabled(false);
            this.sisa--;
        }else{
            board[r][c].setEnabled(false);
            this.sisa--;
            reveal(r-1,c);//north
            reveal(r+1,c);//south
            reveal(r,c-1);//east
            reveal(r,c+1);//west
        }
        System.out.print("reveal: ");
        System.out.print(r + ", ");
        System.out.print(c + "\n");

        if (this.showLog) System.out.println("reveal"); //for debugging mode
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == easy){
            cellRow = 5;
            cellCol = 5;
            sumMines = 3;
        }
        if(e.getSource() == medium){
            cellRow = 10;
            cellCol = 10;
            sumMines = 15;
        }
        if(e.getSource() == hard){
            cellRow = 10;
            cellCol = 15;
            sumMines = 30;
        }
        if(e.getSource() == soundOn){
            isSoundOn = true;
        }
        if(e.getSource() == soundOff){
            isSoundOn = false;
        }
        if(e.getSource() == custom){
            CustomForm customForm = new CustomForm(cellRow, cellCol, sumMines);
            cellRow = customForm.getCellRow();
            cellCol = customForm.getCellCol();
            sumMines = customForm.getSumMines();
        }

        initGame();
    }

    @Override
    public void mouseClicked(MouseEvent arg0) {
        if(arg0.getButton()==1) {
            if (this.showLog) System.out.println("left"); // left click
            Tile t = (Tile)(arg0.getComponent());
            System.out.print("clicked: ");
            System.out.print(t.getR() + ", ");
            System.out.print(t.getC() + "\n");
            if(t.isMined()){
                 gameOver();
            }else{
                reveal(t.getR(), t.getC());
                checkWon();
            }

        }else if(arg0.getButton()==3) {
            if (this.showLog) System.out.println("right"); // right click
            Tile t = (Tile)(arg0.getComponent());
            t.toggle();
        }

    }

    @Override
    public void mouseEntered(MouseEvent arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseExited(MouseEvent arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mousePressed(MouseEvent arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseReleased(MouseEvent arg0) {
        // TODO Auto-generated method stub

    }
}