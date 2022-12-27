package vanilla.witchtrial.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import vanilla.witchtrial.domain.Post;
import vanilla.witchtrial.domain.PostComment;
import vanilla.witchtrial.domain.UserAccount;
import vanilla.witchtrial.dto.PostCommentDto;
import vanilla.witchtrial.repository.PostCommentRepository;
import vanilla.witchtrial.repository.PostRepository;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;

@DisplayName("비즈니스 로직 - 댓글")
@ExtendWith(MockitoExtension.class)
class PostCommentServiceImplTest {

    @InjectMocks
    private PostCommentServiceImpl sut;

    @Mock
    private PostCommentRepository postCommentRepository;
    @Mock
    private PostRepository postRepository;

    @DisplayName("댓글을 입력하면, 댓글을 저장한다.")
    @Test
    void savePostComment()  {
        // given
        UserAccount mock = mock(UserAccount.class);
        given(postRepository.findByIdWithDsl(1L)).willReturn(Optional.of(Post.of("t", "c", "cr", "#t", "TRIAL", mock)));
        given(postCommentRepository.save(any(PostComment.class))).willReturn(null);

        // when
        sut.savePostComment(PostCommentDto.Request.builder().postId(1L).content("content").build());

        // then
        then(postCommentRepository).should().save(any(PostComment.class));
    }
}