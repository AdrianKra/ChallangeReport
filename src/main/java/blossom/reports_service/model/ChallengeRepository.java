package blossom.reports_service.model;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChallengeRepository extends JpaRepository<Challenge, Long> {

  // find
  Optional<Challenge> findById(Long id);

  Optional<Challenge> findByName(String name);

  // delete
  void deleteById(Long id);

  void deleteByName(String name);

  // save
  Challenge save(Challenge challenge);
}
