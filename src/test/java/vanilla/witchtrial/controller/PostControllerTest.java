package vanilla.witchtrial.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;
import vanilla.witchtrial.domain.dto.BoardDto;
import vanilla.witchtrial.domain.dto.PostDto;
import vanilla.witchtrial.global.common.constants.Constants;
import vanilla.witchtrial.global.exception.NotFoundException;
import vanilla.witchtrial.service.PostService;

import java.util.List;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@AutoConfigureMockMvc
@SpringBootTest
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PostService postService;

    @Autowired
    private ObjectMapper objectMapper;

    @DisplayName("게시판 리스트 조회 API (게시글 유형 검색 테스트)")
    @ParameterizedTest
    @MethodSource("searchFilterParams")
    void getBoardList(String type, String value) throws Exception {
        // given
        BoardDto.Request request = BoardDto.Request.builder()
                .searchType(type)
                .searchValue(value)
                .page(0)
                .size(10)
                .build();

        // when
        MvcResult mvcResult = mockMvc.perform(get("/api/v1/board")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        String postTypes = "$['data'][*].postType";
        List<String> results = JsonPath.parse(response.getContentAsString()).read(postTypes);

        // then
        for (String result : results) {
            Assertions.assertEquals(value, result);
        }
        Assertions.assertEquals(10, results.size());
    }

    private static Stream<Arguments> searchFilterParams() {
        return Stream.of(
                Arguments.of("POST_TYPE", "TRIAL"),
                Arguments.of("POST_TYPE", "DATING"),
                Arguments.of("POST_TYPE", "CONSULT"),
                Arguments.of("POST_TYPE", "GOSSIP")
        );
    }

    @DisplayName("게시글 상세 정보 API")
    @Test
    void getPostDetail() throws Exception {
        Long postId = 1L;
        mockMvc.perform(get("/api/v1/board/" + postId)
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$['data'].title", notNullValue()))
                .andExpect(jsonPath("$['data'].content", notNullValue()))
                .andExpect(jsonPath("$['data'].postType", notNullValue()))
                .andExpect(jsonPath("$['data'].hashtag").exists())
                .andExpect(jsonPath("$['data'].hashtag").exists())
                .andExpect(jsonPath("$['data'].createdBy", notNullValue()))
                .andExpect(jsonPath("$['data'].createdAt", notNullValue()))
                .andExpect(jsonPath("$.data.modifiedAt").exists())
                .andExpect(jsonPath("$['data']['comments'][*]", hasSize(2)))
                .andDo(print());
    }

    @DisplayName("게시글 생성 API")
    @Test
    void savePost() throws Exception {
        PostDto.Request postDto = PostDto.Request.builder()
                .title("title")
                .content("content")
                .hashtag("#test")
                .postType("TRIAL")
                .createdBy("vanille")
                .build();

        mockMvc.perform(post("/api/v1/board")
                        .content(objectMapper.writeValueAsString(postDto))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.title").exists())
                .andExpect(jsonPath("$.data.content").exists())
                .andExpect(jsonPath("$.data.hashtag").exists())
                .andExpect(jsonPath("$.data.postType").exists())
                .andExpect(jsonPath("$.data.createdBy").exists())
                .andExpect(jsonPath("$.data.createdAt").exists())
                .andExpect(jsonPath("$.data.modifiedAt").exists())
                .andExpect(jsonPath("$.data.comments.[*]", hasSize(0)))
                .andDo(print());
    }

    @DisplayName("게시글 생성 API : 실패 (잘못된 게시글 유형)")
    @Test
    void savePostFailWithWrongPostType() throws Exception {
        PostDto.Request postDto = PostDto.Request.builder()
                .title("title")
                .content("content")
                .hashtag("#test")
                .postType("wrong")
                .createdBy("vanille")
                .build();

        mockMvc.perform(post("/api/v1/board")
                        .content(objectMapper.writeValueAsString(postDto))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @DisplayName("게시글 수정 API")
    @Test
    void updatePost() throws Exception {
        PostDto.UpdateRequest postDto = PostDto.UpdateRequest.builder()
                .title("title")
                .content("content")
                .hashtag("#test")
                .build();

        mockMvc.perform(patch("/api/v1/board/" + 1)
                        .content(objectMapper.writeValueAsString(postDto))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.title").value("title"))
                .andExpect(jsonPath("$.data.content").value("content"))
                .andExpect(jsonPath("$.data.hashtag").value("#test"))
                .andExpect(jsonPath("$.data.postType").exists())
                .andExpect(jsonPath("$.data.createdAt").exists())
                .andExpect(jsonPath("$.data.modifiedAt").exists())
                .andExpect(jsonPath("$.data.comments").exists())
                .andExpect(jsonPath("$.data.comments.[*]", hasSize(2)))
                .andDo(print());
    }

    @DisplayName("게시글 삭제 API")
    @Test
    void deletePost() throws Exception {
        Long postId = 1L;

        mockMvc.perform(delete("/api/v1/board/" + 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").value(Constants.RESPONSE_SUCCESS))
                .andDo(print());

        Assertions.assertThrows(NotFoundException.class, () -> postService.getPostDetail(postId));
    }

}