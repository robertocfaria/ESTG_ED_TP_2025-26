package Map;

import Interfaces.IDivision;
import Interfaces.IMap;
import Interfaces.IPlayer;

public class GoalDivision extends Division{

    public GoalDivision() {
        super();
    }

    public GoalDivision(String name) {
        super("DIVISAO FINAL");
    }

    @Override
    public IDivision getComportament(IMap maze, IPlayer player) {
        System.out.println("Parabéns! GANHASTE!");
        return null;
    }
}