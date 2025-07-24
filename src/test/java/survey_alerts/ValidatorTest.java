package survey_alerts;

import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import survey_alerts.domain.Survey;
import survey_alerts.validator.exception.BusinessException;
import survey_alerts.validator.util.SurveyBuilder;
import survey_alerts.validator.Validator;

import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

class ValidatorTest {

    Validator validator;

    @BeforeEach
    void setUp() {
        validator = new Validator();
    }

    private SurveyBuilder aSurveyBuilder() {
        return new SurveyBuilder();
    }

    private Date createDate(int day, int month, int year) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month - 1, day, 0, 0, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    @SneakyThrows
    @Test
    void shouldReturnNoErrorsWhenSurveyIsValid() {
        Survey survey = aSurveyBuilder()
                .answeredAt(createDate(21, 9, 2019))
                .withOwner("Michael")
                .build();
        List<String> errors = validator.validate(survey);
        assertThat(errors.size(), equalTo(0));
        assertThat(errors, equalTo(Collections.emptyList()));
    }

    @SneakyThrows
    @Test
    void shouldReturnMessageSurveyMustHaveAnOwner() {
        Survey survey = aSurveyBuilder()
                .answeredAt(createDate(23, 9, 2019))
                .withOwner("")
                .build();
        List<String> errors = validator.validate(survey);
        assertThat(errors.size(), equalTo(1));
        assertThat(errors, contains("Survey must have an owner."));
    }

    @SneakyThrows
    @Test
    void shouldThrowExceptionWhenSurveyIsNull() {
        assertThrows(BusinessException.class, () -> validator.validate(null));
    }

    @SneakyThrows
    @Test
    void shouldReturnMessageWhenAnsweredDateIsInFuture() {
        Survey survey = aSurveyBuilder()
                .answeredAt(createDate(24, 7, 2026))
                .withOwner("Michael")
                .build();
        List<String> errors = validator.validate(survey);
        assertThat(errors.size(), equalTo(1));
        assertThat(errors, contains("Survey can only be answered for current date."));
    }

    @SneakyThrows
    @Test
    void shouldReturnMessageWhenOwnerIsNull() {
        Survey survey = aSurveyBuilder()
                .answeredAt(createDate(24, 7, 2025))
                .withOwner(null)
                .build();
        List<String> errors = validator.validate(survey);
        assertThat(errors.size(), equalTo(1));
        assertThat(errors, contains("Survey must have an owner."));
    }

    @SneakyThrows
    @Test
    void shouldReturnMessageWhenOwnerIsNullAnOwnerAndDateIsInFuture() {
        Survey survey = aSurveyBuilder()
                .answeredAt(createDate(23, 9, 2026))
                .withOwner(null)
                .build();
        List<String> errors = validator.validate(survey);
        assertThat(errors.size(), equalTo(2));
        assertThat(errors, containsInAnyOrder(
                "Survey can only be answered for current date.",
                        "Survey must have an owner."
        ));
    }

    @SneakyThrows
    @Test
    void shouldReturnMessageWhenOwnerIsEmptyAnOwnerAndDateIsInFuture() {
        Survey survey = aSurveyBuilder()
                .answeredAt(createDate(23, 9, 2026))
                .withOwner("")
                .build();
        List<String> errors = validator.validate(survey);
        assertThat(errors.size(), equalTo(2));
        assertThat(errors, containsInAnyOrder(
                "Survey can only be answered for current date.",
                        "Survey must have an owner."
        ));
    }

}
