package blossom.reports_service.model.Repositories;

import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import blossom.reports_service.model.Entities.Challenge;
import blossom.reports_service.model.Enums.Visibility;

@Repository
public interface ChallengeRepository extends JpaRepository<Challenge, Long> {

    Optional<Challenge> findOneById(Long id);

    Challenge save(@NonNull Challenge challenge);

    void deleteById(@NonNull Long id);

    List<Challenge> findAll();

    List<Challenge> findAllByChallengeVisibility(Visibility visibility);

    List<Challenge> findAllByUserId(Long userId);

}
