package vanilla.witchtrial.service;

import org.springframework.web.bind.annotation.RequestBody;
import vanilla.witchtrial.domain.dto.PostCommentDto;

import java.util.List;

public interface PostCommentService {
    List<PostCommentDto.Response> getPostComments(Long postId);

    void savePostComment(@RequestBody PostCommentDto.Request request);
}
