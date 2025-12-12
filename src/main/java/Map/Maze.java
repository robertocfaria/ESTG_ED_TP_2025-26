package Map;

import Exceptions.NotSupportedOperation;
import Importers.DivisionNames;
import Interfaces.IDivision;
import Interfaces.IHallway;
import Interfaces.IMaze;
import Interfaces.IPlayer;
import Structures.Exceptions.ElementNotFoundException;
import Structures.Exceptions.EmptyCollectionException;
import Structures.Interfaces.ListADT;
import Structures.Interfaces.UnorderedListADT;
import Structures.List.ArrayUnorderedList;
import Structures.List.DoubleLinkedUnorderedList;
import Structures.Queue.LinkedQueue;
import Structures.Stack.LinkedStack;

import java.util.Iterator;
import java.util.Random;

import Util.Utils;
import com.fasterxml.jackson.annotation.*;

/**
 * Represents the game map structure, implemented as an undirected graph where
 * divisions are vertices and hallways are edges.
 * <p>
 * This class handles the creation, structure, and traversal of the maze,
 * including generating divisions, connections, and defining the goal division.
 * It implements the {@link IMaze} interface and uses an adjacency matrix
 * to store connections (hallways) between divisions.
 */
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
public class Maze implements IMaze {
    /**
     * The factor by which the capacity of the maze's internal arrays (vertices
     * and adjacency matrix) is increased when needed.
     */
    public static final int INCREASE_FACTOR = 2;

    private Random rand = new Random();
    private String name;
    private IHallway hallway;

    private IHallway[][] adjMatrix;
    private IDivision[] vertices;
    private int count;

    /**
     * Default constructor for Maze, primarily used for serialization/deserialization.
     * Initializes the hallway object.
     */
    public Maze() {
        this.hallway = new Hallway();
    }

    /**
     * Constructs a Maze with a specified initial capacity (number of divisions).
     * This constructor automatically generates the divisions, establishes connections
     * between them, assigns a name, and defines a goal division.
     *
     * @param capacity The desired number of divisions (vertices) to create in the maze.
     */
    public Maze(int capacity) {
        this.vertices = new IDivision[capacity];
        this.adjMatrix = new IHallway[capacity][capacity];
        this.hallway = new Hallway();
        this.count = 0;

        generateDivisions(capacity);
        generateConnections(this.hallway);
        this.name = this.generateName();

        defineGoalDivision();
    }

    private int countConnections() {
        if (this.adjMatrix == null) {
            return 0;
        }

        int connections = 0;

        for (int i = 0; i < this.count; i++) {
            for (int j = 0; j < this.count; j++) {
                if (this.adjMatrix[i][j] != null) {
                    connections++;
                }
            }
        }

        return connections;
    }

    private String generateName() {
        return String.format("Mapa [Divisoes: %d | Conexoes: %d]", this.count, this.countConnections());
    }

    /**
     * Returns the name of the maze.
     *
     * @return A string representing the maze's name and summary statistics.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets a custom name for the maze.
     *
     * @param name The new name for the maze.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the list of players who will be using the shared hallway object
     * for movement and interactions.
     *
     * @param players A {@link ListADT} of {@link IPlayer} objects.
     */
    public void setHallwayPlayers(ListADT<IPlayer> players) {
        this.hallway.setPlayers(players);
    }

    private void generateDivisions(int numberOfDivisions) {
        LinkedQueue<String> divisionNames = getDivisionNames();

        for (int i = 0; i < numberOfDivisions; i++) {
            IDivision newDivision;
            if (this.rand.nextBoolean()) {
                newDivision = new QuestionDivision(divisionNames.dequeue());
            } else {
                newDivision = new LeverDivision(divisionNames.dequeue());
            }
            addVertex(newDivision);
        }
    }

