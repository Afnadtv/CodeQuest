import java.awt.*;
import java.util.Map;
import java.util.TreeMap;
import javax.swing.*;

public class GameWindow extends JFrame {
    private PuzzleManager puzzleManager;
    private Puzzle currentPuzzle;
    private JLabel questionLabel;
    private JLabel feedbackLabel;
    private JButton nextButton, hintButton;
    private TimerLabel timerLabel;
    private JLabel scoreLabel;
    private JPanel answerPanel; // Panel to hold either JTextField or JButtons

    // Stats
    private int totalScore = 0;
    private int currentAttempts = 0;
    private int totalIncorrectAttempts = 0;
    private int hintsUsed = 0;

    public GameWindow(String puzzleFile) {
        super("CodeQuest - Puzzle Game");
        puzzleManager = new PuzzleManager(puzzleFile);
        
        // --- Component Initialization ---
        timerLabel = new TimerLabel();
        scoreLabel = new JLabel("Score: 0");
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 16));
        questionLabel = new JLabel("Question appears here", SwingConstants.CENTER);
        questionLabel.setFont(new Font("Arial", Font.BOLD, 16));
        answerPanel = new JPanel(new BorderLayout()); // Initialize the dynamic answer panel
        hintButton = new JButton("Hint (-150 points)");
        nextButton = new JButton("Next Puzzle");
        feedbackLabel = new JLabel(" ", SwingConstants.CENTER);
        feedbackLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        // --- Main Buttons Panel ---
        JPanel bottomButtonsPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        bottomButtonsPanel.add(hintButton);
        bottomButtonsPanel.add(nextButton);
        
        // --- Main Layout ---
        setLayout(new BorderLayout(10, 10));
        JPanel topPanel = new JPanel(new GridLayout(2, 1));
        topPanel.add(timerLabel);
        topPanel.add(scoreLabel);
        
        add(topPanel, BorderLayout.NORTH);
        add(questionLabel, BorderLayout.CENTER);
        
        JPanel bottomPanel = new JPanel(new BorderLayout(10, 10));
        bottomPanel.add(answerPanel, BorderLayout.NORTH);
        bottomPanel.add(bottomButtonsPanel, BorderLayout.CENTER);
        bottomPanel.add(feedbackLabel, BorderLayout.SOUTH);
        
        add(bottomPanel, BorderLayout.SOUTH);

        // --- Frame Setup ---
        getRootPane().setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(550, 450);
        setLocationRelativeTo(null);
        setVisible(true);

        loadNextPuzzle();

        // --- Action Listeners ---
        nextButton.addActionListener(e -> loadNextPuzzle());
        hintButton.addActionListener(e -> showHint());
    }

    private void loadNextPuzzle() {
        currentPuzzle = puzzleManager.getNextPuzzle();
        if (currentPuzzle != null) {
            questionLabel.setText("<html><div style='text-align: center;'>" + currentPuzzle.getQuestion() + "</div></html>");
            feedbackLabel.setText(" ");
            timerLabel.reset();
            currentAttempts = 0;
            hintButton.setEnabled(true);

            // Dynamically build the answer UI
            buildAnswerUI();
        } else {
            // End of game
            timerLabel.stopTimer();
            int finalTime = timerLabel.getSeconds();
            new ResultsScreen(totalScore, finalTime, totalIncorrectAttempts, hintsUsed);
            dispose();
        }
    }

    private void buildAnswerUI() {
        answerPanel.removeAll(); // Clear previous components
        
        if ("mcq".equals(currentPuzzle.getType())) {
            JPanel buttonPanel = new JPanel(new GridLayout(0, 2, 5, 5)); // 2 columns of buttons
            Map<String, String> options = new TreeMap<>(currentPuzzle.getOptions()); // Sorts keys (A, B, C, D)
            for (Map.Entry<String, String> option : options.entrySet()) {
                JButton optionButton = new JButton(option.getKey() + ". " + option.getValue());
                optionButton.addActionListener(e -> checkAnswer(option.getKey()));
                buttonPanel.add(optionButton);
            }
            answerPanel.add(buttonPanel, BorderLayout.CENTER);
        } else { // "text" type
            JTextField answerField = new JTextField();
            answerField.setHorizontalAlignment(JTextField.CENTER);
            answerField.setFont(new Font("Arial", Font.PLAIN, 16));
            answerField.addActionListener(e -> checkAnswer(answerField.getText().trim()));
            answerPanel.add(answerField, BorderLayout.CENTER);
            // Add a submit button for text fields to be more intuitive
            JButton submitButton = new JButton("Submit");
            submitButton.addActionListener(e -> checkAnswer(answerField.getText().trim()));
            answerPanel.add(submitButton, BorderLayout.EAST);
        }
        
        answerPanel.revalidate();
        answerPanel.repaint();
    }

    private void checkAnswer(String userAnswer) {
        if (userAnswer.equalsIgnoreCase(currentPuzzle.getAnswer())) {
            feedbackLabel.setText("✅ Correct!");
            feedbackLabel.setForeground(new Color(0, 150, 0));
            
            // Scoring logic
            int seconds = timerLabel.getSeconds();
            int timePenalty = (seconds > 7) ? (seconds - 7) * 50 : 0;
            int attemptPenalty = currentAttempts * 100;
            int puzzleScore = 1000 - timePenalty - attemptPenalty;
            if (puzzleScore < 10) puzzleScore = 10;
            
            totalScore += puzzleScore;
            scoreLabel.setText("Score: " + totalScore);
            
            disableAnswerComponents();
            hintButton.setEnabled(false);
        } else {
            feedbackLabel.setText("❌ Incorrect! Try again.");
            feedbackLabel.setForeground(Color.RED);
            currentAttempts++;
            totalIncorrectAttempts++;
        }
    }
    
    private void disableAnswerComponents() {
        for (Component comp : answerPanel.getComponents()) {
            if (comp instanceof JPanel) { // For MCQ buttons
                for (Component button : ((JPanel) comp).getComponents()) {
                    button.setEnabled(false);
                }
            }
            comp.setEnabled(false); // For text field and its submit button
        }
    }
    
    private void showHint() {
        if (currentPuzzle == null) return;
        JOptionPane.showMessageDialog(this, currentPuzzle.getHint(), "Hint", JOptionPane.INFORMATION_MESSAGE);
        totalScore -= 150;
        scoreLabel.setText("Score: " + totalScore);
        hintButton.setEnabled(false);
        hintsUsed++;
    }
}