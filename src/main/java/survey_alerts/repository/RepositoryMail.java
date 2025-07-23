package survey_alerts.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import survey_alerts.domain.Email;

@Repository
public interface RepositoryMail  extends JpaRepository<Email, Long> {


}
