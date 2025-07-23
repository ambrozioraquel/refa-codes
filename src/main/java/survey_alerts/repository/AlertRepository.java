package survey_alerts.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import survey_alerts.domain.Alert;

@Repository
public interface AlertRepository extends JpaRepository<Alert, Long> {
}
