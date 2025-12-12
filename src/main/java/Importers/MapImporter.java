package Importers;

import Map.Map;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;

public class MapImporter {

    private final ObjectMapper mapper;

    public MapImporter() {
        this.mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, true);
        mapper.configure(SerializationFeature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED, false);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    /**
     * Importa o mapa a partir de um arquivo JSON.
     */
    public Map importFromJson(String filePath) throws Exception {
        return mapper.readValue(new File(filePath), Map.class);
    }
}


