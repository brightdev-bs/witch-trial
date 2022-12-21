package vanilla.witchtrial.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vanilla.witchtrial.service.PostService;

@RequiredArgsConstructor
@RequestMapping("/api/v1/board")
@RestController
public class PostRestController {

    private final PostService postService;

//    @GetMapping
//    public ApiResponse boardList(@RequestBody @Valid BoardDto.Request request) {
//        List<BoardDto.Response> boardList = postService.getBoardList(request);
//        return ApiResponse.of(HttpStatus.OK.toString(), boardList);
//    }
//
//    @GetMapping("{postId}")
//    public ApiResponse getPostDetail(@PathVariable Long postId) {
//        PostDto.Response postDetail = postService.getPostDetail(postId);
//        return ApiResponse.of(HttpStatus.OK.toString(), postDetail);
//    }
//
//    @PostMapping
//    public ApiResponse savePost(@RequestBody @Valid PostDto.Request request) {
//        PostDto.Response response = postService.saveNewPost(request);
//        return ApiResponse.of(HttpStatus.OK.toString(), response);
//    }
//
//    @PatchMapping("/{postId}")
//    public ApiResponse editPost(@PathVariable Long postId, @RequestBody @Valid PostDto.UpdateRequest request) {
//        request.setPostId(postId);
//        PostDto.Response response = postService.updatePost(request);
//        return ApiResponse.of(HttpStatus.OK.toString(), response);
//    }
//
//    @DeleteMapping("/{postId}")
//    public ApiResponse deletePost(@PathVariable Long postId) {
//        postService.deletePost(postId);
//        return ApiResponse.of(HttpStatus.OK.toString(), RESPONSE_SUCCESS);
//    }
}
