package Map;

import Event.*;
import Interfaces.IEvent;
import Interfaces.IHallway;
import Interfaces.IPlayer;
import Structures.Interfaces.ListADT;

public class Hallway implements IHallway {
    private static EventManager EVENTS = new EventManager();
    private ListADT<IPlayer> players;

    public Hallway() {
    }

    public ListADT<IPlayer> getPlayers() {
        return players;
    }

    @Override
    public void setPlayers(ListADT<IPlayer> players) {
        this.players = players;
    }

    public static EventManager getEVENTS() {
        return EVENTS;
    }

    public static void setEVENTS(EventManager EVENTS) {
        Hallway.EVENTS = EVENTS;
    }

    @Override
    public IEvent getEvent(IPlayer player) {
        IEvent event = EVENTS.getRandomEvent(this.players);
        IEvent playerLastEvent = player.getLastEvent();

        if (playerLastEvent != null) { // significa que é a primeira jogada
            if (event instanceof RollBack) {
                if (playerLastEvent instanceof SwapTwoPlayers || playerLastEvent instanceof ShuffleAllPlayers) {
                    do {
                        event = EVENTS.getRandomEvent(this.players);
                    } while (event instanceof RollBack);
                }
            }
        }


        if (this.players.size() < 2) {
            while (event instanceof SwapTwoPlayers) {
                event = EVENTS.getRandomEvent(this.players);
            }
        }

        return event;
    }

    @Override
    public String toString() {
        return "Hallway";
    }
}