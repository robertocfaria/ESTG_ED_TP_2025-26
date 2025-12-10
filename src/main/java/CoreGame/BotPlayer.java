package CoreGame;

import Interfaces.IDivision;
import Interfaces.IEvent;
import Interfaces.IHallway;
import Interfaces.IMap;
import Util.Utils;

public class BotPlayer extends Player {

    public BotPlayer() {
        super();
    }

    @Override
    public void move(IMap maze) {
        IDivision currentPos = getDivision();
        IDivision nextPos;

        nextPos = currentPos.getComportment(maze);

        if (nextPos != currentPos) {
            IHallway hallway = maze.getEdge(currentPos, nextPos);
            IEvent event = hallway.getEvent(this);

            movementsHistory.push(event);
            setDivision(nextPos);

            event.apply(this, isRealPlayer());
            divisionsHistory.push(currentPos);

            Utils.waitEnter();
        }
    }
}
