package survey_alerts.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AlertProcessor {
    private final SurveyGateway surveyGateway;
    private final AlertGateway alertGateway;
    private final MailService mailService;
    @Autowired
    public AlertProcessor(SurveyGateway surveyGateway, AlertGateway alertGateway, MailService mailService) {
        this.surveyGateway = surveyGateway;
        this.alertGateway = alertGateway;
        this.mailService = mailService;
    }
    public void process() {
        Survey[] surveys = surveyGateway.findSurveys();
        List<Alert> alerts = new ArrayList<>();
        for (int i = 0; i < surveys.length; i++) {
            for (int j = 0; j < surveys[i].getAnswers().size(); j++) {
                Answer answer = surveys[i].getAnswers().get(j);
                if (answer.getQuestion().equals("Qual a situação do produto?")) {
                    if (answer.getAnswerData().equals("Produto ausente na gondola")) {
                        Alert a = new Alert();
                        a.setPointOfSale(surveys[i].getPointOfSale());
                        a.setDescription("Ruptura detectada!");
                        a.setProduct(surveys[i].getProduct());
                        a.setType(1);
                        alerts.add(a);
                    }
                } else if (answer.getQuestion().equals("Qual o preço do produto?")) {
                    int collectedPrice = Integer.parseInt(answer.getAnswerData());
                    int expectedPrice = Integer.parseInt(surveys[i].getExpectedPrice());
                    if (collectedPrice > expectedPrice) {
                        Alert a = new Alert();
                        a.setPriceDiff(expectedPrice - Integer.parseInt(answer.getAnswerData()));
                        a.setDescription("Preço acima do estipulado!");
                        a.setProduct(surveys[i].getProduct());
                        a.setPointOfSale(surveys[i].getPointOfSale());
                        a.setType(2);
                        alerts.add(a);
                    }
                }
            }
        }
        for (Alert a : alerts) {
            alertGateway.save(a); // Salva os alertas
            Email email = new Email(); // Cria um email para notificar o alerta
            email.addRecipient("supervisor@empresa.com.br");
            email.setSubject("Alerta de Pesquisa:" + a.getDescription());
            email.setBody(String.format("Ponto de venda: %s\nProduto: %s\n", a.getPointOfSale(), a.getProduct()));
            mailService.send(email); // Envia o email de notificação
        }
    }

}
