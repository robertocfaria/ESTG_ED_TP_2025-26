package Event;

import Interfaces.IDivision;
import Interfaces.IPlayer;
import Exceptions.InvalidPlayersCountException;
import Interfaces.IEvent;
import Menus.GameVisuals;

public class RollBack implements IEvent {

    @Override
    public void apply(IPlayer player, boolean isRealPlayer) throws InvalidPlayersCountException {
        IDivision lastPos = player.getLastDivision();
        player.setDivision(lastPos);
        System.out.println("Encontras-te um cão bravo. Vais ter de voltar para a casa onde estavas.");
    }
}