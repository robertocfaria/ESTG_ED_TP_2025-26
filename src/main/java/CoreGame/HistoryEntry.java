package CoreGame;

import Interfaces.IDivision;
import Interfaces.IEvent;

/**
 * Represents a single entry in a player's game history, encapsulating details
 * about a movement action or an event that occurred during a turn.
 */
public class HistoryEntry {

    private EntryType type;
    private IDivision division;
    private String description;
    private IEvent event;

    private String nextDivisionName;

    /**
     * Constructs a history entry of type {@code MOVEMENT}.
     *
     * @param division The division the player was in when the movement occurred.
     * @param description The result or description of the movement attempt.
     */
    public HistoryEntry(IDivision division, String description) {
        this.type = EntryType.MOVEMENT;
        this.division = division;
        this.description = description;
        this.event = null;
        this.nextDivisionName = null;
    }

    /**
     * Constructs a history entry of type {@code EVENT}.
     * The description is automatically generated based on the event's class name.
     *
     * @param event The {@link IEvent} object that occurred.
     */
    public HistoryEntry(IEvent event) {
        this.type = EntryType.EVENT;
        this.event = event;
        this.division = null;
        this.description = "Evento Ocorrido: " + event.getClass().getSimpleName();
        this.nextDivisionName = null;
    }

    /**
     * Sets the name of the division the player successfully advanced to following
     * this entry's action (typically a movement).
     *
     * @param name The name of the next division.
     */
    public void setNextDivision(String name) {
        this.nextDivisionName = name;
    }

    /**
     * Returns a cleaned-up description of the entry, suitable for structured data export
     * (e.g., JSON). It removes quotes, replaces newlines with spaces, and appends
     * the name of the next division if available.
     *
     * @return A sanitized and complete description string.
     */
    public String getDescription() {
        if (this.description == null) {
            return "";
        }

        String cleanDesc = this.description.replace("\"", "'").replace("\n", " ");

        if (this.nextDivisionName != null) {
            cleanDesc += " | Avançou para: " + this.nextDivisionName;
        }

        return cleanDesc;
    }

    /**
     * Returns the string representation of the entry's type.
     *
     * @return A string: "MOVEMENT", "EVENT", or "UNKNOWN" if the type is null.
     */
    public String getTypeString() {
        if (this.type != null) {
            return this.type.toString();
        }
        return "UNKNOWN";
    }

    /**
     * Provides a detailed, human-readable string representation of the history entry.
     * The format varies based on the entry type.
     *
     * @return A formatted string detailing the historical entry.
     */
    @Override
    public String toString() {
        if (type == EntryType.MOVEMENT) {
            String texto = "[SALA: " + division.getName() + "] -> " + description;

            if (nextDivisionName != null) {
                texto += " -> [AVANCOU PARA: " + nextDivisionName + "]";
            }
            return texto;
        } else {
            return "[EVENTO] -> " + description;
        }
    }
}