    private LinkedQueue<String> getDivisionNames() {
        ArrayUnorderedList<String> divisionNames = DivisionNames.importNames("src/main/resources/DivisionNames.json");
        String[] shuffledNames = new String[divisionNames.size()];
        int count = 0;

        for (String name : divisionNames) {
            shuffledNames[count++] = name;
        }

        Random rand = new Random();
        for (int i = shuffledNames.length - 1; i > 0; i--) {
            int index = rand.nextInt(i + 1);

            String temp = shuffledNames[index];
            shuffledNames[index] = shuffledNames[i];
            shuffledNames[i] = temp;
        }

        LinkedQueue<String> nameQueue = new LinkedQueue<>();
        for (String name : shuffledNames) {
            nameQueue.enqueue(name);
        }

        return nameQueue;
    }

    private void generateConnections(IHallway hallway) {
        double density = 0.2;
        int jumpLimit = 3;

        for (int i = 0; i < this.count - 1; i++) {
            this.addEdge(i, i + 1, hallway);
        }

        for (int i = 0; i < this.count; i++) {
            int limit = Math.min(this.count, i + jumpLimit + 1);

            for (int j = i + 2; j < limit; j++) {
                if (Math.random() < density) {
                    this.addEdge(i, j, hallway);
                }
            }
        }
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

    private void defineGoalDivision() {
        int[] distances = calculateDistancesFromStart();

        int furthestIndex = -1;
        int maxDistance = -1;

        for (int i = 1; i < this.count; i++) {
            if (distances[i] > maxDistance) {
                maxDistance = distances[i];
                furthestIndex = i;
            }
        }
        if (furthestIndex == -1) {
            furthestIndex = this.count - 1;
        }

        String goalName = "Sala do Tesouro";

        IDivision goalDivision = new GoalDivision(goalName);
        this.vertices[furthestIndex] = goalDivision;
    }

    private int[] calculateDistancesFromStart() {
        int[] distances = new int[this.count];
        boolean[] visited = new boolean[this.count];

        for (int i = 0; i < this.count; i++) {
            distances[i] = -1;
            visited[i] = false;
        }

        LinkedQueue<Integer> queue = new LinkedQueue<>();
        queue.enqueue(0);
        visited[0] = true;
        distances[0] = 0;

        while (!queue.isEmpty()) {
            int current = queue.dequeue();

            for (int neighbor = 0; neighbor < this.count; neighbor++) {
                if (adjMatrix[current][neighbor] != null && !visited[neighbor]) {
                    visited[neighbor] = true;
                    distances[neighbor] = distances[current] + 1;
                    queue.enqueue(neighbor);
                }
            }
        }

        return distances;
    }

    private boolean isValidIndex(int i) {
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

    /**
     * Finds and returns a random division that is suitable as a starting point.
     * This method selects a division that has two or fewer connections, typically
     * prioritizing divisions at the periphery of the maze.
     *
     * @return A random {@link IDivision} selected as an initial starting position.
     */
    @Override
    public IDivision getRandomInitialDivision() {
        UnorderedListADT<IDivision> result = new DoubleLinkedUnorderedList<>();

        int connections = 0;
        for (int i = 0; i < this.count; i++) {
            for (int j = 0; j < this.count; j++) {
                if (this.adjMatrix[i][j] != null) {
                    connections++;
                }
            }

            if (connections <= 2) {
                result.addToRear(this.vertices[i]);
            }
        }

        int randIndex = this.rand.nextInt(result.size());
        return Utils.getListADTObject(randIndex, result);
    }

    /**
     * Adds a new division (vertex) to the maze.
     * If the internal arrays are full, their capacity is automatically expanded.
     *
     * @param vertex The {@link IDivision} to be added.
     */
    @Override
    public void addVertex(IDivision vertex) {
        if (this.size() == this.vertices.length) {
            this.expandCapacity();
        }

        this.vertices[this.count] = vertex;

        this.count++;
    }

    /**
     * Removes a division (vertex) from the maze.
     * This operation also removes all connections involving the division.
     *
     * @param vertex The {@link IDivision} to be removed.
     * @throws EmptyCollectionException If the maze is empty.
     * @throws ElementNotFoundException If the specified division is not found in the maze.
     */
    @Override
    public void removeVertex(IDivision vertex) throws EmptyCollectionException, ElementNotFoundException {
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

    /**
     * Adds a connection (edge) between two divisions (vertices).
     * This operation is not supported in this implementation as connections are handled
     * by {@link #addEdge(IDivision, IDivision, IHallway)}.
     *
     * @param t The first division.
     * @param t1 The second division.
     * @throws NotSupportedOperation Always throws this exception.
     */
    @Override
    public void addEdge(IDivision t, IDivision t1) throws NotSupportedOperation {
        throw new NotSupportedOperation("This operation can't be done in this graph");
    }

    /**
     * Adds a weighted connection (edge) between two divisions (vertices).
     * This operation is not supported in this implementation as connections are handled
     * by {@link #addEdge(IDivision, IDivision, IHallway)}.
     *
     * @param t The first division.
     * @param t1 The second division.
     * @param v The weight of the connection.
     * @throws NotSupportedOperation Always throws this exception.
     */
    @Override
    public void addEdge(IDivision t, IDivision t1, double v) throws NotSupportedOperation {
        throw new NotSupportedOperation("This operation can't be done in this graph");
    }

    /**
     * Removes the connection (hallway) between two specified divisions.
     *
     * @param vertex1 The first division.
     * @param vertex2 The second division.
     */
    @Override
    public void removeEdge(IDivision vertex1, IDivision vertex2) {
        this.removeEdge(this.getIndex(vertex1), this.getIndex(vertex2));
    }

    /**
     * Calculates the weight of the shortest path between two divisions.
     * This method is currently not implemented.
     *
     * @param startVertex The starting division.
     * @param targetVertex The target division.
     * @return The weight of the shortest path.
     * @throws NotSupportedOperation Always throws this exception.
     */
    @Override
    public double shortestPathWeight(IDivision startVertex, IDivision targetVertex) {
        throw new NotSupportedOperation("Sorry, we didn't have time to make this method");
    }

    /**
     * Returns an iterator that performs a Breadth-First Search (BFS) traversal
     * starting from the specified division.
     *
     * @param startVertex The division where the traversal should begin.
     * @return An {@link Iterator} over the divisions in the order they are visited by BFS.
     */
    @Override
    public Iterator iteratorBFS(IDivision startVertex) {
        int startIndex = this.getIndex(startVertex);

        int x;
        LinkedQueue<Integer> traversalQueue = new LinkedQueue<>();
        ArrayUnorderedList<IDivision> resultList = new ArrayUnorderedList<>();

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

    /**
     * Returns an iterator that performs a Depth-First Search (DFS) traversal
     * starting from the specified division.
     *
     * @param startVertex The division where the traversal should begin.
     * @return An {@link Iterator} over the divisions in the order they are visited by DFS.
     */
    @Override
    public Iterator iteratorDFS(IDivision startVertex) {
        int startIndex = this.getIndex(startVertex);

        int x;
        boolean found;

        LinkedStack<Integer> traversalStack = new LinkedStack<>();
        ArrayUnorderedList<IDivision> resultList = new ArrayUnorderedList<>();
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

    /**
     * Returns an iterator that generates the sequence of divisions representing the
     * shortest path between the start and target divisions. This uses a Breadth-First Search
     * approach to find the path with the minimum number of divisions.
     *
     * @param startVertex The starting division of the path.
     * @param targetVertex The target division of the path.
     * @return An {@link Iterator} over the divisions in the shortest path sequence.
     */
    @Override
    public Iterator iteratorShortestPath(IDivision startVertex, IDivision targetVertex) {
        int startIndex = getIndex(startVertex);
        int targetIndex = getIndex(targetVertex);

        ArrayUnorderedList<IDivision> resultList = new ArrayUnorderedList<>();

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

    /**
     * Adds a connection (edge) between two divisions, specifying the {@link IHallway}
     * object that represents the connection's properties.
     * The connection is added bidirectionally in the adjacency matrix.
     *
     * @param vertex1 The first division.
     * @param vertex2 The second division.
     * @param weight The {@link IHallway} object representing the connection.
     */
    @Override
    public void addEdge(IDivision vertex1, IDivision vertex2, IHallway weight) {
        this.addEdge(this.getIndex(vertex1), this.getIndex(vertex2), weight);
    }

    /**
     * Retrieves the {@link IHallway} object connecting two specified divisions.
     *
     * @param vertex1 The first division.
     * @param vertex2 The second division.
     * @return The {@link IHallway} connecting the two divisions.
     * @throws ElementNotFoundException If either division is not found in the maze.
     */
    @Override
    public IHallway getEdge(IDivision vertex1, IDivision vertex2) throws ElementNotFoundException {
        int i = this.getIndex(vertex1);
        int j = this.getIndex(vertex2);

        if (i == -1 || j == -1) {
            throw new ElementNotFoundException("Division not found");
        }

        return this.adjMatrix[i][j];
    }

    /**
     * Returns an unordered list of all divisions directly connected (adjacent)
     * to the specified division.
     *
     * @param vertex The division whose neighbors are to be retrieved.
     * @return A {@link UnorderedListADT} of adjacent {@link IDivision} objects.
     * @throws ElementNotFoundException If the specified division is not found in the maze.
     */
    @Override
    public UnorderedListADT<IDivision> getAdjacentVertex(IDivision vertex) throws ElementNotFoundException {
        int index = this.getIndex(vertex);

        if (index == -1) {
            throw new ElementNotFoundException("Division not found");
        }

        UnorderedListADT<IDivision> result = new DoubleLinkedUnorderedList<>();

        for (int i = 0; i < this.count; i++) {
            if (i != index && (this.adjMatrix[i][index] != null || this.adjMatrix[index][i] != null)) {
                result.addToRear(this.vertices[i]);
            }
        }

        return result;
    }

    /**
     * Checks if the maze is empty (contains no divisions).
     *
     * @return {@code true} if the maze has zero divisions; {@code false} otherwise.
     */
    @Override
    public boolean isEmpty() {
        return this.count == 0;
    }

    /**
     * Checks if the maze is connected, meaning there is a path between every
     * pair of divisions. This is checked using a Breadth-First Search (BFS)
     * starting from the first division.
     *
     * @return {@code true} if the maze is connected; {@code false} otherwise.
     */
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

    /**
     * Returns the total number of divisions (vertices) in the maze.
     *
     * @return The number of divisions in the maze.
     */
    @Override
    public int size() {
        return this.count;
    }

    /**
     * Sets the array of divisions (vertices). Used primarily for deserialization.
     *
     * @param vertices The new array of {@link IDivision} objects.
     */
    public void setVertices(IDivision[] vertices) {
        this.vertices = vertices;
    }

    /**
     * Sets the adjacency matrix of hallways. Used primarily for deserialization.
     *
     * @param matrix The new 2D array of {@link IHallway} objects.
     */
    public void setAdjMatrix(IHallway[][] matrix) {
        this.adjMatrix = matrix;
    }

    /**
     * Sets the current count of divisions in the maze. Used primarily for deserialization.
     *
     * @param count The new number of divisions.
     */
    public void setCount(int count) {
        this.count = count;
    }

    /**
     * Returns the shared {@link IHallway} instance used for all connections.
     *
     * @return The main {@link IHallway} object.
     */
    public IHallway getHallway() {
        return hallway;
    }

    /**
     * Sets the shared {@link IHallway} instance. Used primarily for deserialization.
     *
     * @param hallway The new {@link IHallway} object.
     */
    public void setHallway(IHallway hallway) {
        if (hallway == null) {
            this.hallway = new Hallway();
        } else {
            this.hallway = hallway;
        }
    }

    /**
     * Returns the current number of divisions (vertices).
     *
     * @return The count of divisions.
     */
    public int getCount() {
        return count;
    }

    /**
     * Returns the array containing all divisions (vertices).
     *
     * @return The array of {@link IDivision} objects.
     */
    public IDivision[] getVertices() {
        return this.vertices;
    }

    /**
     * Returns the adjacency matrix representing the connections (hallways) between divisions.
     *
     * @return The 2D array of {@link IHallway} objects.
     */
    public IHallway[][] getAdjMatrix() {
        return this.adjMatrix;
    }

    /**
     * Generates a string representation of the maze structure, listing each division
     * and its direct neighbors.
     *
     * @return A string showing the connections between divisions.
     */
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