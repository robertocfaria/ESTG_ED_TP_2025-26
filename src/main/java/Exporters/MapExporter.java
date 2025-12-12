package Exporters;

import Map.Maze;

import Structures.Interfaces.UnorderedListADT;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.util.Iterator;

public class MapExporter {
    public static String FILEPATH = "src/main/resources/mazes.json";

    private final ObjectMapper mapper;

    public MapExporter() {
        this.mapper = new ObjectMapper();
        this.mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, true);
        mapper.configure(SerializationFeature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED, false);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public void exportListADTToJson(UnorderedListADT<Maze> mazeList, String filePath) throws Exception {
        Iterator<Maze> iterator = mazeList.iterator();

        Maze[] mazeArray = new Maze[mazeList.size()];
        for (int i = 0; i < mazeArray.length; i++) {
            mazeArray[i] = iterator.next();
        }

        mapper.writeValue(new File(filePath), mazeArray);
    }


    public void exportArrayToJson(Maze[] map, String filePath) throws Exception {
        mapper.writeValue(new File(filePath), map);
    }
}

