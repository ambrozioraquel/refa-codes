package core.validator.util;

public final class ConstantsErrorValidator {

    private ConstantsErrorValidator() {
    }

    public static final String SURVEY_CANNOT_BE_NULL = "Survey cannot be null";
    public static final String SURVEY_CANNOT_HAVE_A_FUTURE_DATE = "Survey can only be answered for current date.";
    public static final String SURVEY_MUST_HAVE_AN_OWNER = "Survey must have an owner.";

}
