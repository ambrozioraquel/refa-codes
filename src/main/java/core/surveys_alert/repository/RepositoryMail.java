package core.surveys_alert.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import core.surveys_alert.domain.Email;

@Repository
public interface RepositoryMail  extends JpaRepository<Email, Long> {


}
