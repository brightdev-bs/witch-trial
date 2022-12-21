package vanilla.witchtrial.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vanilla.witchtrial.domain.Post;
import vanilla.witchtrial.dto.BoardDto;

import java.util.Optional;

public interface PostRepositoryCustom {
    Page<Post> findBoardList(BoardDto.Request request, Pageable pageable);
    long countPosts(BoardDto.Request request);

    Optional<Post> findByIdWithDsl(Long id);
}
