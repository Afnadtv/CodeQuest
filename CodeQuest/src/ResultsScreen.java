import java.awt.*;
import javax.swing.*;

public class ResultsScreen extends JFrame {

    public ResultsScreen(int finalScore, int totalTime, int incorrectAttempts, int hintsUsed) {
        super("Level Complete!");

        // --- Create Labels to display stats ---
        JLabel titleLabel = new JLabel("Level Summary", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));

        JLabel scoreLabel = new JLabel("Final Score: " + finalScore);
        scoreLabel.setFont(new Font("Arial", Font.PLAIN, 16));

        JLabel timeLabel = new JLabel("Total Time Taken: " + totalTime + " seconds");
        timeLabel.setFont(new Font("Arial", Font.PLAIN, 16));

        JLabel attemptsLabel = new JLabel("Incorrect Attempts: " + incorrectAttempts);
        attemptsLabel.setFont(new Font("Arial", Font.PLAIN, 16));

        JLabel hintsLabel = new JLabel("Hints Used: " + hintsUsed);
        hintsLabel.setFont(new Font("Arial", Font.PLAIN, 16));

        JButton homeButton = new JButton("Return to Home");
        
        // --- Button Action: Close this screen and open a new HomeScreen ---
        homeButton.addActionListener(e -> {
            new HomeScreen();
            dispose();
        });

        // --- Layout the components ---
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 1, 15, 15)); // 6 rows for components
        panel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        panel.add(titleLabel);
        panel.add(scoreLabel);
        panel.add(timeLabel);
        panel.add(attemptsLabel);
        panel.add(hintsLabel);
        panel.add(homeButton);

        add(panel);
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}