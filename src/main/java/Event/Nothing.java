package Event;

import Interfaces.IPlayer;
import Exceptions.InvalidPlayersCountException;
import Interfaces.IEvent;
import Menus.GameVisuals;

public class Nothing implements IEvent {

    @Override
    public void apply(IPlayer player, boolean isRealPlayer) throws InvalidPlayersCountException {
       System.out.println("Uma pessoa muito cordial desejou-te BOA VIAGEM!");
    }
}
