package vanilla.witchtrial.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;
import vanilla.witchtrial.repository.UserRepository;
import vanilla.witchtrial.service.PostService;

import java.util.Map;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static vanilla.witchtrial.controller.PostControllerTest.POST_ID;
import static vanilla.witchtrial.global.common.constants.Constants.*;

@DisplayName("게시글 통합 테스트")
@Transactional
@AutoConfigureMockMvc
@SpringBootTest
public class PostControllerITest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper mapper;
    @Autowired private UserRepository userRepository;
    @Autowired private PostService postService;

    @DisplayName("[GET] 게시글 검색 - 본문")
    @Test
    void searchBoardListWithContentValue() throws Exception {
        // when
        MvcResult mvcResult = mockMvc.perform(get("/board")
                        .param("searchType", "CONTENT")
                        .param("searchValue", "Donec dapibus.")
                )
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("boardList"))
                .andReturn();

        // then
        ModelAndView modelAndView = mvcResult.getModelAndView();
        Map<String, Object> model = modelAndView.getModel();
        Page boardList = (Page) model.get("boardList");
        Assertions.assertEquals(boardList.getNumberOfElements(), 2);
    }


    @WithUserDetails(value = "vanille@gmail.com", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @DisplayName("[POST] 게시글 수정 - 성공")
    @Test
    void editPost() throws Exception {
        Long postId = 1L;
        MvcResult mvcResult = mockMvc.perform(post("/board/postForm/" + postId)
                        .param("title", "수정")
                        .param("content", "더미 본문")
                        .param("contentRaw", "더미 본문 raw")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        Assertions.assertEquals(UPDATE_SUCCESS, response);
    }

    @WithUserDetails(value = "vanille2@gmail.com", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @DisplayName("[POST] 게시글 수정 - 실패")
    @Test
    void editPostFail() throws Exception {
        Long postId = 1L;
        MvcResult mvcResult = mockMvc.perform(post("/board/postForm/" + postId)
                        .param("title", "수정")
                        .param("content", "더미 본문")
                        .param("contentRaw", "더미 본문 raw")
                        .with(csrf()))
                .andDo(print())
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        Assertions.assertEquals(UPDATE_FAIL, response);
    }

    @WithUserDetails(value = "vanille@gmail.com", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @DisplayName("[POST] 게시글 삭제 - 성공")
    @Test
    void deletePost() throws Exception {
        MvcResult mvcResult = mockMvc.perform(post("/board/delete/" + POST_ID).with(csrf())).andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        Assertions.assertEquals(DELETE_SUCCESS, response);
    }

    @WithUserDetails(value = "vanille2@gmail.com", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @DisplayName("[POST] 게시글 삭제 - 실패 (같은 사용자 아님)")
    @Test
    void deletePostFailWithWrongUser() throws Exception {
        MvcResult mvcResult = mockMvc.perform(post("/board/delete/" + POST_ID).with(csrf())).andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        Assertions.assertEquals(DELETE_FAIL, response);
    }

}
