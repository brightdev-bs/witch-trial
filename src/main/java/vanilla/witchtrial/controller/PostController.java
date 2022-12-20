package vanilla.witchtrial.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import vanilla.witchtrial.dto.BoardDto;
import vanilla.witchtrial.dto.PostDto;
import vanilla.witchtrial.dto.type.PostSortType;
import vanilla.witchtrial.dto.type.BoardSearchType;
import vanilla.witchtrial.service.PostService;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/board")
@Controller
public class PostController {

    private final PostService postService;

    @GetMapping
    public String getBoardListView(@Valid @Nullable BoardDto.Request request, ModelMap map) {
        List<BoardDto.Response> boardList
                = postService.getBoardList(request);

        long totalCount = postService.countPosts(request);

        map.addAttribute("boardList", boardList);
        map.addAttribute("currentPage", request.getPage());
        map.addAttribute("startPage", request.getStartPage(request.getPage()));
        map.addAttribute("endPage", request.getEndPage(request.getPage(), totalCount));
        map.addAttribute("totalCount", totalCount);
        map.addAttribute("postSortTypes", PostSortType.values());
        map.addAttribute("searchTypes", BoardSearchType.values());
        return "board/index";
    }

    @GetMapping("/{postId}")
    public String post(@PathVariable Long postId, ModelMap map) {
        PostDto.Response postDetail = postService.getPostDetail(postId);
        map.addAttribute("postDetail", postDetail);
        return "board/postDetail";
    }


}
