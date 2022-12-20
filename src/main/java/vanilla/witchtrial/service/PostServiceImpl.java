package vanilla.witchtrial.service;

import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vanilla.witchtrial.domain.Post;
import vanilla.witchtrial.dto.BoardDto;
import vanilla.witchtrial.dto.PostDto;
import vanilla.witchtrial.global.common.constants.ErrorCode;
import vanilla.witchtrial.global.exception.NotFoundException;
import vanilla.witchtrial.repository.PostRepository;

import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    @Override
    public List<BoardDto.Response> getBoardList(BoardDto.Request request) {
        return postRepository.findBoardList(request).stream().map(BoardDto.Response::from).toList();
    }

    @Override
    public long countPosts(BoardDto.Request request) {
        return postRepository.countPosts(request);
    }

    @Override
    public PostDto.Response getPostDetail(Long postId) {
        Post post = postRepository.findByIdWithDsl(postId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.POST_NOT_FOUND));
        return PostDto.Response.from(post);
    }


    @Transactional
    @Override
    public PostDto.Response saveNewPost(PostDto.Request postDto) {
        Post post = PostDto.Request.toEntity(postDto);
        postRepository.save(post);

        return PostDto.Response.from(post);
    }

    @Transactional
    @Override
    public PostDto.Response updatePost(PostDto.UpdateRequest postDto) {
        Post post = postRepository.findByIdWithDsl(postDto.getPostId()).orElseThrow(() -> new NotFoundException(ErrorCode.POST_NOT_FOUND));
        if(!StringUtils.isBlank(postDto.getTitle())) {
            post.setTitle(postDto.getTitle());
        }
        if(!StringUtils.isBlank(postDto.getContent())) {
            post.setContent(postDto.getContent());
        }
        post.setHashtag(postDto.getHashtag());

        return PostDto.Response.from(post);
    }

    @Transactional
    @Override
    public void deletePost(Long id) {
        Post post = postRepository.findByIdWithDsl(id).orElseThrow(() -> new NotFoundException(ErrorCode.POST_NOT_FOUND));
        postRepository.delete(post);
    }
}
