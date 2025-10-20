import java.awt.*;
import javax.swing.*;

public class HomeScreen extends JFrame {

    // --- Style Constants ---
    private static final Color BG_COLOR = new Color(45, 52, 54); // Dark Gray/Blue
    private static final Color TEXT_COLOR = new Color(223, 230, 233); // Light Gray
    private static final Color BUTTON_COLOR = new Color(99, 110, 114);
    private static final Font TITLE_FONT = new Font("Arial", Font.BOLD, 28);
    private static final Font INFO_FONT = new Font("Arial", Font.PLAIN, 15);
    private static final Font BUTTON_FONT = new Font("Arial", Font.BOLD, 14);

    public HomeScreen() {
        super("Welcome to CodeQuest!");

        // --- Main Title ---
        JLabel titleLabel = new JLabel("CodeQuest: The Java Challenge", SwingConstants.CENTER);
        titleLabel.setFont(TITLE_FONT);
        titleLabel.setForeground(TEXT_COLOR); // Style: Set text color
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10)); // Style: Add padding

        // --- Text Area for Description and Rules ---
        JTextArea infoArea = new JTextArea();
        infoArea.setFont(INFO_FONT);
        infoArea.setEditable(false);
        infoArea.setLineWrap(true);
        infoArea.setWrapStyleWord(true);
        infoArea.setText(
            "Welcome to CodeQuest! Test your Java knowledge against the clock.\n\n" +
            "## Game Rules:\n" +
            "  • Each puzzle starts with 1000 base points.\n" +
            "  • Time Penalty: After 7 seconds, you lose 50 points every second.\n" +
            "  • Attempt Penalty: You lose 100 points for each incorrect answer.\n" +
            "  • Hint Penalty: Using a hint will cost you 150 points.\n\n" +
            "Choose your difficulty below to begin!"
        );
        // Style: Set colors and internal padding
        infoArea.setBackground(BG_COLOR);
        infoArea.setForeground(TEXT_COLOR);
        infoArea.setMargin(new Insets(10, 15, 10, 15));

        // Style: Put text area in a scroll pane with matching colors
        JScrollPane scrollPane = new JScrollPane(infoArea);
        scrollPane.setBorder(BorderFactory.createEmptyBorder()); // Remove border from scroll pane
        scrollPane.getViewport().setBackground(BG_COLOR);

        // --- Difficulty Buttons ---
        JButton easyButton = new JButton("Easy");
        JButton mediumButton = new JButton("Medium");
        JButton hardButton = new JButton("Hard");
        
        // --- Button Panel ---
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(BG_COLOR); // Style: Set panel background
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 20, 10)); // Style: Add padding
        buttonPanel.add(easyButton);
        buttonPanel.add(mediumButton);
        buttonPanel.add(hardButton);

        // --- Style the buttons ---
        styleButton(easyButton);
        styleButton(mediumButton);
        styleButton(hardButton);

        // --- Button Actions ---
        easyButton.addActionListener(e -> { new GameWindow("puzzles_easy.json"); dispose(); });
        mediumButton.addActionListener(e -> { new GameWindow("puzzles_medium.json"); dispose(); });
        hardButton.addActionListener(e -> { new GameWindow("puzzles_hard.json"); dispose(); });

        // --- Frame Layout and Setup ---
        Container contentPane = getContentPane();
        contentPane.setBackground(BG_COLOR); // Style: Set main background color
        setLayout(new BorderLayout(10, 10));
        add(titleLabel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        setSize(500, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // --- Helper method to style buttons uniformly ---
    private void styleButton(JButton button) {
        button.setFont(BUTTON_FONT);
        button.setBackground(BUTTON_COLOR);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false); // Removes the focus border
        button.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25)); // Adds padding
    }
}