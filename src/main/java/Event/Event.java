package Event;

import CoreGame.IPlayer;
import Interfaces.IEvent;

public abstract class Event implements IEvent {
    protected IPlayer player;

    public Event(IPlayer player) {
        this.player = player;
    }

    @Override
    public IPlayer getPlayer() {
        return this.player;
    }
}
