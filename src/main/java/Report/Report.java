package Report;

import Interfaces.IDivision;
import Interfaces.IEvent;

public class Report {
    IDivision position;
    IEvent event;

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
