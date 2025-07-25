package core.survey_alert;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.junit.jupiter.MockitoExtension;
import core.surveys_alert.domain.Alert;
import core.surveys_alert.domain.Email;
import core.surveys_alert.service.MailService;
import core.surveys_alert.service.nofifier.AlertNotifier;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AlertNotifierTest {

    private MailService mailService;
    private AlertNotifier alertNotifier;

    @BeforeEach
    void setUp() throws Exception {
        mailService = mock(MailService.class);
        alertNotifier = new AlertNotifier(mailService);

        // Injeta valor no campo privado emailRecipient
        Field field = AlertNotifier.class.getDeclaredField("emailRecipient");
        field.setAccessible(true);
        field.set(alertNotifier, "destino@email.com");
    }

    @Test
    void notifyAlertSendingCorrectEmail() {
        Alert alert = new Alert();
        alert.setDescription("Ruptura detectada!");
        alert.setPointOfSale("Loja 1");
        alert.setProduct("Arroz");

        ArgumentCaptor<Email> captor = ArgumentCaptor.forClass(Email.class);

        alertNotifier.notifyAlert(alert);

        verify(mailService).send(captor.capture());
        Email enviado = captor.getValue();

        assertEquals(1, enviado.getRecipients().size());
        assertEquals("destino@email.com", enviado.getRecipients().get(0));
        assertEquals("Alerta de Pesquisa: Ruptura detectada!", enviado.getSubject());
        assertEquals(
                "Ponto de venda: Loja 1\nProduto: Arroz",
                enviado.getBody().replace("\r\n", "\n")
        );
    }
}
