package vanilla.witchtrial.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.micrometer.common.util.StringUtils;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Pageable;
import vanilla.witchtrial.domain.Post;
import vanilla.witchtrial.domain.dto.BoardDto;
import vanilla.witchtrial.domain.dto.type.BoardSearchType;
import vanilla.witchtrial.domain.dto.type.PostType;

import java.util.List;

import static vanilla.witchtrial.domain.QPost.post;
import static vanilla.witchtrial.global.common.ErrorCode.INVALID_PARAMS;

public class PostRepositoryImpl implements PostRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public PostRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<Post> findBoardList(BoardDto.Request request, Pageable pageable) {
        return queryFactory
                .selectFrom(post)
                .where(getSearchOption(request))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    private BooleanExpression getSearchOption(BoardDto.Request request) {
        if(request.getSearchType() != null) {

            if(request.getSearchType().equals(BoardSearchType.TITLE.name())) {
                return post.title.contains(request.getSearchValue());
            }

            if(request.getSearchType().equals(BoardSearchType.POST_TYPE.name())) {
                if(request.getSearchValue() == null || request.getSearchValue().isBlank()) return null;
                return post.postType.eq(Enum.valueOf(PostType.class, request.getSearchValue()));
            }

            if(request.getSearchType().equals(BoardSearchType.ID.name())) {
                if(!StringUtils.isBlank(request.getSearchValue()) && request.getSearchValue().chars().allMatch(Character::isDigit))
                    return post.id.eq(Long.valueOf(request.getSearchValue()));
                else
                    throw new IllegalArgumentException(INVALID_PARAMS.getMessage());
            }

            if(request.getSearchType().equals(BoardSearchType.CONTENT.name())) {
                return post.content.contains(request.getSearchValue());
            }

            if(request.getSearchType().equals(BoardSearchType.HASHTAG.name())) {
                return post.hashtag.contains(request.getSearchValue());
            }

            if(request.getSearchType().equals(BoardSearchType.NICKNAME.name())) {
                return post.createdBy.contains(request.getSearchValue());
            }

        }


        return null;
    }
}
