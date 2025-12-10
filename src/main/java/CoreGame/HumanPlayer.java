package CoreGame;

import Interfaces.*;
import Util.Utils;

public class HumanPlayer extends Player {

    public HumanPlayer(String name) {
        super(name);
    }

    @Override
    public void move(IMap maze) {
        IDivision currentPos = getDivision();
        IDivision nextPos;

        nextPos = currentPos.getComportment(maze);

        if (nextPos != currentPos) {
            IHallway hallway = maze.getEdge(currentPos, nextPos);
            IEvent event = hallway.getEvent(this);

            divisionsHistory.push(currentPos);
            setDivision(nextPos);

            event.apply(this, isRealPlayer());
            movementsHistory.push(event);

            Utils.waitEnter();
        }
    }
}
