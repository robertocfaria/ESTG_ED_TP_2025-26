package Map;

import Interfaces.IDivision;
import Interfaces.IMaze;
import Interfaces.IPlayer;

/**
 * An abstract base class that provides common properties and methods for all
 * specific division types (rooms) in the maze.
 * It implements the {@link IDivision} interface and handles the storage of the
 * division's name.
 */
public abstract class Division implements IDivision {
    private String name;

    /**
     * Default constructor required for Jackson deserialization.
     */
    public Division() {
    }

    /**
     * Constructs a Division with a specified name.
     *
     * @param name The descriptive name of the division (e.g., "Sala Misteriosa").
     */
    public Division (String name) {
        this.name = name;
    }

    /**
     * Retrieves the name of the division.
     *
     * @return The division's name as a {@code String}.
     */
    @Override
    public String getName() { return this.name; }

    /**
     * Sets a new name for the division.
     *
     * @param name The new name of the division.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Abstract method that must be implemented by concrete subclasses to define
     * the specific challenge or behavior of this division and determine the player's
     * next position based on the outcome.
     *
     * @param maze The overall {@link IMaze} structure.
     * @param player The {@link IPlayer} currently interacting with the division.
     * @return The {@link IDivision} the player moves to, or the current division, or {@code null} if movement fails.
     */
    @Override
    public abstract IDivision getComportament(IMaze maze, IPlayer player);
}