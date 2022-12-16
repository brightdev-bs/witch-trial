package vanilla.witchtrial.repository;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import vanilla.witchtrial.domain.Post;

import static vanilla.witchtrial.global.common.constants.ErrorCode.POST_NOT_FOUND;

@DataJpaTest
class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    @DisplayName("게시글 단건 조회 QueryDsl 실패 테스트")
    @Test
    void findByIdWithDslException() {
        Assertions.assertThrows(EntityNotFoundException.class,
                () -> postRepository.findByIdWithDsl(-1L).orElseThrow(() -> new EntityNotFoundException(POST_NOT_FOUND.toString())));

    }

    @DisplayName("게시글 단건 조회 QueryDsl 성공 테스트")
    @Test
    void findByIdWithDslSucess() {
        Post post = postRepository.findByIdWithDsl(1L).orElseThrow(() -> new EntityNotFoundException(POST_NOT_FOUND.toString()));
        Assertions.assertEquals(2, post.getPostComments().size());
    }

}