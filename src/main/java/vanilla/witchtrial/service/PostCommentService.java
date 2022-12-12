package vanilla.witchtrial.service;

import vanilla.witchtrial.domain.dto.PostCommentDto;

import java.util.List;

public interface PostCommentService {
    List<PostCommentDto.Response> getPostComments(Long postId);
}
