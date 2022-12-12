package vanilla.witchtrial.service;

import org.springframework.data.domain.Page;
import vanilla.witchtrial.domain.dto.BoardDto;
import vanilla.witchtrial.domain.dto.PostDto;

public interface PostService {

    Page<BoardDto.Response> getBoardList(BoardDto.Request request);

    BoardDto.Response getPostDetail(Long postId);

    void saveNewPost(PostDto.Request postDto);

    void updatePost(PostDto.UpdateRequest postDto);

    void deletePost(Long id);
}
