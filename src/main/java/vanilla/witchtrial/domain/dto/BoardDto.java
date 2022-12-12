package vanilla.witchtrial.domain.dto;

import lombok.Builder;
import lombok.Data;
import vanilla.witchtrial.domain.dto.type.SearchType;

import java.time.LocalDateTime;

public class BoardDto {

    @Builder
    @Data
    public static class Request {
        private SearchType searchType;
        private String searchValue;
    }

    @Builder
    @Data
    public static class Response {
        private String title;
        private String content;
        private String hashtag;
        private LocalDateTime createdAt;
        private String createdBy;
    }
}
