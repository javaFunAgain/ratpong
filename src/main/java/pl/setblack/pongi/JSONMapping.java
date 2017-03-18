package pl.setblack.pongi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import javaslang.jackson.datatype.JavaslangModule;

/**
 * Created by jarek on 3/17/17.
 */
public class JSONMapping {

    private static final ObjectMapper MAPPER = configureMapping();

    private static ObjectMapper configureMapping() {
        return new ObjectMapper()
                .registerModule(new ParameterNamesModule())
                .registerModule(new Jdk8Module())
                .registerModule(new JavaTimeModule())
                .registerModule(new JavaslangModule());
    }


    public static final ObjectMapper getJsonMapping() {
        return JSONMapping.MAPPER;
    }
}
