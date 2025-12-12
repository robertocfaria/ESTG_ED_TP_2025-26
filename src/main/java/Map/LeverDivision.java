package Map;

import Interfaces.IDivision;
import Interfaces.ILever;
import Interfaces.IMaze;
import Interfaces.IPlayer;
import Lever.Lever;
import Reader.Reader;
import Structures.Interfaces.UnorderedListADT;
import java.util.Iterator; // Importante
import java.util.Random;

public class LeverDivision extends Division {
    private Random rand = new Random();
    private ILever[] myLevers; // Persistência das alavancas

    public LeverDivision() {
        super();
    }

    public ILever[] getMyLevers() {
        return myLevers;
    }

    public void setMyLevers(ILever[] myLevers) {
        this.myLevers = myLevers;
    }

    public LeverDivision(String name) {
        super(name);
    }

    @Override
    public IDivision getComportament(IMaze maze, IPlayer player) {
        System.out.println(this.toString());

        // 1. GERAÇÃO PREGUIÇOSA (Lazy Loading) - Só gera se ainda não existirem
        if (this.myLevers == null) {
            try {
                UnorderedListADT<IDivision> neighbors = maze.getAdjacentVertex(this);
                int neighborsCount = neighbors.size();
                int totalLevers = neighborsCount * 2;

                this.myLevers = new ILever[totalLevers];

                // CORREÇÃO CRÍTICA: Usar Iterator em vez de removeFirst()
                Iterator<IDivision> it = neighbors.iterator();
                int i = 0;

                // Preenche as reais
                while(it.hasNext()) {
                    this.myLevers[i] = new Lever(it.next(), i);
                    i++;
                }

                // Preenche as falsas (null)
                for (; i < totalLevers; i++) {
                    this.myLevers[i] = new Lever(null, i);
                }

                // Baralhar (Fisher-Yates)
                for (int k = totalLevers - 1; k > 0; k--) {
                    int index = this.rand.nextInt(k + 1);
                    ILever temp = this.myLevers[index];
                    this.myLevers[index] = this.myLevers[k];
                    this.myLevers[k] = temp;
                }

            } catch (Exception e) {
                System.out.println("Erro ao gerar alavancas: " + e.getMessage());
                return this;
            }
        }

        int leversCount = this.myLevers.length;
        System.out.println("Existem " + leversCount + " alavancas nesta sala.");

        for (int i = 0; i < leversCount; i++) {
            System.out.println((i + 1) + " - Alavanca");
        }

        int choiceIndex;
        if (player.isRealPlayer()) {
            choiceIndex = Reader.readInt(1, leversCount, "Qual alavanca deseja puxar?: ") - 1;
        } else {
            choiceIndex = rand.nextInt(leversCount);
            System.out.println("O " + player.getName() + " escolheu a alavanca " + (choiceIndex + 1));
        }
        // 3. LÓGICA DE MOVIMENTO
        ILever chosenLever = this.myLevers[choiceIndex];
        IDivision destination = chosenLever.getDivision();

        String resultado;
        if (destination == null) {
            resultado = "ARMADILHA";
        } else {
            resultado = "PASSAGEM ABERTA PARA A SALA: " + destination.getName();
        }
        String log = "Escolheu Alavanca " + (choiceIndex + 1) + " -> " + resultado;
        player.addHistoryMove(this, log);

        if (destination == null) {
            System.out.println(">> CLACK!... Nada acontece. Parece que esta alavanca esta partida.");
            return this;
        } else {
            System.out.println(">> RUMBLE!... Uma passagem secreta abre-se!");

            return destination;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Esta na sala: ").append(getName()).append("\n");
        sb.append("------------------------------------------------\n");
        sb.append("Para conseguir avancar para a proxima sala tem\n");
        sb.append("de escolher uma alavanca!");

        return sb.toString();
    }
}