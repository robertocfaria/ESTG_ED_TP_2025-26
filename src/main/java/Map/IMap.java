package Map;

import java.util.List;

/**
 * Represents a graph-based map of the maze, where vertices correspond to divisions
 * (rooms) and edges represent hallways connecting them.
 * <p>
 * Implementations of this interface should manage the storage and retrieval of
 * divisions and hallways, ensuring proper connectivity and structural integrity
 * of the maze.
 */
public interface IMap {
    // IHallway getHallway(IDivision vertex1, IDivision vertex2); ????????????
    //List<IDivision> findPath(IDivision start, IDivision end); ??????????????

    /**
     * Adds a new division (vertex) to the map.
     *
     * @param vertex the division to be added; must not be {@code null}
     */
    void addDivision(IDivision vertex);

    /**
     * Removes an existing division (vertex) from the map.
     * <p>
     * Implementations should also remove any hallways connected to this division.
     *
     * @param vertex the division to be removed; must not be {@code null}
     */
    void removeDivision(IDivision vertex);

    /**
     * Creates a hallway (edge) between two divisions.
     * <p>
     * The hallway may contain additional information such as events or movement effects.
     *
     * @param vertex1 the first division; must not be {@code null}
     * @param vertex2 the second division; must not be {@code null}
     * @param weight  the hallway connecting the two divisions, must not be {@code null}
     */
    void addHallway(IDivision vertex1, IDivision vertex2, IHallway weight);

    /**
     * Returns all divisions directly connected to the given division.
     *
     * @param vertex the division whose neighbors are to be retrieved
     * @return a list of adjacent divisions
     */
    List<IDivision> getAdjacentDivisions(IDivision vertex);

    /**
     * Checks whether the map contains no divisions.
     *
     * @return {@code true} if the map has no divisions, {@code false} otherwise
     */
    boolean isEmpty();

    /**
     * Determines whether the map is fully connected.
     * <p>
     * A map is considered connected if every division is reachable from any other division.
     *
     * @return {@code true} if the map is connected, {@code false} otherwise
     */
    boolean isConnected();

    /**
     * Returns the number of divisions (vertices) currently in the map.
     *
     * @return the number of divisions in the map
     */
    int size();
}
