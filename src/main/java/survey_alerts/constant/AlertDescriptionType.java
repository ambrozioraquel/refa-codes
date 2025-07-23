package survey_alerts.constant;

import lombok.Getter;

@Getter
public enum AlertDescriptionType {
    ABSENT(1),
    PRICE(2);

    private final int code;

    AlertDescriptionType(int code) {
        this.code = code;
    }
}
