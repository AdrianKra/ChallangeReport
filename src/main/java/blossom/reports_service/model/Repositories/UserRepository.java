package blossom.reports_service.model.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import blossom.reports_service.model.Entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  // find
  Optional<User> findById(Long id);

  Optional<User> findByEmail(String email);

  // delete
  void deleteById(Long id);

  // save
  User save(User user);
}