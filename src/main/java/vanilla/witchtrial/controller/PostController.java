package vanilla.witchtrial.controller;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.lang.Nullable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import vanilla.witchtrial.dto.BoardDto;
import vanilla.witchtrial.dto.PostDto;
import vanilla.witchtrial.dto.UserPrincipal;
import vanilla.witchtrial.dto.type.BoardSearchType;
import vanilla.witchtrial.dto.type.PostSortType;
import vanilla.witchtrial.dto.type.PostType;
import vanilla.witchtrial.service.PostService;

import static vanilla.witchtrial.global.common.constants.Constants.*;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/board")
@Controller
public class PostController {

    private final PostService postService;

    @GetMapping
    public String getBoardListView(@Valid @Nullable BoardDto.Request request,
                                   @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
                                   ModelMap map) {
        Page<BoardDto.Response> boardList = postService.getBoardList(request, pageable);

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

    @GetMapping(value = {"/postForm", "/postForm/{postId}"})
    public String postForm(@PathVariable @Nullable Long postId, ModelMap map) {
        if(postId != null) {
            PostDto.Response postDetail = postService.getPostDetail(postId);
            map.addAttribute("postDetail", postDetail);
            map.addAttribute("status", "update");
        }
        map.addAttribute("postTypes", PostType.values());
        return "board/postForm";
    }

    @PostMapping("/postForm")
    @ResponseBody
    public String saveNewPost(@Valid PostDto.Request request, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        request.setUserPrincipal(userPrincipal);
        postService.saveNewPost(request);
        return SAVE_SUCCESS;
    }

    @PostMapping("/postForm/{postId}")
    @ResponseBody
    public String updatePost(@PathVariable Long postId,
                             PostDto.UpdateRequest request,
                             @AuthenticationPrincipal UserPrincipal userPrincipal
    ) {
        request.setPostId(postId);
        request.setUserPrincipal(userPrincipal);
        try {
            postService.updatePost(request);
            return UPDATE_SUCCESS;
        } catch (EntityNotFoundException | IllegalAccessException e) {
            return UPDATE_FAIL;
        }
    }

    @PostMapping("/delete/{postId}")
    @ResponseBody
    public String deletePost(@PathVariable Long postId,
                             @AuthenticationPrincipal UserPrincipal userPrincipal
    ) {
        try {
            postService.deletePost(postId, userPrincipal);
            return DELETE_SUCCESS;
        } catch (IllegalAccessException e) {
            return DELETE_FAIL;
        }
    }


}
