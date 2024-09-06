package bind.iotstudycafe.member.service;

import bind.iotstudycafe.exampleDomain.domain.ExampleDomain;
import bind.iotstudycafe.exampleDomain.dto.ExampleDomainSave;
import bind.iotstudycafe.member.domain.Member;
import bind.iotstudycafe.member.dto.MemberSaveDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private static final String RequestMapping = "/members";

    private final WebClient iotCafeWebClient;

    @Override
    public Mono<Member> saveBodyToMono(MemberSaveDto memberSaveDto) {

        return iotCafeWebClient.post()
                .uri(RequestMapping+"/save")
                .bodyValue(memberSaveDto)
                .retrieve()
                .bodyToMono(Member.class);
    }

}
