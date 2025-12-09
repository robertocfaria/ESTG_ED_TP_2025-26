package Lever;

import Interfaces.ILever;

public class Lever implements ILever {
    private int id;
    private boolean unlocksHallway;

    public Lever(int id, boolean unlocksHallway) {
        this.id = id;
        this.unlocksHallway = unlocksHallway;
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public boolean unlocksHallway() {
        return this.unlocksHallway;
    }

    @Override
    public String toString() {
        return "Alavanca " + this.id;
    }
}
