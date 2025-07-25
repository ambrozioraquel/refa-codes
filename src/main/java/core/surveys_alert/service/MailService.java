package core.surveys_alert.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import core.surveys_alert.domain.Email;
import core.surveys_alert.repository.RepositoryMail;

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
