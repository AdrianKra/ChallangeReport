package blossom.reports_service.model.Repositories;

import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import blossom.reports_service.model.Entities.Challenge;
import blossom.reports_service.model.Entities.ChallengeProgress;
import blossom.reports_service.model.Entities.User;
import blossom.reports_service.model.Enums.Visibility;


/**
 * This interface represents a repository for challenge progress entities in the
 * database.
 */
@Repository
public interface ChallengeProgressRepository extends JpaRepository<ChallengeProgress, Long> {

    ChallengeProgress save(@NonNull ChallengeProgress challenge);

    void deleteById(@NonNull Long id);

    ChallengeProgress findOneById(Long id);

    List<ChallengeProgress> findAll();

    List<ChallengeProgress> findAllByUser(User user);

    List<ChallengeProgress> findAllByChallenge(Challenge challenge);

    List<ChallengeProgress> findAllByProgressVisibility(Visibility visibility);

}
