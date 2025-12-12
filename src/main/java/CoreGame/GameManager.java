package CoreGame;

import Exporters.ExportGameHistory;
import Map.*;
import Interfaces.IPlayer;
import Menus.GameVisuals;
import Reader.Reader;
import Structures.Interfaces.ListADT;
import Structures.List.ArrayUnorderedList;
import Util.Utils;

/**
 * Manages the core game flow, including initialization, player management,
 * turn execution, and determining the winner.
 * It is responsible for orchestrating the interactions between the players,
 * the maze, and the game visualization components.
 */
public class GameManager {
    private ArrayUnorderedList<IPlayer> players;
    private IPlayer winnerPlayer;
    private Maze maze;
    private int turn;
    private boolean finished;

    /**
     * Constructs a new GameManager and initializes the game state.
     * The players list is empty, there is no winner, the game is not finished,
     * and the turn counter is set to 1.
     */
    public GameManager() {
        this.players = new ArrayUnorderedList<>();
        this.winnerPlayer = null;
        this.turn = 1;
        this.finished = false;
    }

    /**
     * Starts the main game loop using the provided maze.
     * <p>
     * The process includes:
     * <ul>
     * <li>Setting the maze.</li>
     * <li>Configuring and adding players (real and bots).</li>
     * <li>Setting the players' initial positions in the maze.</li>
     * <li>Executing game rounds until a winner is found.</li>
     * </ul>
     * Finally, it displays the game result, prints the full movement history of all players,
     * and exports the game history to a JSON file.
     *
     * @param maze The {@link Maze} object to be used for this game session.
     */
    public void startGame(Maze maze) {
        this.maze = maze;
        addPlayers();
        maze.setHallwayPlayers(players);
        this.setInitialPosition();
        boolean gameRunning = true;

        while (gameRunning) {
            turn();
            if (winnerPlayer != null) {
                gameRunning = false;
            }
        }

        System.out.println("\n++++++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println("+++ JOGO TERMINADO - VITORIA DE " + winnerPlayer.getName().toUpperCase());
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++\n");
        Utils.waitEnter();
        System.out.println("--- HISTORICO DAS JOGADAS ---");
        for(IPlayer player : players) {
            player.printFullHistory();
        }

        ExportGameHistory.exportToJson(players);
        System.out.println("O resumo do jogo foi guardado. Consulte quiser!");
        System.out.println("\n");
    }

    /**
     * Executes one full round (turn) of the game.
     * <p>
     * It displays a round separator, iterates through all players allowing each one
     * to make a move, and checks immediately after each move if the player has won.
     * If a winner is found, the round is terminated early.
     */
    private void turn() {
        GameVisuals.showRoundSeparator(this.turn);

        for (IPlayer currentPlayer : players) {
            GameVisuals.showPlayerTurn(currentPlayer.getName());
            currentPlayer.move(maze);
            if (isWinner(currentPlayer)) {
                GameVisuals.showVictory(currentPlayer.getName());
                return;
            }
        }

        System.out.println("\n>>>> Fim da Ronda: " + this.turn + " <<<<");
        turn++;
    }

    /**
     * Prompts the user to configure and add real players and bot players to the game.
     * It validates that at least one player (real or bot) is added before proceeding.
     * For real players, it prompts for a name. Bots are added automatically.
     */
    private void addPlayers() {
        int realPlayers = 0;
        int botPlayers = 0;

        // 1. Validação: Repete as perguntas até a soma ser maior que 0
        do {
            GameVisuals.showPlayerConfigHeader();

            realPlayers = Reader.readInt(0, 10, "Quantos jogadores reais (0 a 10): ");
            botPlayers = Reader.readInt(0, 5, "Quantos BOTS (0 a 5): ");

            if (realPlayers + botPlayers == 0) {
                System.out.println();
                System.out.println("Erro: O jogo precisa de pelo menos 1 jogador (Real ou Bot).");
                System.out.println("Por favor, tente novamente.");
                System.out.println();
            }

        } while (realPlayers + botPlayers == 0);


        String nameTemp;
        for (int i = 0; i < realPlayers; i++) {
            nameTemp = Reader.readString("Nome do Jogador " + (i + 1) + ": ");
            players.addToRear(new Player(nameTemp));

            GameVisuals.showPlayerAdded(nameTemp, i + 1);
            GameVisuals.showNextPlayerSeparator();
        }

        for (int i = 0; i < botPlayers; i++) {
            players.addToRear(new Player());
        }

        if (botPlayers > 0) {
            System.out.println("BOT(s) adicionados com sucesso!");
        }
    }

    /**
     * Sets the initial position for every player in the game.
     * All players are placed in a random starting division provided by the maze.
     */
    private void setInitialPosition() {
        for (IPlayer player : players) {
            player.setDivision(maze.getRandomInitialDivision());
        }
    }

    /**
     * Checks if a given player is the winner of the game.
     * A player wins if their current division is an instance of {@link GoalDivision}.
     * If the player is the winner, the {@code winnerPlayer} field is set.
     *
     * @param player The player to check for a victory condition.
     * @return {@code true} if the player has reached the goal; {@code false} otherwise.
     */
    private boolean isWinner(IPlayer player) {
        if (player.getDivision() instanceof GoalDivision) {
            this.winnerPlayer = player;
            return true;
        }
        return false;
    }

    /**
     * Returns the list of all players currently participating in the game.
     *
     * @return An {@link ListADT} containing all {@link IPlayer} objects.
     */
    public ListADT<IPlayer> getPlayers() {
        return this.players;
    }
}
