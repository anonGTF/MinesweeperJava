import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.*;

public class Tile extends JButton{
    private int r,c;   //posisi
    private int count; //jumlah bom di sekitar tile
    private boolean isMine;
    private boolean flagged;

    public Tile(int r, int c) {
        this.r = r;
        this.c = c;
        flagged = false;
        this.setPreferredSize(new Dimension(50, 50));
    }

    public void toggle(){
        flagged = !flagged;
        if(flagged){
            this.setIcon(new ImageIcon("src/assets/flag.png"));
        }else{
            this.setIcon(null);
        }
    }

    public void incrementCount(){
        this.count++;
    }

    public boolean isMined(){
        return this.isMine;
    }

    public void setMine(){
        this.isMine = true;
    }

    public int getCount() {
        return this.count;
    }

    public int getR() {
        return this.r;
    }

    public int getC() {
        return this.c;
    }
}