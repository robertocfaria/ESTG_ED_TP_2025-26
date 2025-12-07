package Event;

import CoreGame.IPlayer;
import Exceptions.InvalidPlayersCountException;
import Interfaces.IDivision;
import Interfaces.IEvent;
import Structures.Interfaces.ListADT;

public class SwapTwoPlayers implements IEvent {

    @Override
    public void apply(ListADT<IPlayer> players) throws InvalidPlayersCountException {
        if (players.size() != 2) {
            throw new InvalidPlayersCountException("SwapTwoPlayers event requires exactly 2 players at a time");
        }

        IPlayer player1 = players.removeFirst();
        IPlayer player2 = players.removeFirst();

        IDivision temp = player1.getDivision();
        player1.setDivision(player2.getDivision());
        player2.setDivision(temp);

        System.out.println(player1.getName() + " and " + player2.getName() + " swapped positions");
    }
}
