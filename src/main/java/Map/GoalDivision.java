package Map;

import Interfaces.IDivision;
import Interfaces.IMap;
import Interfaces.IPlayer;

import com.fasterxml.jackson.annotation.*;

public class GoalDivision extends Division{

    public GoalDivision() {
        super();
    }

    public GoalDivision(String name) {
        super(name);
    }

    @Override
    public IDivision getComportament(IMap maze, IPlayer player) {
        System.out.println("Parabéns! GANHASTE!");
        return null;
    }
}
