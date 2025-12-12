package Interfaces;

import CoreGame.HistoryEntry;
import Structures.Stack.ArrayStack;

/**
 * Defines the contract for any entity that participates in the maze game,
 * representing either a human player or an AI bot.
 * <p>
 * This interface encapsulates all necessary player state and actions, including
 * movement logic, status effects (stunned, extra rounds), position management,
 * and game history tracking.
 */
public interface IPlayer {

    /**
     * Executes the player's turn, including attempting to move, handling
     * failures, checking for events in hallways, and updating status effects.
     * The core logic for advancement is implemented here.
     *
     * @param maze The overall {@link IMaze} structure required to validate movement
     * and retrieve hallway properties.
     */
    void move(IMaze maze);

    /**
     * Retrieves the name of the player.
     *
     * @return The player's name as a {@code String}.
     */
    String getName();

    /**
     * Retrieves the number of rounds the player is currently stunned and will skip.
     *
     * @return The current stunned round count.
     */
    int getStunned();

    /**
     * Sets or updates the number of rounds the player is stunned for.
     * This is used both to apply a stun effect and to decrement the counter each turn.
     *
     * @param numberOfRounds The new or updated number of stunned rounds.
     */
    void addStunnedRound(int numberOfRounds);

    /**
     * Retrieves the number of extra moves or rounds the player has accumulated.
     *
     * @return The current extra round count.
     */
    int getExtraRounds();

    /**
     * Sets the number of extra moves or rounds granted to the player.
     *
     * @param numberOfRounds The number of extra rounds to set.
     */
    void setExtraRound(int numberOfRounds);

    /**
     * Checks if the player is a real player (human-controlled).
     *
     * @return {@code true} if the player is human; {@code false} if they are a bot.
     */
    boolean isRealPlayer();

    /**
     * Sets the player's current division in the maze.
     *
     * @param division The new {@link IDivision} where the player is located.
     */
    void setDivision(IDivision division);

    /**
     * Returns the player's current division in the maze.
     *
     * @return The current {@link IDivision} object.
     */
    IDivision getDivision();

    /**
     * Returns the last {@link IEvent} encountered by the player without removing it
     * from the history stack.
     *
     * @return The most recent {@link IEvent}, or {@code null} if the history is empty.
     */
    IEvent getLastEvent();

    /**
     * Returns the last {@link IDivision} the player occupied before their current position,
     * without removing it from the history stack.
     *
     * @return The previous {@link IDivision}, or {@code null} if the history is empty.
     */
    IDivision getLastDivision();

    /**
     * Adds a new history entry detailing a movement action or attempt.
     *
     * @param division The {@link IDivision} associated with the move.
     * @param log A string description of the movement action (e.g., success, failure, reason).
     */
    void addHistoryMove(IDivision division, String log);

    /**
     * Adds a new history entry detailing an event that occurred.
     *
     * @param event The {@link IEvent} object that occurred.
     */
    void addHistoryEvent(IEvent event);

    /**
     * Prints the player's full movement and event history to the console.
     * The history is typically printed in chronological order.
     */
    void printFullHistory();

    /**
     * Creates and returns a copy of the player's full history stack.
     * This is typically used for exporting or external analysis, ensuring the original
     * stack structure remains intact.
     *
     * @return A copy of the history as an {@link ArrayStack} of {@link HistoryEntry}.
     */
    ArrayStack<HistoryEntry> getHistoryCopy();
}