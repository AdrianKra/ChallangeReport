package blossom.reports_service.model.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import blossom.reports_service.model.Entities.Challenge;
import blossom.reports_service.model.Entities.ChallengeReport;
import blossom.reports_service.model.Entities.ChallengeSummary;
import blossom.reports_service.model.Entities.User;

@Repository
public interface ChallengeReportRepository extends JpaRepository<ChallengeReport, Long> {

  // find
  Optional<ChallengeReport> findById(Long id);

  Optional<ChallengeReport> findByChallenge(Challenge challenge);

  Iterable<ChallengeReport> findAllByStatus(Enum status);

  // delete
  void deleteById(Long id);

  // save
  ChallengeReport save(ChallengeReport challengeReport);

  Iterable<ChallengeReport> findAllByUserId(Long userId);

  boolean existsByChallengeIdAndUserId(Long challengeId, Long userId);

  Iterable<ChallengeReport> findAllByUser(User user);

  Optional<ChallengeReport> findByChallengeIdAndUserId(Long challengeId, Long userId);

}
