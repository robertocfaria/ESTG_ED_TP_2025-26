package CoreGame;

import Interfaces.*;
import Structures.List.ArrayUnorderedList;
import Structures.Stack.ArrayStack;
import Util.Utils;

/**
 * Represents a player participating in the game, which can be either a human-controlled
 * player or a bot. This class manages the player's state, including their position,
 * any status effects (like stunned or extra rounds), and their full history of movements
 * and events.
 */
public class Player implements IPlayer {
    protected static int botNumber = 1;
    protected String name;
    protected int stunned;
    protected int extraRound;
    protected boolean realPlayer;
    protected ArrayStack<IEvent> movementsHistory;
    protected ArrayStack<IDivision> divisionsHistory;
    protected IDivision division;
    protected ArrayStack<HistoryEntry> fullHistory;
    protected int totMoves;

    /**
     * Constructs a new real (human) player with a specified name.
     * Initializes all history stacks and counters to zero or null, and sets {@code realPlayer} to true.
     *
     * @param name The name of the human player.
     */
    public Player(String name) {
        this.name = name;
        this.stunned = 0;
        this.extraRound = 0;
        this.realPlayer = true;
        this.division = null;
        this.movementsHistory = new ArrayStack<>();
        this.divisionsHistory = new ArrayStack<>();
        this.fullHistory = new ArrayStack<>();
        this.totMoves = 0;
    }

    /**
     * Constructs a new bot player.
     * Assigns a default name using the static {@code botNumber} counter (e.g., "BOT1"),
     * increments the counter, and sets {@code realPlayer} to false.
     * Initializes all history stacks and counters to zero or null.
     */
    public Player() {
        this.name = "BOT" + botNumber;
        botNumber++;
        this.stunned = 0;
        this.extraRound = 0;
        this.realPlayer = false;
        this.division = null;
        this.movementsHistory = new ArrayStack<>();
        this.divisionsHistory = new ArrayStack<>();
        this.fullHistory = new ArrayStack<>();
        this.totMoves = 0;
    }

    /**
     * Returns the name of the player.
     *
     * @return The player's name.
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * Returns the number of rounds the player is currently stunned.
     *
     * @return The current stunned round count.
     */
    @Override
    public int getStunned() {
        return this.stunned;
    }

    /**
     * Sets or adds the specified number of rounds the player will be stunned for.
     * This method is generally used to decrement the stun counter after a turn
     * or set it when a stunning event occurs.
     *
     * @param numberOfRounds The new or updated number of stunned rounds.
     */
    @Override
    public void addStunnedRound(int numberOfRounds) {
        this.stunned = numberOfRounds;
    }

    /**
     * Returns the number of extra moves or rounds the player currently has.
     *
     * @return The current extra round count.
     */
    @Override
    public int getExtraRounds() {
        return this.extraRound;
    }

    /**
     * Sets the number of extra moves or rounds the player receives.
     *
     * @param numberOfRounds The number of extra rounds to grant the player.
     */
    @Override
    public void setExtraRound(int numberOfRounds) {
        this.extraRound = numberOfRounds;
    }

    /**
     * Checks if the player is a real player (human-controlled).
     *
     * @return {@code true} if the player is human; {@code false} if they are a bot.
     */
    @Override
    public boolean isRealPlayer() {
        return realPlayer;
    }

    /**
     * Sets the player's current division in the maze.
     *
     * @param division The new {@link IDivision} where the player is located.
     */
    @Override
    public void setDivision(IDivision division) {
        this.division = division;
    }

    /**
     * Returns the player's current division in the maze.
     *
     * @return The current {@link IDivision} object.
     */
    @Override
    public IDivision getDivision() {
        return this.division;
    }

    /**
     * Returns the last event encountered by the player without removing it from the history stack.
     *
     * @return The most recent {@link IEvent}, or {@code null} if the history is empty.
     */
    @Override
    public IEvent getLastEvent() {
        if (this.movementsHistory.isEmpty()) {
            return null;
        }

        return this.movementsHistory.peek();
    }

    /**
     * Returns the last division the player was in before the current one, without
     * removing it from the history stack.
     *
     * @return The previous {@link IDivision}, or {@code null} if the history is empty.
     */
    @Override
    public IDivision getLastDivision() {
        if (this.divisionsHistory.isEmpty()) {
            return null;
        }

        return this.divisionsHistory.peek();
    }

