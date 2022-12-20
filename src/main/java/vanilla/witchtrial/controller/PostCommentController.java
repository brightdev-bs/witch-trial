package vanilla.witchtrial.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import vanilla.witchtrial.domain.dto.PostCommentDto;
import vanilla.witchtrial.service.PostCommentService;

@RequiredArgsConstructor
@RequestMapping("/board/{postId}/comments")
@Controller
public class PostCommentController {

    private final PostCommentService postCommentService;

    @PostMapping
    public String saveNewPostComment(@PathVariable Long postId, @Valid PostCommentDto.Request request) {
        request.setPostId(postId);
        postCommentService.savePostComment(request);
        return "redirect:/board/" + postId;
    }

    @PostMapping("/{commentId}")
    public String deletePostComment(@PathVariable Long postId, @PathVariable Long commentId) {
        postCommentService.deleteComment(commentId);
        return "redirect:/board/" + postId;
    }
}
