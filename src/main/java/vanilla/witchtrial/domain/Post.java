package vanilla.witchtrial.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import vanilla.witchtrial.dto.type.PostType;

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
    @Setter private String title;
    @Column(nullable = false, length = 2000)
    @Setter private String content;

    @Setter private String hashtag;
    @Setter private int view;
    @Setter private int liked;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PostType postType;

    @ManyToOne
    private UserAccount user;

    @ToString.Exclude
    @OrderBy("id")
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private final Set<PostComment> postComments = new LinkedHashSet<>();

    protected Post() {}

    private Post(String title, String content, String hashtag, PostType postType, UserAccount user) {
        this.title = title;
        this.content = content;
        this.hashtag = hashtag;
        this.postType = postType;
        this.user = user;
        this.view = 0;
        this.liked = 0;
    }

    public static Post of(String title, String content, String hashtag, String postType, UserAccount user) {
        // Todo : 검증 로직
        return new Post(title, content, hashtag, Enum.valueOf(PostType.class, postType), user);
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
