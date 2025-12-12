package CoreGame;

import Interfaces.IDivision;
import Interfaces.IEvent;

public class HistoryEntry {

    // Enum (se não estiver noutro ficheiro, deve estar aqui)
    public enum EntryType {
        MOVEMENT,
        EVENT
    }

    private EntryType type;
    private IDivision division;      // Sala onde ocorreu a ação
    private String description;
    private IEvent event;

    // NOVO CAMPO: Para guardar o nome da sala seguinte
    private String nextDivisionName;

    public HistoryEntry(IDivision division, String description) {
        this.type = EntryType.MOVEMENT;
        this.division = division;
        this.description = description;
        this.event = null;
        this.nextDivisionName = null; // Inicialmente nulo
    }

    public HistoryEntry(IEvent event) {
        this.type = EntryType.EVENT;
        this.event = event;
        this.division = null;
        this.description = "Evento Ocorrido: " + event.getClass().getSimpleName();
        this.nextDivisionName = null;
    }

    /**
     * Define para qual sala o jogador avançou.
     * Deve ser chamado quando o jogador acerta e muda de sala.
     */
    public void setNextDivision(String name) {
        this.nextDivisionName = name;
    }

    @Override
    public String toString() {
        if (type == EntryType.MOVEMENT) {
            String texto = "[SALA: " + division.getName() + "] -> " + description;

            // Se houver informação de destino, adicionamos à frente
            if (nextDivisionName != null) {
                texto += " -> [AVANCOU PARA: " + nextDivisionName + "]";
            }
            return texto;
        } else {
            return "[EVENTO] -> " + description;
        }
    }

    public String getDescription() {
        if (this.description == null) {
            return "";
        }

        String cleanDesc = this.description.replace("\"", "'").replace("\n", " ");

        // Também adicionamos ao JSON para ficar registado no ficheiro
        if (this.nextDivisionName != null) {
            cleanDesc += " | Avançou para: " + this.nextDivisionName;
        }

        return cleanDesc;
    }

    public String getTypeString() {
        if (this.type != null) {
            return this.type.toString();
        }
        return "UNKNOWN";
    }
}