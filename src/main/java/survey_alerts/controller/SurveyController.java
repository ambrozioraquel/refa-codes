package survey_alerts.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import survey_alerts.domain.Survey;
import survey_alerts.service.SurveyAlertService;
import survey_alerts.service.SurveyService;

import java.util.List;

@RestController
@RequestMapping("/surveys")
public class SurveyController {

    private final SurveyService surveyService;
    private final SurveyAlertService surveyAlertService;

    @Autowired
    public SurveyController(SurveyService surveyService, SurveyAlertService surveyAlertService) {
        this.surveyService = surveyService;
        this.surveyAlertService = surveyAlertService;
    }

    @PostMapping
    public Survey createSurvey(@RequestBody Survey survey) {
        return surveyService.createSurvey(survey);
    }

    @PostMapping("/generate-alerts")
    public void generateAlerts() {
        surveyAlertService.generateAlertsFromAllSurveys();
    }

    @GetMapping
    public List<Survey> getAllSurveys() {
        return surveyService.getAllSurveys();
    }
}
