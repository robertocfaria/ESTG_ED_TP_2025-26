package Event;

import CoreGame.IPlayer;
import Interfaces.IEvent;
import Structures.Interfaces.UnorderedListADT;
import Structures.List.ArrayUnorderedList;
import Structures.List.DoubleLinkedUnorderedList;

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
