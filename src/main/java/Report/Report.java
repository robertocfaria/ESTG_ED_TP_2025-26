package Report;

import Interfaces.IEvent;
import Interfaces.IDivision;

public class Report {
    IDivision position; // sala para a qual o jogador se moveu
    IEvent event;       // evento que aconteceu ao se mover para a sala

    public Report(IDivision position, IEvent event) {
        this.position = position;
        this.event = event;
    }

    public IDivision getPosition() {
        return position;
    }

    public IEvent getEvent() {
        return event;
    }
}
