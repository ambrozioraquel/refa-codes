package core.surveys_alert.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailDTO {

    private List<String> recipients = new ArrayList<>();
    private String subject;
    private String body;

    public String getSubject() {
        return subject != null ? subject.trim() : null;
    }

    public String getBody() {
        // Remove \r\n e substitui por espaço ou outro separador, se desejar
        return body != null ? body.replace("\r\n", " | ").trim() : null;
    }
}
