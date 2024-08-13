package bind.iotstudycafe.commons.login.service;

import bind.iotstudycafe.commons.login.domain.LoginDto;
import bind.iotstudycafe.member.domain.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginService {

    @Autowired
    private final WebClient iotCafeWebClient;

    public Mono<ResponseEntity<Member>> login(LoginDto loginDto) {

        log.info("loginDto: {}", loginDto);

        return iotCafeWebClient.post()
                .uri("/login")
                .bodyValue(loginDto)
                .retrieve()
                .toEntity(Member.class);
    }

//    public Mono<ResponseEntity<Member>> login(LoginDto loginDto) {
//
//        log.info("loginDto: {}", loginDto);
//
//        return iotCafeWebClient.post()
//                .uri("/login")
//                .bodyValue(loginDto)
//                .exchangeToMono(response -> {
//                    // 세션을 가져오기 위한 응답 헤더 검사
//                    String sessionId = response.cookies().getFirst("JSESSIONID").getValue();
//
//                    log.info("sessionId: {}", sessionId);
//
//                    // 세션 정보를 클라이언트로 전달하기 위해 HTTP 응답 헤더에 추가
//                    return response.bodyToMono(Member.class)
//                            .map(member -> ResponseEntity.ok()
//                                    .header(HttpHeaders.SET_COOKIE, sessionId)
//                                    .body(member)
//                            );
//                });
//    }

    public void logout(String sessionId) {

        if (sessionId != null) {
            // WAS로 로그아웃 요청 전송
            iotCafeWebClient.post()
                    .uri("/logout")
                    .header(HttpHeaders.COOKIE, sessionId)
                    .retrieve()
                    .bodyToMono(Void.class)
                    .block();
        }
    }
}
