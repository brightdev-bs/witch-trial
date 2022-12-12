package vanilla.witchtrial.domain.dto;

import java.time.LocalDateTime;

public class PostCommentDto {

    public static class Request {

    }

    public static class Response {
        private String content;
        private String createdBy;
        private LocalDateTime createdAt;
        private LocalDateTime modifiedAt;
    }
}
