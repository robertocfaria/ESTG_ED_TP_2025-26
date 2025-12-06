package Interfaces;

import Structures.Interfaces.NetworkADT;
import Structures.Interfaces.UnorderedListADT;

public interface IMap<T extends IDivision> extends NetworkADT<T> {

    void addEdge(T vertex1, T vertex2, IHallway weight);

    UnorderedListADT<T> getAdjacentVertex(T vertex);

    IHallway getEdge(T vertex1, T vertex2);
}
