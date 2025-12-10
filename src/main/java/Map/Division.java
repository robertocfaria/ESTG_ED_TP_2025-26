package Map;

import Interfaces.IDivision;

public abstract class Division implements IDivision {

    private String name;

    public Division (String name) {
        this.name = name;
    }

    @Override
    public abstract IDivision getComportament();
}
