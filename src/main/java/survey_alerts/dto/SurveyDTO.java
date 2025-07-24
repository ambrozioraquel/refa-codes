package survey_alerts.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import survey_alerts.domain.Answer;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SurveyDTO {

    private String pointOfSale;
    private String expectedPrice;
    private String product;
    private List<Answer> answers;
}
