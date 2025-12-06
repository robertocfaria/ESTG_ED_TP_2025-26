package Event;

import CoreGame.IPlayer;
import Interfaces.IDivision;
import Interfaces.IEvent;
import Structures.Interfaces.UnorderedListADT;

import static Event.StunnedPlays.RAND;

public class ShuffleAllPlayers implements IEvent {
    UnorderedListADT<IPlayer> players;

    public ShuffleAllPlayers(UnorderedListADT<IPlayer> players) {
        if (players == null) {
            throw new IllegalArgumentException("List of players is null");
        }

        this.players = players;
    }

    public void apply(String description) {
        System.out.println(description);

        IDivision[] playersDivision = new IDivision[players.size()];

        int index = 0;
        for (IPlayer player : players) {
            playersDivision[index++] = player.getDivision();
        }

        IDivision temp;
        for (int i = 0; i < playersDivision.length; i++) {
            index = RAND.nextInt(i + 1);

            temp = playersDivision[index];
            playersDivision[index] = playersDivision[i];
            playersDivision[i] = temp;
        }

        index = 0;
        for (IPlayer player : players) {
            player.setDivision(playersDivision[index++]);
        }
    }

    @Override
    public IPlayer getPlayer() {
        return this.players.first();
    }
}
