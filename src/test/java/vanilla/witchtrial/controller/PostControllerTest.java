package vanilla.witchtrial.controller;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import vanilla.witchtrial.config.TestSecurityConfig;
import vanilla.witchtrial.domain.Post;
import vanilla.witchtrial.domain.UserAccount;
import vanilla.witchtrial.dto.BoardDto;
import vanilla.witchtrial.dto.PostDto;
import vanilla.witchtrial.service.PostService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("View 컨트롤러 - 게시글")
@Import(TestSecurityConfig.class)
@WebMvcTest(PostController.class)
class PostControllerTest {

    public static final Long POST_ID = 1L;
    public static final Long USER_ID = 1L;
    @Autowired private MockMvc mockMvc;
    @MockBean private PostService postService;


    @DisplayName("[view][GET] 게시판 리스트")
    @Test
    void getBoardListView() throws Exception {

        Page mock = Mockito.mock(Page.class);
        when(postService.getBoardList(any(BoardDto.Request.class), any(Pageable.class))).thenReturn(mock);

        mockMvc.perform(get("/board").with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(model().attributeExists("boardList"))
                .andExpect(view().name("board/index"));

        then(postService).should().getBoardList(any(BoardDto.Request.class), any(Pageable.class));
    }

    @DisplayName("[view][GET] 게시글 등록 페이지 - 인증X")
    @Test
    void getPostFormFailAndRedirection() throws Exception {
        mockMvc.perform(get("/board/postForm")
                        .with(csrf())
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/login"))
                .andDo(print());

    }

    @WithMockUser(value = "vanille", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @DisplayName("[view][GET] 게시글 등록 페이지 - 인증")
    @Test
    void getPostForm() throws Exception {
        mockMvc.perform(get("/board/postForm")
                        .with(csrf())
                )
                .andExpect(status().isOk())
                .andExpect(view().name("board/postForm"))
                .andExpect(model().attributeExists("postTypes"))
                .andDo(print());
    }

    @WithMockUser(value = "vanille", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @DisplayName("[POST] 게시글 등록 - 성공")
    @Test
    void saveNewPost() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("title", "제목");
        params.add("postType", "trial");
        params.add("content", "더미 본문");
        params.add("contentRaw", "더미 본문 raw");

        mockMvc.perform(post("/board/postForm")
                        .params(params)
                        .with(csrf())
                )
                .andExpect(status().isOk())
                .andDo(print());
    }

    @WithMockUser(value = "vanille", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @DisplayName("[POST] 게시글 등록 - 실패")
    @Test
    void saveNewPostFail() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("title", "      ");
        params.add("postType", "trial");
        params.add("content", "더미 본문");

        MvcResult mvcResult = mockMvc.perform(post("/board/postForm")
                        .params(params)
                        .with(csrf())
                )
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andReturn();

        String responseBody = mvcResult.getResponse().getContentAsString();
        Assertions.assertThat(responseBody.contains("title")).isTrue();
    }

    @DisplayName("[view][GET] 게시글 상세 페이지")
    @Test
    void getPostDetailView() throws Exception {
        Long postId = 1L;
        given(postService.getPostDetail(postId)).willReturn(
                PostDto.Response.from(Post.of("title", "content", "content-raw", "TRIAL", getUserAccount(USER_ID)))
        );

        mockMvc.perform(get("/board/" + postId).with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("board/postDetail"))
                .andExpect(model().attributeExists("postDetail"))
                .andDo(print());

        then(postService).should().getPostDetail(postId);
    }

    @DisplayName("[view][GET] 게시글 수정 페이지 - 인증X")
    @Test
    void editFormFailAndRedirection() throws Exception {
        Long postId = 1L;
        mockMvc.perform(get("/board/postForm/" + postId).with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/login"))
                .andDo(print());
    }

    @WithMockUser
    @DisplayName("[POST] 게시글 수정 페이지 - 인증")
    @Test
    void editForm() throws Exception {
        Long postId = 1L;
        mockMvc.perform(get("/board/postForm/" + postId).with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("board/postForm"))
                .andDo(print());
    }

    @WithMockUser(value = "vanille", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @DisplayName("[POST] 게시글 수정 - 실패")
    @Test
    void editPostFail() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("title", "      ");
        params.add("postType", "trial");
        params.add("content", "더미 본문");

        MvcResult mvcResult = mockMvc.perform(post("/board/postForm")
                        .params(params)
                        .with(csrf())
                )
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andReturn();

        String responseBody = mvcResult.getResponse().getContentAsString();
        Assertions.assertThat(responseBody.contains("title")).isTrue();
    }

    @DisplayName("[POST] 게시글 삭제 - 인증X")
    @Test
    void deletePostFail() throws Exception {
        mockMvc.perform(post("/board/delete/" + POST_ID).with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/login"))
                .andDo(print());
    }

    private Post createPost(Long id) {
        UserAccount user = getUserAccount(USER_ID);

        Post post = Post.of(
                "title",
                "content",
                "content-raw",
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