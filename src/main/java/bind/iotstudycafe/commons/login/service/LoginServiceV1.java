package bind.iotstudycafe.commons.login.service;

import bind.iotstudycafe.commons.login.SessionAuthenticationToken;
import bind.iotstudycafe.commons.login.domain.LoginDto;
import bind.iotstudycafe.member.domain.Member;
import bind.iotstudycafe.member.domain.MemberGrade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginServiceV1 implements LoginService, AuthenticationProvider {

    private final WebClient iotCafeWebClient;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String username = authentication.getName();
        String password = (String) authentication.getCredentials();

        log.info("password = {} ", password);

        // WAS 서버로 인증 요청 및 세션 ID 반환받기
        Mono<ResponseEntity<Member>> responseMono = iotCafeWebClient.post()
                .uri("/login")
                .bodyValue(new LoginDto(username, password))
                .retrieve()
                .toEntity(Member.class);

        ResponseEntity<Member> response = responseMono.block();  // 동기적 처리

        // 인증 실패 시
        if (response == null || response.getStatusCode().isError()) {
            throw new BadCredentialsException("Invalid username or password");
        }

        // 인증 성공 시, 권한 설정
        Member member = response.getBody();
        String sessionId = response.getHeaders().getFirst("Set-Cookie");  // WAS에서 반환된 세션 ID 추출

        log.info("sessionId: {}", sessionId);

        List<GrantedAuthority> authorities = new ArrayList<>();

        authorities.add(new SimpleGrantedAuthority(member.getMemberGrade().toString()));

        return new SessionAuthenticationToken(username, password, sessionId, authorities);

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
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
