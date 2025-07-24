package survey_alerts.validator;

import survey_alerts.domain.Survey;
import survey_alerts.validator.exception.BusinessException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static survey_alerts.validator.util.ConstantsErrorValidator.*;

public class ValidatorSurvey {

    private final LocalDate currentDate;

    public ValidatorSurvey(LocalDate currentDate) {
        this.currentDate = currentDate;
    }

    public List<String> validateSurveyData(Survey survey) throws BusinessException {
        List<String> errors = new ArrayList<>();

        if (survey == null) throw new BusinessException(SURVEY_CANNOT_BE_NULL);

        if (currentDate.isBefore(survey.getDateAnswered())) {
            errors.add(SURVEY_CANNOT_HAVE_A_FUTURE_DATE);
        }
        if (survey.getOwner() == null || survey.getOwner().trim().isEmpty()) {
            errors.add(SURVEY_MUST_HAVE_AN_OWNER);
        }
        return errors;
    }
}
