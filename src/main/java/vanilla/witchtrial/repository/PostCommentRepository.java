package vanilla.witchtrial.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import vanilla.witchtrial.domain.PostComment;

@RepositoryRestResource
public interface PostCommentRepository extends JpaRepository<PostComment, Long> {
    
}