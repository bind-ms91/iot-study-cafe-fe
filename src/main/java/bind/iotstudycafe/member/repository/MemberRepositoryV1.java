package bind.iotstudycafe.member.repository;

import bind.iotstudycafe.member.domain.Member;
import bind.iotstudycafe.member.dto.MemberSearchCond;
import bind.iotstudycafe.member.dto.MemberUpdateDto;

import java.util.List;
import java.util.Optional;

public interface MemberRepositoryV1 {

    Member save(Member member);

    Optional<Member> findById(Long id);

    Optional<Member> findByMemberId(String memberId);

    List<Member> findAll(MemberSearchCond cond);

    void update(Long id, MemberUpdateDto updateParam);

    void delete(Long id);

}
