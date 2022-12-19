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
import vanilla.witchtrial.domain.dto.PostCommentDto;
import vanilla.witchtrial.service.PostCommentServiceImpl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("View 컨트롤러 - 댓글")
@Import({SecurityConfig.class})
@WebMvcTest(PostCommentController.class)
class PostCommentControllerTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;
    @MockBean private PostCommentServiceImpl postCommentService;

    @DisplayName("[view][GET] 댓글 등록")
    @Test
    void saveNewPostComment() throws Exception {
        // given
        Long postId = 1L;
        PostCommentDto.Request request = PostCommentDto.Request.builder()
                .content("content")
                .build();

        willDoNothing().given(postCommentService).savePostComment(any(PostCommentDto.Request.class));

        // when & then
        mockMvc.perform(post("/board/" + postId + "/comments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/board/" + postId))
                .andExpect(redirectedUrl("/board/" + postId));

        then(postCommentService).should().savePostComment(any(PostCommentDto.Request.class));

    }

    @DisplayName("[view][GET] 게시글 상세 페이지")
    @Test
    void getPostDetailView() throws Exception {
        // given
        Long postId = 1L;
        Long commentId = 1L;

        willDoNothing().given(postCommentService).deleteComment(commentId);

        // when & then
        mockMvc.perform(delete("/board/" + postId + "/comments/" + commentId))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/board/" + postId))
                .andExpect(redirectedUrl("/board/" + postId));

        then(postCommentService).should().deleteComment(commentId);
    }

}