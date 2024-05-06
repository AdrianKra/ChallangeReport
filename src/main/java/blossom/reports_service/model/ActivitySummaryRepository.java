package blossom.reports_service.model;

import java.util.Optional;

public interface ActivitySummaryRepository {

  // find
  Optional<ActivitySummary> findById(Long id);

  Optional<ActivitySummary> findByActivity(Activity activity);

  Iterable<ActivitySummary> findAll();

  // delete
  void deleteById(Long id);

  // save
  ActivitySummary save(ActivitySummary activitySummary);

}
