package vanilla.witchtrial.dto.type;

import lombok.Getter;

// 마녀재판, 연애, 고민, 잡담
public enum PostType {
    TRIAL("마녀재판"),
    DATING("연애"),
    CONSULT("상담"),
    GOSSIP("잡담"),
    ;
    @Getter private String description;

    PostType(String description) {
        this.description = description;
    }
}
