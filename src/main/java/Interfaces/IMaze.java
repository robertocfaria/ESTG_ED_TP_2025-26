package Interfaces;

import Structures.Interfaces.NetworkADT;
import Structures.Interfaces.UnorderedListADT;

/**
 * Defines the contract for the game map, which is modeled as an undirected graph.
 * This interface extends {@link NetworkADT}, where {@link IDivision} objects
 * represent the vertices (rooms/locations) and {@link IHallway} objects represent
 * the weighted edges (connections) between them.
 */
public interface IMaze extends NetworkADT<IDivision> {

    /**
     * Retrieves the descriptive name of the maze.
     *
     * @return The name of the maze as a {@code String}.
     */
    String getName();

    /**
     * Adds a weighted connection (edge) between two divisions using a specific hallway object.
     * This is typically an undirected operation, meaning the connection exists in both directions.
     *
     * @param vertex1 The first {@link IDivision}.
     * @param vertex2 The second {@link IDivision}.
     * @param weight The {@link IHallway} object that represents the connection and its properties.
     */
    void addEdge(IDivision vertex1, IDivision vertex2, IHallway weight);

    /**
     * Retrieves an unordered list of all divisions directly connected (adjacent)
     * to the specified division.
     *
     * @param vertex The {@link IDivision} whose neighbors are to be retrieved.
     * @return An {@link UnorderedListADT} of adjacent {@link IDivision} objects.
     */
    UnorderedListADT<IDivision> getAdjacentVertex(IDivision vertex);

    /**
     * Retrieves the {@link IHallway} object connecting two specified divisions.
     * This method is used to access the properties of the connection, such as events.
     *
     * @param vertex1 The first {@link IDivision}.
     * @param vertex2 The second {@link IDivision}.
     * @return The {@link IHallway} connecting the two divisions.
     */
    IHallway getEdge(IDivision vertex1, IDivision vertex2);

    /**
     * Selects and returns a suitable division to be used as a starting point
     * for a new player. The implementation typically chooses a division at the periphery
     * of the maze with a limited number of connections.
     *
     * @return A random {@link IDivision} designated as an initial starting position.
     */
    IDivision getRandomInitialDivision();
}