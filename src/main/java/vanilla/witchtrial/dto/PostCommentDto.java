package vanilla.witchtrial.dto;

import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import vanilla.witchtrial.domain.Post;
import vanilla.witchtrial.domain.PostComment;

import java.time.LocalDateTime;

public class PostCommentDto {

    @Builder
    @Data
    public static class Request {
        private Long postId;
        @Length(max = 500)
        private String content;

        public static PostComment toEntity(Post post, String content) {
            return PostComment.of(post, content);
        }
    }

    @Builder
    @Data
    public static class Response {
        private Long commentId;
        private String content;
        private String createdBy;
        private LocalDateTime createdAt;
        private LocalDateTime modifiedAt;

        public static Response from(PostComment entity) {
            return Response.builder()
                    .commentId(entity.getId())
                    .content(entity.getContent())
                    .createdBy(entity.getCreatedBy())
                    .createdAt(entity.getCreatedAt())
                    .modifiedAt(entity.getModifiedAt())
                    .build();
        }

    }
}
