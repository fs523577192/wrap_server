package org.firas.common.validator;

public class IpValidator extends StringValidator {

    protected static final String DEFAULT_MESSAGE =
            "The input is not a valid IP";

    public static final String CODE = "IP";

    public static final String IPV4_COMPONENT = "(2[0-4]\\d|25[0-5]|[01]?\\d\\d?)";
    public static final String IPV4_PATTERN =
            "(" + IPV4_COMPONENT + "\\.){3}" + IPV4_COMPONENT;
    public static final String IPV6_COMPONENT = "[\\da-fA-F]{1,4}";
    public static final String IPV6_PATTERN =
            "^(" + IPV6_COMPONENT + ":){6}" + IPV4_PATTERN + "$|" +
            "^::(" + IPV6_COMPONENT + ":){0,4}" + IPV4_PATTERN + "$|" +
            "^(" + IPV6_COMPONENT + ":):(" + IPV6_COMPONENT + ":){0,3}" + IPV4_PATTERN + "$|" +
            "^(" + IPV6_COMPONENT + ":){2}:(" + IPV6_COMPONENT + ":){0,2}" + IPV4_PATTERN + "$|" +
            "^(" + IPV6_COMPONENT + ":){3}:(" + IPV6_COMPONENT + ":){0,1}" + IPV4_PATTERN + "$|" +
            "^(" + IPV6_COMPONENT + ":){4}:" + IPV4_PATTERN + "$|" +
            "^(" + IPV6_COMPONENT + ":){7}" + IPV6_COMPONENT + "$|" +
            "^:((:" + IPV6_COMPONENT + "){1,6}|:)$|" +
            "^" + IPV6_COMPONENT + ":((:" + IPV6_COMPONENT + "){1,5}|:)$|" +
            "^(" + IPV6_COMPONENT + ":){2}((:" + IPV6_COMPONENT + "){1,4}|:)$|" +
            "^(" + IPV6_COMPONENT + ":){3}((:" + IPV6_COMPONENT + "){1,3}|:)$|" +
            "^(" + IPV6_COMPONENT + ":){4}((:" + IPV6_COMPONENT + "){1,2}|:)$|" +
            "^(" + IPV6_COMPONENT + ":){5}:(" + IPV6_COMPONENT + ")?$|" +
            "^(" + IPV6_COMPONENT + ":){6}:$";
    public static final String PATTERN = "^" + IPV4_PATTERN + "$|" + IPV6_PATTERN;

	public IpValidator() {
        this(DEFAULT_MESSAGE);
        this.codeMin = this.codeMax = this.codePattern = CODE;
    }

    public IpValidator(String message) {
        super(null, message, 7, message, PATTERN, message);
        this.codeMin = this.codeMax = this.codePattern = CODE;
    }

}
