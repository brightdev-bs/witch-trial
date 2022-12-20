package vanilla.witchtrial.repository;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import vanilla.witchtrial.domain.Post;
import vanilla.witchtrial.domain.dto.BoardDto;
import vanilla.witchtrial.domain.dto.type.BoardSearchType;
import vanilla.witchtrial.domain.dto.type.PostSortType;
import vanilla.witchtrial.domain.dto.type.PostType;

import java.util.List;
import java.util.Optional;

import static vanilla.witchtrial.domain.QPost.post;
import static vanilla.witchtrial.domain.QPostComment.postComment;
import static vanilla.witchtrial.global.common.constants.Constants.DEFAULT_PAGE_SIZE;

public class PostRepositoryImpl implements PostRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public PostRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<Post> findBoardList(BoardDto.Request request) {
        return queryFactory
                .selectFrom(post)
                .where(getSearchOption(request))
                .offset(request.getPage() * DEFAULT_PAGE_SIZE)
                .limit(request.getSize())
                .orderBy(getSortOption(request))
                .fetch();
    }

    private BooleanExpression getSearchOption(BoardDto.Request request) {
        if(request != null && request.getSearchType() != null && request.getSearchValue() != null && !request.getSearchValue().isBlank()) {

            if(request.getSearchType().equals(BoardSearchType.TITLE.name())) {
                return post.title.contains(request.getSearchValue());
            }

            if(request.getSearchType().equals(BoardSearchType.POST_TYPE.name())) {
                if(request.getSearchValue() == null || request.getSearchValue().isBlank()) return null;
                return post.postType.eq(Enum.valueOf(PostType.class, request.getSearchValue().toUpperCase()));
            }

            if(request.getSearchType().equals(BoardSearchType.CONTENT.name())) {
                return post.content.contains(request.getSearchValue());
            }

            if(request.getSearchType().equals(BoardSearchType.HASHTAG.name())) {
                String searchValue = request.getSearchValue();
                if(searchValue.startsWith("#"))
                    return post.hashtag.contains(request.getSearchValue());
                else
                    return post.hashtag.contains("#" + request.getSearchValue());
            }

            if(request.getSearchType().equals(BoardSearchType.NICKNAME.name())) {
                return post.createdBy.contains(request.getSearchValue());
            }

        }

        return null;
    }

    private OrderSpecifier<?> getSortOption(BoardDto.Request request) {
        if(request != null && request.getSortType() != null && !request.getSortType().isBlank()) {
            String sortType = request.getSortType().toUpperCase();
            if(sortType.equals(PostSortType.RECENT.name())) {
                return new OrderSpecifier<>(Order.ASC, post.createdAt);
            }

            if(sortType.equals(PostSortType.VIEW.name())) {
                return new OrderSpecifier<>(Order.ASC, post.view);
            }

            if(sortType.equals(PostSortType.LIKED.name())) {
                return new OrderSpecifier<>(Order.ASC, post.liked);
            }
        }
        return new OrderSpecifier<>(Order.ASC, post.createdAt);
    }

    @Override
    public long countPosts(BoardDto.Request request) {
        return queryFactory
                .selectFrom(post)
                .where(getSearchOption(request))
                .stream().count();
    }

    @Override
    public Optional<Post> findByIdWithDsl(Long id) {
        return Optional.ofNullable(queryFactory.selectFrom(post)
                .leftJoin(post.postComments, postComment).fetchJoin()
                .where(post.id.eq(id))
                .fetchOne());
    }
}
