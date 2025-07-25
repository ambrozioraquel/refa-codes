package core.surveys_alert.service;

import core.surveys_alert.domain.Survey;
import org.springframework.stereotype.Service;
import core.surveys_alert.repository.SurveyRepository;

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