    /**
     * Executes the player's turn, allowing them to attempt a move within the maze.
     * <p>
     * This method handles:
     * <ul>
     * <li>Checking and decrementing the {@code stunned} counter.</li>
     * <li>Looping for movement attempts as long as the player succeeds or has extra rounds.</li>
     * <li>Interacting with the current division's logic (via {@link IDivision#getComportament(IMaze, IPlayer)}).</li>
     * <li>Handling successful moves, including checking for hallway events.</li>
     * <li>Updating the player's position and history stacks.</li>
     * <li>Checking for victory (reaching a {@link Map.GoalDivision}).</li>
     * <li>Handling failed moves and decrementing {@code extraRound} if available.</li>
     * </ul>
     *
     * @param maze The {@link IMaze} object representing the game map.
     */
    @Override
    public void move(IMaze maze) {
        if (getStunned() > 0) {
            System.out.println(">>> Estás atordoado e perdes esta vez! <<<");
            addStunnedRound(getStunned() - 1);

            addHistoryMove(this.division, "Perdeu a vez por estar atordoado.");
            if (isRealPlayer()) {
                Utils.waitEnter();
            }
            return;
        }

        boolean isTurnActive = true;
        int movesInThisTurn = 0;
        ArrayUnorderedList<String> divisionNames = new ArrayUnorderedList<>();

        while (isTurnActive) {
            if (totMoves > 0) {
                System.out.println("\n-- Historico do jogo/jogada --");
                System.out.println(">> Desde o inicio do jogo ja passou por: " + totMoves + " casas");
                System.out.println(">> Esta jogada ja fez " + movesInThisTurn + " movimentos: ");
                for (String division : divisionNames) {
                    System.out.print(division.toString() + " -> ");
                }
                System.out.println("\n-- Fim do historico do jogo/jogada --");
            }
            System.out.println();

            IDivision currentPos = getDivision();
            IDivision nextPos = currentPos.getComportament(maze, this);

            if (nextPos != null && !nextPos.equals(currentPos)) {
                try {
                    if (!this.fullHistory.isEmpty() && this.fullHistory.peek().getTypeString().equals("MOVEMENT")) {
                        this.fullHistory.peek().setNextDivision(nextPos.getName());
                    }

                    IHallway hallway = maze.getEdge(currentPos, nextPos);
                    IEvent event = hallway.getEvent(this);

                    divisionsHistory.push(currentPos);
                    setDivision(nextPos);

                    this.totMoves++;
                    movesInThisTurn++;
                    divisionNames.addToRear(nextPos.getName());

                    System.out.println(">>> SUCESSO! Avançaste para: " + nextPos.getName());
                    if (event != null) {
                        System.out.println("Encontraste um evento no corredor!");
                        event.apply(this, isRealPlayer());
                        addHistoryEvent(event);
                    }

                    if (nextPos instanceof Map.GoalDivision) {
                        System.out.println("Chegaste ao destino final!");
                        addHistoryMove(nextPos, "!!! VITORIA - JOGO TERMINADO !!!");
                        isTurnActive = false;
                    } else if (getStunned() > 0) {
                        System.out.println("!!! O evento deixou-te ATORDOADO! A tua vez acabou. !!!");
                        isTurnActive = false;
                    } else {
                        System.out.println("Como acertaste, podes continuar a jogar!");
                    }

                } catch (Exception e) {
                    System.out.println("Erro crítico ao mover: " + e.getMessage());
                    isTurnActive = false;
                }
            } else {
                System.out.println(">>> FALHASTE O DESAFIO! <<<");
                if (getExtraRounds() > 0) {
                    System.out.println("MAS ESPERA! Tens " + getExtraRounds() + " Jogada(s) Extra!");
                    setExtraRound(getExtraRounds() - 1);
                } else {
                    System.out.println("A tua vez terminou.");
                    isTurnActive = false;
                }
            }
        }
    }

    /**
     * Creates a new {@link HistoryEntry} of type {@code MOVEMENT} and pushes it
     * onto the player's full history stack.
     *
     * @param division The division associated with the movement.
     * @param log A string description of the movement action.
     */
    @Override
    public void addHistoryMove(IDivision division, String log) {
        HistoryEntry entry = new HistoryEntry(division, log);
        this.fullHistory.push(entry);
    }

    /**
     * Creates a new {@link HistoryEntry} of type {@code EVENT} and pushes it
     * onto the player's full history stack.
     *
     * @param event The {@link IEvent} object that occurred.
     */
    @Override
    public void addHistoryEvent(IEvent event) {
        HistoryEntry entry = new HistoryEntry(event);
        this.fullHistory.push(entry);
    }

    /**
     * Prints the player's full movement and event history to the console,
     * displaying the entries in chronological order (oldest to newest).
     * The history is temporarily reversed and then restored to maintain the stack order.
     */
    @Override
    public void printFullHistory() {
        System.out.println("\n--- LOG do " + this.name.toUpperCase() + " ---");
        Structures.Stack.ArrayStack<HistoryEntry> temp = new Structures.Stack.ArrayStack<>();

        while (!this.fullHistory.isEmpty()) {
            try {
                HistoryEntry entry = this.fullHistory.pop();
                if (entry != null) {
                    System.out.println(entry.toString());
                    temp.push(entry);
                }
            } catch (Exception e) {
                break;
            }
        }

        while (!temp.isEmpty()) {
            try {
                this.fullHistory.push(temp.pop());
            } catch (Exception e) {
                break;
            }
        }
        System.out.println("-----------------------------------");
    }

    /**
     * Creates and returns a complete copy of the player's full history stack.
     * The original history stack remains unchanged.
     *
     * @return A deep copy of the {@code fullHistory} stack as an {@link ArrayStack} of {@link HistoryEntry}.
     */
    public ArrayStack<HistoryEntry> getHistoryCopy() {

        ArrayStack<HistoryEntry> copy = new ArrayStack<>();
        ArrayStack<HistoryEntry> temp = new ArrayStack<>();

        while (!this.fullHistory.isEmpty()) {
            temp.push(this.fullHistory.pop());
        }

        while (!temp.isEmpty()) {
            HistoryEntry entry = temp.pop();
            this.fullHistory.push(entry);
            copy.push(entry);
        }

        return copy;
    }
}
