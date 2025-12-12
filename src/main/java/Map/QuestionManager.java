package Map;

import Structures.List.ArrayUnorderedList;
import Structures.Queue.LinkedQueue;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.Random;
/**
 * Manages the collection and distribution of {@link Question} objects for the game.
 * It is responsible for importing questions from an external JSON file, shuffling them,
 * and serving them sequentially to {@link QuestionDivision} instances via a queue,
 * ensuring questions are not immediately repeated.
 */
public class QuestionManager {
    private ArrayUnorderedList<Question> questions;
    private LinkedQueue<Question> queueQuestions;
    /**
     * Constructs a QuestionManager, initializes the list and queue, imports all
     * questions from the JSON file, and sets up the initial randomized queue.
     */
    public QuestionManager() {
        this.questions = new ArrayUnorderedList<>();
        this.queueQuestions = new LinkedQueue<>();
        importQuestions();
        setQueue();
    }

    /**
     * Prepares the question queue by shuffling the internal list of questions
     * and enqueueing them. This method is called upon initialization and whenever
     * the queue becomes empty. After transfer, the internal list is cleared.
     */
    private void setQueue() {
        shuffleQuestions();

        this.queueQuestions = new LinkedQueue<>();
        for (Question question : this.questions) {
            queueQuestions.enqueue(question);
        }

        this.questions = new ArrayUnorderedList<>();
    }
    /**
     * Randomly shuffles the questions currently stored in the internal list
     * using the Fisher-Yates algorithm.
     */
    private void shuffleQuestions() {
        if (this.questions.isEmpty()) {
            return;
        }

        int size = questions.size();
        Question[] tempQuestions = new Question[size];

        Iterator<Question> it = this.questions.iterator();
        int i = 0;
        while (it.hasNext()) {
            tempQuestions[i] = it.next();
            i++;
        }

        Random rand = new Random();
        for (i = size - 1; i > 0; i--) {
            int index = rand.nextInt(i + 1);
            Question temp = tempQuestions[index];
            tempQuestions[index] = tempQuestions[i];
            tempQuestions[i] = temp;
        }

        this.questions = new ArrayUnorderedList<>();
        for (Question q : tempQuestions) {
            this.questions.addToRear(q);
        }
    }
    /**
     * Retrieves the next question from the queue. If the queue is empty, it first
     * calls {@link #setQueue()} to shuffle and refill the queue from the internal list.
     * The retrieved question is immediately added back to the internal list for future cycles.
     *
     * @return The next {@link Question} in the randomized sequence, or {@code null} if an error occurs.
     */
    public Question getQuestion() {
        if (queueQuestions.isEmpty()) {
            setQueue();
        }

        try {
            Question tempQuestion = queueQuestions.dequeue();
            questions.addToRear(tempQuestion);
            return tempQuestion;
        } catch (Exception e) {
            return null;
        }
    }
    /**
     * Imports the questions from the designated JSON file path and populates
     * the internal questions list. Errors during file loading are logged.
     */
    private void importQuestions() {
        try {
            String jsonContent = readFile("src/main/resources/Questions.json");
            this.questions = parseJsonToArrayList(jsonContent);
        } catch (Exception e) {
            System.err.println("Erro ao carregar questões: " + e.getMessage());
            e.printStackTrace();
        }

    }
    /**
     * Reads the entire content of a file into a single string.
     *
     * @param filename The path to the file to be read.
     * @return A {@code String} containing the entire file content with trimmed lines.
     * @throws IOException If a file reading error occurs.
     */
    private static String readFile(String filename) throws IOException {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line.trim());
            }
        }
        return sb.toString();
    }
    /**
     * Manually parses the raw JSON string content (expected to be an array of question objects)
     * and extracts the question text, options, and correct index to create a list of {@link Question} objects.
     * <p>
     * Note: This implementation uses manual string parsing rather than a dedicated JSON library like Jackson.
     *
     * @param json The raw JSON string containing the question data.
     * @return An {@link ArrayUnorderedList} of parsed {@link Question} objects.
     */
    public static ArrayUnorderedList<Question> parseJsonToArrayList(String json) {
        ArrayUnorderedList<Question> list = new ArrayUnorderedList<>();

        int index = 0;

        while (true) {
            int qIndex = json.indexOf("\"question\":", index);
            if (qIndex == -1) {
                break;
            }

            int startQ = qIndex + 11;
            startQ = json.indexOf("\"", startQ) + 1;

            int endQ = json.indexOf("\",", startQ);
            String questionText = json.substring(startQ, endQ);

            int optLabelIndex = json.indexOf("\"options\":", endQ);
            int startOptArray = json.indexOf("[", optLabelIndex);
            int endOptArray = json.indexOf("]", startOptArray);

            String rawOptions = json.substring(startOptArray + 1, endOptArray);

            String[] optionsArr = new String[4];
            int optCursor = 0;

            for (int i = 0; i < 4; i++) {
                int startQuote = rawOptions.indexOf("\"", optCursor);
                if (startQuote == -1) break;
                int endQuote = rawOptions.indexOf("\"", startQuote + 1);

                optionsArr[i] = rawOptions.substring(startQuote + 1, endQuote);
                optCursor = endQuote + 1;
            }

            int correctLabelIndex = json.indexOf("\"correct_index\":", endOptArray);
            int startCorrect = json.indexOf(":", correctLabelIndex) + 1;
            int endCorrect = json.indexOf("}", startCorrect);
            String numStr = json.substring(startCorrect, endCorrect).trim();
            numStr = numStr.replace(",", "");

            int correctIndex = Integer.parseInt(numStr);

            Question q = new Question(questionText, optionsArr, correctIndex);
            list.addToRear(q);

            index = endCorrect;
        }
        return list;
    }
    /**
     * Retrieves the internal unordered list of questions (used primarily for import/export/refill).
     *
     * @return The {@link ArrayUnorderedList} of {@link Question} objects.
     */
    public ArrayUnorderedList<Question> getQuestions() {
        return questions;
    }
    /**
     * Sets the internal unordered list of questions. If the input is null, it initializes an empty list.
     *
     * @param questions The new {@link ArrayUnorderedList} of questions.
     */
    public void setQuestions(ArrayUnorderedList<Question> questions) {
        if (questions == null) {
            this.questions = new ArrayUnorderedList<>();
        } else {
            this.questions = questions;
        }
    }
    /**
     * Retrieves the queue of questions currently scheduled to be asked.
     *
     * @return The {@link LinkedQueue} of {@link Question} objects.
     */
    public LinkedQueue<Question> getQueueQuestions() {
        return queueQuestions;
    }
    /**
     * Sets the queue of questions. If the input is null, it initializes an empty queue.
     *
     * @param queueQuestions The new {@link LinkedQueue} of questions.
     */
    public void setQueueQuestions(LinkedQueue<Question> queueQuestions) {
        if (queueQuestions == null) {
            this.queueQuestions = new LinkedQueue<>();
        } else {
            this.queueQuestions = queueQuestions;
        }
    }
}