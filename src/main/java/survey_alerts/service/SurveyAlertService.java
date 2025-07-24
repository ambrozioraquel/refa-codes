package survey_alerts.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import survey_alerts.domain.*;
import survey_alerts.repository.AlertRepository;
import survey_alerts.repository.SurveyRepository;
import survey_alerts.service.factory.AlertFactory;
import survey_alerts.service.nofifier.AlertNotifier;

import java.util.List;

@Service
public class SurveyAlertService {
    private final SurveyRepository surveyRepository;
    private final AlertRepository alertRepository;
    private final AlertFactory alertFactory;
    private final AlertNotifier alertNotifier;

    @Autowired
    public SurveyAlertService(SurveyRepository surveyRepository, AlertRepository alertRepository, AlertFactory alertFactory, AlertNotifier alertNotifier) {
        this.surveyRepository = surveyRepository;
        this.alertRepository = alertRepository;
        this.alertFactory = alertFactory;
        this.alertNotifier = alertNotifier;
    }

    public List<Alert> generateAlertsFromAllSurveys() {
        List<Alert> alerts = surveyRepository.findAll().stream()
                .flatMap(survey -> alertFactory.createAlertsFromSurvey(survey).stream())
                .toList();
        processAlerts(alerts);
        return alerts;
    }

    private void processAlerts(List<Alert> alerts) {
        alerts.forEach(alert -> {
            alertRepository.save(alert);
            alertNotifier.notifyAlert(alert);
        });
    }

    public List<Alert> getAllAlerts() {
        return alertRepository.findAll();
    }
}
