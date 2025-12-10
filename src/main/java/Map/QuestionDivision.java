package Map;

import Interfaces.IDivision;
import Interfaces.IMap;
import Reader.Reader;
import Structures.Exceptions.ElementNotFoundException;
import Structures.Interfaces.UnorderedListADT;
import Util.Utils;
import java.util.Iterator;

public class QuestionDivision extends Division{

    private static QuestionManager questions;

    public QuestionDivision(String name) {
        super(name);
        if (questions == null) {
            try {
                questions = new QuestionManager();
            } catch (Exception e) {
                System.err.println("Erro crítico ao carregar QuestionManager: " + e.getMessage());
            }
        }
    }

    @Override
    public IDivision getComportament(IMap maze) {
        Reader reader = new Reader();
        Question questTemp = questions.getQuestion();

        System.out.println(toString());
        System.out.println(questTemp.toString());
        int option = (reader.readInt(1,4, "Seleicone uma resposta: ") - 1);

        //TODO COLOCAR A STACK A GUARDAR A PERGUNTA RESPOSTA

        if(questTemp.isCorrect(option)) {
            System.out.println("Muito Bem! BOA");
            System.out.println("Como acertaste, podes avancar!");
            System.out.println("Para que sala queres ir?");
            UnorderedListADT<IDivision> adjacentPositions = maze.getAdjacentVertex(this);


            Iterator<IDivision> it = adjacentPositions.iterator();
            IDivision[] neighbors = new IDivision[adjacentPositions.size()];
            int i = 0;
            while (it.hasNext()) {
                IDivision div = it.next();
                neighbors[i] = div;
                System.out.println((i + 1) + ". " + div.getName());
                i++;
            }

            int choice = reader.readInt(1, adjacentPositions.size(), "Escolha a sala: ");

            return neighbors[choice - 1];

        } else {
            System.out.println("Infelizmente falhaste! Melhor sorte na proxima.");
            Utils.waitEnter();
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

        return sb.toString();
     }
}
