package Map;

import Exceptions.NotSupportedOperation;
import Importers.DivisionNames;
import Interfaces.IDivision;
import Interfaces.IHallway;
import Interfaces.IMap;
import Interfaces.IPlayer;
import NewSctructures.ArrayUnorderedListWithGet;
import NewSctructures.UnorderedListWithGetADT;
import Structures.Exceptions.ElementNotFoundException;
import Structures.Exceptions.EmptyCollectionException;
import Structures.Interfaces.ListADT;
import Structures.List.ArrayUnorderedList;
import Structures.Queue.LinkedQueue;
import Structures.Stack.LinkedStack;

import java.util.Iterator;
import java.util.Random;

public class Map implements IMap {
    public static final int INCREASE_FACTOR = 2;
    private IHallway[][] adjMatrix;
    private IDivision[] vertices;
    private int count;
    private Random rand = new Random();
    private IHallway hallway;

    public Map(int capacity) {
        this.vertices = new IDivision[capacity];
        this.adjMatrix = new IHallway[capacity][capacity];
        this.hallway = new Hallway();
        this.count = 0;

        generateDivisions(capacity);
        generateConnections(this.hallway);
        defineGoalDivision();
    }

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
        // Probabilidade baixa para não encher o mapa de linhas
        double density = 0.2;

        // ALCANCE MÁXIMO DO ATALHO
        // A sala 0 só poderá ligar no máximo até à 3 ou 4. Nunca à 9.
        int jumpLimit = 3;

        // 1. Criar o caminho principal (Espinha Dorsal)
        for (int i = 0; i < this.count - 1; i++) {
            // Podes adicionar: hallway.setAttributes(1); // Custo normal
            this.addEdge(i, i + 1, hallway);
        }

        // 2. Criar atalhos CURTOS
        for (int i = 0; i < this.count; i++) {
            // O segredo está aqui: o limite NÃO é 'this.count'.
            // É o índice atual + o salto máximo.
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

    /**
     * Dificuldade acrescida, pois usa o caminho mais longo para criar a sala Goal
     */
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

        // Inicializar distâncias
        for (int i = 0; i < this.count; i++) {
            distances[i] = -1; // -1 significa inalcançável
            visited[i] = false;
        }

        // Configurar o início (índice 0)
        LinkedQueue<Integer> queue = new LinkedQueue<>();
        queue.enqueue(0);
        visited[0] = true;
        distances[0] = 0;

        // BFS Loop
        while (!queue.isEmpty()) {
            int current = queue.dequeue();

            for (int neighbor = 0; neighbor < this.count; neighbor++) {
                // Se existe ligação e ainda não foi visitado
                if (adjMatrix[current][neighbor] != null && !visited[neighbor]) {
                    visited[neighbor] = true;
                    distances[neighbor] = distances[current] + 1; // A distância é a do anterior + 1
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

    @Override
    public IDivision getRandomInitialDivision() {
        ArrayUnorderedListWithGet<IDivision> result = new ArrayUnorderedListWithGet<>();

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
        return result.get(randIndex);
    }

    @Override
    public void addVertex(IDivision vertex) {
        if (this.size() == this.vertices.length) {
            this.expandCapacity();
        }

        this.vertices[this.count] = vertex;

        this.count++;
    }

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

    @Override
    public void addEdge(IDivision t, IDivision t1) throws NotSupportedOperation {
        throw new NotSupportedOperation("This operation can't be done in this graph");
    }

    @Override
    public void addEdge(IDivision t, IDivision t1, double v) throws NotSupportedOperation {
        throw new NotSupportedOperation("This operation can't be done in this graph");
    }

    @Override
    public void removeEdge(IDivision vertex1, IDivision vertex2) {
        this.removeEdge(this.getIndex(vertex1), this.getIndex(vertex2));
    }

    @Override
    public double shortestPathWeight(IDivision startVertex, IDivision targetVertex) {
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

    @Override
    public void addEdge(IDivision vertex1, IDivision vertex2, IHallway weight) {
        this.addEdge(this.getIndex(vertex1), this.getIndex(vertex2), weight);
    }

    @Override
    public IHallway getEdge(IDivision vertex1, IDivision vertex2) throws ElementNotFoundException {
        int i = this.getIndex(vertex1);
        int j = this.getIndex(vertex2);

        if (i == -1 || j == -1) {
            throw new ElementNotFoundException("Division not found");
        }

        return this.adjMatrix[i][j];
    }

    @Override
    public UnorderedListWithGetADT<IDivision> getAdjacentVertex(IDivision vertex) throws ElementNotFoundException {
        int index = this.getIndex(vertex);

        if (index == -1) {
            throw new ElementNotFoundException("Division not found");
        }

        UnorderedListWithGetADT<IDivision> result = new ArrayUnorderedListWithGet<>();

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