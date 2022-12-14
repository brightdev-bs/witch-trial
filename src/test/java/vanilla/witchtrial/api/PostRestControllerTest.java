package vanilla.witchtrial.api;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;
import vanilla.witchtrial.config.SecurityConfig;

@Transactional
@Import(SecurityConfig.class)
@AutoConfigureMockMvc
@SpringBootTest
class PostRestControllerTest {

//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private PostService postService;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @DisplayName("게시판 리스트 조회 API (게시글 유형 검색 테스트)")
//    @ParameterizedTest
//    @MethodSource("searchFilterParams")
//    void getBoardList(String type, String value) throws Exception {
//        // given
//        BoardDto.Request request = BoardDto.Request.builder()
//                .searchType(type)
//                .searchValue(value)
//                .build();
//        PageRequest pageable = PageRequest.of(0, 10);
//
//        // when
//        MvcResult mvcResult = mockMvc.perform(get("/api/v1/board")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(request))
//                )
//                .andExpect(status().isOk())
//                .andDo(print())
//                .andReturn();
//        MockHttpServletResponse response = mvcResult.getResponse();
//        String postTypes = "$['data'][*].postType";
//        List<String> results = JsonPath.parse(response.getContentAsString()).read(postTypes);
//
//        // then
//        for (String result : results) {
//            Assertions.assertEquals(value, result);
//        }
//        Assertions.assertEquals(10, results.size());
//    }
//
//    private static Stream<Arguments> searchFilterParams() {
//        return Stream.of(
//                Arguments.of("POST_TYPE", "TRIAL"),
//                Arguments.of("POST_TYPE", "DATING"),
//                Arguments.of("POST_TYPE", "CONSULT"),
//                Arguments.of("POST_TYPE", "GOSSIP")
//        );
//    }
//
//    @DisplayName("게시글 상세 정보 API")
//    @Test
//    void getPostDetail() throws Exception {
//        Long postId = 1L;
//        mockMvc.perform(get("/api/v1/board/" + postId)
//                .contentType(MediaType.APPLICATION_JSON)
//        )
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$['data'].title", notNullValue()))
//                .andExpect(jsonPath("$['data'].content", notNullValue()))
//                .andExpect(jsonPath("$['data'].postType", notNullValue()))
//                .andExpect(jsonPath("$['data'].hashtag").exists())
//                .andExpect(jsonPath("$['data'].hashtag").exists())
//                .andExpect(jsonPath("$['data'].createdBy", notNullValue()))
//                .andExpect(jsonPath("$['data'].createdAt", notNullValue()))
//                .andExpect(jsonPath("$.data.modifiedAt").exists())
//                .andExpect(jsonPath("$['data']['comments'][*]", hasSize(2)))
//                .andDo(print());
//    }
//
//    @WithUserDetails(value = "vanille", userDetailsServiceBeanName = "userDetailsService", setupBefore = TestExecutionEvent.TEST_EXECUTION)
//    @DisplayName("게시글 생성 API")
//    @Test
//    void savePost() throws Exception {
//        PostDto.Request postDto = PostDto.Request.builder()
//                .title("title")
//                .content("content")
//                .hashtag("#test")
//                .postType("TRIAL")
//                .createdBy("vanille")
//                .build();
//
//        mockMvc.perform(post("/api/v1/board")
//                        .content(objectMapper.writeValueAsString(postDto))
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .with(csrf())
//                )
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.data.title").exists())
//                .andExpect(jsonPath("$.data.content").exists())
//                .andExpect(jsonPath("$.data.hashtag").exists())
//                .andExpect(jsonPath("$.data.postType").exists())
//                .andExpect(jsonPath("$.data.createdBy").exists())
//                .andExpect(jsonPath("$.data.createdAt").exists())
//                .andExpect(jsonPath("$.data.modifiedAt").exists())
//                .andExpect(jsonPath("$.data.comments.[*]", hasSize(0)))
//                .andDo(print());
//    }
//
//    @WithMockUser
//    @DisplayName("게시글 생성 API : 실패 (잘못된 게시글 유형)")
//    @Test
//    void savePostFailWithWrongPostType() throws Exception {
//        PostDto.Request postDto = PostDto.Request.builder()
//                .title("title")
//                .content("content")
//                .hashtag("#test")
//                .postType("wrong")
//                .createdBy("vanille")
//                .build();
//
//        mockMvc.perform(post("/api/v1/board")
//                        .content(objectMapper.writeValueAsString(postDto))
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .with(csrf())
//                )
//                .andExpect(status().isBadRequest())
//                .andDo(print());
//    }
//
//    @WithMockUser
//    @DisplayName("게시글 수정 API")
//    @Test
//    void updatePost() throws Exception {
//        PostDto.UpdateRequest postDto = PostDto.UpdateRequest.builder()
//                .title("title")
//                .content("content")
//                .hashtag("#test")
//                .build();
//
//        mockMvc.perform(patch("/api/v1/board/" + 1)
//                        .content(objectMapper.writeValueAsString(postDto))
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .with(csrf())
//                )
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.data.title").value("title"))
//                .andExpect(jsonPath("$.data.content").value("content"))
//                .andExpect(jsonPath("$.data.hashtag").value("#test"))
//                .andExpect(jsonPath("$.data.postType").exists())
//                .andExpect(jsonPath("$.data.createdAt").exists())
//                .andExpect(jsonPath("$.data.modifiedAt").exists())
//                .andExpect(jsonPath("$.data.comments").exists())
//                .andExpect(jsonPath("$.data.comments.[*]", hasSize(2)))
//                .andDo(print());
//    }
//
//    @WithMockUser
//    @DisplayName("게시글 삭제 API")
//    @Test
//    void deletePost() throws Exception {
//        Long postId = 1L;
//
//        mockMvc.perform(delete("/api/v1/board/" + 1).with(csrf()))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.data").value(Constants.RESPONSE_SUCCESS))
//                .andDo(print());
//
//        Assertions.assertThrows(NotFoundException.class, () -> postService.getPostDetail(postId));
//    }

}