package Lever;

import Interfaces.IDivision;
import Interfaces.ILever;

import com.fasterxml.jackson.annotation.*;

/**
 * Represents a physical lever object in the game, which is associated with a specific
 * {@link IDivision} and has a unique identifier. This class implements the core
 * functionality and state management for a game lever.
 * <p>
 * It is configured with Jackson annotations to support polymorphic persistence and
 * serialization, ensuring that maze structures can be saved and loaded correctly.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Lever.class, name = "lever")
})
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
public class Lever implements ILever {
    private IDivision division;
    private int id;

    /**
     * Default constructor required for Jackson deserialization.
     */
    public Lever() {
    }

    /**
     * Constructs a Lever with an associated division and a unique ID.
     *
     * @param division The {@link IDivision} the lever belongs to.
     * @param id The unique integer ID of the lever.
     */
    public Lever(IDivision division, int id) {
        this.division = division;
        this.id = id;
    }

    /**
     * Retrieves the division associated with this lever.
     *
     * @return The associated {@link IDivision} object.
     */
    @Override
    public IDivision getDivision() {
        return this.division;
    }

    /**
     * Sets the division associated with this lever.
     *
     * @param division The new {@link IDivision} to associate with the lever.
     */
    public void setDivision(IDivision division) {
        this.division = division;
    }

    /**
     * Retrieves the unique identifier of the lever.
     *
     * @return The integer ID of the lever.
     */
    public int getId() {
        return this.id;
    }

    /**
     * Sets the unique identifier of the lever.
     *
     * @param id The new integer ID for the lever.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns a string representation of the lever, typically used for display purposes.
     *
     * @return A string in the format "Alavanca [id]".
     */
    @Override
    public String toString() {
        return "Alavanca " + this.id;
    }
}