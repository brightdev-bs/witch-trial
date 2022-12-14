package vanilla.witchtrial.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import vanilla.witchtrial.domain.Post;

@RepositoryRestResource
public interface PostRepository extends JpaRepository<Post, Long>, PostRepositoryCustom {
}