package Map;

public class Question {
    private String question;
    private String[] options;
    private int correctIndex;

    public Question(String question, String[] options, int correctIndex) {
        this.question = question;
        this.options = options;
        this.correctIndex = correctIndex;
    }

    public String getQuestion() { return question; }

    public String[] getOptions() { return options; }

    public boolean isCorrect(int index) { return index == this.correctIndex; }

    public boolean getCorrectIndex(int index) { return correctIndex == index; }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(question).append("\n");
        sb.append("--- Opcoes ---");
        for (int i = 0; i < options.length; i++) {
            sb.append("\n").append(i + 1).append(". ").append(options[i]);
        }

        return sb.toString();
    }

}