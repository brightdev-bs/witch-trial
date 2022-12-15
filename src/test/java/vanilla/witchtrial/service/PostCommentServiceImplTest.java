package vanilla.witchtrial.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import vanilla.witchtrial.domain.dto.PostCommentDto;
import vanilla.witchtrial.repository.PostCommentRepository;
import vanilla.witchtrial.repository.PostRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
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

    @DisplayName("게시글 ID를 통해 댓글을 조회한다.")
    @Test
    void getCommentsWithPostId() {
        Long postId = 1L;
        List<PostCommentDto.Response> postComments = sut.getPostComments(1L);

        assertThat(postComments).isNotNull();
        then(postRepository).should().findById(postId);
    }

    @DisplayName("댓글 정보를 입력하면, 댓글을 저장한다.")
    @Test
    void savePostComment() {
        Long postId = 1L;
        List<PostCommentDto.Response> postComments = sut.getPostComments(1L);

        assertThat(postComments).isNotNull();
        then(postRepository).should().findById(postId);
    }

}