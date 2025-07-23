package survey_alerts;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import survey_alerts.constant.AlertDescriptionType;
import survey_alerts.domain.*;
import survey_alerts.repository.AlertRepository;
import survey_alerts.repository.SurveyRepository;
import survey_alerts.service.SurveyAlertService;
import survey_alerts.service.factory.AlertFactory;
import survey_alerts.service.nofifier.AlertNotifier;

import java.util.List;

import static org.mockito.Mockito.*;

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

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveGerarAlertaDeRupturaEPreco() {

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

        Alert alert1 = new Alert(null,"Loja 1", "Ruptura detectada!", "Arroz", AlertDescriptionType.ABSENT, 0);
        Alert alert2 = new Alert(null,"Loja 1", "Preço acima do estipulado!", "Arroz", AlertDescriptionType.PRICE, 1);

        when(surveyRepository.findAll()).thenReturn(List.of(survey));
        when(alertFactory.createAlertsFromSurvey(any())).thenReturn(List.of(alert1, alert2));

        surveyAlertService.generateAlertsFromAllSurveys();

        verify(alertRepository).save(alert1);
        verify(alertRepository).save(alert2);

        verify(alertNotifier).notifyAlert(alert1);
        verify(alertNotifier).notifyAlert(alert2);
    }
}
