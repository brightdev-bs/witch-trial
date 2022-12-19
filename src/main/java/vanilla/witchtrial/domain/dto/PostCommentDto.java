package vanilla.witchtrial.domain.dto;

import lombok.Builder;
import lombok.Data;
import vanilla.witchtrial.domain.Post;
import vanilla.witchtrial.domain.PostComment;

import java.time.LocalDateTime;

public class PostCommentDto {

    @Builder
    @Data
    public static class Request {
        private Long postId;
        private String content;

        public static PostComment toEntity(Post post, String content) {
            return PostComment.of(post, content);
        }
    }

    @Builder
    @Data
    public static class Response {
        private Long postId;
        private String content;
        private String createdBy;
        private LocalDateTime createdAt;
        private LocalDateTime modifiedAt;

        public static Response from(PostComment entity) {
            return Response.builder()
                    .content(entity.getContent())
                    .createdBy(entity.getCreatedBy())
                    .createdAt(entity.getCreatedAt())
                    .modifiedAt(entity.getModifiedAt())
                    .build();
        }

    }
}
