package CoreGame;

import Interfaces.*;
import Structures.Exceptions.ElementNotFoundException;
import Structures.Interfaces.ListADT;
import Structures.Interfaces.UnorderedListADT;
import Structures.List.ArrayUnorderedList;
import Structures.List.DoubleLinkedUnorderedList;
import Util.Utils;

public class HumanPlayer extends Player {

    public HumanPlayer(String name) {
        super(name);
    }

    @Override
    public void move(IMap maze) {
        IDivision currentPos = getDivision();
        IDivision nextPos;

        nextPos = currentPos.getComportament(maze);

        if (nextPos != currentPos) {
            IHallway hallway = maze.getEdge(currentPos, nextPos);
            IEvent event = hallway.getEvent(this);

            event.apply(this); // o jogador tem de escolher com qual jogador quer trocar
            Utils.waitEnter();

            setDivision(nextPos);
        }

    }
}
