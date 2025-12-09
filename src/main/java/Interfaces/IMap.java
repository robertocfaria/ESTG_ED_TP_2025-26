package Interfaces;

import NewSctructures.UnorderedListWithGetADT;
import Structures.Interfaces.NetworkADT;

public interface IMap extends NetworkADT<IDivision> {

    void addEdge(IDivision vertex1, IDivision vertex2, IHallway weight);

    UnorderedListWithGetADT<IDivision> getAdjacentVertex(IDivision vertex);

    IHallway getEdge(IDivision vertex1, IDivision vertex2);

    UnorderedListWithGetADT<IDivision> getInitialVertexes();
}
