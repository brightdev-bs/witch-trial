package vanilla.witchtrial.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import vanilla.witchtrial.domain.Post;
import vanilla.witchtrial.domain.dto.BoardDto;
import vanilla.witchtrial.domain.dto.PostDto;
import vanilla.witchtrial.domain.dto.type.SearchType;
import vanilla.witchtrial.repository.PostRepository;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;

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
                .searchType(SearchType.TITLE)
                .searchValue("search keyword")
                .build();

        // When
        Page<BoardDto.Response> boardList = sut.getBoardList(request);

        // Then
        assertThat(boardList).isNotNull();

    }

    @DisplayName("게시글을 조회하면, 게시글을 반환한다.")
    @Test
    void getPostDetailWithPostId() {
        // Given

        // When
        BoardDto.Response post = sut.getPostDetail(1L);

        // Then
        assertThat(post).isNotNull();
    }

    @DisplayName("게시글 정보를 입력하면, 게시글을 생성한다.")
    @Test
    void saveNewPost() {
        // Given
        PostDto.Request postDto = PostDto.Request.builder()
                .title("title")
                .content("content")
                .hashtag("#test")
                .createdAt(LocalDateTime.now())
                .createdBy("vanille")
                .build();

        sut.saveNewPost(postDto);

        then(postRepository).should().save(any(Post.class));
    }

    @DisplayName("게시글 정보를 입력하면, 게시글을 생성한다.")
    @Test
    void updatePost() {
        // Given
        PostDto.UpdateRequest postDto = PostDto.UpdateRequest.builder()
                .title("title")
                .content("content")
                .hashtag("#test")
                .build();

        sut.updatePost(postDto);

        then(postRepository).should().save(any(Post.class));
    }


    @DisplayName("게시글 ID를 입력하면, 게시글을 삭제한다.")
    @Test
    void deletePost() {
        sut.deletePost(1L);

        then(postRepository).should().delete(any(Post.class));
    }

}