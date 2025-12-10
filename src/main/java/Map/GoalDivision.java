package Map;

import Interfaces.IDivision;
import Interfaces.IMap;

public class GoalDivision extends Division{

    public GoalDivision(String name) {
        super(name);
    }

    @Override
    public IDivision getNewChosenDivision(IMap maze) {
        return null;
    }

    @Override
    public IDivision getComportament() {
        System.out.println("Parabéns! GANHASTE!");
        return null;
    }
}
