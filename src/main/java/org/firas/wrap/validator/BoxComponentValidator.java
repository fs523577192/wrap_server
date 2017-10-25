package org.firas.wrap.validator;

import java.util.Map;
import java.util.HashMap;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;

import org.firas.common.validator.Validator;
import org.firas.common.validator.ValidationError;
import org.firas.wrap.entity.BoxComponent;

public class BoxComponentValidator extends Validator<Map<Integer, Integer>> {

    private String message;

    public BoxComponentValidator() {
        this.message = "The input string is not a json of a map " +
                "from integer to integer";
    }

    public BoxComponentValidator(String message) {
        this.message = message;
    }

    public boolean validate(String str) {
        try {
            converted = new ObjectMapper().readValue(str,
                    INTEGER_INTEGER_MAP_TYPE);
            return true;
        } catch (JsonMappingException e) {
        } catch (JsonParseException e) {
        } catch (IOException e) {
        }
        this.errors.add(new ValidationError("IntegerIntegerMap", message));
        return false;
    }

    protected static final TypeReference<Map<Integer, Integer>>
            INTEGER_INTEGER_MAP_TYPE =
            new TypeReference<Map<Integer, Integer>>(){};

    private Map<Integer, Integer> converted;

    public Map<Integer, Integer> getConverted() {
        return converted;
    }
}
