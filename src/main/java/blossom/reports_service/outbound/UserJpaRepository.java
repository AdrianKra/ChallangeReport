package blossom.reports_service.outbound;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import blossom.reports_service.model.Entities.User;
import blossom.reports_service.model.Repositories.UserRepository;

import java.util.Optional;

@Repository
public interface UserJpaRepository extends UserRepository, CrudRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE u.email = :email")
    Optional<User> findByEmail(@Param("email") String email);
}
