package CoreGame;

import Map.IDivision;
import Map.Map;
import Reader.Reader;
import Structures.List.ArrayUnorderedList;
import Util.Utils;

import java.util.Iterator;

public class GameManager {
    private ArrayUnorderedList<IPlayer> players;
    private IPlayer winnerPlayer;
    private Map maze;
    private int turn;

    //aqui temos de receber o mapa
    public GameManager() {
        this.players = new ArrayUnorderedList<IPlayer>();
        this.winnerPlayer = null;
        this.maze = maze;
        this.turn = 1;
    }

    public void startGame() {
        boolean gameRunning = true;

        addPlayers();
        setInitialPlayerDivisions();

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

            //lógica;

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
        System.out.println(" jogadores reais e 5 jogadores automaticos (BOTS).");

        int realPlayers = reader.readInt(1,10,"Quantos jogadores reais (1 a 10): ");
        for(int i = 0; i < realPlayers; i++) {
            players.addToRear(new HumanPlayer(reader.readString("Nome: ")));
        }

        int botPlayers = reader.readInt(0,10,"Quantos BOTS (0 a 5): ");
        for(int i = 0; i < botPlayers; i++) {
            players.addToRear(new BotPlayer());
        }
        if(botPlayers > 0) {
            System.out.println("BOT(s) adicionados com sucesso!");
        }
    }

    private void setInitialPlayerDivisions() {}

    private void swapPlayerDivision(IPlayer p1, IPlayer p2) {
        IDivision tempDivision = p2.getDivision();
        p2.setDivision(p1.getDivision());
        p1.setDivision(tempDivision);
        //TODO adicionar um evento para bloquear retroceder - quando voltar atras
    }

    private void shuffleAllPlayersDivision() {

    }

    private boolean isWinner(IPlayer player) {
        //TODO - fazer este metodo

        this.winnerPlayer = player;
        return true;
    }

}
