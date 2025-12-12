package CoreGame;

/**
 * Defines the possible types of entries that can be recorded in a player's history or
 * a general game log.
 */
public enum EntryType {

    /**
     * Represents an entry related to a player's successful or attempted movement
     * between divisions in the maze.
     */
    MOVEMENT,

    /**
     * Represents an entry related to a specific event that occurred within a division,
     * such as answering a question, activating a lever, or encountering a specific item.
     */
    EVENT
}