package vanilla.witchtrial.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Table(indexes = {
        @Index(columnList = "tagName"),
})
@Entity
public class Hashtag extends AuditingFields {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String tagName;
    private int relatedPostCount;

    @ManyToOne
    @JoinColumn
    private Post post;

    public void setPost(Post post) {
        if(this.post != null) {
            this.post.getHashtags().remove(this);
        }
        this.post = post;
        post.getHashtags().add(this);
    }

}
