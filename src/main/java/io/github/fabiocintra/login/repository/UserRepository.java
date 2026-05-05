package io.github.fabiocintra.login.repository;

import io.github.fabiocintra.login.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {

    boolean existsByUsername(String username);
    User findByUsername(String username);

}
