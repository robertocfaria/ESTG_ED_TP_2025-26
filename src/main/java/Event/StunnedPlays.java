package Event;

import Interfaces.IPlayer;
import Exceptions.InvalidPlayersCountException;
import Interfaces.IEvent;
import Menus.GameVisuals;

import java.util.Random;

public class StunnedPlays implements IEvent {
    private Random rand = new Random();

    @Override
    public void apply(IPlayer player, boolean isRealPlayer) throws InvalidPlayersCountException {
        int stunnedPlays = this.rand.nextInt(2) + 1;

        player.addStunnedRound(stunnedPlays);

        GameVisuals.showStunnedPlaysEvent(player.getName(), stunnedPlays, isRealPlayer);
    }
}
