package Lever;

import Interfaces.ILever;
import Interfaces.ILeverDivision;
import Reader.Reader;
import Structures.Exceptions.EmptyCollectionException;
import Structures.List.ArrayUnorderedList;

public class LeverDivision implements ILeverDivision {
    private ArrayUnorderedList<ILever> levers;
    private boolean isCorrectLever;

    public LeverDivision() {
        this.levers = new ArrayUnorderedList<>();
    }

    @Override
    public void applyChallenge() {
        if (this.levers.isEmpty()) {
            throw new EmptyCollectionException("All levers have been activated");
        }

        for (ILever lever : this.levers) {
            System.out.print(lever);
        }

        int choice = Reader.readInt(1, this.levers.size(), "Escolha a alavanca a puxar:");
        ILever chosenLever = this.levers.first(); //trocar por um getIndex

        if (chosenLever != null) {
            this.isCorrectLever = chosenLever.unlocksHallway();
        } else {
            System.out.println("A alavanca escolhida já foi ativada");
        }

        this.levers.remove(chosenLever);
    }

    @Override
    public boolean answeredCorrectly() {
        return this.isCorrectLever;
    }

    @Override
    public void setMiniumNumberOfLevers(int quantity) throws IllegalArgumentException {
        if (quantity < 1) {
            throw new IllegalArgumentException("A lever must be applied to a division with 1 or plus hallways");
        }

        for (int i = 0; i < quantity; i++) {
            this.levers.addToRear(new Lever(i + 1, true));
        }

        for (int i = quantity; i < quantity + 3; i++) {
            this.levers.addToRear(new Lever(i + 1, false));
        }
    }
}
