package Event;

import Interfaces.IPlayer;
import Exceptions.InvalidPlayersCountException;
import Interfaces.IEvent;

/**
 * Represents a negative event that causes the player to become stunned,
 * forcing them to skip one or more subsequent turns.
 */
public class StunnedPlays implements IEvent {

    /**
     * Applies the stunning effect to the specified player.
     * The player's {@code stunned} round count is set to 1, causing them to
     * lose their next turn. A descriptive message is printed to the console.
     *
     * @param player The {@link IPlayer} who encountered and triggered the event.
     * @param isRealPlayer A boolean flag indicating if the player is human-controlled.
     * @throws InvalidPlayersCountException This implementation does not throw this exception,
     * but it is included to satisfy the {@link IEvent} interface signature.
     */
    @Override
    public void apply(IPlayer player, boolean isRealPlayer) throws InvalidPlayersCountException {
        int stunnedPlays = 1;
        player.addStunnedRound(stunnedPlays);
        System.out.println("Um mafarrico atirou-te com um pedra. Ficas-te atordoado! Vais perder jogadas, para descansar.\n");
    }
}