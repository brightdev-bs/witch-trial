package vanilla.witchtrial.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vanilla.witchtrial.global.response.ApiResponse;
import vanilla.witchtrial.service.PostService;

@RequiredArgsConstructor
@RequestMapping("/api/board")
@RestController
public class PostController {

    private final PostService postService;

    @GetMapping
    public ApiResponse boardList() {
        return null;
    }
}
