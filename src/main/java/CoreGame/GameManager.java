package CoreGame;

import Interfaces.IHallway;
import Map.*;
import Interfaces.IPlayer;
import Menus.GameVisuals;
import Reader.Reader;
import Structures.Interfaces.ListADT;
import Structures.List.ArrayUnorderedList;
import Util.Utils;

public class GameManager {
    private ArrayUnorderedList<IPlayer> players;
    private IPlayer winnerPlayer;
    private Map maze;
    private int turn;
    private boolean finished;

    public GameManager() {
        this.players = new ArrayUnorderedList<>();
        this.winnerPlayer = null;
        this.turn = 1;
        this.finished = false;
    }

    public void startGame(Map maze) {
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
        System.out.println("Jogo Terminado!");
        System.out.println("Parabens " + winnerPlayer.getName() + " pela Vitoria");
        Utils.waitEnter();

    }

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

        System.out.println("Fim da Ronda: " + this.turn);
        turn++;
    }

    private void addPlayers() {
        String nameTemp;

        GameVisuals.showPlayerConfigHeader();
        int realPlayers = Reader.readInt(0, 10, "Quantos jogadores reais (0 a 10): ");
        for (int i = 0; i < realPlayers; i++) {
            nameTemp = Reader.readString("Nome do Jogador " + (i + 1) + ": ");
            players.addToRear(new Player(nameTemp));

            GameVisuals.showPlayerAdded(nameTemp, i + 1);
            GameVisuals.showNextPlayerSeparator();
        }

        int botPlayers = Reader.readInt(0, 5, "Quantos BOTS (0 a 5): ");
        for (int i = 0; i < botPlayers; i++) {
            players.addToRear(new Player());
        }
        if (botPlayers > 0) {
            System.out.println("BOT(s) adicionados com sucesso!");
        }

        //verifica se tem pelo menos 1 jogador


    }

    private void setInitialPosition() {
        for (IPlayer player : players) {
            player.setDivision(maze.getRandomInitialDivision());
        }
    }

    private boolean isWinner(IPlayer player) {
        if (player.getDivision() instanceof GoalDivision) {
            this.winnerPlayer = player;
            return true;
        }
        return false;
    }

    public ListADT<IPlayer> getPlayers() {
        return this.players;
    }
}
