package vanilla.witchtrial.domain.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import org.springframework.lang.Nullable;
import vanilla.witchtrial.domain.Post;
import vanilla.witchtrial.domain.dto.type.PostType;
import vanilla.witchtrial.global.validation.EnumValid;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class PostDto {

    @Builder
    @Data
    public static class Request {
        @NotNull(message = "title is necessary.")
        @Size(max = 255)
        private String title;

        @NotNull(message = "content is necessary")
        @Size(max = 2000)
        private String content;

        @Nullable
        private String hashtag;

        @EnumValid(enumClass = PostType.class, ignoreCase = true)
        @NotNull(message = "post-type is necessary")
        private String postType;
        private String createdBy;

        public static Post toEntity(Request dto) {
            return Post.of(dto.getTitle(), dto.getContent(), dto.getHashtag(), dto.getPostType());
        }
    }

    @Builder
    @Data
    public static class UpdateRequest {
        private Long postId;
        @NotNull
        private String title;
        @NotNull
        private String content;
        @Nullable
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
        private LocalDateTime modifiedAt;
        private Set<PostCommentDto.Response> comments;

        public static Response from(Post post) {
            return Response.builder()
                    .title(post.getTitle())
                    .content(post.getContent())
                    .hashtag(post.getHashtag())
                    .postType(post.getPostType())
                    .createdBy(post.getCreatedBy())
                    .createdAt(post.getCreatedAt())
                    .modifiedAt(post.getModifiedAt())
                    .comments(post.getPostComments().stream()
                            .map(PostCommentDto.Response::from)
                            .collect(Collectors.toCollection(LinkedHashSet::new)))
                    .build();
        }

    }
}
