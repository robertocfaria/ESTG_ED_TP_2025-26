package Interfaces;

import Map.Hallway;
import Structures.Interfaces.ListADT;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import com.fasterxml.jackson.annotation.*;

/**
 * Defines the contract for all hallways (connections) between divisions in the maze.
 * A hallway represents an edge in the graph structure and may contain events that
 * a player encounters while moving between divisions.
 * <p>
 * This interface is configured for polymorphism and persistence using Jackson annotations,
 * allowing it to be correctly serialized and deserialized.
 */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Hallway.class, name = "hallway")
})
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
public interface IHallway {

    /**
     * Retrieves an event that the specified player encounters while traversing this hallway.
     * The hallway is responsible for determining whether an event occurs (e.g., based on chance)
     * and instantiating the appropriate {@link IEvent} implementation.
     *
     * @param player The {@link IPlayer} currently moving through the hallway.
     * @return An {@link IEvent} object to be applied to the player, or {@code null} if no event occurs.
     */
    IEvent getEvent(IPlayer player);

    /**
     * Sets the list of all active players in the game for the hallway.
     * This is necessary for events that modify the positions or states of other players
     * (e.g., {@code SwapTwoPlayers} or {@code ShuffleAllPlayers}).
     *
     * @param players The {@link ListADT} of all active {@link IPlayer} objects in the game.
     */
    void setPlayers(ListADT<IPlayer> players);
}