package survey_alerts.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import survey_alerts.constant.AlertDescriptionType;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlertDTO {

    private String pointOfSale;
    private String description;
    private String product;
    private AlertDescriptionType type;
    private int priceDiff;
}
