package org.firas.wrap.validator;

import java.util.Map;
import java.util.HashMap;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.extern.slf4j.Slf4j;

import org.firas.common.validator.Validator;
import org.firas.common.validator.ValidationError;
import org.firas.wrap.entity.BoxComponent;

@Slf4j
public class BoxComponentValidator extends Validator<Map<Integer, Integer>> {

    private String message, idMessage, numberMessage;

    public BoxComponentValidator() {
        this.message = "The input string is not a json of a map " +
                "from integer to integer";
    }

    public BoxComponentValidator(
            String message, String idMessage, String numberMessage) {
        this.message = message;
        this.idMessage = idMessage;
        this.numberMessage = numberMessage;
    }

    public boolean validate(String str) {
        log.debug(String.valueOf(str));
        try {
            Map<String, Integer> temp = new ObjectMapper().readValue(str,
                    STRING_INTEGER_MAP_TYPE);
            if (temp.size() <= 0) {
                throw new IllegalArgumentException();
            }
            converted = new HashMap<>(temp.size(), 1f);
            for (Map.Entry<String, Integer> entry : temp.entrySet()) {
                Integer componentId = Integer.parseInt(entry.getKey());
                if (componentId < 1) {
                    this.errors.add(new ValidationError("Integer.min", idMessage));
                    return false;
                }
                if (entry.getValue() < 1) {
                    this.errors.add(new ValidationError("Integer.min", numberMessage));
                    return false;
                }
                converted.put(componentId, entry.getValue());
            }
            return true;
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
        } catch (IOException e) {
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
        }
        this.errors.add(new ValidationError("IntegerIntegerMap", message));
        return false;
    }

    protected static final TypeReference<Map<String, Integer>>
            STRING_INTEGER_MAP_TYPE =
            new TypeReference<Map<String, Integer>>(){};

    private Map<Integer, Integer> converted;

    public Map<Integer, Integer> getConverted() {
        return converted;
    }
}
