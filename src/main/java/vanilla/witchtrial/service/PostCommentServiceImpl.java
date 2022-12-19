package vanilla.witchtrial.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import vanilla.witchtrial.domain.Post;
import vanilla.witchtrial.domain.dto.PostCommentDto;
import vanilla.witchtrial.global.common.constants.ErrorCode;
import vanilla.witchtrial.global.exception.NotFoundException;
import vanilla.witchtrial.repository.PostCommentRepository;
import vanilla.witchtrial.repository.PostRepository;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class PostCommentServiceImpl implements PostCommentService {

    private final PostRepository postRepository;
    private final PostCommentRepository postCommentRepository;

    @Override
    public void savePostComment(@RequestBody PostCommentDto.Request request) {
        Post post = postRepository.findByIdWithDsl(request.getPostId()).orElseThrow(() -> new NotFoundException(ErrorCode.POST_NOT_FOUND));
        postCommentRepository.save(PostCommentDto.Request.toEntity(post, request.getContent()));
    }

    public void deleteComment(Long commentId) {
        postCommentRepository.deleteById(commentId);
    }
}
