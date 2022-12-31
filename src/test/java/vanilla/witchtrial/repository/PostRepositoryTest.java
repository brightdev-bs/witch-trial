package vanilla.witchtrial.repository;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import vanilla.witchtrial.domain.Post;
import vanilla.witchtrial.dto.BoardDto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static vanilla.witchtrial.global.common.constants.ErrorCode.POST_NOT_FOUND;

@DataJpaTest
class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    @DisplayName("게시판 조회 QueryDsl 테스트")
    @Test
    void findBoardList() {
        BoardDto.Request request = BoardDto.Request.builder().build();
        PageRequest pageRequest = PageRequest.of(0, 10);

        Page<Post> boardList = postRepository.findBoardList(request, pageRequest);
        assertEquals(10, boardList.getSize());
    }

    @DisplayName("게시글 단건 조회 QueryDsl 실패 테스트")
    @Test
    void findByIdWithDslException() {
        assertThrows(EntityNotFoundException.class,
                () -> postRepository.findByIdWithDsl(-1L).orElseThrow(() -> new EntityNotFoundException(POST_NOT_FOUND.toString())));

    }

    @DisplayName("게시글 단건 조회 QueryDsl 성공 테스트")
    @Test
    void findByIdWithDslSucess() {
        Post post = postRepository.findByIdWithDsl(1L).orElseThrow(() -> new EntityNotFoundException(POST_NOT_FOUND.toString()));
        assertEquals(1, post.getPostComments().size());
    }

}