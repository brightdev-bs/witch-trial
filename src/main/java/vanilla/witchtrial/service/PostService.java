package vanilla.witchtrial.service;

import vanilla.witchtrial.dto.BoardDto;
import vanilla.witchtrial.dto.PostDto;

import java.util.List;

public interface PostService {

    List<BoardDto.Response> getBoardList(BoardDto.Request request);

    long countPosts(BoardDto.Request request);

    PostDto.Response getPostDetail(Long postId);

    PostDto.Response saveNewPost(PostDto.Request postDto);

    PostDto.Response updatePost(PostDto.UpdateRequest postDto);

    void deletePost(Long id);
}
