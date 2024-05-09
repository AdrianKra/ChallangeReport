package blossom.reports_service.model;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivitySummaryRepository extends JpaRepository<ActivitySummary, Long> {

  // find
  Optional<ActivitySummary> findById(Long id);

  Optional<ActivitySummary> findByActivity(Activity activity);

  // delete
  void deleteById(Long id);

  // save
  ActivitySummary save(ActivitySummary activitySummary);

}
