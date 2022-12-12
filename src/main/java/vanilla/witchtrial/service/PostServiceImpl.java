package vanilla.witchtrial.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vanilla.witchtrial.domain.dto.BoardDto;
import vanilla.witchtrial.domain.dto.PostDto;
import vanilla.witchtrial.repository.PostRepository;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    @Override
    public Page<BoardDto.Response> getBoardList(BoardDto.Request request) {
        return Page.empty();
    }

    @Override
    public BoardDto.Response getPostDetail(Long postId) {

        return BoardDto.Response.builder().build();
    }


    @Override
    public void saveNewPost(PostDto.Request postDto) {

    }

    @Override
    public void updatePost(PostDto.UpdateRequest postDto) {
    }

    @Override
    public void deletePost(Long id) {
    }
}
