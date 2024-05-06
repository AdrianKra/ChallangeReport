package blossom.reports_service.model;

import java.util.Optional;

public interface ActivityReportRepository {

  // find
  ActivityReport findById(Long id);

  ActivityReport findByName(String name);

  Optional<ActivityReport> findByActivity(Activity activity);

  // delete
  void deleteById(Long id);

  void deleteByName(String name);

  // save
  void save(ActivityReport activityReport);

  void saveAll(ActivityReport activityReport);

  // update
  void update(ActivityReport activityReport);

  void updateAll(ActivityReport activityReport);

}
