package Event;

import Interfaces.IPlayer;
import Exceptions.InvalidPlayersCountException;
import Interfaces.IEvent;
import Structures.Interfaces.ListADT;

import java.util.Random;

public class StunnedPlays implements IEvent {
    private Random rand = new Random();

    @Override
    public void apply(ListADT<IPlayer> players) throws InvalidPlayersCountException {
        if (players.size() != 1) {
            throw new InvalidPlayersCountException("StunnedPlays event applies only to 1 player at a time");
        }

        IPlayer player = players.first();

        int stunnedPlays = this.rand.nextInt(2) + 1;
        player.addStunnedRound(stunnedPlays);

        System.out.println(player.getName() + " got stunned for " + stunnedPlays + " turns");
    }
}
