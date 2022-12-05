package vanilla.witchtrial.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vanilla.witchtrial.domain.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
}