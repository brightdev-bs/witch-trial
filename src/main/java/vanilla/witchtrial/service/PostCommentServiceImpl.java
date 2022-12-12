package vanilla.witchtrial.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vanilla.witchtrial.domain.PostComment;
import vanilla.witchtrial.domain.dto.PostCommentDto;
import vanilla.witchtrial.repository.PostCommentRepository;

import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class PostCommentServiceImpl implements PostCommentService {

    private final PostCommentRepository postCommentRepository;

    @Override
    public List<PostCommentDto.Response> getPostComments(Long postId) {
        return null;
    }
}
