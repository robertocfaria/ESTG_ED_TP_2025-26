package Map;

import com.fasterxml.jackson.annotation.*;


@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
public class Question {
    private String question;
    private String[] options;
    private int correctIndex;

    public Question() {
    }

    public Question(String question, String[] options, int correctIndex) {
        this.question = question;
        this.options = options;
        this.correctIndex = correctIndex;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String[] getOptions() {
        return options;
    }

    public void setOptions(String[] options) {
        this.options = options;
    }

    public boolean getCorrectIndex(int index) {
        return correctIndex == index;
    }

    public void setCorrectIndex(int correctIndex) {
        this.correctIndex = correctIndex;
    }

    public boolean isCorrect(int index) {
        return index == this.correctIndex;
    }

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