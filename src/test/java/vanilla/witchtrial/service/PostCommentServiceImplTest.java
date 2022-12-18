package vanilla.witchtrial.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import vanilla.witchtrial.domain.Post;
import vanilla.witchtrial.domain.PostComment;
import vanilla.witchtrial.domain.dto.PostCommentDto;
import vanilla.witchtrial.repository.PostCommentRepository;
import vanilla.witchtrial.repository.PostRepository;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@DisplayName("비즈니스 로직 - 댓글")
@ExtendWith(MockitoExtension.class)
class PostCommentServiceImplTest {

    @InjectMocks
    private PostCommentServiceImpl sut;

    @Mock
    private PostCommentRepository postCommentRepository;
    @Mock
    private PostRepository postRepository;

    @DisplayName("게시글 ID로 조회하면, 해당 댓글 리스트를 조회한다.")
    @Test
    void getPostComments() {
        // given
        Long postId = 1L;
        Optional<Post> of = Optional.of(Post.of("title", "content", "#java", "TRIAL"));
        given(postRepository.findById(postId)).willReturn(of);

        // when
        List<PostCommentDto.Response> postComments = sut.getPostComments(postId);

        // then
        Assertions.assertThat(postComments).isNotNull();
        then(postRepository).should().findById(postId);
    }

    @DisplayName("게시글 정보를 입력하면, 댓글을 저장한다.")
    @Test
    void savePostComment() {
        // given
        given(postCommentRepository.save(any(PostComment.class))).willReturn(null);

        // when
        sut.savePostComment(PostCommentDto.Request.builder().content("content").build());

        // then
        then(postCommentRepository).should().save(any(PostComment.class));
    }

}