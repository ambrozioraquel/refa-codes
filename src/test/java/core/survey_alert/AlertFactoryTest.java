package core.survey_alert;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import core.surveys_alert.domain.Alert;
import core.surveys_alert.domain.Answer;
import core.surveys_alert.domain.Survey;
import core.surveys_alert.service.factory.AlertFactory;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static core.surveys_alert.constant.AlertDescriptionType.AUSENTE;
import static core.surveys_alert.constant.AlertDescriptionType.PRECO;
import static core.surveys_alert.constant.Constants.*;

@ExtendWith(MockitoExtension.class)
class AlertFactoryTest {

    private AlertFactory alertFactory;

    @BeforeEach
    void setUp() {
        alertFactory = new AlertFactory();
    }

    @Test
    void createAbsentAlertWhenProductIsAbsent() {
        Survey survey = createSurveyWithAnswer(QUESTION_STATUS, ANSWER_ABSENT, "0.00");

        List<Alert> alerts = alertFactory.createAlertsFromSurvey(survey);

        assertEquals(1, alerts.size());
        Alert alert = alerts.get(0);
        assertEquals(ALERT_DESC_ABSENT, alert.getDescription());
        assertEquals(AUSENTE, alert.getType());
    }

    @Test
    void createPriceAlertWhenCollectedPriceIsGreaterThanExpected() {
        Survey survey = createSurveyWithAnswer(QUESTION_PRICE, "15.00", "10.00");

        List<Alert> alerts = alertFactory.createAlertsFromSurvey(survey);

        assertEquals(1, alerts.size());
        Alert alert = alerts.get(0);
        assertEquals(ALERT_DESC_PRICE, alert.getDescription());
        assertEquals(PRECO, alert.getType());
    }

    @Test
    void notCreateAlertWhenPriceIsEqualToExpected() {
        Survey survey = createSurveyWithAnswer(QUESTION_PRICE, "10.00", "10.00");

        List<Alert> alerts = alertFactory.createAlertsFromSurvey(survey);

        assertTrue(alerts.isEmpty());
    }

    @Test
    void notCreateAlertWhenPriceIsLowerThanExpected() {
        Survey survey = createSurveyWithAnswer(QUESTION_PRICE, "9.00", "10.00");

        List<Alert> alerts = alertFactory.createAlertsFromSurvey(survey);

        assertTrue(alerts.isEmpty());
    }

    @Test
    void shouldNotCreateAlertWhenPriceIsInvalid() {
        Survey survey = createSurveyWithAnswer(QUESTION_PRICE, "invalid", "10.00");

        List<Alert> alerts = alertFactory.createAlertsFromSurvey(survey);

        assertTrue(alerts.isEmpty());
    }

    @Test
    void shouldNotCreateAnyAlertWhenAnswerIsUnrelated() {
        Survey survey = createSurveyWithAnswer("OTHER_QUESTION", "some answer", "10.00");

        List<Alert> alerts = alertFactory.createAlertsFromSurvey(survey);

        assertTrue(alerts.isEmpty());
    }

    private Survey createSurveyWithAnswer(String question, String answerData, String expectedPrice) {
        Survey survey = new Survey();
        survey.setExpectedPrice(expectedPrice);
        survey.setProduct("Produto Teste");
        survey.setPointOfSale("PDV 123");

        Answer answer = new Answer(null, question, answerData, survey);
        survey.setAnswers(List.of(answer));
        return survey;
    }
}