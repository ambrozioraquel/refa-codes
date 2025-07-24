package survey_alerts;

import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import survey_alerts.domain.Survey;
import survey_alerts.validator.exception.BusinessException;
import survey_alerts.validator.util.SurveyBuilder;
import survey_alerts.validator.ValidatorSurvey;

import java.time.LocalDate;
import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

class ValidatorSurveyTest {

    ValidatorSurvey validator;
    LocalDate currentDate;


    @BeforeEach
    void setUp() {
        currentDate = LocalDate.now();
        validator = new ValidatorSurvey(currentDate);
    }

    private SurveyBuilder aSurveyBuilder() {
        return new SurveyBuilder();
    }

    private LocalDate createLocalDate(int day, int month, int year) {
        return LocalDate.of(year, month, day);
    }

    @SneakyThrows
    @Test
    void shouldReturnNoErrorsWhenSurveyIsValid() {
        Survey survey = aSurveyBuilder()
                .answeredAt(createLocalDate(21, 9, 2019))
                .withOwner("Michael")
                .build();
        List<String> errors = validator.validateSurveyData(survey);
        assertThat(errors.size(), equalTo(0));
        assertThat(errors, equalTo(Collections.emptyList()));
    }

    @SneakyThrows
    @Test
    void shouldReturnMessageSurveyMustHaveAnOwner() {
        Survey survey = aSurveyBuilder()
                .answeredAt(createLocalDate(23, 9, 2019))
                .withOwner("")
                .build();
        List<String> errors = validator.validateSurveyData(survey);
        assertThat(errors.size(), equalTo(1));
        assertThat(errors, contains("Survey must have an owner."));
    }

    @SneakyThrows
    @Test
    void shouldThrowExceptionWhenSurveyIsNull() {
        assertThrows(BusinessException.class, () -> validator.validateSurveyData(null));
    }

    @SneakyThrows
    @Test
    void shouldReturnMessageWhenAnsweredDateIsInFuture() {
        Survey survey = aSurveyBuilder()
                .answeredAt(createLocalDate(24, 7, 2026))
                .withOwner("Michael")
                .build();
        List<String> errors = validator.validateSurveyData(survey);
        assertThat(errors.size(), equalTo(1));
        assertThat(errors, contains("Survey can only be answered for current date."));
    }

    @SneakyThrows
    @Test
    void shouldReturnMessageWhenOwnerIsNull() {
        Survey survey = aSurveyBuilder()
                .answeredAt(createLocalDate(24, 7, 2025))
                .withOwner(null)
                .build();
        List<String> errors = validator.validateSurveyData(survey);
        assertThat(errors.size(), equalTo(1));
        assertThat(errors, contains("Survey must have an owner."));
    }

    @SneakyThrows
    @Test
    void shouldReturnMessageWhenOwnerIsNullAnOwnerAndDateIsInFuture() {
        Survey survey = aSurveyBuilder()
                .answeredAt(createLocalDate(23, 9, 2026))
                .withOwner(null)
                .build();
        List<String> errors = validator.validateSurveyData(survey);
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
                .answeredAt(createLocalDate(23, 9, 2026))
                .withOwner("")
                .build();
        List<String> errors = validator.validateSurveyData(survey);
        assertThat(errors.size(), equalTo(2));
        assertThat(errors, containsInAnyOrder(
                "Survey can only be answered for current date.",
                        "Survey must have an owner."
        ));
    }

}
