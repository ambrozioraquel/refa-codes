package survey_alerts.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import survey_alerts.domain.Survey;
import survey_alerts.repository.SurveyRepository;
import survey_alerts.service.SurveyAlertService;

@RestController
@RequestMapping("/surveys")
public class SurveyController {

    private final SurveyRepository surveyRepository;

    private final SurveyAlertService surveyAlertService;

    @Autowired
    public SurveyController(SurveyRepository surveyRepository, SurveyAlertService surveyAlertService) {
        this.surveyRepository = surveyRepository;
        this.surveyAlertService = surveyAlertService;
    }

    @PostMapping
    public Survey createSurvey(@RequestBody Survey survey) {
        if (survey.getAnswers() != null) {
            survey.getAnswers().forEach(a -> a.setSurvey(survey));
        }
        return surveyRepository.save(survey);
    }

    @PostMapping("/generate-alerts")
    public void generateAlerts() {
        surveyAlertService.generateAlertsFromAllSurveys();
    }

    @GetMapping
    public Iterable<Survey> getAllSurveys() {
        return surveyRepository.findAll();
    }
}
