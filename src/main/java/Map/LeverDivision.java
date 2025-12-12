package Map;

import Interfaces.IDivision;
import Interfaces.ILever;
import Interfaces.IMaze;
import Interfaces.IPlayer;
import Lever.Lever;
import Reader.Reader;
import Structures.Interfaces.UnorderedListADT;
import java.util.Iterator;
import java.util.Random;

/**
 * Represents a division in the maze where the player must solve a physical puzzle
 * involving choosing a correct lever to find the exit.
 * <p>
 * This division dynamically generates a set of levers on the first visit, with
 * some leading to an adjacent division (correct exit) and others leading to nowhere (traps).
 */
public class LeverDivision extends Division {
    private Random rand = new Random();
    private ILever[] myLevers;

    /**
     * Default constructor required for Jackson deserialization.
     */
    public LeverDivision() {
        super();
    }

    /**
     * Defines the behavior of the lever division:
     * <ol>
     * <li>If it's the first visit, it generates levers: an equal number of
     * correct levers (linking to neighbors) and trap levers (linking to null).
     * The total number of levers is {@code neighbors * 2}. The array is then shuffled.</li>
     * <li>Prompts the player (or randomly chooses for bots) to pull a lever.</li>
     * <li>Logs the outcome to the player's history.</li>
     * <li>If a trap lever is chosen, the player remains in the current division.</li>
     * <li>If a correct lever is chosen, the player moves to the corresponding division.</li>
     * </ol>
     *
     * @param maze The overall {@link IMaze} structure.
     * @param player The {@link IPlayer} currently interacting with the division.
     * @return The {@link IDivision} the player moves to, or the current division if a trap is chosen.
     */
    @Override
    public IDivision getComportament(IMaze maze, IPlayer player) {
        System.out.println(this);

        if (this.myLevers == null) {
            try {
                UnorderedListADT<IDivision> neighbors = maze.getAdjacentVertex(this);
                int neighborsCount = neighbors.size();
                int totalLevers = neighborsCount * 2;

                this.myLevers = new ILever[totalLevers];

                Iterator<IDivision> it = neighbors.iterator();
                int i = 0;

                while(it.hasNext()) {
                    this.myLevers[i] = new Lever(it.next(), i);
                    i++;
                }

                for (; i < totalLevers; i++) {
                    this.myLevers[i] = new Lever(null, i);
                }

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
    /**
     * Retrieves the array of levers present in this division.
     *
     * @return An array of {@link ILever} objects.
     */
    public ILever[] getMyLevers() {
        return myLevers;
    }
    /**
     * Sets the array of levers for this division.
     *
     * @param myLevers The new array of {@link ILever} objects.
     */
    public void setMyLevers(ILever[] myLevers) {
        this.myLevers = myLevers;
    }
    /**
     * Constructs a LeverDivision with a specified name.
     *
     * @param name The descriptive name of the division.
     */
    public LeverDivision(String name) {
        super(name);
    }
    /**
     * Provides a multi-line string description of the division, prompting the player
     * to choose a lever.
     *
     * @return A formatted string detailing the division's challenge.
     */
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