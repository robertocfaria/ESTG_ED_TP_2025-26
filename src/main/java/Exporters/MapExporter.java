package Exporters;

import Map.Map;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;

public class MapExporter {

    private final ObjectMapper mapper;

    public MapExporter() {
        this.mapper = new ObjectMapper();
        this.mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, true);
        mapper.configure(SerializationFeature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED, false);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    /**
     * Exporta o mapa para um arquivo JSON.
     */
    public void exportToJson(Map map, String filePath) throws Exception {
        mapper.writeValue(new File(filePath), map);
    }
}


