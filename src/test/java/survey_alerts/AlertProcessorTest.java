package survey_alerts;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import survey_alerts.domain.*;

import java.util.List;

import static org.mockito.Mockito.*;

class AlertProcessorTest {

    @Mock
    private SurveyGateway surveyGateway;

    @Mock
    private AlertGateway alertGateway;

    @Mock
    private MailService mailService;

    @InjectMocks
    private AlertProcessor alertProcessor;

    @BeforeEach
    void setup() {
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
        when(surveyGateway.findSurveys()).thenReturn(new Survey[]{ survey });

        alertProcessor.process();

        verify(alertGateway, times(2)).save(any(Alert.class));
        verify(mailService, times(2)).send(any(Email.class));
    }
}
