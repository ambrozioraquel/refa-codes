package core.surveys_alert.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import core.surveys_alert.constant.AlertDescriptionType;

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
