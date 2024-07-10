package bind.iotstudycafe.member.service;

import bind.iotstudycafe.member.domain.Member;
import bind.iotstudycafe.member.domain.MemberGrade;
import bind.iotstudycafe.member.dto.MemberSaveDto;
import bind.iotstudycafe.member.dto.MemberSearchCond;
import bind.iotstudycafe.member.dto.MemberUpdateDto;
import bind.iotstudycafe.member.repository.MemberRepositoryV2;
import bind.iotstudycafe.member.repository.querydsl.MemberQueryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberServiceV2 implements MemberService {

    private final MemberRepositoryV2 memberRepositoryV2;
    private final MemberQueryRepository memberQueryRepository;

    @Override
    public Member save(MemberSaveDto memberSaveDto) {
        Member member = new Member();
        member.setMemberId(memberSaveDto.getMemberId());
        member.setMemberName(memberSaveDto.getMemberName());
        member.setMemberPassword(memberSaveDto.getMemberPassword());
        member.setAge(memberSaveDto.getAge() );
        member.setMemberGrade(memberSaveDto.getMemberGrade());
        return memberRepositoryV2.save(member);
    }

    @Override
    public Optional<Member> findById(Long id) {
        return memberRepositoryV2.findById(id);
    }

    @Override
    public Optional<Member> findByMemberId(String memberId) {
        return memberRepositoryV2.findByMemberId(memberId);
    }

    @Override
    public List<Member> findAll(MemberSearchCond cond) {
        return memberQueryRepository.findAll(cond);
    }

    @Override
    public void update(Long id, MemberUpdateDto updateParam) {
        Member findMember = memberRepositoryV2.findById(id).orElseThrow();
        findMember.setMemberGrade(updateParam.getMemberGrade());
        findMember.setMemberPassword(updateParam.getMemberPassword());
        findMember.setMemberName(updateParam.getMemberName());
        findMember.setAge(updateParam.getAge());
    }

    @Override
    public void deleteById(Long id) {
        memberRepositoryV2.deleteById(id);
    }
}
