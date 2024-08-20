package bind.iotstudycafe.commons.login.service;

import bind.iotstudycafe.commons.login.domain.LoginDto;
import bind.iotstudycafe.member.domain.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    public Mono<ResponseEntity<Member>> loginV1(LoginDto loginDto) {

        log.info("loginDto: {}", loginDto);

        return iotCafeWebClient.post()
                .uri("/login")
                .bodyValue(loginDto)
                .retrieve()
                .toEntity(Member.class);
//                .doOnNext(responseEntity -> {
//                    // 쿠키 정보 저장
//                    String cookieInfo = responseEntity.getHeaders().getFirst(HttpHeaders.SET_COOKIE);
//
//                    log.info("cookieInfo: {}", cookieInfo);
//                    if (cookieInfo != null) {
//                        webClientConfig.setSessionCookie(cookieInfo);
//                    }
//                });
    }

//    public Mono<ResponseEntity<Member>> loginV2(LoginDto loginDto) {
//
//        log.info("loginDto: {}", loginDto);
//
//        return iotCafeWebClient.post()
//                .uri("/login")
//                .bodyValue(loginDto)
//                .exchangeToMono(response -> {
//                    // 로그인 성공 시 쿠키를 저장
//                    String cookieInfo = response.headers().asHttpHeaders().getFirst(HttpHeaders.SET_COOKIE);
//
//                    log.info("cookieInfo: {}", cookieInfo);
//
//                    webClientCookieFilter.setCookie(cookieInfo);
//                    // 세션 정보를 클라이언트로 전달하기 위해 HTTP 응답 헤더에 추가
//                    return response.bodyToMono(Member.class)
//                            .map(member -> ResponseEntity.ok().body(member));
//                });
//    }

    public void logout() {

        log.info("logout");

        // WAS로 로그아웃 요청 전송
        iotCafeWebClient.post()
                    .uri("/logout")
                    .retrieve()
                    .bodyToMono(Void.class)
                    .block();
    }
}
