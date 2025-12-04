package Map;

import Structures.Interfaces.NetworkADT;
import Structures.Interfaces.UnorderedListADT;

public interface IMap<T extends IDivision> extends NetworkADT<T> {

    void addEdge(T vertex1, T vertex2, IHallway weight);

    UnorderedListADT<T> getAdjacentVertex(T vertex);

    IHallway getEdge(T vertex1, T vertex2);
    /**void addDivision(T vertex);

    void removeDivision(T vertex);

    IHallway getHallway(T vertex1, T vertex2);

    void addHallway(T vertex1, T vertex2, IHallway weight);

    void removeHallway(T vertex1, T vertex2);

    UnorderedListADT<T> getAdjacentDivisions(T vertex);

    boolean isEmpty();

    boolean isConnected();

    int size();

    String toString();*/
}
