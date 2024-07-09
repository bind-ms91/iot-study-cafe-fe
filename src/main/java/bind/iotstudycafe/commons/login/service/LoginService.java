package bind.iotstudycafe.commons.login.service;

import bind.iotstudycafe.member.domain.Member;
import bind.iotstudycafe.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final MemberService memberService;

    public Member login(String loginId, String password) {

        Optional<Member> findMemberOptional = memberService.findByMemberId(loginId);

        //TODO 로그인 예외처리
        if (findMemberOptional.isPresent()) {
            return findMemberOptional.filter(m -> m.getMemberPassword().equals(password)).orElse(null);
        }

        return null;
    }

}
