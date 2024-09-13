package bind.iotstudycafe.member.service;

import bind.iotstudycafe.exampleDomain.domain.ExampleDomain;
import bind.iotstudycafe.exampleDomain.dto.ExampleDomainSave;
import bind.iotstudycafe.member.domain.Member;
import bind.iotstudycafe.member.dto.MemberSaveDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger log = LoggerFactory.getLogger(MemberServiceImpl.class);

    private final WebClient iotCafeWebClient;

    @Override
    public Mono<Member> saveBodyToMono(MemberSaveDto memberSaveDto) {

        return iotCafeWebClient.post()
                .uri(RequestMapping+"/save")
                .bodyValue(memberSaveDto)
                .retrieve()
                .bodyToMono(Member.class);
    }

    // ID 중복 체크
    public boolean isMemberIdExists(String memberId) {
        // WAS 서버에 중복 체크 요청
        Mono<Member> response = iotCafeWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(RequestMapping)
                        .queryParam("memberId", memberId)
                        .build())
                .retrieve()
                .bodyToMono(Member.class);

        log.info(String.valueOf(response.blockOptional().isPresent()));

        return response.blockOptional().isPresent();
    }


    // 회원 가입 처리
    public Member saveMember(MemberSaveDto memberSaveDto) {
        // WAS 서버에 회원 가입 요청
        Mono<Member> response = iotCafeWebClient.post()
                .uri("/members/save")
                .bodyValue(memberSaveDto)
                .retrieve()
                .bodyToMono(Member.class);

        return response.block(); // 동기식 처리
    }

}
