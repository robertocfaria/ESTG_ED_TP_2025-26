package CoreGame;

import Interfaces.IDivision;
import Interfaces.IEvent;

public class HistoryEntry {

    private EntryType type;
    private IDivision division;
    private String description;
    private IEvent event;


    public HistoryEntry(IDivision division, String description) {
        this.type = EntryType.MOVEMENT;
        this.division = division;
        this.description = description;
        this.event = null;
    }

    public HistoryEntry(IEvent event) {
        this.type = EntryType.EVENT;
        this.event = event;
        this.division = null;
        this.description = "Evento Ocorrido: " + event.getClass().getSimpleName();
    }

    @Override
    public String toString() {
        if (type == EntryType.MOVEMENT) {
            return "[SALA: " + division.getName() + "] -> " + description;
        } else {
            return "[EVENTO] -> " + description;
        }
    }







    // Getters se precisares...
    public EntryType getType() { return type; }
    public IDivision getDivision() { return division; }
}