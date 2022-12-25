package vanilla.witchtrial.service;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.annotation.Transactional;
import vanilla.witchtrial.config.TestSecurityConfig;
import vanilla.witchtrial.domain.Post;
import vanilla.witchtrial.domain.UserAccount;
import vanilla.witchtrial.dto.BoardDto;
import vanilla.witchtrial.dto.PostDto;
import vanilla.witchtrial.dto.UserPrincipal;
import vanilla.witchtrial.dto.type.BoardSearchType;
import vanilla.witchtrial.repository.PostRepository;
import vanilla.witchtrial.repository.UserRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.when;

@Transactional
@DisplayName("비즈니스 로직 - 게시판")
@Import(TestSecurityConfig.class)
@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    public static final Long POST_ID = 1L;
    public static final Long USER_ID = 1L;

    @InjectMocks private PostServiceImpl sut; // System under test
    @Mock private PostRepository postRepository;
    @Mock private UserRepository userRepository;
    @Mock private TestSecurityConfig testUserPrinciple;


    @DisplayName("게시글을 검색하면, 게시글 리스트를 반환한다.")
    @Test
    void getBoardList() {
        // Given
        BoardDto.Request request = BoardDto.Request.builder()
                .searchType(BoardSearchType.TITLE.name())
                .searchValue("search keyword")
                .build();
        PageRequest pageable = PageRequest.of(0, 10);

        Page mock = Mockito.mock(Page.class);
        Page sutMock = Mockito.mock(Page.class);
        when(postRepository.findBoardList(request, pageable)).thenReturn(mock);
        when(sut.getBoardList(request, pageable)).thenReturn(sutMock);

        // When
        Page<BoardDto.Response> boardList = sut.getBoardList(request, pageable);

        // Then
        assertThat(boardList).isNotNull();
    }

    @DisplayName("게시글을 조회하면, 게시글을 반환한다.")
    @Test
    void getPostDetailWithPostId() {
        // Given

        long postId = POST_ID;
        Post post = createPost(postId);
        given(postRepository.findByIdWithDsl(postId)).willReturn(Optional.of(post));

        // When
        sut.getPostDetail(postId);

        // Then
        then(postRepository).should().findByIdWithDsl(postId);
    }

    @DisplayName("게시글 정보를 입력하면, 게시글을 생성한다.")
    @Test
    void saveNewPost() {
        // Given
        UserPrincipal userPrincipal = new UserPrincipal(USER_ID, "vanille@gmail.com", "pw", "vanille", List.of());
        when(userRepository.getReferenceById(USER_ID)).thenReturn(any(UserAccount.class));
        PostDto.Request postDto = PostDto.Request.builder()
                .title("title")
                .content("content")
                .contentRaw("content-raw")
                /*.hashtag("#test")*/
                .postType("TRIAL")
                .createdBy("vanille")
                .userPrincipal(userPrincipal)
                .build();

        sut.saveNewPost(postDto);

        then(postRepository).should().save(any(Post.class));
    }

    @DisplayName("게시글 정보를 수정하면, 게시글을 업데이트한다.")
    @Test
    void updatePost() throws IllegalAccessException {
        // Given
        Long postId = POST_ID;
        Post post = createPost(postId);

        UserAccount userAccount = getUserAccount(USER_ID);
        PostDto.UpdateRequest postDto = PostDto.UpdateRequest.builder()
                .postId(postId)
                .title("change-title")
                .content("change-content")
                .contentRaw("content-raw")
                .userPrincipal(userAccount.from())
                .build();

        when(postRepository.getReferenceById(postId)).thenReturn(post);

        sut.updatePost(postDto);

        assertThat(post)
                .hasFieldOrPropertyWithValue("id", postId)
                .hasFieldOrPropertyWithValue("title", postDto.getTitle())
                .hasFieldOrPropertyWithValue("content", postDto.getContent())
                .hasFieldOrPropertyWithValue("contentRaw", postDto.getContentRaw());
//                .hasFieldOrPropertyWithValue("hashtag", postDto.getHashtag());
    }

    @DisplayName("작성자가 아닌 사람이 수정을 요청하면 RuntimeException이 발생한다.")
    @Test
    void updatePostFail() {
        // Given
        Long postId = POST_ID;
        Post post = createPost(postId);

        UserAccount userAccount = getUserAccount(2L);
        PostDto.UpdateRequest postDto = PostDto.UpdateRequest.builder()
                .postId(postId)
                .title("change-title")
                .content("change-content")
                .userPrincipal(userAccount.from())
                .build();

        when(postRepository.getReferenceById(postId)).thenReturn(post);
        Assertions.assertThrows(IllegalAccessException.class, () -> sut.updatePost(postDto));
    }

    @DisplayName("없는 게시글 ID가 넘어오면, 게시글을 업데이트 실패한다.")
    @Test
    void updatePostFailWithWrongId() {
        // when
        UserAccount userAccount = getUserAccount(USER_ID);
        PostDto.UpdateRequest postDto = PostDto.UpdateRequest.builder()
                .postId(-1L)
                .title("change-title")
                .content("change-content")
                .userPrincipal(userAccount.from())
                .build();
        when(postRepository.getReferenceById(-1L)).thenThrow(EntityNotFoundException.class);

        // then
        Assertions.assertThrows(EntityNotFoundException.class, () -> sut.updatePost(postDto));
    }


    @DisplayName("게시글 ID를 입력하면, 게시글을 삭제한다.")
    @Test
    void deletePost() throws IllegalAccessException {
        Post post = createPost(POST_ID);
        given(postRepository.getReferenceById(POST_ID)).willReturn(post);

        sut.deletePost(POST_ID, getUserAccount(USER_ID).from());

        then(postRepository).should().delete(any(Post.class));
    }

    @DisplayName("잘못된 게시글 ID를 입력하면, 게시글을 삭제 실패한다.")
    @Test
    void deletePostFailWithWrongId() {
        long wrongPostId = -1L;
        when(postRepository.getReferenceById(wrongPostId)).thenThrow(EntityNotFoundException.class);
        Assertions.assertThrows(EntityNotFoundException.class, () -> sut.deletePost(wrongPostId, getUserAccount(USER_ID).from()));
    }

    @DisplayName("게시글 작성자의 요청이 아니면, 게시글을 삭제 실패한다.")
    @Test
    void deletePostFailWithWrongUser() {
        Post post = createPost(POST_ID);
        when(postRepository.getReferenceById(POST_ID)).thenReturn(post);
        Assertions.assertThrows(IllegalAccessException.class, () -> sut.deletePost(POST_ID, getUserAccount(-1L).from()));
    }

    private Post createPost(Long id) {
        UserAccount user = getUserAccount(USER_ID);

        Post post = Post.of(
                "title",
                "content",
                "content-raw",
                "#java",
                "TRIAL",
                user
        );
        ReflectionTestUtils.setField(post, "id", id);

        return post;
    }

    private static UserAccount getUserAccount(Long id) {
        UserAccount user = UserAccount.of(
                "vanille@gmail.com",
                "vanille",
                "pw"
        );

        ReflectionTestUtils.setField(user, "id", id);
        return user;
    }


}