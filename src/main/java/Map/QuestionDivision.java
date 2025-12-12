package Map;

import Interfaces.IDivision;
import Interfaces.IMaze;
import Interfaces.IPlayer;
import Reader.Reader;
import Structures.Interfaces.UnorderedListADT;
import java.util.Iterator;
import java.util.Random;
/**
 * Represents a division in the maze where the player must correctly answer a
 * multiple-choice question to proceed to an adjacent division.
 * If the player answers incorrectly, they remain in the current division.
 */
public class QuestionDivision extends Division {

    private static QuestionManager questions = new QuestionManager();
    /**
     * Default constructor required for Jackson deserialization.
     */
    public QuestionDivision() {
        super();
    }
    /**
     * Constructs a QuestionDivision with a specified name.
     * It ensures the static {@code QuestionManager} is initialized if it's currently null.
     *
     * @param name The descriptive name of the division.
     */
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

    /**
     * Defines the behavior of the question division:
     * <ol>
     * <li>A random {@link Question} is retrieved from the {@code QuestionManager}.</li>
     * <li>The player (or bot) provides an answer choice.</li>
     * <li>The outcome (Correct/Incorrect) is logged to the player's history.</li>
     * <li>If correct, the player is prompted to choose one of the adjacent divisions to move to.</li>
     * <li>If incorrect, the player remains in the current division.</li>
     * </ol>
     *
     * @param maze The overall {@link IMaze} structure, used to find adjacent divisions for successful movement.
     * @param player The {@link IPlayer} currently interacting with the division.
     * @return The {@link IDivision} the player moves to upon a correct answer, or the current division upon failure.
     */
    @Override
    public IDivision getComportament(IMaze maze, IPlayer player) {

        Question myQuestion = questions.getQuestion();

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

    /**
     * Formats the current division's name and the question text into a message
     * ready to be displayed to the player.
     *
     * @param myQuestion The {@link Question} object to be displayed.
     * @return A formatted string containing the room status and the question.
     */
    public String print(Question myQuestion) {
        StringBuilder sb = new StringBuilder();
        sb.append("Esta na sala: ").append(getName()).append("\n");
        sb.append("------------------------------------------------\n");
        sb.append("Para conseguir avancar para a proxima sala tem\n");
        sb.append("de responder acertadamente a esta pergunta:\n");
        if (myQuestion != null) {
            sb.append(myQuestion);
        }
        return sb.toString();
    }
}
