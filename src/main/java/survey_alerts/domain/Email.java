package survey_alerts.domain;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Email {
    private List<String> recipients = new ArrayList<>();
    private String subject;
    private String body;

    public void addRecipient(String email) {
        recipients.add(email);
    }

    public List<String> getRecipients() {
        return recipients;
    }

    public String getSubject() {
        return subject;
    }

    public String getBody() {
        return body;
    }

    public void setRecipients(List<String> recipients) {
        this.recipients = recipients;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
