package CoreGame;

import Interfaces.IDivision;
import Interfaces.IHallway;
import Interfaces.IMap;
import Structures.Exceptions.ElementNotFoundException;
import Structures.Interfaces.UnorderedListADT;

public class HumanPlayer extends Player{

    public HumanPlayer(String name) {
        super(name);
    }

    @Override
    public void move(IMap maze) {
        IDivision currentPos = getDivision();
        IDivision nextPos;

        nextPos = currentPos.getComportament(maze);

        if(nextPos != currentPos) {
            IHallway event = maze.getEdge(currentPos,nextPos);
            event.getEvent(this);
            setDivision(nextPos);
        }

    }
}
