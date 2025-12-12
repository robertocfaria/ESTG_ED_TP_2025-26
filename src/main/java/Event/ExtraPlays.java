package Event;

import Interfaces.IPlayer;
import Exceptions.InvalidPlayersCountException;
import Interfaces.IEvent;

/**
 * Represents an in-game event that grants a player extra moves or rounds.
 * This event is non-persistent and only affects the current player's state for the round.
 */
public class ExtraPlays implements IEvent {
    /**
     * The fixed number of extra plays granted by this event.
     */
    public static final int EXTRA_PLAYS = 2;

    /**
     * Applies the effect of the ExtraPlays event to the specified player.
     * The player's extra round count is set to {@code EXTRA_PLAYS}.
     *
     * @param player The {@link IPlayer} who encountered and triggered the event.
     * @param isRealPlayer A boolean flag indicating if the player is human-controlled.
     * @throws InvalidPlayersCountException This implementation does not throw this exception,
     * but it is included to satisfy the {@link IEvent} interface signature.
     */
    @Override
    public void apply(IPlayer player, boolean isRealPlayer) throws InvalidPlayersCountException {
        player.setExtraRound(EXTRA_PLAYS);
        System.out.println("Encontras-te um homem generoso. Ele deu-te mais " + EXTRA_PLAYS + " jogadas extra!");
    }
}
