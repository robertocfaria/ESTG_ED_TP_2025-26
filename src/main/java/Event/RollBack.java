package Event;

import Interfaces.IDivision;
import Interfaces.IPlayer;
import Exceptions.InvalidPlayersCountException;
import Interfaces.IEvent;
import Report.Report;
import Structures.Interfaces.ListADT;

public class RollBack implements IEvent {

    @Override
    public void apply(ListADT<IPlayer> players) throws InvalidPlayersCountException {
        if (players.size() != 1) {
            throw new InvalidPlayersCountException("RollBack event applies only to 1 player at a time");
        }

        IPlayer player = players.first();

        //IDivision lastPos = player.getLastDivision();

        //player.setDivision(lastPos);

        System.out.println(player.getName() + " is now at the previous position");
    }
}
