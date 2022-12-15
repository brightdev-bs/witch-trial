package vanilla.witchtrial.domain.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.lang.Nullable;
import vanilla.witchtrial.domain.Post;

import java.time.LocalDateTime;

public class BoardDto {

    @Data
    public static class Request extends PagingDto {
        @Nullable
        private String searchType = "";
        @Nullable
        private String searchValue = "";

        public Request() {}

        @Builder
        public Request(@Nullable String searchType, @Nullable String searchValue, int page, int size) {
            super(page, size);
            this.searchType = searchType;
            this.searchValue = searchValue;
        }
    }

    @Builder
    @Data
    public static class Response {
        private String title;
        private String content;
        private String hashtag;
        private String postType;
        private LocalDateTime createdAt;
        private String createdBy;

        public static Response from(Post post) {
            return Response.builder()
                    .title(post.getTitle())
                    .content(post.getTitle())
                    .hashtag(post.getHashtag())
                    .postType(post.getPostType().name())
                    .createdAt(post.getCreatedAt())
                    .createdBy(post.getCreatedBy())
                    .build();
        }
    }
}