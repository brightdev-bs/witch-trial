package vanilla.witchtrial.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import vanilla.witchtrial.domain.Post;
import vanilla.witchtrial.domain.PostComment;
import vanilla.witchtrial.dto.PostCommentDto;
import vanilla.witchtrial.global.common.constants.ErrorCode;
import vanilla.witchtrial.global.exception.NotFoundException;
import vanilla.witchtrial.repository.PostCommentRepository;
import vanilla.witchtrial.repository.PostRepository;

@RequiredArgsConstructor
@Service
public class PostCommentServiceImpl implements PostCommentService {

    private final PostRepository postRepository;
    private final PostCommentRepository postCommentRepository;

    @Transactional
    @Override
    public void savePostComment(@RequestBody PostCommentDto.Request request) {
        Post post = postRepository.findByIdWithDsl(request.getPostId()).orElseThrow(() -> new NotFoundException(ErrorCode.POST_NOT_FOUND));
        postCommentRepository.save(PostCommentDto.Request.toEntity(post, request.getContent()));
    }

    @Transactional
    public void deleteComment(Long commentId) {
        PostComment comment = postCommentRepository.findById(commentId).orElseThrow(() -> new EntityNotFoundException("없음"));
        postCommentRepository.delete(comment);
    }
}
