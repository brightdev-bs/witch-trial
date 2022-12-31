package vanilla.witchtrial.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vanilla.witchtrial.domain.Hashtag;
import vanilla.witchtrial.domain.Post;
import vanilla.witchtrial.domain.UserAccount;
import vanilla.witchtrial.dto.BoardDto;
import vanilla.witchtrial.dto.PostDto;
import vanilla.witchtrial.dto.UserPrincipal;
import vanilla.witchtrial.global.common.constants.ErrorCode;
import vanilla.witchtrial.global.exception.NotFoundException;
import vanilla.witchtrial.repository.HashtagRepository;
import vanilla.witchtrial.repository.PostRepository;
import vanilla.witchtrial.repository.UserRepository;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final HashtagRepository hashtagRepository;
    private final UserRepository userRepository;

    @Override
    public Page<BoardDto.Response> getBoardList(BoardDto.Request request, Pageable pageable) {
        return postRepository.findBoardList(request, pageable).map(BoardDto.Response::from);
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
        UserAccount user = userRepository.getReferenceById(postDto.getUserPrincipal().id());
        Post post = PostDto.Request.toEntity(postDto, user);
        postRepository.save(post);

        Set<String> tags = postDto.getHashtags();
        Set<Hashtag> hashtags = new LinkedHashSet<>();
        for (String tag : tags) {
            hashtags.add(Hashtag.of(tag, post));
        }
        hashtagRepository.saveAll(hashtags);

        return PostDto.Response.from(post);
    }

    @Transactional
    @Override
    public PostDto.Response updatePost(PostDto.UpdateRequest postDto) throws EntityNotFoundException, IllegalAccessException {
        Post post = postRepository.findByIdWithDsl(postDto.getPostId()).orElseThrow(() -> new NotFoundException(ErrorCode.POST_NOT_FOUND));
        UserPrincipal userPrincipal = postDto.getUserPrincipal();

        if(post.getUser().getId().equals(userPrincipal.id())) {
            post.setTitle(postDto.getTitle());
            post.setContent(postDto.getContent());
            post.setContentRaw(postDto.getContentRaw());

            // 해시태그
            Set<Hashtag> hashtags = post.getHashtags();
            hashtagRepository.deleteAll(hashtags);

            Set<String> updateTags = postDto.getHashtags();
            Set<Hashtag> updateHashtags = new HashSet<>();
            for (String updateTag : updateTags) {
                Hashtag hashtag = Hashtag.of(updateTag, post);
                updateHashtags.add(hashtag);
            }
            hashtagRepository.saveAll(updateHashtags);
            post.setHashtags(updateHashtags);

            return PostDto.Response.from(post);
        } else {
            throw new IllegalAccessException(ErrorCode.ILLEGAL_CLIENT_REQUEST.getMessage());
        }
    }

    @Transactional
    @Override
    public void deletePost(Long id, UserPrincipal userPrincipal) throws IllegalAccessException {
        Post post = postRepository.getReferenceById(id);
        if (post.getUser().getId().equals(userPrincipal.id())) {
            postRepository.delete(post);
        } else {
            throw new IllegalAccessException(ErrorCode.ILLEGAL_CLIENT_REQUEST.getMessage());
        }
    }
}
