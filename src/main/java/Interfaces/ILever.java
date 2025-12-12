package Interfaces;

import Lever.Lever;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * Defines the contract for a Lever mechanism, which is typically a component
 * within a specific type of {@link IDivision} (e.g., {@code LeverDivision}).
 * The lever's state or action often influences the maze state or the player's next move.
 * <p>
 * This interface is configured for polymorphism using Jackson annotations.
 */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Lever.class, name = "lever")
})
public interface ILever {

    /**
     * Retrieves the {@link IDivision} that is associated with this lever.
     * This is typically the division that the lever's state change will affect,
     * or the division where the lever is physically located.
     *
     * @return The associated {@link IDivision} object.
     */
    IDivision getDivision();
}