package blossom.reports_service.model;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityReportRepository extends JpaRepository<ActivityReport, Long> {

  // find
  Optional<ActivityReport> findById(Long id);

  Optional<ActivityReport> findByName(String name);

  Optional<ActivityReport> findByActivity(Activity activity);

  // delete
  void deleteById(Long id);

  void deleteByName(String name);

  // save
  ActivityReport save(ActivityReport activityReport);

}
