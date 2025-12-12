package Interfaces;

import Map.*;
import com.fasterxml.jackson.annotation.*;

/**
 * Defines the contract for all division types (rooms/locations) within the {@link IMaze}.
 * Each division represents a node in the graph structure of the maze and encapsulates
 * specific behavior that affects the player.
 * <p>
 * This interface is configured for polymorphism and persistence using Jackson annotations,
 * allowing it to be correctly serialized and deserialized regardless of the concrete
 * implementation (e.g., {@code QuestionDivision}, {@code LeverDivision}, etc.).
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = QuestionDivision.class, name = "question"),
        @JsonSubTypes.Type(value = LeverDivision.class, name = "lever"),
        @JsonSubTypes.Type(value = GoalDivision.class, name = "goal")
})
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
public interface IDivision {
    /**
     * Retrieves the unique or descriptive name of the division.
     *
     * @return The name of the division as a {@code String}.
     */
    String getName();

    /**
     * Executes the specific behavior or challenge associated with this division
     * and determines the player's next position based on the outcome.
     * <p>
     * If the player successfully completes the division's challenge, the method returns
     * the next {@link IDivision} they can move to. If they fail or stay put, it returns
     * the current division or {@code null} depending on the implementation.
     *
     * @param maze The overall {@link IMaze} structure, allowing the division to check
     * for connections or other maze properties.
     * @param player The {@link IPlayer} currently interacting with the division.
     * @return The {@link IDivision} the player moves to, or the current division, or {@code null} if movement fails.
     */
    IDivision getComportament(IMaze maze, IPlayer player);
}