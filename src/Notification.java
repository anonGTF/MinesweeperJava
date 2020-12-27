import javax.swing.*;

public class Notification {
    private int result;

    public Notification(String title, String message){
        this.result = JOptionPane.showConfirmDialog(null, message,title,
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
    }

    public int getResult() {
        return result;
    }
}
