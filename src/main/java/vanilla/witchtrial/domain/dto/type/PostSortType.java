package vanilla.witchtrial.domain.dto.type;

import lombok.Getter;

public enum PostSortType {

    RECENT("최신순"),
    VIEW("조회순"),
    LIKED("추천순");

    @Getter private final String description;

    PostSortType(String description) {
        this.description = description;
    }


}
