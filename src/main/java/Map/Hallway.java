package Map;

import Event.*;
import Interfaces.IEvent;
import Interfaces.IHallway;
import Interfaces.IPlayer;

public class Hallway implements IHallway {
    private static EventManager EVENTS = new EventManager();

    /**
     * o evento de voltar casa atras só funciona até à posicao em que os jogadores trocaram de posicoes
     * */
    @Override
    public IEvent getEvent(IPlayer player) {
        IEvent event = EVENTS.getRandomEvent();
       /** IEvent playerLastEvent = player.getLastEvent();

        if (event instanceof RollBack) {
            if (playerLastEvent instanceof SwapTwoPlayers || playerLastEvent instanceof ShuffleAllPlayers) {
                do {
                    event = EVENTS.getRandomEvent();
                } while (event instanceof RollBack);
            }
        }
        */

        return event;
    }

    @Override
    public String toString() {
        return "Hallway";
    }
}