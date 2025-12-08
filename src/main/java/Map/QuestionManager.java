package Map;

import Structures.List.ArrayList;
import Structures.List.ArrayUnorderedList;



import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class QuestionManager {

    private ArrayUnorderedList<Question> questions;


    public QuestionManager() {
        //this.questions = new ArrayUnorderedList<>();
        importQuestions();
    }

    public Question getQuestion() {


        return null;
    }

    //baralhar perguntas
    //se criar qeue
    //

    private void importQuestions() {
        try {
            String jsonContent = readFile("questions.json");
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