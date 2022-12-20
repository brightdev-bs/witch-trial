package vanilla.witchtrial.dto.type;

import lombok.Getter;

@Getter
public enum BoardSearchType {
    TITLE("제목"),
    POST_TYPE("카테고리"),
    CONTENT("본문"),
    NICKNAME("작성자"),
    HASHTAG("해시태그");

    @Getter private final String description;

    BoardSearchType(String description) {
        this.description = description;
    }
}
