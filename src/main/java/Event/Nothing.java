package Event;

import Interfaces.IPlayer;
import Exceptions.InvalidPlayersCountException;
import Interfaces.IEvent;
import Menus.GameVisuals;

/**
 * Represents a neutral event in the game. When triggered, this event has no mechanical
 * impact on the player's state, status effects, or progress. It serves primarily
 * as a filler or narrative element.
 */
public class Nothing implements IEvent {

    /**
     * Applies the effect of the Nothing event to the specified player.
     * This method contains only a print statement and makes no changes to the player's state.
     *
     * @param player The {@link IPlayer} who encountered the event.
     * @param isRealPlayer A boolean flag indicating if the player is human-controlled.
     * @throws InvalidPlayersCountException This implementation does not throw this exception,
     * but it is included to satisfy the {@link IEvent} interface signature.
     */
    @Override
    public void apply(IPlayer player, boolean isRealPlayer) throws InvalidPlayersCountException {
       System.out.println("Uma pessoa muito cordial desejou-te BOA VIAGEM!");
    }
}
