package blossom.reports_service.model;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityReportRepository extends JpaRepository<ActivityReport, Long> {

  // find
  Optional<ActivityReport> findById(Long id);

  Optional<ActivityReport> findByActivity(Activity activity);

  Iterable<ActivityReport> findAllByStatus(Enum status);

  Iterable<ActivityReport> findAllByUser(User user);

  // delete
  void deleteById(Long id);

  void deleteByName(String name);

  // save
  ActivityReport save(ActivityReport activityReport);

  Iterable<ActivityReport> findAllByUserId(Long userId);

  boolean existsByActivityIdAndUserId(Long activityId, Long userId);

}
