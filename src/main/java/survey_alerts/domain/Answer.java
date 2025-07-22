package survey_alerts.domain;

import lombok.Data;

@Data
public class Answer {
    private String question;
    private String answerData;

    public String getQuestion() {
        return question;
    }

    public String getAnswerData() {
        return answerData;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setAnswerData(String answerData) {
        this.answerData = answerData;
    }
}
