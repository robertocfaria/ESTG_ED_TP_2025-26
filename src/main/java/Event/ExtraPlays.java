package Event;

import Interfaces.IPlayer;
import Exceptions.InvalidPlayersCountException;
import Interfaces.IEvent;
import Structures.Interfaces.ListADT;

public class ExtraPlays implements IEvent {
    public static final int EXTRA_PLAYS = 2;

    @Override
    public void apply(ListADT<IPlayer> players) throws InvalidPlayersCountException {
        if (players.size() != 1) {
            throw new InvalidPlayersCountException("ExtraPlays event applies only to 1 player at a time");
        }

        IPlayer player = players.first();
        player.addExtraRound(EXTRA_PLAYS);

        System.out.println(EXTRA_PLAYS + "to: " + player.getName());
    }
}
