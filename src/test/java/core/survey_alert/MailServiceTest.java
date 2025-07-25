package core.survey_alert;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import core.surveys_alert.domain.Email;
import core.surveys_alert.repository.RepositoryMail;
import core.surveys_alert.service.MailService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class MailServiceTest {

    @Mock
    private RepositoryMail repositoryMail;

    @InjectMocks
    private MailService mailService;

    @Test
    void saveEmailOnSend() {
        Email email = new Email();
        mailService.send(email);
        verify(repositoryMail).save(email);
    }

    @Test
    void getAllEmails() {
        Email email1 = new Email();
        Email email2 = new Email();
        List<Email> emails = List.of(email1, email2);

        when(repositoryMail.findAll()).thenReturn(emails);

        List<Email> result = mailService.getAllEmails();

        assertEquals(emails, result);
        verify(repositoryMail).findAll();
    }
}