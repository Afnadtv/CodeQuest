import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TimerLabel extends JLabel {
    private int seconds = 0;
    private Timer timer;

    public TimerLabel() {
        setText("Time: 0s");
        timer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                seconds++;
                setText("Time: " + seconds + "s");
            }
        });
        timer.start();
    }
    
    // Allows other classes to get the current time
    public int getSeconds() {
        return seconds;
    }

    public void reset() {
        seconds = 0;
        setText("Time: 0s");
        timer.restart();
    }

    public void stopTimer() {
        timer.stop();
    }
}