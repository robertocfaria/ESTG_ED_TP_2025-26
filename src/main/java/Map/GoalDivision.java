package Map;

import Interfaces.IDivision;
import Interfaces.IMaze;
import Interfaces.IPlayer;

public class GoalDivision extends Division{

    public GoalDivision() {
        super();
    }

    public GoalDivision(String name) {
        super("DIVISAO FINAL");
    }

    @Override
    public IDivision getComportament(IMaze maze, IPlayer player) {
        System.out.println("Parabéns! GANHASTE!");
        return null;
    }
}