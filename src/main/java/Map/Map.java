package Map;

import Exceptions.NotSupportedOperation;
import Interfaces.IHallway;
import Interfaces.IMap;
import Structures.Exceptions.ElementNotFoundException;
import Structures.Exceptions.EmptyCollectionException;
import Structures.Interfaces.UnorderedListADT;
import Structures.List.ArrayUnorderedList;
import Structures.Queue.LinkedQueue;
import Structures.Stack.LinkedStack;

import java.util.Iterator;

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

    public Map(int capacity) {
        //TODO Logica da crialação da matriz

        this.vertices = new IDivision[capacity];
        this.adjMatrix = new IHallway[capacity][capacity];
        this.count = 0;
    }

    /*TODO Miguel
    private void expandCapacity() {
        int newCapacity = this.count * INCREASE_FACTOR;

        expandedVertices =  new Object[newCapacity];
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

    TODO metodo para scar a division
    TODO metodo para saber quais sao as inicia - iniciar a matriz e verificar as conexoes para ver qual sao as extremidades


     */

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

    private void addEdge(int i, int j, IHallway edge) {
        if (this.isValidIndex(i) && this.isValidIndex(j)) {
            this.adjMatrix[i][j] = edge;
            this.adjMatrix[j][i] = edge;
        }
    }

    @Override
    public void addVertex(T vertex) {
        if (this.size() == this.vertices.length) {
            this.expandCapacity();
        }

        this.vertices[this.count] = vertex;

        this.count++;
    }

    @Override
    public void removeVertex(T vertex) throws EmptyCollectionException, ElementNotFoundException {
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
    public void addEdge(T t, T t1) throws NotSupportedOperation {
        throw new NotSupportedOperation("This operation can't be done in this graph");
    }

    @Override
    public void addEdge(T t, T t1, double v) throws NotSupportedOperation {
        throw new NotSupportedOperation("This operation can't be done in this graph");
    }

    @Override
    public void removeEdge(T vertex1, T vertex2) {
        this.removeEdge(this.getIndex(vertex1), this.getIndex(vertex2));
    }

    @Override
    public double shortestPathWeight(T startVertex, T targetVertex) {
       /** int start = getIndex(startVertex);
        int target = getIndex(targetVertex);

        if (start == -1 || target == -1) {
            return -1;
        }

        int n = this.count;
        double[] dist = new double[n];
        boolean[] visited = new boolean[n];

        for (int i = 0; i < n; i++) {
            dist[i] = Double.POSITIVE_INFINITY;
        }
        dist[start] = 0.0;

        for (int count = 0; count < n - 1; count++) {
            int u = -1;
            double minDist = Double.POSITIVE_INFINITY;

            for (int v = 0; v < n; v++) {
                if (!visited[v] && dist[v] < minDist) {
                    minDist = dist[v];
                    u = v;
                }
            }

            if (u == -1) break;
            if (u == target) break;

            visited[u] = true;

            for (int v = 0; v < n; v++) {
                double weight = adjMatrix[u][v];

                if (weight != -1 && !visited[v]) {
                    double newDist = dist[u] + weight;

                    if (newDist < dist[v]) {
                        dist[v] = newDist;
                    }
                }
            }
        }

        return dist[target] == Double.POSITIVE_INFINITY ? -1 : dist[target];*/
       return 0;
    }

    @Override
    public Iterator iteratorBFS(T startVertex) {
        int startIndex = this.getIndex(startVertex);

        int x;
        LinkedQueue<Integer> traversalQueue = new LinkedQueue<>();
        ArrayUnorderedList<T> resultList = new ArrayUnorderedList<>();

        if (!this.isValidIndex(startIndex)) {
            return resultList.iterator();
        }

        boolean[] visited = new boolean[this.count];

        traversalQueue.enqueue(startIndex);
        visited[startIndex] = true;

        while (!traversalQueue.isEmpty()) {
            x = traversalQueue.dequeue();
            resultList.addToRear(this.vertices[x]);

            for (int i = 0; i < this.count; i++) {
                if (this.adjMatrix[x][i] != null && !visited[i]) {
                    traversalQueue.enqueue(i);
                    visited[i] = true;
                }
            }
        }

        return resultList.iterator();
    }

    @Override
    public Iterator iteratorDFS(T startVertex) {
        int startIndex = this.getIndex(startVertex);

        int x;
        boolean found;

        LinkedStack<Integer> traversalStack = new LinkedStack<>();
        ArrayUnorderedList<T> resultList = new ArrayUnorderedList<>();
        boolean[] visited = new boolean[this.count];

        if (!this.isValidIndex(startIndex)) {
            return resultList.iterator();
        }

        traversalStack.push(startIndex);
        resultList.addToRear(vertices[startIndex]);
        visited[startIndex] = true;

        while (!traversalStack.isEmpty()) {
            x = traversalStack.peek();
            found = false;

            for (int i = 0; i < this.count && !found; i++) {
                if (this.adjMatrix[x][i] != null && !visited[i]) {
                    traversalStack.push(i);
                    resultList.addToRear(this.vertices[i]);
                    visited[i] = true;
                    found = true;
                }
            }

            if (!found && !traversalStack.isEmpty()) {
                traversalStack.pop();
            }
        }

        return resultList.iterator();
    }

    @Override
    public Iterator iteratorShortestPath(T startVertex, T targetVertex) {
        int startIndex = getIndex(startVertex);
        int targetIndex = getIndex(targetVertex);

        ArrayUnorderedList<T> resultList = new ArrayUnorderedList<>();

        if (!isValidIndex(startIndex) || !isValidIndex(targetIndex)) {
            return resultList.iterator();
        }

        boolean[] visited = new boolean[this.count];
        int[] predecessor = new int[this.count];
        for (int i = 0; i < this.count; i++) {
            predecessor[i] = -1;
        }

        LinkedQueue<Integer> queue = new LinkedQueue<>();
        queue.enqueue(startIndex);
        visited[startIndex] = true;

        boolean found = false;

        while (!queue.isEmpty()) {
            int current = queue.dequeue();

            if (current == targetIndex) {
                found = true;
                break;
            }

            for (int i = 0; i < this.count; i++) {
                if (adjMatrix[current][i] != null && !visited[i]) {
                    visited[i] = true;
                    predecessor[i] = current;
                    queue.enqueue(i);
                }
            }
        }

        if (!found) {
            return resultList.iterator();
        }

        LinkedStack<Integer> stack = new LinkedStack<>();
        int step = targetIndex;

        while (step != -1) {
            stack.push(step);
            step = predecessor[step];
        }

        while (!stack.isEmpty()) {
            resultList.addToRear(vertices[stack.pop()]);
        }

        return resultList.iterator();
    }

    @Override
    public void addEdge(T vertex1, T vertex2, IHallway weight) {
        this.addEdge(this.getIndex(vertex1), this.getIndex(vertex2), weight);
    }

    @Override
    public IHallway getEdge(T vertex1, T vertex2) throws ElementNotFoundException {
        int i = this.getIndex(vertex1);
        int j = this.getIndex(vertex2);

        if (i == -1 || j == -1) {
            throw new ElementNotFoundException("Division not found");
        }

        return this.adjMatrix[i][j];
    }

    @Override
    public UnorderedListADT<T> getAdjacentVertex(T vertex) throws ElementNotFoundException {
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