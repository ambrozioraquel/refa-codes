package survey_alerts.validator;

import survey_alerts.domain.Survey;
import survey_alerts.validator.exception.BusinessException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Validator {

    public List<String> validate(Survey survey) throws BusinessException {
        List<String> errors = new ArrayList<>();
        if (survey == null) {
            throw new BusinessException("Survey cannot be null.");
        }

        if (currentDate().before(survey.getDateAnswered())) {
            errors.add("Survey can only be answered for current date.");
        }
        if (survey.getOwner() == null || survey.getOwner().isEmpty()) {
            errors.add("Survey must have an owner.");
        }
        return errors;
    }

    private Date currentDate() {
        return new Date();
    }
}
