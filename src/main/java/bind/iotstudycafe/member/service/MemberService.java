package bind.iotstudycafe.member.service;

import bind.iotstudycafe.exampleDomain.domain.ExampleDomain;
import bind.iotstudycafe.member.domain.Member;
import bind.iotstudycafe.member.dto.MemberSaveDto;
import bind.iotstudycafe.member.dto.MemberSearchCond;
import bind.iotstudycafe.member.dto.MemberUpdateDto;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

public interface MemberService {

    Mono<Member> saveBodyToMono(MemberSaveDto memberSaveDto);
}
