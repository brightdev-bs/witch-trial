package vanilla.witchtrial.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import vanilla.witchtrial.domain.Hashtag;
import vanilla.witchtrial.domain.Post;
import vanilla.witchtrial.domain.UserAccount;
import vanilla.witchtrial.dto.type.PostType;
import vanilla.witchtrial.global.validation.EnumValid;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.web.util.HtmlUtils.htmlUnescape;

public class PostDto {

    @Builder
    @Data
    public static class Request {
        @NotBlank(message = "title is necessary.")
        @Size(max = 255)
        private String title;

        @NotBlank(message = "content is necessary")
        @Size(max = 3000)
        private String content;

        @NotBlank(message = "content is necessary")
        @Size(max = 2000)
        private String contentRaw;

        @EnumValid(enumClass = PostType.class, ignoreCase = true)
        @NotNull(message = "post-type is necessary")
        private String postType;
        private Set<String> hashtags;
        private String createdBy;
        private UserPrincipal userPrincipal;

        public static Post toEntity(Request dto, UserAccount user) {
            return Post.of(dto.getTitle(), dto.getContent(), dto.getContentRaw(), dto.getPostType(), user);
        }
    }

    @Builder
    @Data
    public static class UpdateRequest {
        private Long postId;
        @NotBlank
        private String title;
        @NotBlank
        private String content;
        @NotBlank
        private String contentRaw;
        private Set<String> hashtags;
        private UserPrincipal userPrincipal;
    }

    @Builder
    @Data
    public static class Response {
        private Long id;
        private String title;
        private String content;
        private PostType postType;
        private String createdBy;
        private LocalDateTime createdAt;
        private LocalDateTime modifiedAt;
        private Set<String> hashtags;
        private Set<PostCommentDto.Response> comments;

        public static Response from(Post post) {
            return Response.builder()
                    .id(post.getId())
                    .title(post.getTitle())
                    .content(htmlUnescape(post.getContentRaw()))
                    .postType(post.getPostType())
                    .createdBy(post.getCreatedBy())
                    .createdAt(post.getCreatedAt())
                    .modifiedAt(post.getModifiedAt())
                    .comments(post.getPostComments().stream()
                            .map(PostCommentDto.Response::from)
                            .collect(Collectors.toCollection(LinkedHashSet::new)))
                    .hashtags(post.getHashtags().stream()
                            .map(Hashtag::getTagName)
                            .collect(Collectors.toCollection(LinkedHashSet::new)))
                    .build();
        }

    }
}
