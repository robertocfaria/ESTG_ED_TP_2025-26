package Map;

import Structures.Exceptions.ElementNotFoundException;
import Structures.Exceptions.EmptyCollectionException;
import Structures.Interfaces.UnorderedListADT;
import Structures.List.ArrayUnorderedList;

public class Map implements IMap {
    public static final int INCREASE_FACTOR = 2;
    public static final int DEFAULT_CAPACITY = 20;
    private IHallway[][] adjMatrix;
    private IDivision[] vertices;
    private int count;

    public Map() {
        this.vertices = new IDivision[DEFAULT_CAPACITY];
        this.adjMatrix = new IHallway[DEFAULT_CAPACITY][DEFAULT_CAPACITY];
        this.count = 0;
    }

    private void expandCapacity() {
        int newCapacity = this.count * INCREASE_FACTOR;

        IDivision[] expandedVertices = new IDivision[newCapacity];
        IHallway[][] expandedAdjMatrix = new IHallway[newCapacity][newCapacity];

        for (int i = 0; i < this.count; i++) {
            expandedVertices[i] = this.vertices[i];
        }
        this.vertices = expandedVertices;

        for (int i = 0; i < this.count; i++) {
            for (int j = 0; j < this.count; j++) {
                expandedAdjMatrix[i][j] = this.adjMatrix[i][j];
            }
        }
        this.adjMatrix = expandedAdjMatrix;
    }

    private boolean indexIsValid(int i) {
        return 0 <= i && i < this.count;
    }

    private int getIndex(IDivision vertex) {
        for (int i = 0; i < this.count; i++) {
            if (this.vertices[i].equals(vertex)) {
                return i;
            }
        }

        return -1;
    }

    public void addEdge(int i, int j, IHallway edge) {
        if (this.indexIsValid(i) && this.indexIsValid(j)) {
            this.adjMatrix[i][j] = edge;
            this.adjMatrix[j][i] = edge;
        }
    }

    @Override
    public void addDivision(IDivision vertex) {
        if (this.size() == this.vertices.length) {
            this.expandCapacity();
        }

        this.vertices[this.count] = vertex;
        /**for (int i = 0; i < this.count; i++) {
         this.adjMatrix[this.count][i] = null;
         this.adjMatrix[i][this.count] = null;
         }*/

        this.count++;
    }


    @Override
    public void removeDivision(IDivision vertex) throws EmptyCollectionException, ElementNotFoundException {
        if (this.isEmpty()) {
            throw new EmptyCollectionException("Graph hasn't divisions to be remove");
        }

        int index = this.getIndex(vertex);
        if (index == -1) {
            throw new ElementNotFoundException("Division not found");
        }

        for (int i = index; i < this.count - 1; i++) {
            this.vertices[i] = this.vertices[i + 1];
        }
        this.vertices[this.count - 1] = null;
    }

    @Override
    public void addHallway(IDivision vertex1, IDivision vertex2, IHallway weight) {
        this.addEdge(this.getIndex(vertex1), this.getIndex(vertex2), weight);
    }

    @Override
    public UnorderedListADT<IDivision> getAdjacentDivisions(IDivision vertex) throws ElementNotFoundException {
        int i = this.getIndex(vertex);

        if (i == -1) {
            throw new ElementNotFoundException("Division not found");
        }

        UnorderedListADT<IDivision> result = new ArrayUnorderedList<>();

        return result;
    }

    @Override
    public boolean isEmpty() {
        return this.count == 0;
    }

    @Override
    public boolean isConnected() {
        return false;
    }

    @Override
    public int size() {
        return this.count;
    }
}
