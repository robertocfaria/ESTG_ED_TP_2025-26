package Map;

import Interfaces.IDivision;
import Interfaces.IMap;

public class QuestionDivision extends Division{

    private static QuestionManager questions;

    public QuestionDivision(String name) {
        super(name);
    }

    @Override
    public IDivision getComportament() {
        return null;
    }

    @Override
    public IDivision getNewChosenDivision(IMap maze) {
        return null;
    }
}
