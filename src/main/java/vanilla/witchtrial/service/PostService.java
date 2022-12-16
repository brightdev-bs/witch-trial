package vanilla.witchtrial.service;

import org.springframework.data.domain.Pageable;
import vanilla.witchtrial.domain.dto.BoardDto;
import vanilla.witchtrial.domain.dto.PostDto;

import java.util.List;

public interface PostService {

    List<BoardDto.Response> getBoardList(BoardDto.Request request, Pageable pageable);

    PostDto.Response getPostDetail(Long postId);

    PostDto.Response saveNewPost(PostDto.Request postDto);

    PostDto.Response updatePost(PostDto.UpdateRequest postDto);

    void deletePost(Long id);
}
