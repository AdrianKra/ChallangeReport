package blossom.reports_service.model;

import java.util.Optional;

public interface UserRepository {

  // find
  Optional<User> findById(Long id);

  Optional<User> findByName(String name);

  Optional<User> findByActivity(Activity activity);

  // delete
  void deleteById(Long id);

  void deleteByName(String name);

  // save
  void save(User user);

  void saveAll(User user);
}