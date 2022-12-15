package vanilla.witchtrial.domain.dto;

import lombok.Builder;
import lombok.Data;
import vanilla.witchtrial.domain.PostComment;

import java.time.LocalDateTime;

public class PostCommentDto {

    public static class Request {

    }

    @Builder
    @Data
    public static class Response {
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
