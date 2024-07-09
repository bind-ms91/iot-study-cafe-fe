package bind.iotstudycafe.member.service;

import bind.iotstudycafe.member.domain.Member;
import bind.iotstudycafe.member.domain.MemberGrade;
import bind.iotstudycafe.member.dto.MemberSaveDto;
import bind.iotstudycafe.member.repository.MemberRepositoryV1;
import bind.iotstudycafe.member.dto.MemberSearchCond;
import bind.iotstudycafe.member.dto.MemberUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberServiceV1 implements MemberService {

//    @Qualifier("memberRepository")
    private final MemberRepositoryV1 memberRepository;

    @Override
    public Member save(MemberSaveDto memberSaveDto) {
        Member member = new Member();
        member.setMemberId(memberSaveDto.getMemberId());
        member.setMemberName(memberSaveDto.getMemberName());
        member.setMemberPassword(memberSaveDto.getMemberPassword());
        member.setAge(memberSaveDto.getAge() );
        member.setMemberGrade(memberSaveDto.getMemberGrade());
        return memberRepository.save(member);
    }

    @Override
    public Optional<Member> findById(Long id) {
        return memberRepository.findById(id);
    }

    @Override
    public Optional<Member> findByMemberId(String memberId) {
        return memberRepository.findByMemberId(memberId);
    }

    @Override
    public List<Member> findAll(MemberSearchCond cond) {
        return memberRepository.findAll(cond);
    }

    @Override
    public void update(Long id, MemberUpdateDto updateParam) {
        memberRepository.update(id, updateParam);
    }

    @Override
    public void deleteById(Long id) {
        memberRepository.delete(id);
    }
}
