import java.util.Map;

public class Puzzle {
    private String question;
    private String answer;
    private String hint;
    private String type; // "text" or "mcq"
    private Map<String, String> options; // For MCQ questions

    public Puzzle(String question, String answer, String hint, String type, Map<String, String> options) {
        this.question = question;
        this.answer = answer;
        this.hint = hint;
        this.type = type;
        this.options = options;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    public String getHint() {
        return hint;
    }

    public String getType() {
        return type;
    }

    public Map<String, String> getOptions() {
        return options;
    }
}