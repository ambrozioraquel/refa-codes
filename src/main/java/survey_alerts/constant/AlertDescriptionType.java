package survey_alerts.constant;

import lombok.Getter;

@Getter
public enum AlertDescriptionType {
    AUSENTE(1),
    PRECO(2);

    private final int code;

    AlertDescriptionType(int code) {
        this.code = code;
    }
}
