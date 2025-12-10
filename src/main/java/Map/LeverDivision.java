package Map;

import Interfaces.IDivision;
import Interfaces.ILever;
import Interfaces.IMap;
import Lever.Lever;
import NewSctructures.ArrayUnorderedListWithGet;
import NewSctructures.UnorderedListWithGetADT;
import Reader.Reader;
import Structures.Interfaces.UnorderedListADT;
import Structures.List.ArrayUnorderedList;
import Util.Utils;

import java.util.Random;

public class LeverDivision extends Division {
    private UnorderedListADT<ILever> myLevers;

    public LeverDivision(String name) {
        super(name);
    }

    @Override
    public IDivision getComportament(IMap maze) {
        System.out.println(toString());
        if (this.myLevers == null || this.myLevers.isEmpty()) {
            this.myLevers = new ArrayUnorderedList<>();
            try {
                //aqui saca as divisoes adjacentes e cria um array com o dobro do tamanho
                UnorderedListADT<IDivision> neighbors = maze.getAdjacentVertex(this);
                int realCount = neighbors.size();
                IDivision[] tempArray = new IDivision[realCount * 2];
                for (int i = 0; i < realCount; i++) {
                    tempArray[i] = neighbors.removeFirst();
                }
                //as posicoes que sobram ficam a null
                for (int i = realCount; i < tempArray.length; i++) {
                    tempArray[i] = null;
                }

                // Fisher-Yates baeralha
                Random rand = new Random();
                for (int i = tempArray.length - 1; i > 0; i--) {
                    int index = rand.nextInt(i + 1);
                    // Troca
                    IDivision temp = tempArray[index];
                    tempArray[index] = tempArray[i];
                    tempArray[i] = temp;
                }
                System.out.println("Existem " + tempArray.length + " alavancas nesta sala.");

                for (int i = 0; i < tempArray.length; i++) {
                    System.out.println((i + 1) + ". Puxar a Alavanca " + (i + 1));
                }

                int choice = Reader.readInt(1, (tempArray.length+1), "Qual alavanca queres puxar? ");

                IDivision destination = tempArray[choice-1];

                if (destination == null) {
                    System.out.println("CLACK!... Nada acontece. Parece que esta alavanca está partida.");
                    Utils.waitEnter();
                    return this; // O jogador fica na mesma sala
                } else {
                    System.out.println("RUMBLE!... Uma passagem secreta abre-se!");
                    System.out.println("Estas a caminho sa seguinte sala: " + destination.getName());
                    Utils.waitEnter();

                    return destination; // O jogador move-se
                }

            } catch (Exception e) {
                System.out.println("Erro ao gerar alavancas: " + e.getMessage());
                return this;
            }
        }
        return this;
    }

    @Override
    public String toString() {
        return "Alavanca";
    }
}
