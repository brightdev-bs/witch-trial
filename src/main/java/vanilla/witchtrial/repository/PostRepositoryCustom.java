package vanilla.witchtrial.repository;

import org.springframework.data.domain.Pageable;
import vanilla.witchtrial.domain.Post;
import vanilla.witchtrial.domain.dto.BoardDto;

import java.util.List;
import java.util.Optional;

public interface PostRepositoryCustom {
    List<Post> findBoardList(BoardDto.Request request, Pageable pageable);

    Optional<Post> findByIdWithDsl(Long id);
}
