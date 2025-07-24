package survey_alerts.validator.util;

import survey_alerts.domain.Survey;

import java.util.Date;

public class SurveyBuilder {
    private Date answeredAt = new Date();
    private String owner = "default";

    public SurveyBuilder answeredAt(Date date) {
        this.answeredAt = date;
        return this;
    }

    public SurveyBuilder withOwner(String owner) {
        this.owner = owner;
        return this;
    }

    public Survey build() {
        return new Survey(answeredAt, owner);
    }

}
