package core.survey_alert;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import core.surveys_alert.domain.Answer;
import core.surveys_alert.domain.Survey;
import core.surveys_alert.repository.SurveyRepository;
import core.surveys_alert.service.SurveyService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SurveyServiceTest {

    @Mock
    private SurveyRepository surveyRepository;

    @InjectMocks
    private SurveyService surveyService;

    @Test
    void surveyWithAnswers() {
        Survey survey = new Survey();
        Answer answer = new Answer();
        survey.setAnswers(List.of(answer));

        when(surveyRepository.save(survey)).thenReturn(survey);

        Survey result = surveyService.createSurvey(survey);

        assertEquals(survey, result);
        assertEquals(survey, answer.getSurvey());
        verify(surveyRepository).save(survey);
    }

    @Test
    void getAllSurveys() {
        Survey survey1 = new Survey();
        Survey survey2 = new Survey();
        List<Survey> surveys = List.of(survey1, survey2);

        when(surveyRepository.findAll()).thenReturn(surveys);

        List<Survey> result = surveyService.getAllSurveys();

        assertEquals(surveys, result);
        verify(surveyRepository).findAll();
    }
}