package survey_alerts.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import survey_alerts.domain.Alert;
import survey_alerts.domain.Survey;
import survey_alerts.service.SurveyAlertService;
import survey_alerts.service.SurveyService;

import java.util.List;

@RestController
@RequestMapping("/survey")
public class SurveyController {

    private final SurveyService surveyService;
    private final SurveyAlertService surveyAlertService;

    @Autowired
    public SurveyController(SurveyService surveyService, SurveyAlertService surveyAlertService) {
        this.surveyService = surveyService;
        this.surveyAlertService = surveyAlertService;
    }

    @PostMapping
    public ResponseEntity<Survey> createSurvey(@RequestBody Survey survey) {
        Survey createdSurvey = surveyService.createSurvey(survey);
        return ResponseEntity.ok(createdSurvey);
    }

    @PostMapping("/generate-alerts")
    public ResponseEntity<List<Alert>> generateAlerts() {
        List<Alert> alerts = surveyAlertService.generateAlertsFromAllSurveys();
        if (alerts.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(alerts);
    }

    @GetMapping
    public ResponseEntity<List<Survey>> getAllSurveys() {
        List<Survey> surveys = surveyService.getAllSurveys();
        if (surveys.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(surveys);
    }
}
