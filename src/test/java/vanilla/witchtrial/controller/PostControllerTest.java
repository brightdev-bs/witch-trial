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
import vanilla.witchtrial.domain.dto.BoardDto;

import java.util.List;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

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
                .andExpect(jsonPath("$['data']['comments'][*]", hasSize(2)))
                .andDo(print());
    }

}