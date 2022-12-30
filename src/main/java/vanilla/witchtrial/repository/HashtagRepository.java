package vanilla.witchtrial.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vanilla.witchtrial.domain.Hashtag;

public interface HashtagRepository extends JpaRepository<Hashtag, Long> {
}
