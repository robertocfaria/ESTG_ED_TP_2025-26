package Importers;

import Map.Maze;
import Structures.Interfaces.UnorderedListADT;
import Structures.List.DoubleLinkedUnorderedList;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;

/**
 * Utility class responsible for importing maze data from external JSON files.
 * It uses the Jackson library to deserialize JSON content, converting an array
 * of {@link Maze} objects in the file into a dynamic {@link UnorderedListADT} for game use.
 */
public class MapImporter {

    private final ObjectMapper mapper;

    /**
     * Constructs a MapImporter and initializes the internal {@code ObjectMapper}.
     * The mapper is configured to handle potential data inconsistencies during
     * deserialization (e.g., ignoring unknown JSON properties).
     */
    public MapImporter() {
        this.mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, true);
        mapper.configure(SerializationFeature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED, false);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    private Maze[] importArrayFromJson(String filePath) throws IOException {
        return mapper.readValue(new File(filePath), Maze[].class);
    }

    /**
     * Imports an array of {@link Maze} objects from a specified JSON file and
     * converts them into an {@link UnorderedListADT} of mazes.
     *
     * @param filePath The relative or absolute path to the JSON file containing the maze data.
     * @return An {@link UnorderedListADT} (specifically a {@link DoubleLinkedUnorderedList})
     * containing all the {@link Maze} objects read from the file.
     * @throws IOException If the file cannot be read, the path is incorrect, or
     * there is a general error during JSON processing.
     */
    public UnorderedListADT<Maze> importListADTFromJson(String filePath) throws IOException {
        Maze[] mazeArray = this.importArrayFromJson(filePath);

        UnorderedListADT<Maze> mazeList = new DoubleLinkedUnorderedList<>();

        for (int i = 0; i < mazeArray.length; i++) {
            mazeList.addToRear(mazeArray[i]);
        }

        return mazeList;
    }
}

