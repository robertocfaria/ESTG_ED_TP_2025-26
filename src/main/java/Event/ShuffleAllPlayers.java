package Event;

import CoreGame.IPlayer;
import Exceptions.InvalidPlayersCountException;
import Map.IDivision;
import Interfaces.IEvent;
import Structures.Interfaces.ListADT;
import Structures.Interfaces.UnorderedListADT;

import static Event.StunnedPlays.RAND;

public class ShuffleAllPlayers implements IEvent {

    @Override
    public void apply(ListADT<IPlayer> players) throws InvalidPlayersCountException {
        if (players.isEmpty()) {
            throw new InvalidPlayersCountException("ShuffleAllPlayers event requires at least 1 player");
        }

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

        System.out.println("All players have swaped positions randomly");
    }
}
