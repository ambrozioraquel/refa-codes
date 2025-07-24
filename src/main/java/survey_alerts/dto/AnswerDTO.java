package survey_alerts.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import survey_alerts.domain.Survey;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnswerDTO {

    private String question;
    private String answerData;
    @JsonBackReference
    private Survey survey;
}
