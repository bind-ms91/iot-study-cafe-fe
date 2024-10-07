package bind.iotstudycafe.commons.login.service;

import bind.iotstudycafe.commons.login.SessionAuthenticationToken;
import bind.iotstudycafe.commons.login.domain.LoginDto;
import bind.iotstudycafe.member.domain.Member;
import bind.iotstudycafe.member.domain.MemberGrade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerErrorException;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginServiceV1 implements LoginService {

    private final WebClient iotCafeWebClient;

//    @Override
//    public Authentication authenticate(Authentication authentication) {
//
//        String username = authentication.getName();
//        String password = (String) authentication.getCredentials();
//
//        log.info("password = {} ", password);
//
//        // WAS 서버로 인증 요청 및 세션 ID 반환받기
//        Mono<ResponseEntity<Member>> responseMono = iotCafeWebClient.post()
//                .uri("/login")
//                .bodyValue(new LoginDto(username, password))
//                .retrieve()
//                // onStatus() 메서드를 사용하여 상태 코드에 따른 맞춤형 오류 처리를 추가
//                .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> {
//                    log.info("clientResponse = {}", clientResponse);
//                    return Mono.error(new BadCredentialsException("Invalid username or password"));
//                })
//                .onStatus(HttpStatusCode::is5xxServerError, clientResponse -> {
//                    return Mono.error(new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "Server error"));
//                })
//                .toEntity(Member.class);
//
//         // 동기적 처리
//        ResponseEntity<Member> response = responseMono.block();
//        // 인증 실패 시
//        if (response == null || response.getStatusCode().isError()) {
//            throw new BadCredentialsException("Invalid username or password");
//        }
//
//        // 인증 성공 시, 권한 설정
//        Member member = response.getBody();
//        String sessionId = response.getHeaders().getFirst("Set-Cookie");  // WAS에서 반환된 세션 ID 추출
//
//        log.info("sessionId: {}", sessionId);
//
//        List<GrantedAuthority> authorities = new ArrayList<>();
//
//        authorities.add(new SimpleGrantedAuthority(member.getMemberGrade().toString()));
//
//        return new SessionAuthenticationToken(username, password, sessionId, authorities);
//
//    }
//
//    @Override
//    public boolean supports(Class<?> authentication) {
//        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
//    }

    @Override
    public String loginAndCreateSession(LoginDto loginDto) {
        log.info("Authenticating user: {}", loginDto.getLoginId());

        // WAS 서버에 로그인 요청 및 세션 ID 반환
        Mono<ResponseEntity<Member>> responseMono = iotCafeWebClient.post()
                .uri("/login")
                .bodyValue(loginDto)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> {
                    log.error("Authentication failed for user: {}", loginDto.getLoginId());
                    return Mono.error(new BadCredentialsException("Invalid username or password"));
                })
                .onStatus(HttpStatusCode::is5xxServerError, clientResponse -> {
                    log.error("Server error during authentication for user: {}", loginDto.getLoginId());
                    return Mono.error(new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "Server error"));
                })
                .toEntity(Member.class);

        ResponseEntity<Member> response = responseMono.block();
        if (response == null || response.getStatusCode().isError()) {
            throw new BadCredentialsException("Invalid username or password");
        }

        Member member = response.getBody();
        String sessionId = response.getHeaders().getFirst("Set-Cookie");  // WAS에서 반환된 세션 ID

        log.info("Authenticated user: {}, sessionId: {}", loginDto.getLoginId(), sessionId);

        // 세션 ID 레디스에 저장 (생략)

        return sessionId;
    }

    @Override
    public Mono<ResponseEntity<Member>> login(LoginDto loginDto) {

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

    @Override
    public void logout() {

        // WAS로 로그아웃 요청 전송
        iotCafeWebClient.post()
                    .uri("/logout")
                    .retrieve()
                    .bodyToMono(Void.class)
                    .block();
    }
}
