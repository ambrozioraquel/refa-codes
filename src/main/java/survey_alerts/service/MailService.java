package survey_alerts.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import survey_alerts.domain.Email;
import survey_alerts.repository.RepositoryMail;

import java.util.List;

@Service
public class MailService {

    RepositoryMail repositoryMail;

    @Autowired
    public MailService(RepositoryMail repositoryMail) {
        this.repositoryMail = repositoryMail;
    }

    public void send(Email email) {
        repositoryMail.save(email);
    }

    public List<Email> getAllEmails() {
        return repositoryMail.findAll();
    }
}
