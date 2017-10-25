package org.firas.common.validator;

import java.util.Map;
import java.io.IOException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StringStringMapValidator extends Validator<Map<String, String>> {

    @Getter @Setter protected String message;

    protected static final String DEFAULT_MESSAGE =
                    "The input is not a json of a map from String to String";

    public static final String CODE = "StringStringMap";

    protected String code = CODE;


    protected Map<String, String> converted;


    protected static final TypeReference<Map<String, String>>
            STRING_STRING_MAP_TYPE =
            new TypeReference<Map<String, String>>(){};


    public StringStringMapValidator() {
        this(DEFAULT_MESSAGE);
    }
    
    public StringStringMapValidator(String message) {
        this.message = message;
    }


    public Map<String, String> getConverted() {
        return converted;
    }

    public boolean validate(String str) {
        try {
            converted = new ObjectMapper().readValue(str,
                    STRING_STRING_MAP_TYPE);
            return true;
        } catch (JsonMappingException e) {
        } catch (JsonParseException e) {
        } catch (IOException e) {
        }
        this.errors.add(new ValidationError(code, message));
        return false;
    }

}
