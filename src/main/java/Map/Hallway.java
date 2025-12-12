package Map;

import Event.*;
import Interfaces.IEvent;
import Interfaces.IHallway;
import Interfaces.IPlayer;
import Structures.Interfaces.ListADT;
import Structures.List.DoubleLinkedUnorderedList;

/**
 * Represents a connection (edge) between two divisions in the maze.
 * A Hallway is responsible for generating and determining the occurrence
 * of a random {@link IEvent} when a player traverses it.
 * It uses a static {@link EventManager} for event generation.
 */
public class Hallway implements IHallway {
    private static EventManager EVENTS = new EventManager();
    private ListADT<IPlayer> players;

    /**
     * Constructs a new Hallway, initializing the internal list of players as empty.
     */
    public Hallway() {
        this.players = new DoubleLinkedUnorderedList<>();
    }

    /**
     * Retrieves the list of all players associated with this hallway.
     * This list is primarily used by global events like {@code ShuffleAllPlayers}.
     *
     * @return The {@link ListADT} of {@link IPlayer} objects.
     */
    public ListADT<IPlayer> getPlayers() {
        return players;
    }

    /**
     * Sets the list of all active players in the game for the hallway.
     * If the provided list is null, it initializes a new empty list.
     *
     * @param players The {@link ListADT} of all active {@link IPlayer} objects.
     */
    @Override
    public void setPlayers(ListADT<IPlayer> players) {
        if (players == null) {
            this.players = new DoubleLinkedUnorderedList<>();
        } else {
            this.players = players;
        }
    }

    /**
     * Retrieves the static {@link EventManager} instance used by all hallways.
     *
     * @return The static {@link EventManager}.
     */
    public static EventManager getEVENTS() {
        return EVENTS;
    }

    /**
     * Sets the static {@link EventManager} instance used by all hallways.
     *
     * @param EVENTS The new {@link EventManager} instance.
     */
    public static void setEVENTS(EventManager EVENTS) {
        Hallway.EVENTS = EVENTS;
    }

    /**
     * Determines and retrieves a random {@link IEvent} for a player traversing the hallway,
     * applying specific game rules to prevent undesirable or impossible event combinations.
     * <p>
     * Rules applied:
     * <ul>
     * <li>Prevents a {@code RollBack} event if the player's immediate last event was
     * a global position-changing event (like {@code SwapTwoPlayers} or {@code ShuffleAllPlayers}),
     * ensuring the {@code RollBack} targets a legitimate previous division.</li>
     * <li>Prevents a {@code SwapTwoPlayers} event if there are fewer than 2 players in the game.</li>
     * </ul>
     *
     * @param player The {@link IPlayer} currently moving through the hallway.
     * @return An {@link IEvent} object to be applied to the player.
     */
    @Override
    public IEvent getEvent(IPlayer player) {
        IEvent event = EVENTS.getRandomEvent(this.players);
        IEvent playerLastEvent = player.getLastEvent();

        if (playerLastEvent != null) { // significa que é a primeira jogada
            if (event instanceof RollBack) {
                if (playerLastEvent instanceof SwapTwoPlayers || playerLastEvent instanceof ShuffleAllPlayers) {
                    do {
                        event = EVENTS.getRandomEvent(this.players);
                    } while (event instanceof RollBack);
                }
            }
        }


        if (this.players.size() < 2) {
            while (event instanceof SwapTwoPlayers) {
                event = EVENTS.getRandomEvent(this.players);
            }
        }

        return event;
    }

    /**
     * Returns a simple string representation of the Hallway.
     *
     * @return The string "Hallway".
     */
    @Override
    public String toString() {
        return "Hallway";
    }
}