package vanilla.witchtrial.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import vanilla.witchtrial.config.TestSecurityConfig;
import vanilla.witchtrial.dto.PostCommentDto;
import vanilla.witchtrial.service.PostCommentServiceImpl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("View 컨트롤러 - 댓글")
@Import({TestSecurityConfig.class})
@WebMvcTest(PostCommentController.class)
class PostCommentControllerTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;
    @MockBean private PostCommentServiceImpl postCommentService;

    @DisplayName("[view][GET] 댓글 등록 - 미인증 사용자")
    @Test
    void saveNewPostCommentWithAnonymous() throws Exception {
        // given
        Long postId = 1L;
        PostCommentDto.Request request = PostCommentDto.Request.builder()
                .content("content")
                .build();

        // when & then
        mockMvc.perform(post("/board/" + postId + "/comments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .with(csrf())
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/login"));

    }

    @WithMockUser
    @DisplayName("[view][GET] 댓글 등록 - 인증된 사용자")
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
                        .with(csrf())
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/board/" + postId))
                .andExpect(redirectedUrl("/board/" + postId));

        then(postCommentService).should().savePostComment(any(PostCommentDto.Request.class));
    }

    @DisplayName("[view][GET] 댓글 삭제 - 인증된 사용자")
    @Test
    void deleteCommentWithAnonymous() throws Exception {
        // given
        Long postId = 1L;
        Long commentId = 1L;

        // when & then
        mockMvc.perform(post("/board/" + postId + "/comments/" + commentId).with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/login"));
    }


    @WithMockUser
    @DisplayName("[view][GET] 댓글 삭제 - 인증된 사용자")
    @Test
    void deleteComment() throws Exception {
        // given
        Long postId = 1L;
        Long commentId = 1L;

        willDoNothing().given(postCommentService).deleteComment(commentId);

        // when & then
        mockMvc.perform(post("/board/" + postId + "/comments/" + commentId).with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/board/" + postId))
                .andExpect(redirectedUrl("/board/" + postId));

        then(postCommentService).should().deleteComment(commentId);
    }

}