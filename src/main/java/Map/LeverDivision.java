package Map;

import Interfaces.IDivision;
import Interfaces.ILever;
import Interfaces.IMap;
import Lever.Lever;
import NewSctructures.ArrayUnorderedListWithGet;
import NewSctructures.UnorderedListWithGetADT;
import Reader.Reader;

public class LeverDivision extends Division {
    private UnorderedListWithGetADT<IDivision> adjacentDivision;
    private UnorderedListWithGetADT<ILever> levers;

    public LeverDivision(String name) {
        super(name);
        this.adjacentDivision = new ArrayUnorderedListWithGet<>();
        this.levers = new ArrayUnorderedListWithGet<>();

    }

    @Override
    public IDivision getComportament() {
        return null;
    }

    @Override
    public IDivision getNewChosenDivision(IMap maze) {
        this.adjacentDivision = maze.getAdjacentVertex(this);

        for (int i = 0; i < this.adjacentDivision.size() * 2; i++) { // metade sao alavancas normais, outras retornam null
            ILever lever = new Lever(this.adjacentDivision.get(i), i);
            this.levers.addToRear(lever);
        }

        int choice = Reader.readInt(1, this.levers.size(), "Escolha a alavanca a puxar:");
        ILever chosenLever = this.levers.get(choice - 1);

        return chosenLever.getDivision();
    }

    @Override
    public String toString() {
        return "Alavanca";
    }
}
