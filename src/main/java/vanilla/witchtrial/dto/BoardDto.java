package vanilla.witchtrial.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.lang.Nullable;
import vanilla.witchtrial.domain.Post;

import java.time.LocalDateTime;

public class BoardDto {

    @Data
    public static class Request {
        @Nullable
        private String searchType = "";
        @Nullable
        private String searchValue = "";
        @Nullable
        private String sortType = "";

        public Request() {}

        @Builder
        public Request(@Nullable String searchType, @Nullable String searchValue, @Nullable String sort) {
            this.searchType = searchType;
            this.searchValue = searchValue;
            this.sortType = sort;
        }
    }

    @Builder
    @Data
    public static class Response {
        private Long id;
        private String title;
        private String content;
        private int liked;
        private int view;
        private String hashtag;
        private String postType;
        private LocalDateTime createdAt;
        private String createdBy;

        public static Response from(Post post) {
            return Response.builder()
                    .id(post.getId())
                    .title(post.getTitle())
                    .content(post.getTitle())
                    .liked(post.getLiked())
                    .view(post.getView())
                    .postType(post.getPostType().name())
                    .createdAt(post.getCreatedAt())
                    .createdBy(post.getCreatedBy())
                    .build();
        }

    }
}
