import javax.swing.*;

public class Notification {
    private int result;

    public Notification(String title, String[] messages){
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        for (String message : messages) {
            JLabel label = new JLabel(message);
            label.setAlignmentX(JLabel.CENTER_ALIGNMENT);
            panel.add(label);
        }

        this.result = JOptionPane.showConfirmDialog(null, panel,title,
                JOptionPane.YES_NO_OPTION,
                JOptionPane.PLAIN_MESSAGE);
    }

    public int getResult() {
        return result;
    }
}
