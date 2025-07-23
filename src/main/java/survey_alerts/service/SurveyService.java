package survey_alerts.service;

import org.springframework.stereotype.Service;
import survey_alerts.domain.Survey;
import survey_alerts.repository.SurveyRepository;

import java.util.List;

@Service
public class SurveyService {
    private final SurveyRepository surveyRepository;

    public SurveyService(SurveyRepository surveyRepository) {
        this.surveyRepository = surveyRepository;
    }

    public Survey createSurvey(Survey survey) {
        if (survey.getAnswers() != null) {
            survey.getAnswers().forEach(a -> a.setSurvey(survey));
        }
        return surveyRepository.save(survey);
    }

    public List<Survey> getAllSurveys() {
        return surveyRepository.findAll();
    }
}
