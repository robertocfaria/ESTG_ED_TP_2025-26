package Map;

import Interfaces.IDivision;

public abstract class GoalDivision extends Division{

    public GoalDivision(String name) {
        super(name);
    }

    @Override
    public IDivision getComportament() {
        System.out.println("Parabéns! GANHASTE!");
        return null;
    }
}
