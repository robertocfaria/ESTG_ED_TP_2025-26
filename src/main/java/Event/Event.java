package Event;

import Interfaces.IPlayer;
import Interfaces.IEvent;

public abstract class Event implements IEvent {
    protected IPlayer player;

    public Event(IPlayer player) {
        this.player = player;
    }

    public IPlayer getPlayer() {
        return this.player;
    }
}
