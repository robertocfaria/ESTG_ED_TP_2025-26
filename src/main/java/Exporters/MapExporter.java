package Exporters;

import Map.Maze;

import Structures.Interfaces.UnorderedListADT;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.util.Iterator;

/**
 * Utility class responsible for serializing and exporting a collection of {@link Maze}
 * objects into a JSON file format. It uses the Jackson library for JSON processing
 * and configures it for human-readable output.
 */
public class MapExporter {

    /**
     * The default file path where maze data is expected to be stored or exported.
     */
    public static String FILEPATH = "src/main/resources/mazes.json";

    private final ObjectMapper mapper;

    /**
     * Constructs a MapExporter and initializes the internal {@code ObjectMapper}.
     * The mapper is configured with the following features for clean JSON output:
     * <ul>
     * <li>Enables indentation for readability.</li>
     * <li>Allows writing null map values.</li>
     * <li>Disables unwrapping of single-element arrays.</li>
     * <li>Ignores unknown properties during deserialization to prevent crashes on schema changes.</li>
     * </ul>
     */
    public MapExporter() {
        this.mapper = new ObjectMapper();
        this.mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, true);
        mapper.configure(SerializationFeature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED, false);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    /**
     * Converts an unordered list of {@link Maze} objects into a JSON array and writes
     * the content to the specified file path.
     *
     * @param mazeList The {@link UnorderedListADT} containing the {@link Maze} objects to export.
     * @param filePath The destination path of the JSON file.
     * @throws Exception If an error occurs during the file writing or JSON serialization process.
     */
    public void exportListADTToJson(UnorderedListADT<Maze> mazeList, String filePath) throws Exception {
        Iterator<Maze> iterator = mazeList.iterator();

        Maze[] mazeArray = new Maze[mazeList.size()];
        for (int i = 0; i < mazeArray.length; i++) {
            mazeArray[i] = iterator.next();
        }

        mapper.writeValue(new File(filePath), mazeArray);
    }
}

