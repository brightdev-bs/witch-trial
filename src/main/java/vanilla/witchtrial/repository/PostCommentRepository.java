package vanilla.witchtrial.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vanilla.witchtrial.domain.PostComment;

public interface PostCommentRepository extends JpaRepository<PostComment, Long> {
    
}