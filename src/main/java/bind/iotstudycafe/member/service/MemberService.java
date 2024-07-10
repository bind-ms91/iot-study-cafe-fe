package bind.iotstudycafe.member.service;

import bind.iotstudycafe.member.domain.Member;
import bind.iotstudycafe.member.dto.MemberSaveDto;
import bind.iotstudycafe.member.dto.MemberSearchCond;
import bind.iotstudycafe.member.dto.MemberUpdateDto;

import java.util.List;
import java.util.Optional;

public interface MemberService {

    Member save(MemberSaveDto memberSaveDto);

    Optional<Member> findById(Long id);

    Optional<Member> findByMemberId(String memberId);

    List<Member> findAll(MemberSearchCond cond);

    void update(Long id, MemberUpdateDto updateParam);

    void deleteById(Long id);

}
