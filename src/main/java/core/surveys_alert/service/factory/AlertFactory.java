package core.surveys_alert.service.factory;

import org.springframework.stereotype.Component;
import core.surveys_alert.domain.Alert;
import core.surveys_alert.domain.Answer;
import core.surveys_alert.domain.Survey;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static core.surveys_alert.constant.AlertDescriptionType.*;
import static core.surveys_alert.constant.Constants.*;

@Component
public class AlertFactory {


    public List<Alert> createAlertsFromSurvey(Survey survey) {
        return survey.getAnswers().stream()
                .map(answer -> mapToAlert(survey, answer)) // Mapeia cada resposta para um possivel alerta
                .flatMap(java.util.Optional::stream)// Remove os Optionals vazios
                .toList();
    }

    private Optional<Alert> mapToAlert(Survey survey, Answer answer) {
        if (isAbsentProduct(answer)) {  // Cria alerta de ruptura se o produto estiver ausente
            return Optional.of(createAbsentAlert(survey));
        }
        if (isPriceQuestion(answer)) { // Cria alerta de preço se o preço coletado for maior que o esperado
            return getPriceDifference(answer, survey.getExpectedPrice())
                    .filter(diff -> diff > 0)
                    .map(diff -> createPriceAlert(survey, diff));
        }
        return Optional.empty();
    }

    // Verifica se a resposta indica ausencia do produto
    private boolean isAbsentProduct(Answer answer) {
        return QUESTION_STATUS.equals(answer.getQuestion()) && ANSWER_ABSENT.equals(answer.getAnswerData());
    }

    // Verifica se a resposta eh referente ao preco do produto
    private boolean isPriceQuestion(Answer answer) {
        return QUESTION_PRICE.equals(answer.getQuestion());
    }

    // Calcula a diferença entre o preço coletado e o esperado
    private Optional<Integer> getPriceDifference(Answer answer, String expectedPriceStr) {
        try {
            BigDecimal collectedPrice = new BigDecimal(answer.getAnswerData());
            BigDecimal expectedPrice = new BigDecimal(expectedPriceStr);
            return Optional.of(collectedPrice.subtract(expectedPrice).intValue());
        } catch (NumberFormatException e) {
            return Optional.empty(); // Retorna vazio se nao for possivel converter para numero
        }
    }

    private Alert createAbsentAlert(Survey survey) {
        return new Alert(null,
                survey.getPointOfSale(),
                ALERT_DESC_ABSENT,
                survey.getProduct(),
                AUSENTE,
                0
        );
    }

    private Alert createPriceAlert(Survey survey, int priceDifference) {
        return new Alert(null,
                survey.getPointOfSale(),
                ALERT_DESC_PRICE,
                survey.getProduct(),
                PRECO,
                priceDifference
        );
    }
}
