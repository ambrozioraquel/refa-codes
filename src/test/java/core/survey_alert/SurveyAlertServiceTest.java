package core.survey_alert;

import core.surveys_alert.constant.AlertDescriptionType;
import core.surveys_alert.domain.Alert;
import core.surveys_alert.domain.Survey;
import core.surveys_alert.domain.Answer;
import core.surveys_alert.repository.AlertRepository;
import core.surveys_alert.repository.SurveyRepository;
import core.surveys_alert.service.SurveyAlertService;
import core.surveys_alert.service.factory.AlertFactory;
import core.surveys_alert.service.nofifier.AlertNotifier;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SurveyAlertServiceTest {

    @Mock
    private SurveyRepository surveyRepository;

    @Mock
    private AlertRepository alertRepository;

    @Mock
    private AlertFactory alertFactory;

    @Mock
    private AlertNotifier alertNotifier;

    @InjectMocks
    private SurveyAlertService surveyAlertService;

    @Test
    void deveGerarAlertaDeRupturaEPreco() {
        // Arrange
        Survey survey = new Survey();
        survey.setPointOfSale("Loja 1");
        survey.setProduct("Arroz");
        survey.setExpectedPrice("5");

        Answer a1 = new Answer();
        a1.setQuestion("Qual a situação do produto?");
        a1.setAnswerData("Produto ausente na gondola");

        Answer a2 = new Answer();
        a2.setQuestion("Qual o preço do produto?");
        a2.setAnswerData("6");


        survey.setAnswers(List.of(a1, a2));

        Alert alert1 = new Alert(null, "Loja 1", "Ruptura detectada!", "Arroz", AlertDescriptionType.AUSENTE, 0);
        Alert alert2 = new Alert(null, "Loja 1", "Preço acima do estipulado!", "Arroz", AlertDescriptionType.PRECO, 1);

        when(surveyRepository.findAll()).thenReturn(List.of(survey));
        when(alertFactory.createAlertsFromSurvey(any(Survey.class))).thenReturn(List.of(alert1, alert2));

        // Act
        List<Alert> result = surveyAlertService.generateAlertsFromAllSurveys();

        // Assert
        verify(alertRepository).save(alert1);
        verify(alertRepository).save(alert2);
        verify(alertNotifier).notifyAlert(alert1);
        verify(alertNotifier).notifyAlert(alert2);
        verifyNoMoreInteractions(alertRepository, alertNotifier);

        assertEquals(List.of(alert1, alert2), result);
    }
}