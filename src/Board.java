import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class Board extends JPanel implements MouseListener {

    private int cellRow;
    private int cellCol;
    private int sumMines;
    private boolean showLog;
    private boolean isSoundOn;
    private int sisa;
    private Tile[][] tiles;
    private List<GameListener> listeners = new ArrayList<>();
    private AudioManager audioManager = new AudioManager();

    public Board(){
        this(5, 5, 3, true);
    }

    public Board(int cellCol, int cellRow, int sumMines, boolean isSoundOn) {
        this.cellCol = cellCol;
        this.cellRow = cellRow;
        this.sumMines = sumMines;
        this.isSoundOn = isSoundOn;
        initGame();
    }

    public void initGame(){
        showLog = false;
        sisa = cellRow * cellCol;

        tiles = new Tile[cellRow][cellCol];

        this.setLayout(new GridLayout(cellRow, cellCol));
        this.removeAll();

        for(int row = 0; row < tiles.length; row++) {
            for(int col = 0; col < tiles[0].length; col++) {
                Tile t = new Tile(row, col);
                t.addMouseListener(this);
                this.add(t);
                tiles[row][col] = t;
            }
        }
        this.validate();
        generateMines();
        countMines();
        this.setVisible(true);
    }

    public void generateMines() {

        int count = 0;
        while(count < sumMines) {

            //generate random location untuk mine
            int row = (int)(Math.random()* tiles.length);
            int col = (int)(Math.random()* tiles[0].length);

            // jika hasil random merupakan mine, maka random kembali
            while(tiles[row][col].isMined()) {
                row = (int)(Math.random()* tiles.length);
                col = (int)(Math.random()* tiles[0].length);
            }

            tiles[row][col].setMine();
            count++;
        }

    }

    /* cek ada berapa banyak mines di sekitar tile */
    public void updateCount(int r, int c) {

        if(!tiles[r][c].isMined()) return;

        for(int row = r-1; row <= r+1; row++) {
            for(int col = c-1; col <= c+1; col++) {
                try {
                    tiles[row][col].incrementCount(); // tambah satu count jika menjumpai mine
                }catch(Exception e) {
                    //do nothing!
                }
            }
        }
    }

    /* hitung nilai dari setiap tile */
    public void countMines() {
        for(int row = 0; row < tiles.length; row++) {
            for(int col = 0; col < tiles[0].length; col++) {
                updateCount(row,col);
            }
        }
    }

    /* cek kondisi apakah sudah menang atau belum */
    public void checkWon(){
        if(this.showLog) System.out.println(this.sisa);

        if(this.sisa == sumMines) {
            System.out.println("Win!");
            if (isSoundOn) audioManager.gameWonSfxPlay();
            for (GameListener gl : listeners)
                gl.onWon();
        }
    }

    /* kondisi ketika kalah */
    public void gameOver(){
        System.out.println("gameOver");
        for(int r = 0; r < tiles.length; r++){
            for(int c = 0; c < tiles[0].length; c++){
                if(tiles[r][c].isMined()){
                    tiles[r][c].setIcon(new ImageIcon("src/assets/mine.png"));
                }
            }
        }
        if (isSoundOn) audioManager.gameOverSfxPlay();
        for (GameListener gl : listeners)
            gl.onGameOver();
    }

    public void reveal(int r, int c){
        if( r<0 || r>= tiles.length || c<0|| c>= tiles[0].length ||
                tiles[r][c].getText().length()>0 || !tiles[r][c].isEnabled()){
            return;
        }else if(tiles[r][c].getCount()!=0){
            tiles[r][c].setText(tiles[r][c].getCount()+"");
            tiles[r][c].setEnabled(false);
            this.sisa--;
        }else{
            tiles[r][c].setEnabled(false);
            this.sisa--;
            reveal(r-1,c);//north
            reveal(r+1,c);//south
            reveal(r,c-1);//east
            reveal(r,c+1);//west
        }

        if (this.showLog) { //for debugging mode
            System.out.println("reveal");
            System.out.print(r + ", ");
            System.out.print(c + "\n");
        }
    }

    public void addListener(GameListener toAdd) {
        listeners.add(toAdd);
    }

    @Override
    public void mouseClicked(MouseEvent arg0) {
        if(arg0.getButton()==1) {
            if (isSoundOn) audioManager.leftClickedSfxPlay();
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
            if (isSoundOn) audioManager.rightClickedSfxPlay();
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

    public int getCellRow() {
        return cellRow;
    }

    public void setCellRow(int cellRow) {
        this.cellRow = cellRow;
    }

    public int getCellCol() {
        return cellCol;
    }

    public void setCellCol(int cellCol) {
        this.cellCol = cellCol;
    }

    public int getSumMines() {
        return sumMines;
    }

    public void setSumMines(int sumMines) {
        this.sumMines = sumMines;
    }

    public void setSoundOn(boolean soundOn) {
        isSoundOn = soundOn;
    }
}