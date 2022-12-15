package vanilla.witchtrial.domain.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import org.springframework.lang.Nullable;
import vanilla.witchtrial.domain.Post;
import vanilla.witchtrial.domain.dto.type.PostType;

import java.time.LocalDateTime;
import java.util.List;

public class PostDto {

    @Builder
    @Data
    public static class Request {
        @NotNull
        @Size(max = 255)
        private String title;

        @NotNull
        @Size(max = 2000)
        private String content;

        @Nullable
        private String hashtag;

        @NotNull
        private String postType;
        private String createdBy;
    }

    @Builder
    @Data
    public static class UpdateRequest {
        private Long postId;
        private String title;
        private String content;
        private String hashtag;
    }

    @Builder
    @Data
    public static class Response {
        private String title;
        private String content;
        private String hashtag;
        private PostType postType;
        private String createdBy;
        private LocalDateTime createdAt;
        private List<PostCommentDto.Response> comments;

        public static Response toResponse(Post post) {
            return Response.builder()
                    .title(post.getTitle())
                    .content(post.getContent())
                    .hashtag(post.getHashtag())
                    .postType(post.getPostType())
                    .createdBy(post.getCreatedBy())
                    .comments(post.getPostComments().stream().map(PostCommentDto.Response::from).toList())
                    .createdAt(post.getCreatedAt())
                    .build();
        }
    }
}
