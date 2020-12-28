import java.awt.event.*;
import javax.swing.*;

public class Ticker extends JLabel implements ActionListener{
    private int elapsedTime = 0;
    private int seconds =0;
    private String secondsString = String.format("%02d", seconds);
    private Timer timer;
    private boolean isPaused = false;

    public Ticker(){

        timer = new Timer(1000, this);
        timer.start();
    }

    public void start(){
        timer.start();
    }

    public void pause(){
        this.isPaused = true;
    }

    public void unpause(){
        this.isPaused = false;
    }

    public void reset() {
        timer.stop();
        elapsedTime=0;
        seconds =0;
        secondsString = String.format("%03d", seconds);
        Ticker.super.setText(secondsString);
    }

    public int getSeconds() {
        return seconds;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!isPaused){
            elapsedTime=elapsedTime+1000;
            seconds = elapsedTime/1000;
            secondsString = String.format("%03d", seconds);
            this.setText(secondsString);
        }
    }
}