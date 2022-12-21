package vanilla.witchtrial.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vanilla.witchtrial.dto.BoardDto;
import vanilla.witchtrial.dto.PostDto;

public interface PostService {

    Page<BoardDto.Response> getBoardList(BoardDto.Request request, Pageable pageable);

    long countPosts(BoardDto.Request request);

    PostDto.Response getPostDetail(Long postId);

    PostDto.Response saveNewPost(PostDto.Request postDto);

    PostDto.Response updatePost(PostDto.UpdateRequest postDto);

    void deletePost(Long id);
}
