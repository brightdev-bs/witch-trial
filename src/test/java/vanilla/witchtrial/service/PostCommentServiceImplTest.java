package vanilla.witchtrial.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import vanilla.witchtrial.domain.PostComment;
import vanilla.witchtrial.domain.dto.PostCommentDto;
import vanilla.witchtrial.repository.PostCommentRepository;

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