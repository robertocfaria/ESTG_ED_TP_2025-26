package Map;

/**
 * A data model class representing a single multiple-choice question used in the maze game,
 * typically within a {@link QuestionDivision}. It stores the question text, a list
 * of possible answer options, and the index of the correct answer.
 */
public class Question {
    private String question;
    private String[] options;
    private int correctIndex;

    /**
     * Default constructor required for Jackson deserialization.
     */
    public Question() {
    }

    /**
     * Constructs a Question object with all necessary components.
     *
     * @param question The main text of the question.
     * @param options An array of strings representing the possible answer options.
     * @param correctIndex The zero-based index of the correct answer within the options array.
     */
    public Question(String question, String[] options, int correctIndex) {
        this.question = question;
        this.options = options;
        this.correctIndex = correctIndex;
    }

    /**
     * Retrieves the main text of the question.
     *
     * @return The question text as a {@code String}.
     */
    public String getQuestion() {
        return question;
    }

    /**
     * Sets the main text of the question.
     *
     * @param question The new question text.
     */
    public void setQuestion(String question) {
        this.question = question;
    }

    /**
     * Retrieves the array of possible answer options.
     *
     * @return An array of {@code String} options.
     */
    public String[] getOptions() {
        return options;
    }

    /**
     * Sets the array of possible answer options.
     *
     * @param options The new array of options.
     */
    public void setOptions(String[] options) {
        this.options = options;
    }
    /**
     * Checks if the provided index matches the correct answer index.
     *
     * @param index The zero-based index chosen by the player.
     * @return {@code true} if the index is the correct index; {@code false} otherwise.
     */
    public boolean getCorrectIndex(int index) {
        return correctIndex == index;
    }
    /**
     * Sets the zero-based index for the correct answer.
     *
     * @param correctIndex The new index of the correct answer.
     * @throws IllegalArgumentException If the provided index is out of bounds
     * for the current options array.
     */
    public void setCorrectIndex(int correctIndex) {
        if (correctIndex < 0 || correctIndex > this.options.length) {
            throw new IllegalArgumentException("Invalid index");
        }

        this.correctIndex = correctIndex;
    }
    /**
     * Checks if the provided index is the correct answer.
     * This method has the same functionality as {@code getCorrectIndex(int index)}.
     *
     * @param index The zero-based index chosen by the player.
     * @return {@code true} if the index is correct; {@code false} otherwise.
     */
    public boolean isCorrect(int index) {
        return index == this.correctIndex;
    }
    /**
     * Returns a formatted string representation of the question, including
     * the question text and a numbered list of all options.
     *
     * @return A {@code String} formatted for display to the user.
     */
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