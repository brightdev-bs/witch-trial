package vanilla.witchtrial.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import vanilla.witchtrial.dto.BoardDto;
import vanilla.witchtrial.dto.PostDto;
import vanilla.witchtrial.dto.type.BoardSearchType;
import vanilla.witchtrial.dto.type.PostSortType;
import vanilla.witchtrial.service.PostService;

@RequiredArgsConstructor
@RequestMapping("/board")
@Controller
public class PostController {

    private final PostService postService;

    @GetMapping
    public String getBoardListView(@Valid @Nullable BoardDto.Request request,
                                   @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.ASC) Pageable pageable,
                                   ModelMap map) {
        Page<BoardDto.Response> boardList = postService.getBoardList(request, pageable);

        map.addAttribute("maxPage", 10);
        map.addAttribute("boardList", boardList);
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
