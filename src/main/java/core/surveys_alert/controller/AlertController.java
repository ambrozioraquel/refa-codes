package core.surveys_alert.controller;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import core.surveys_alert.domain.Alert;
import core.surveys_alert.dto.AlertDTO;
import core.surveys_alert.service.SurveyAlertService;

import java.util.List;

@RestController
@RequestMapping("/alert")
public class AlertController {

    private final SurveyAlertService surveyAlertService;
    private final ModelMapper modelMapper;

    public AlertController(SurveyAlertService surveyAlertService, ModelMapper modelMapper) {
        this.surveyAlertService = surveyAlertService;
        this.modelMapper = modelMapper;
    }

    @PostMapping()
    public ResponseEntity<List<AlertDTO>> generateAlerts() {
        List<Alert> alerts = surveyAlertService.generateAlertsFromAllSurveys();
        if (alerts.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        List<AlertDTO> alertDTOs = alerts.stream()
                .map(alert -> modelMapper.map(alert, AlertDTO.class))
                .toList();
        return ResponseEntity.ok(alertDTOs);
    }

    @GetMapping
    public ResponseEntity<List<AlertDTO>> getAllAlerts() {
        List<Alert> alerts = surveyAlertService.getAllAlerts();
        if (alerts.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        List<AlertDTO> alertDTOs = alerts.stream()
                .map(alert -> modelMapper.map(alert, AlertDTO.class))
                .toList();
        return ResponseEntity.ok(alertDTOs);
    }
}
