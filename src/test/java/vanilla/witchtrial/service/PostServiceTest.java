package vanilla.witchtrial.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.annotation.Transactional;
import vanilla.witchtrial.domain.Post;
import vanilla.witchtrial.dto.BoardDto;
import vanilla.witchtrial.dto.PostDto;
import vanilla.witchtrial.dto.type.BoardSearchType;
import vanilla.witchtrial.global.exception.NotFoundException;
import vanilla.witchtrial.repository.PostRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.when;

@Transactional
@DisplayName("비즈니스 로직 - 게시판")
@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @InjectMocks private PostServiceImpl sut; // System under test
    @Mock private PostRepository postRepository;

    @DisplayName("게시글을 검색하면, 게시글 리스트를 반환한다.")
    @Test
    void getBoardList() {
        // Given
        BoardDto.Request request = BoardDto.Request.builder()
                .searchType(BoardSearchType.TITLE.name())
                .searchValue("search keyword")
                .page(0)
                .size(10)
                .build();

        // When
        List<BoardDto.Response> boardList = sut.getBoardList(request);

        // Then
        assertThat(boardList).isNotNull();

    }

    @DisplayName("게시글을 조회하면, 게시글을 반환한다.")
    @Test
    void getPostDetailWithPostId() {
        // Given
        Post post = Post.of("테스트", "본문", "#java", "TRIAL");
        given(postRepository.findByIdWithDsl(1L)).willReturn(Optional.of(post));

        // When
        sut.getPostDetail(1L);

        // Then
        then(postRepository).should().findByIdWithDsl(1L);
    }

    @DisplayName("게시글 정보를 입력하면, 게시글을 생성한다.")
    @Test
    void saveNewPost() {
        // Given
        PostDto.Request postDto = PostDto.Request.builder()
                .title("title")
                .content("content")
                .hashtag("#test")
                .postType("TRIAL")
                .createdBy("vanille")
                .build();

        sut.saveNewPost(postDto);

        then(postRepository).should().save(any(Post.class));
    }

    @DisplayName("게시글 정보를 수정하면, 게시글을 업데이트한다.")
    @Test
    void updatePost() {
        // Given
        Long postId = 1L;
        Post post = createPost(postId);
        PostDto.UpdateRequest postDto = PostDto.UpdateRequest.builder()
                .postId(postId)
                .title("change-title")
                .content("change-content")
                .hashtag("#test")
                .build();

        when(postRepository.findByIdWithDsl(postId)).thenReturn(Optional.of(post));

        sut.updatePost(postDto);

        assertThat(post)
                .hasFieldOrPropertyWithValue("id", postId)
                .hasFieldOrPropertyWithValue("title", postDto.getTitle())
                .hasFieldOrPropertyWithValue("content", postDto.getContent())
                .hasFieldOrPropertyWithValue("hashtag", postDto.getHashtag());
    }

    @DisplayName("없는 게시글 ID가 넘어오면, 게시글을 업데이트 실패한다.")
    @Test
    void updatePostFailWithWrongId() {
        // Given
        PostDto.UpdateRequest postDto = PostDto.UpdateRequest.builder()
                .postId(-1L)
                .title("change-title")
                .content("change-content")
                .hashtag("#test")
                .build();

        Assertions.assertThrows(NotFoundException.class, () -> sut.updatePost(postDto));
    }


    @DisplayName("게시글 ID를 입력하면, 게시글을 삭제한다.")
    @Test
    void deletePost() {
        Long postId = 1L;
        Post post = createPost(postId);
        when(postRepository.findByIdWithDsl(postId)).thenReturn(Optional.of(post));

        sut.deletePost(1L);

        then(postRepository).should().delete(any(Post.class));
    }

    @DisplayName("잘못된 게시글 ID를 입력하면, 게시글을 삭제 실패한다.")
    @Test
    void deletePostFailWithWrongId() {
        Long postId = -1L;
        Assertions.assertThrows(NotFoundException.class, () -> sut.deletePost(postId));
    }

    private Post createPost(Long id) {
        Post post = Post.of(
                "title",
                "content",
                "#java",
                "TRIAL"
        );
        ReflectionTestUtils.setField(post, "id", id);

        return post;
    }


}