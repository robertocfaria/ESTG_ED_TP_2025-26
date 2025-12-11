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
    private Question myQuestion; // Persistência: A pergunta desta sala específica

    public QuestionDivision(String name) {
        super(name);
        if (questions == null) {
            try {
                questions = new QuestionManager();
            } catch (Exception e) {
                System.err.println("Erro crítico: " + e.getMessage());
            }
        }
        // Atribui a pergunta logo na criação da sala
        if (questions != null) {
            this.myQuestion = questions.getQuestion();
        }
    }

    @Override
    public IDivision getComportament(IMap maze, IPlayer player) {
        // Fallback de segurança caso a pergunta seja null
        if (this.myQuestion == null && questions != null) {
            this.myQuestion = questions.getQuestion();
        }

        System.out.println(toString()); // O toString já imprime a pergunta

        int numOptions = 4; // Ou myQuestion.getOptions().length se for dinâmico
        int optionIndex;

        if (player.isRealPlayer()) {
            // Humano: Lê 1 a 4, subtrai 1 para obter índice 0 a 3
            optionIndex = Reader.readInt(1, numOptions, "Selecione uma resposta: ") - 1;
        } else {
            // Bot: Gera índice 0 a 3 diretamente
            Random rand = new Random();
            optionIndex = rand.nextInt(numOptions);
            System.out.println("O Bot escolheu a opção: " + (optionIndex + 1));
        }

        // TODO: COLOCAR A STACK A GUARDAR A PERGUNTA RESPOSTA (player.addHistory(...))

        if (this.myQuestion.isCorrect(optionIndex)) {
            System.out.println("Muito Bem! BOA");

            UnorderedListADT<IDivision> adjacentPositions = maze.getAdjacentVertex(this);
            int neighborsSize = adjacentPositions.size();

            // Converter para array para facilitar acesso por índice
            IDivision[] neighbors = new IDivision[neighborsSize];
            Iterator<IDivision> it = adjacentPositions.iterator();
            int i = 0;

            System.out.println("Para que sala queres ir?");
            while (it.hasNext()) {
                IDivision div = it.next();
                neighbors[i] = div;
                System.out.println((i + 1) + ". " + div.getName());
                i++;
            }

            int choiceIndex;
            if (player.isRealPlayer()) {
                choiceIndex = Reader.readInt(1, neighborsSize, "Escolha a sala: ") - 1;
            } else {
                Random rand = new Random();
                // nextInt(bound) gera de 0 a bound-1. Perfeito para índices de array.
                if (neighborsSize > 0) {
                    choiceIndex = rand.nextInt(neighborsSize);
                } else {
                    return this; // Sem saída
                }
                System.out.println("O Bot escolheu a posição: " + (choiceIndex + 1));
            }

            return neighbors[choiceIndex];

        } else {
            System.out.println("Infelizmente falhaste!");
            return this;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Esta na sala: ").append(getName()).append("\n");
        sb.append("------------------------------------------------\n");
        sb.append("Para conseguir avancar para a proxima sala tem\n");
        sb.append("de responder acertadamente a esta pergunta:\n");
        if (this.myQuestion != null) {
            sb.append(this.myQuestion.toString());
        }
        return sb.toString();
    }
}
