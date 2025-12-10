package Map;

import Interfaces.IDivision;
import Interfaces.ILever;
import Interfaces.IMap;
import Lever.Lever;
import Reader.Reader;
import Structures.Interfaces.UnorderedListADT;
import Util.Utils;

import java.util.Random;

public class LeverDivision extends Division {
    private Random rand = new Random();

    public LeverDivision(String name) {
        super(name);
    }

    @Override
    public IDivision getComportament(IMap maze) {
        System.out.println(this);
        int neighborsCount;
        int choice;
        int leversCount;

        try {
            UnorderedListADT<IDivision> neighbors = maze.getAdjacentVertex(this);
            neighborsCount = neighbors.size();

            ILever[] levers = new ILever[neighborsCount * 2];
            leversCount = levers.length;

            for (int i = 0; i < neighborsCount; i++) {
                levers[i] = new Lever(neighbors.removeFirst(), i);
            }

            for (int i = neighborsCount; i < leversCount; i++) {
                levers[i] = new Lever(null, i);
            }

            //Fisher-Yates baralha
            ILever temp;
            int index;
            for (int i = leversCount - 1; i > 0; i--) {
                index = this.rand.nextInt(i + 1);

                temp = levers[index];
                levers[index] = levers[i];
                levers[i] = temp;
            }

            System.out.println("Existem " + leversCount + " alavancas nesta sala.");
            for (int i = 0; i < leversCount; i++) {
                System.out.println((i + 1) + ". Puxar a Alavanca " + (i + 1));
            }
            choice = Reader.readInt(1, (leversCount + 1), "Qual alavanca deseja puxar?:");

            IDivision destination = levers[choice - 1].getDivision();
            if (destination == null) {
                System.out.println("CLACK!... Nada acontece. Parece que esta alavanca esta partida.");
                Utils.waitEnter();
                return this; // O jogador fica na mesma sala
            } else {
                System.out.println("RUMBLE!... Uma passagem secreta abre-se!");
                System.out.println("Estas a caminho da seguinte sala: " + destination.getName());
                Utils.waitEnter();

                return destination; // O jogador move-se
            }
        } catch (Exception e) {
            System.out.println("Erro ao gerar alavancas: " + e.getMessage());
            return this;
        }
    }

    @Override
    public String toString() {
        return "Alavanca";
    }
}
