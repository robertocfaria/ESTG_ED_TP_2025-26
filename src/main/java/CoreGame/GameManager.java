package CoreGame;

import Interfaces.IDivision;
import Map.Map;
import Interfaces.IPlayer;
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
        System.out.println("Inicio da Ronda: " + this.turn);

        for (IPlayer currentPlayer : players) {
            System.out.println("Vez do jogador: " + currentPlayer.getName());

            currentPlayer.move(maze);

            if(isWinner(currentPlayer)) {
                System.out.println("Temos um vencedor!");
                return;
            }
        }

        System.out.println("Fim da Ronda: " + this.turn);
        turn++;
    }

    private void addPlayers() {
        Reader reader = new Reader();

        System.out.println("Escolha quantos jogadores vai adicionar. Pode adicionar ate 10");
        System.out.println("jogadores reais e 5 jogadores automaticos (BOTS).");

        int realPlayers = reader.readInt(1,10,"Quantos jogadores reais (1 a 10): ");
        for(int i = 0; i < realPlayers; i++) {
            IDivision d = maze.getInitial();
            players.addToRear(new HumanPlayer(reader.readString("Nome: "), d));
        }

        int botPlayers = reader.readInt(0,10,"Quantos BOTS (0 a 5): ");
        for(int i = 0; i < botPlayers; i++) {
            players.addToRear(new BotPlayer(maze.getInitial()));
        }
        if(botPlayers > 0) {
            System.out.println("BOT(s) adicionados com sucesso!");
        }
    }

    private boolean isWinner(IPlayer player) {
        //TODO - fazer este metodo

        this.winnerPlayer = player;
        return true;
    }

}
