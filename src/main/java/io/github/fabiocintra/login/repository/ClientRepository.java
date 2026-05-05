package io.github.fabiocintra.login.repository;

import io.github.fabiocintra.login.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {

    boolean existsByClientId(String clientId);

    Optional<Client> findByClientId(String clientId);
}
