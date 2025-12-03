package Map;

import Structures.Interfaces.UnorderedListADT;

public interface IMap<T extends IDivision> {

    void addDivision(T vertex);

    void removeDivision(T vertex);

    IHallway getHallway(T vertex1, T vertex2);

    void addHallway(T vertex1, T vertex2, IHallway weight);

    void removeHallway(T vertex1, T vertex2);

    UnorderedListADT<T> getAdjacentDivisions(T vertex);

    boolean isEmpty();

    boolean isConnected();

    int size();

    String toString();
}
