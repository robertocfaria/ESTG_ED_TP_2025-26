package Lever;

import Interfaces.IDivision;
import Interfaces.ILever;

public class Lever implements ILever {
    private IDivision division;
    private int id;

    public Lever(IDivision division, int id) {
        this.division = division;
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    @Override
    public IDivision getDivision() {
        return this.division;
    }

    @Override
    public String toString() {
        return "Alavanca " + this.id;
    }
}
