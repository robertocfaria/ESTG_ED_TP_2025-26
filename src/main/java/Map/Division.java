package Map;

import Interfaces.IDivision;
import Interfaces.IMaze;
import Interfaces.IPlayer;

public abstract class Division implements IDivision {
    private String name;

    public Division() {
    }

    public Division (String name) {
        this.name = name;
    }

    @Override
    public String getName() { return this.name; }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public abstract IDivision getComportament(IMaze maze, IPlayer player);
}