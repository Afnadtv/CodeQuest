import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class PuzzleManager {
    private List<Puzzle> puzzles;
    private int currentIndex = 0;
    private String puzzleFile;

    public PuzzleManager(String fileName) {
        this.puzzles = new ArrayList<>();
        this.puzzleFile = fileName;
        loadPuzzles();
    }

    private void loadPuzzles() {
        try {
            File file = new File(this.puzzleFile);
            if (!file.exists()) { /* ... error handling ... */ return; }
            
            JSONParser parser = new JSONParser();
            JSONArray jsonArray = (JSONArray) parser.parse(new FileReader(file));
            if (jsonArray.isEmpty()) { /* ... error handling ... */ return; }

            for (Object obj : jsonArray) {
                JSONObject puzzleObj = (JSONObject) obj;
                String question = (String) puzzleObj.get("question");
                String answer = (String) puzzleObj.get("answer");
                String hint = (String) puzzleObj.getOrDefault("hint", "No hint available.");
                String type = (String) puzzleObj.getOrDefault("type", "text");
                
                Map<String, String> options = null;
                if ("mcq".equals(type)) {
                    options = new HashMap<>();
                    JSONObject optionsObj = (JSONObject) puzzleObj.get("options");
                    for (Object key : optionsObj.keySet()) {
                        options.put((String) key, (String) optionsObj.get(key));
                    }
                }
                
                puzzles.add(new Puzzle(question, answer, hint, type, options));
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "❌ Error loading puzzles: " + e.getMessage(), "Loading Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public Puzzle getNextPuzzle() {
        if (currentIndex < puzzles.size()) {
            return puzzles.get(currentIndex++);
        }
        return null;
    }
}