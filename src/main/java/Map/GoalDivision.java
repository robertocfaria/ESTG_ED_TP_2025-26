package Map;

import Interfaces.IDivision;
import Interfaces.IMap;

public class GoalDivision extends Division{

    public GoalDivision(String name) {
        super(name);
    }

    @Override
    public IDivision getComportment(IMap maze) {
        System.out.println("Parabéns! GANHASTE!");
        return null;
    }
}
