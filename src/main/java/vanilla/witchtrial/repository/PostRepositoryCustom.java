package vanilla.witchtrial.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vanilla.witchtrial.domain.Post;
import vanilla.witchtrial.domain.dto.BoardDto;

import java.util.List;

public interface PostRepositoryCustom {
    List<Post> findBoardList(BoardDto.Request request, Pageable pageable);
}
