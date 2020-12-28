import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class CustomForm implements ChangeListener {

    private JSlider sCellRow, sCellCol, sSumMines;
    private JLabel lCellRow, lCellCol, lSumMines;
    private int cellRow, cellCol, sumMines, resCellRow, resCellCol, resSumMines;
    private boolean canceled = true;

    private JPanel panel;

    public CustomForm(int cellRow, int cellCol, int sumMines) {
        this.cellRow = cellRow;
        this.cellCol = cellCol;
        this.sumMines = sumMines;

        sCellRow = new JSlider(3, 12, cellRow);
        sCellCol = new JSlider(3, 25, cellCol);
        sSumMines = new JSlider(1, 50, 25);

        sCellRow.addChangeListener(this);
        sCellCol.addChangeListener(this);
        sSumMines.addChangeListener(this);

        lCellRow = new JLabel("Cell Row: " + cellRow);
        lCellCol = new JLabel("Cell Col: " + cellCol);
        lSumMines = new JLabel("Sum Mines: " + cellCol * cellRow / 4);

        panel = new JPanel(new GridLayout(0, 1));
        panel.add(lCellCol);
        panel.add(sCellCol);
        panel.add(lCellRow);
        panel.add(sCellRow);
        panel.add(lSumMines);
        panel.add(sSumMines);

        int result = JOptionPane.showConfirmDialog(null, panel, "Custom level",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {

            this.canceled = false;

            resCellRow = sCellRow.getValue();
            resCellCol = sCellCol.getValue();
            resSumMines = Math.max(sSumMines.getValue() * resCellRow * resCellCol / 100, 1); // set minimal mines to 1

            try {
                this.cellRow = resCellRow;
                this.cellCol = resCellCol;
                this.sumMines = resSumMines;
            } catch (Exception err) {
                System.out.println(err);
            }
        }
    }

    public boolean isCanceled(){
        return canceled;
    }

    public int getCellRow() {
        return cellRow;
    }

    public int getCellCol() {
        return cellCol;
    }

    public int getSumMines() {
        return sumMines;
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        resCellRow = sCellRow.getValue();
        resCellCol = sCellCol.getValue();
        resSumMines = Math.max(sSumMines.getValue() * resCellRow * resCellCol / 100, 1); // set minimal mines to 1

        if (e.getSource() == sCellRow){
            lCellRow.setText("Cell Row: " + resCellRow);
        } else if (e.getSource() == sCellCol) {
            lCellCol.setText("Cell Col: " + resCellCol);
        }
        lSumMines.setText("Sum Mines: " + resSumMines);
    }
}
