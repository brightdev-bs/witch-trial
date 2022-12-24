package vanilla.witchtrial.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import vanilla.witchtrial.dto.UserPrincipal;

import java.time.LocalDateTime;

@Getter
@ToString
@Table(indexes = {
        @Index(columnList = "email"),
        @Index(columnList = "username")
})
@EntityListeners(AuditingEntityListener.class)
@Entity
public class UserAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true, length = 100)
    private String username;

    @Column(nullable = false)
    private String password;

    @CreatedDate
    private LocalDateTime createdAt;

    protected UserAccount() {
    }

    private UserAccount(String email, String username, String password) {
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public static UserAccount of(String email, String username, String password) {
        return new UserAccount(email, username, password);
    }

    public UserPrincipal from() {
        return UserPrincipal.of(this.id, this.email, this.password, this.username);
    }
}
