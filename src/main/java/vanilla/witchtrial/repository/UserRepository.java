package vanilla.witchtrial.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vanilla.witchtrial.domain.UserAccount;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserAccount, Long> {
    Optional<UserAccount> findByEmail(String email);
}
