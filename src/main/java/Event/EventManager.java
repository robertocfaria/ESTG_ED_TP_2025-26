package Event;

import Interfaces.IEvent;
import Interfaces.IPlayer;
import Structures.Interfaces.ListADT;

import java.util.Random;

public class EventManager {
    private Class[] events = new Class[]{ExtraPlays.class, RollBack.class, ShuffleAllPlayers.class, StunnedPlays.class, SwapTwoPlayers.class};
    private Random rand = new Random();

    public IEvent getRandomEvent(ListADT<IPlayer> players) {
        Class randEvent = this.events[this.rand.nextInt(events.length)];

        if (randEvent == ExtraPlays.class) {
            return new ExtraPlays();
        } else if (randEvent == RollBack.class) {
            return new RollBack();
        } else if (randEvent == ShuffleAllPlayers.class) {
            return new ShuffleAllPlayers(players);
        } else if (randEvent == StunnedPlays.class) {
            return new StunnedPlays();
        } else {
            return new SwapTwoPlayers(players);
        }
    }
}
