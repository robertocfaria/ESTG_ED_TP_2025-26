package Map;

import Interfaces.IDivision;
import Interfaces.IMap;
import Interfaces.IPlayer;
import Reader.Reader;
import Structures.Interfaces.UnorderedListADT;
import java.util.Iterator;
import java.util.Random;

public class QuestionDivision extends Division {

    private static QuestionManager questions;

    public QuestionDivision(String name) {
        super(name);
        if (questions == null) {
            try {
                questions = new QuestionManager();
            } catch (Exception e) {
                System.err.println("Erro crítico: " + e.getMessage());
            }
        }
    }

    @Override
    public IDivision getComportament(IMap maze, IPlayer player) {

        Question myQuestion = questions.getQuestion();

        //imprime a perungta
        System.out.println(print(myQuestion));

        int numOptions = 4;
        int optionIndex;

        if (player.isRealPlayer()) {
            optionIndex = Reader.readInt(1, numOptions, "Selecione uma resposta: ") - 1;
        } else {
            Random rand = new Random();
            optionIndex = rand.nextInt(numOptions);
            System.out.println("O Bot escolheu a opcao: " + (optionIndex + 1));
        }

        boolean isCorrect = myQuestion.isCorrect(optionIndex);
        String logMsg = "Perg: " + myQuestion.getQuestion() + " | " + (isCorrect ? "ACERTOU" : "FALHOU");
        player.addHistoryMove(this, logMsg);

        if (myQuestion.isCorrect(optionIndex)) {
            System.out.println(">>> Muito Bem! BOA! RESPOSTA CORRETA");

            UnorderedListADT<IDivision> adjacentPositions = maze.getAdjacentVertex(this);
            int neighborsSize = adjacentPositions.size();

            // Converter para array para facilitar acesso por índice
            IDivision[] neighbors = new IDivision[neighborsSize];
            Iterator<IDivision> it = adjacentPositions.iterator();
            int i = 0;

            while (it.hasNext()) {
                IDivision div = it.next();
                neighbors[i] = div;
                System.out.println((i + 1) + ". " + div.getName());
                i++;
            }
            int choiceIndex;
            if (player.isRealPlayer()) {
                choiceIndex = Reader.readInt(1, neighborsSize, "Para avancar: Escolha a sala que pretende: ") - 1;
            } else {
                Random rand = new Random();
                if (neighborsSize > 0) {
                    choiceIndex = rand.nextInt(neighborsSize);
                } else {
                    return this;
                }
                System.out.println("O Bot escolheu a posicao: " + (choiceIndex + 1));
            }
            return neighbors[choiceIndex];
        } else {
            System.out.println("Infelizmente falhaste!");
            return this;
        }
    }


    public String print(Question myQuestion) {
        StringBuilder sb = new StringBuilder();
        sb.append("Esta na sala: ").append(getName()).append("\n");
        sb.append("------------------------------------------------\n");
        sb.append("Para conseguir avancar para a proxima sala tem\n");
        sb.append("de responder acertadamente a esta pergunta:\n");
        if (myQuestion != null) {
            sb.append(myQuestion.toString());
        }
        return sb.toString();
    }
}
