package Interfaces;

/**
 * Defines the contract for all events that can occur in the game, such as status
 * effects, position changes, or granting extra turns.
 * <p>
 * Any class implementing this interface must provide a method to apply the event's
 * effect to a target player.
 */
public interface IEvent {

    /**
     * Applies the specific effect of the event to the current player.
     * Implementations may modify the player's status (e.g., stunned, extra rounds),
     * history, or position.
     *
     * @param currentPlayer The {@link IPlayer} object that encountered the event.
     * @param isRealPlayers A boolean flag indicating if the current player is human-controlled,
     * which can influence the event's execution (e.g., for user input).
     */
    void apply(IPlayer currentPlayer, boolean isRealPlayers);
}