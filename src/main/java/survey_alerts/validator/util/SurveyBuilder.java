package survey_alerts.validator.util;

import survey_alerts.domain.Survey;

import java.time.LocalDate;

public class SurveyBuilder {
    private LocalDate answeredAt = LocalDate.now();
    private String owner = "default";

    public SurveyBuilder answeredAt(LocalDate date) {
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
