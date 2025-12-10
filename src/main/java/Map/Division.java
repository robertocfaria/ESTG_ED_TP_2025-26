package Map;

import Interfaces.IDivision;

public abstract class Division implements IDivision {

    private String name;

    //lista de player que ja estiveram aqui!

    public Division (String name) {
        this.name = name;
    }

    @Override
    public String getName() { return this.name; }

    @Override
    public abstract IDivision getComportament();
}
