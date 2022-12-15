package vanilla.witchtrial.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vanilla.witchtrial.domain.Post;
import vanilla.witchtrial.domain.dto.BoardDto;
import vanilla.witchtrial.domain.dto.PostDto;
import vanilla.witchtrial.domain.dto.type.PostType;
import vanilla.witchtrial.global.common.ErrorCode;
import vanilla.witchtrial.global.exception.NotFoundException;
import vanilla.witchtrial.repository.PostRepository;

import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    @Override
    public List<BoardDto.Response> getBoardList(BoardDto.Request request, Pageable pageable) {
        return postRepository.findBoardList(request, pageable).stream().map(BoardDto.Response::from).toList();
    }

    @Override
    public PostDto.Response getPostDetail(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.POST_NOT_FOUND));
        return PostDto.Response.toResponse(post);
    }


    @Transactional
    @Override
    public void saveNewPost(PostDto.Request postDto) {
        Post post = Post.of(postDto.getTitle(), postDto.getContent()
                , postDto.getHashtag(), Enum.valueOf(PostType.class, postDto.getPostType()));
        postRepository.save(post);
    }

    // Todo : 제대로 업데이트 되는지 테스트 작성할 것.
    @Transactional
    @Override
    public void updatePost(PostDto.UpdateRequest postDto) {
        Post post = postRepository.findById(postDto.getPostId()).orElseThrow(() -> new NotFoundException(ErrorCode.POST_NOT_FOUND));
        post.updatePost(postDto);
    }

    @Transactional
    @Override
    public void deletePost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new NotFoundException(ErrorCode.POST_NOT_FOUND));
        postRepository.delete(post);
    }
}
