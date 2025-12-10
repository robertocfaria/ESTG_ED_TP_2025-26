package CoreGame;

import Interfaces.IDivision;
import Map.Map;
import Map.GoalDivision;
import Interfaces.IPlayer;
import Menus.GameVisuals;
import Reader.Reader;
import Structures.List.ArrayUnorderedList;
import Util.Utils;

public class GameManager {
    private ArrayUnorderedList<IPlayer> players;
    private IPlayer winnerPlayer;
    private Map maze;
    private int turn;
    private boolean finished;


    public GameManager(Map maze) {
        this.players = new ArrayUnorderedList<IPlayer>();
        this.winnerPlayer = null;
        this.maze = maze;
        this.turn = 1;
        this.finished = false;
    }

    public void startGame() {
        boolean gameRunning = true;

        addPlayers();
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

            if(isWinner(currentPlayer)) {
                GameVisuals.showVictory(currentPlayer.getName());
                return;
            }
        }

        System.out.println("Fim da Ronda: " + this.turn);
        turn++;


    }

    private void addPlayers() {
        Reader reader = new Reader();
        String nameTemp;

        GameVisuals.showPlayerConfigHeader();
        int realPlayers = reader.readInt(1,10,"Quantos jogadores reais (1 a 10): ");
        for(int i = 0; i < realPlayers; i++) {
            nameTemp = reader.readString("Nome do Jogador " + (i+1) + " : ");
            players.addToRear(new HumanPlayer(nameTemp));
            GameVisuals.showPlayerAdded(nameTemp, i+1);
            GameVisuals.showNextPlayerSeparator();
        }

        int botPlayers = reader.readInt(0,10,"Quantos BOTS (0 a 5): ");
        for(int i = 0; i < botPlayers; i++) {
            players.addToRear(new BotPlayer());
        }
        if(botPlayers > 0) {
            System.out.println("BOT(s) adicionados com sucesso!");
        }

        setInitialPosition();
    }

    private void setInitialPosition(){
        for (IPlayer player : players) {
            player.setDivision(maze.getInitial());
        }
    }

    private boolean isWinner(IPlayer player) {
        if(player.getDivision() instanceof GoalDivision) {
            this.winnerPlayer = player;
            return true;
        }
        return false;
    }

}
