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

        sb.append("Pergunta: ");
        sb.append(question);
        sb.append("\n");
        sb.append("--Opcoes--");
        sb.append("\n");
        sb.append("1. - ");
        sb.append(options[0]);
        sb.append("\n");
        sb.append("2. - ");
        sb.append(options[1]);
        sb.append("\n");
        sb.append("3. - ");
        sb.append(options[2]);
        sb.append("\n");
        sb.append("4. - ");
        sb.append(options[3]);
        sb.append("\n");

        return sb.toString();
    }

}