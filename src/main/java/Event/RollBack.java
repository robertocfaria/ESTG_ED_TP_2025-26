package Event;

import Interfaces.IDivision;
import Interfaces.IPlayer;
import Exceptions.InvalidPlayersCountException;
import Interfaces.IEvent;

/**
 * Represents a detrimental event that forces the player to revert their position
 * to the previous division they occupied before the current move.
 */
public class RollBack implements IEvent {

    /**
     * Applies the rollback effect to the specified player.
     * The player's current division is replaced with the division they were in
     * before their last successful move, effectively moving them backward one step.
     *
     * @param player The {@link IPlayer} who encountered and triggered the event.
     * @param isRealPlayer A boolean flag indicating if the player is human-controlled.
     * @throws InvalidPlayersCountException This implementation does not throw this exception,
     * but it is included to satisfy the {@link IEvent} interface signature.
     */
    @Override
    public void apply(IPlayer player, boolean isRealPlayer) throws InvalidPlayersCountException {
        IDivision lastPos = player.getLastDivision();
        player.setDivision(lastPos);
        System.out.println("Encontras-te um cão bravo. Vais ter de voltar para a casa onde estavas.");
    }
}
