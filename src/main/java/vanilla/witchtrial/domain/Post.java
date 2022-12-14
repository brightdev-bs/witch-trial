package vanilla.witchtrial.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import vanilla.witchtrial.dto.type.PostType;

import java.util.*;

@Getter
@ToString
@Table(indexes = {
        @Index(columnList = "title"),
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

    @Column(nullable = false, length = 3000)
    @Setter private String content; // Base64

    @Column(nullable = false, length = 2000)
    @Setter private String contentRaw; // 검색을 위한 본문
    @Setter private int view;
    @Setter private int liked;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PostType postType;

    @ManyToOne
    private UserAccount user;

    @ToString.Exclude
    @Setter
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Hashtag> hashtags;

    @ToString.Exclude
    @OrderBy("id")
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostComment> postComments = new ArrayList<>();

    protected Post() {}

    private Post(String title, String content, String contentRaw, PostType postType, UserAccount user) {
        this.title = title;
        this.content = content;
        this.contentRaw =contentRaw;
        this.postType = postType;
        this.user = user;
        this.view = 0;
        this.liked = 0;
        this.hashtags = new LinkedHashSet<>();
    }

    public static Post of(String title, String content, String contentRaw, String postType, UserAccount user) {
        return new Post(title, content, contentRaw, Enum.valueOf(PostType.class, postType), user);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Post post)) return false;
        return this.getId() != null && this.getId().equals(post.getId()); // 영속화 되있지 않으면 다른 객체로 볼 것임.
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }


}
