package blossom.reports_service.model;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {

  // find
  Optional<Activity> findById(Long id);

  Optional<Activity> findByName(String name);

  // delete
  void deleteById(Long id);

  void deleteByName(String name);

  // save
  Activity save(Activity activity);
}
