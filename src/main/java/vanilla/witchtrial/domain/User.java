package vanilla.witchtrial.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Entity
public class User extends AuditingFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false, length=100)
    private String username;

    @Column(nullable = false)
    private String password;

    protected User() {}

    private User(String email, String username, String password) {
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public static User of(String email, String username, String password) {
        return new User(email, username, password);
    }
}
