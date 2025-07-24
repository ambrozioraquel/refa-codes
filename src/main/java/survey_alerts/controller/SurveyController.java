package survey_alerts.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import survey_alerts.domain.Survey;
import survey_alerts.dto.SurveyDTO;
import survey_alerts.service.SurveyService;
import org.modelmapper.ModelMapper;


import java.util.List;

@RestController
@RequestMapping("/survey")
public class SurveyController {

    private final SurveyService surveyService;
    private final ModelMapper modelMapper;


    @Autowired
    public SurveyController(SurveyService surveyService, ModelMapper modelMapper) {
        this.surveyService = surveyService;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    public ResponseEntity<SurveyDTO> createSurvey(@RequestBody Survey surveyDTO) {
        Survey survey = modelMapper.map(surveyDTO, Survey.class);
        Survey createdSurvey = surveyService.createSurvey(survey);
        SurveyDTO createdSurveyDTO = modelMapper.map(createdSurvey, SurveyDTO.class);
        return ResponseEntity.ok(createdSurveyDTO);
    }

    @GetMapping
    public ResponseEntity<List<SurveyDTO>> getAllSurveys() {
        List<Survey> surveys = surveyService.getAllSurveys();
        if (surveys.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        List<SurveyDTO> surveysDTOs = surveys.stream()
                .map(survey -> modelMapper.map(survey, SurveyDTO.class))
                .toList();
        return ResponseEntity.ok(surveysDTOs);
    }
}
