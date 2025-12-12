package Event;

import Interfaces.IDivision;
import Interfaces.IPlayer;
import Exceptions.InvalidPlayersCountException;
import Interfaces.IEvent;
import Menus.GameVisuals;
import Structures.Interfaces.ListADT;

import java.util.Random;

public class ShuffleAllPlayers implements IEvent {
    private Random rand = new Random();
    private ListADT<IPlayer> players;

    public ShuffleAllPlayers(ListADT<IPlayer> players) {
        this.players = players;
    }

    @Override
    public void apply(IPlayer player, boolean isRealPlayer) throws InvalidPlayersCountException {
        if (this.players.isEmpty()) {
            throw new InvalidPlayersCountException("ShuffleAllPlayers event requires at least 1 player");
        }

        IDivision[] playersDivision = new IDivision[this.players.size()];

        int index = 0;
        for (IPlayer p : this.players) {
            playersDivision[index++] = p.getDivision();
        }

        IDivision temp;
        for (int i = 0; i < playersDivision.length; i++) {
            index = this.rand.nextInt(i + 1);

            temp = playersDivision[index];
            playersDivision[index] = playersDivision[i];
            playersDivision[i] = temp;
        }

        index = 0;
        for (IPlayer p2 : this.players) {
           p2.setDivision(playersDivision[index++]);
        }

        System.out.println("Entras-te numa festa, e todos os jogadores trocaram de salas. O que se passou?");
    }
}