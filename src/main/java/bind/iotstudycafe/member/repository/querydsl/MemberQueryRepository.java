package bind.iotstudycafe.member.repository.querydsl;

import bind.iotstudycafe.member.domain.Member;
import bind.iotstudycafe.member.domain.MemberGrade;
import bind.iotstudycafe.member.dto.MemberSearchCond;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

import static bind.iotstudycafe.member.domain.QMember.member;


@Repository
public class MemberQueryRepository {

    private final JPAQueryFactory query;

    public MemberQueryRepository(EntityManager em) {
        this.query = new JPAQueryFactory(em);
    }


    public List<Member> findAll(MemberSearchCond cond) {

        return query.select(member)
                .from(member)
                .where(
                    likeMemberId(cond.getMemberId()),
                    eqMemberGrade(cond.getMemberGrade()),
                    likeMemberName(cond.getMemberName()),
                    betweenMemberName(cond.getMaxAge(), cond.getMinAge())
                )
                .fetch();
    }

    private BooleanExpression likeMemberId(String memberId) {
        if (StringUtils.hasText(memberId)) {
            return member.memberId.like( memberId + "%");
//            return member.memberId.like("%" + memberId + "%");
        }
        return null;
    }

    private BooleanExpression eqMemberGrade(String memberGrade) {
        if (StringUtils.hasText(memberGrade)) {
            return member.memberGrade.eq(MemberGrade.valueOf(memberGrade));
        }
        return null;
    }

    private BooleanExpression likeMemberName(String memberName) {
        if (StringUtils.hasText(memberName)) {
            return member.memberName.like("%" + memberName + "%");
        }
        return null;
    }

    private BooleanExpression betweenMemberName(Integer maxAge, Integer minAge) {
        if (maxAge != null || minAge != null) {
            return member.age.between(minAge, maxAge);
        }
        return null;
    }

}
