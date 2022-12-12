package vanilla.witchtrial.domain.dto;

import lombok.Builder;
import lombok.Data;
import vanilla.witchtrial.domain.dto.type.PostType;

import java.time.LocalDateTime;

public class PostDto {

    @Builder
    @Data
    public static class Request {
        private String title;
        private String content;
        private String hashtag;
        private PostType postType;
        private String createdBy;
        private LocalDateTime createdAt;
    }

    @Builder
    @Data
    public static class UpdateRequest {
        private String title;
        private String content;
        private String hashtag;
    }

    @Builder
    @Data
    public static class Response {

    }
}
