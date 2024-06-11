package blossom.reports_service.model.Repositories;

import blossom.reports_service.model.Entities.User;

public interface UserRepository {

    User findByEmail(String email);

    User save(User user);

}
