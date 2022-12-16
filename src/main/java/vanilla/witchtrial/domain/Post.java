package vanilla.witchtrial.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;
import vanilla.witchtrial.domain.dto.PostDto;
import vanilla.witchtrial.domain.dto.type.PostType;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@ToString
@Table(indexes = {
        @Index(columnList = "title"),
        @Index(columnList = "hashtag"),
        @Index(columnList = "createdAt"),
        @Index(columnList = "createdBy")
})
@Entity
public class Post extends AuditingFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String title;
    @Column(nullable = false, length = 2000)
    private String content;

    private String hashtag;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PostType postType;

    @ToString.Exclude
    @OrderBy("id")
    @OneToMany(mappedBy = "post")
    private final Set<PostComment> postComments = new LinkedHashSet<>();

    protected Post() {}

    private Post(String title, String content, String hashtag, PostType postType) {
        this.title = title;
        this.content = content;
        this.hashtag = hashtag;
        this.postType = postType;
    }

    public static Post of(String title, String content, String hashtag, PostType postType) {
        return new Post(title, content, hashtag, postType);
    }

    public void updatePost(PostDto.UpdateRequest postDto) {
        this.title = postDto.getTitle();
        this.content = postDto.getContent();
        this.hashtag = postDto.getHashtag();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Post post)) return false;
        return id != null && id.equals(post.id); // 영속화 되있지 않으면 다른 객체로 볼 것임.
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
