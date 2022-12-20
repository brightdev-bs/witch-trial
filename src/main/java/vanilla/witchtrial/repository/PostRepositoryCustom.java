package vanilla.witchtrial.repository;

import vanilla.witchtrial.domain.Post;
import vanilla.witchtrial.dto.BoardDto;

import java.util.List;
import java.util.Optional;

public interface PostRepositoryCustom {
    List<Post> findBoardList(BoardDto.Request request);
    long countPosts(BoardDto.Request request);

    Optional<Post> findByIdWithDsl(Long id);
}
