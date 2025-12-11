package Event;

import Interfaces.IPlayer;
import Exceptions.InvalidPlayersCountException;
import Interfaces.IEvent;
import Menus.GameVisuals;

public class ExtraPlays implements IEvent {
    public static final int EXTRA_PLAYS = 2;

    @Override
    public void apply(IPlayer player, boolean isRealPlayer) throws InvalidPlayersCountException {
        player.setExtraRound(EXTRA_PLAYS);

        GameVisuals.showExtraPlaysEvent(player.getName(), EXTRA_PLAYS, isRealPlayer);
    }
}
