package vanilla.witchtrial.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import vanilla.witchtrial.domain.Post;
import vanilla.witchtrial.domain.dto.BoardDto;

import java.util.List;

@RepositoryRestResource
public interface PostRepository extends JpaRepository<Post, Long>, PostRepositoryCustom {
}