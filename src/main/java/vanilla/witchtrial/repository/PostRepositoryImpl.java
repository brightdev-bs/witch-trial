package vanilla.witchtrial.repository;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import vanilla.witchtrial.domain.Post;
import vanilla.witchtrial.dto.BoardDto;
import vanilla.witchtrial.dto.type.BoardSearchType;
import vanilla.witchtrial.dto.type.PostSortType;
import vanilla.witchtrial.dto.type.PostType;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static vanilla.witchtrial.domain.QHashtag.hashtag;
import static vanilla.witchtrial.domain.QPost.post;
import static vanilla.witchtrial.domain.QPostComment.postComment;

public class PostRepositoryImpl implements PostRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public PostRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<Post> findBoardList(BoardDto.Request request, Pageable pageable) {
        List<Post> posts = new ArrayList<>();
        if(request.getSearchType().equals(BoardSearchType.HASHTAG.name())) {
            String searchValue = request.getSearchValue();
            posts = queryFactory
                    .selectFrom(post)
                    .leftJoin(hashtag).on(hashtag.post.id.eq(post.id))
                    .where(searchValue != null && !searchValue.isBlank() ? hashtag.tagName.eq(searchValue) : null)
                    .fetch();

            Long count = queryFactory
                    .select(post.count())
                    .from(post)
                    .leftJoin(hashtag).on(hashtag.post.id.eq(post.id))
                    .where(searchValue != null && !searchValue.isBlank() ? hashtag.tagName.eq(searchValue) : null)
                    .fetchOne();
            return new PageImpl<>(posts, pageable, count);
        } else {
            BooleanExpression where = getSearchOption(request);
            posts = queryFactory
                    .selectFrom(post)
                    .where(where)
                    .offset(pageable.getOffset())
                    .limit(pageable.getPageSize())
                    .orderBy(getSortOption(request))
                    .fetch();

            Long count = queryFactory
                    .select(post.count())
                    .from(post)
                    .where(where)
                    .fetchOne();

            return new PageImpl<>(posts, pageable, count);
        }
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
                return post.contentRaw.contains(request.getSearchValue());
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
                return new OrderSpecifier<>(Order.DESC, post.createdAt);
            }

            if(sortType.equals(PostSortType.VIEW.name())) {
                return new OrderSpecifier<>(Order.DESC, post.view);
            }

            if(sortType.equals(PostSortType.LIKED.name())) {
                return new OrderSpecifier<>(Order.DESC, post.liked);
            }
        }
        return new OrderSpecifier<>(Order.DESC, post.createdAt);
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
