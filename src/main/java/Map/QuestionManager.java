package Map;

import Structures.List.ArrayUnorderedList;
import Structures.Queue.LinkedQueue;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.Random;

public class QuestionManager {
    private ArrayUnorderedList<Question> questions;
    private LinkedQueue<Question> queueQuestions;

    public QuestionManager() {
        this.questions = new ArrayUnorderedList<>();
        this.queueQuestions = new LinkedQueue<>();
        importQuestions();
        setQueue();
    }

    public ArrayUnorderedList<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(ArrayUnorderedList<Question> questions) {
        this.questions = questions;
    }

    public LinkedQueue<Question> getQueueQuestions() {
        return queueQuestions;
    }

    public void setQueueQuestions(LinkedQueue<Question> queueQuestions) {
        this.queueQuestions = queueQuestions;
    }

    private void setQueue() {
        shuffleQuestions();

        this.queueQuestions = new LinkedQueue<>();
        for (Question question : this.questions) {
            queueQuestions.enqueue(question);
        }

        this.questions = new ArrayUnorderedList<>();
    }

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

    private void importQuestions() {
        try {
            String jsonContent = readFile("src/main/resources/Questions.json");
            this.questions = parseJsonToArrayList(jsonContent);
        } catch (Exception e) {
            System.err.println("Erro ao carregar questões: " + e.getMessage());
            e.printStackTrace();
        }

    }

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

    public static ArrayUnorderedList<Question> parseJsonToArrayList(String json) {
        ArrayUnorderedList<Question> list = new ArrayUnorderedList<>();

        int index = 0;

        while (true) {
            int qIndex = json.indexOf("\"question\":", index);
            if (qIndex == -1) {
                break;
            }

            int startQ = qIndex + 11; // 11 tamanho de "question": + margem se houver espaços
            startQ = json.indexOf("\"", startQ) + 1; // Procura a primeira aspa depois dos dois pontos

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
            int startCorrect = json.indexOf(":", correctLabelIndex) + 1; // Procura o valor numérico (pode haver espaços)
            int endCorrect = json.indexOf("}", startCorrect);
            String numStr = json.substring(startCorrect, endCorrect).trim();
            numStr = numStr.replace(",", ""); // Remove virgulas se existirem (caso não seja o último elemento do json)

            int correctIndex = Integer.parseInt(numStr);

            Question q = new Question(questionText, optionsArr, correctIndex);
            list.addToRear(q);

            index = endCorrect;
        }
        return list;
    }
}