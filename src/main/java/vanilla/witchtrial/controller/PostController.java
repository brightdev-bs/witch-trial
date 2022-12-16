package vanilla.witchtrial.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import vanilla.witchtrial.domain.dto.BoardDto;
import vanilla.witchtrial.domain.dto.PostDto;
import vanilla.witchtrial.global.response.ApiResponse;
import vanilla.witchtrial.service.PostService;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/v1/board")
@RestController
public class PostController {

    private final PostService postService;

    @GetMapping
    public ApiResponse boardList(@RequestBody @Valid BoardDto.Request request) {
        List<BoardDto.Response> boardList = postService.getBoardList(request, PageRequest.of(request.getPage(), request.getSize()));
        return ApiResponse.of(HttpStatus.OK.toString(), boardList);
    }

    @GetMapping("{postId}")
    public ApiResponse getPostDetail(@PathVariable Long postId) {
        PostDto.Response postDetail = postService.getPostDetail(postId);
        return ApiResponse.of(HttpStatus.OK.toString(), postDetail);
    }

    @PostMapping
    public ApiResponse savePost(@RequestBody @Valid PostDto.Request request) {
        PostDto.Response response = postService.saveNewPost(request);
        return ApiResponse.of(HttpStatus.OK.toString(), response);
    }
}
