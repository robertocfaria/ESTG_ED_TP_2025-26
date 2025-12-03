package Map;

import Structures.Exceptions.ElementNotFoundException;
import Structures.Exceptions.EmptyCollectionException;
import Structures.Interfaces.UnorderedListADT;
import Structures.List.ArrayUnorderedList;
import Structures.Queue.LinkedQueue;

public class Map<T extends IDivision> implements IMap<T> {
    public static final int INCREASE_FACTOR = 2;
    public static final int DEFAULT_CAPACITY = 20;
    private IHallway[][] adjMatrix;
    private T[] vertices;
    private int count;

    public Map() {
        this.vertices = (T[]) new IDivision[DEFAULT_CAPACITY];
        this.adjMatrix = new IHallway[DEFAULT_CAPACITY][DEFAULT_CAPACITY];
        this.count = 0;
    }

    private void expandCapacity() {
        int newCapacity = this.count * INCREASE_FACTOR;

        T[] expandedVertices = (T[]) new Object[newCapacity];
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

    private boolean isValidIndex(int i) {
        return 0 <= i && i < this.count;
    }

    private int getIndex(T vertex) {
        for (int i = 0; i < this.count; i++) {
            if (this.vertices[i].equals(vertex)) {
                return i;
            }
        }

        return -1;
    }

    private void removeEdge(int i, int j) {
        if (this.isValidIndex(i) && this.isValidIndex(j)) {
            this.adjMatrix[i][j] = null;
            this.adjMatrix[j][i] = null;
        }
    }

    public void addEdge(int i, int j, IHallway edge) {
        if (this.isValidIndex(i) && this.isValidIndex(j)) {
            this.adjMatrix[i][j] = edge;
            this.adjMatrix[j][i] = edge;
        }
    }

    public void addDivision(T vertex) {
        if (this.size() == this.vertices.length) {
            this.expandCapacity();
        }

        this.vertices[this.count] = vertex;

        this.count++;
    }

    @Override
    public void removeDivision(T vertex) throws EmptyCollectionException, ElementNotFoundException {
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
    public void addHallway(T vertex1, T vertex2, IHallway weight) {
        this.addEdge(this.getIndex(vertex1), this.getIndex(vertex2), weight);
    }

    @Override
    public void removeHallway(T vertex1, T vertex2) {
        this.removeEdge(this.getIndex(vertex1), this.getIndex(vertex2));
    }

    @Override
    public IHallway getHallway(T vertex1, T vertex2) throws ElementNotFoundException {
        int i = this.getIndex(vertex1);
        int j = this.getIndex(vertex2);

        if (i == -1 || j == -1) {
            throw new ElementNotFoundException("Division not found");
        }

        return this.adjMatrix[i][j];
    }

    @Override
    public UnorderedListADT<T> getAdjacentDivisions(T vertex) throws ElementNotFoundException {
        int index = this.getIndex(vertex);

        if (index == -1) {
            throw new ElementNotFoundException("Division not found");
        }

        UnorderedListADT<T> result = new ArrayUnorderedList<>();

        for (int i = 0; i < this.count; i++) {
            if (i != index && (this.adjMatrix[i][index] != null || this.adjMatrix[index][i] != null)) {
                result.addToRear(this.vertices[i]);
            }
        }

        return result;
    }

    @Override
    public boolean isEmpty() {
        return this.count == 0;
    }

    @Override
    public boolean isConnected() {
        if (this.count <= 1) {
            return true;
        }

        boolean[] visited = new boolean[this.count];
        LinkedQueue<Integer> queue = new LinkedQueue<>();

        queue.enqueue(0);
        visited[0] = true;

        int countVisited = 1;

        while (!queue.isEmpty()) {
            int current = queue.dequeue();

            for (int i = 0; i < this.count; i++) {
                if ((this.adjMatrix[current][i] != null || this.adjMatrix[i][current] != null) && !visited[i]) {
                    visited[i] = true;
                    queue.enqueue(i);
                    countVisited++;
                }
            }
        }

        return countVisited == this.count;
    }

    @Override
    public int size() {
        return this.count;
    }

    @Override
    public String toString() {
        StringBuilder stgB = new StringBuilder();

        for (int i = 0; i < this.count; i++) {
            stgB.append(vertices[i]).append(" -> ");

            for (int j = 0; j < this.count; j++) {
                if (i != j && (adjMatrix[i][j] != null || adjMatrix[j][i] != null)) {
                    stgB.append(vertices[j]).append(", ");
                }
            }
            stgB.append("\n");
        }

        return stgB.toString();
    }
}