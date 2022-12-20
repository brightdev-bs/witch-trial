package vanilla.witchtrial.service;

import org.springframework.web.bind.annotation.RequestBody;
import vanilla.witchtrial.dto.PostCommentDto;

public interface PostCommentService {

    void savePostComment(@RequestBody PostCommentDto.Request request);

    void deleteComment(Long commentId);
}
