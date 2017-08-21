package org.firas.common.validator;

import java.util.List;
import java.util.ArrayList;
import java.util.Objects;
import lombok.Getter;
import lombok.Setter;

public class DomainValidator extends Validator {

    @Getter @Setter protected String[] domain;
    @Getter @Setter protected String message;

    protected static final String
            DEFAULT_MESSAGE = "The input is not in the specific domain";

    public static final String CODE = "Domain";

    protected String code = CODE;


    public DomainValidator(int[] domain) {
        this(domain, DEFAULT_MESSAGE);
    }

    public DomainValidator(int[] domain, String message) {
        this.message = message;
        this.domain = new String[domain.length];
        for (int i = 0; i < domain.length; ++i) {
            this.domain[i] = Integer.toString(domain[i]);
        }
    }

    public DomainValidator(Integer[] domain) {
        this(domain, DEFAULT_MESSAGE);
    }

    public DomainValidator(Integer[] domain, String message) {
        this.message = message;
        this.domain = new String[domain.length];
        for (int i = 0; i < domain.length; ++i) {
            this.domain[i] = domain[i].toString();
        }
    }

    public DomainValidator(long[] domain) {
        this(domain, DEFAULT_MESSAGE);
    }

    public DomainValidator(long[] domain, String message) {
        this.message = message;
        this.domain = new String[domain.length];
        for (int i = 0; i < domain.length; ++i) {
            this.domain[i] = Long.toString(domain[i]);
        }
    }

    public DomainValidator(Long[] domain) {
        this(domain, DEFAULT_MESSAGE);
    }

    public DomainValidator(Long[] domain, String message) {
        this.message = message;
        this.domain = new String[domain.length];
        for (int i = 0; i < domain.length; ++i) {
            this.domain[i] = domain[i].toString();
        }
    }

    public DomainValidator(String[] domain) {
        this(domain, DEFAULT_MESSAGE);
    }

    public DomainValidator(String[] domain, String message) {
        this.message = message;
        this.domain = domain;
    }


    /**
     * Checks whether str represents an integer
     * (optionally with minimum or maximum constraints)
     *
     * @param  String str  the string to be validated
     * @return boolean  true if str represents a value in the specific array,
     *                  false if the str is not in the array
     */
    public boolean validate(String str) {
        for (int i = 0; i < domain.length; ++i) {
            if (Objects.equals(str, domain[i])) {
                return true;
            }
        }
        this.errors.add(new ValidationError(code, message));
        return false;
    }

}
