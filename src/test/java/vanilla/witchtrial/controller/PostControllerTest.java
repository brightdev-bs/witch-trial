package vanilla.witchtrial.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import vanilla.witchtrial.config.SecurityConfig;
import vanilla.witchtrial.domain.Post;
import vanilla.witchtrial.domain.dto.BoardDto;
import vanilla.witchtrial.domain.dto.PostDto;
import vanilla.witchtrial.service.PostService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("View 컨트롤러 - 게시글")
@Import(SecurityConfig.class)
@WebMvcTest(PostController.class)
class PostControllerTest {

    @Autowired private MockMvc mockMvc;

    @MockBean private PostService postService;

    @Autowired private ObjectMapper objectMapper;

    @DisplayName("[view][GET] 게시판 리스트")
    @Test
    void getBoardListView() throws Exception {
        // given
        String request = objectMapper.writeValueAsString(BoardDto.Request.builder().build());

        mockMvc.perform(get("/board")
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("board/index"))
                .andExpect(model().attributeExists("boardList"));

        then(postService).should().getBoardList(any(BoardDto.Request.class));
    }

    @DisplayName("[view][GET] 게시글 상세 페이지")
    @Test
    void getPostDetailView() throws Exception {
        Long postId = 1L;
        given(postService.getPostDetail(postId)).willReturn(
                PostDto.Response.from(Post.of("title", "content", "#java", "TRIAL"))
        );

        mockMvc.perform(get("/board/" + postId))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("board/postDetail"))
                .andExpect(model().attributeExists("postDetail"))
                .andDo(print());

        then(postService).should().getPostDetail(postId);
    }

}