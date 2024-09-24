package bind.iotstudycafe.commons.filter;

import bind.iotstudycafe.commons.login.SessionAuthenticationToken;
import bind.iotstudycafe.commons.login.domain.LoginDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
//
//@Slf4j
//public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
//
//    public CustomAuthenticationFilter(AuthenticationManager authenticationManager) {
//        super.setAuthenticationManager(authenticationManager);
//        setFilterProcessesUrl("/login");  // 로그인 요청을 처리하는 URL 설정
//    }
//
//    @Override
//    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
//
////        String username = obtainUsername(request);
////        String password = obtainPassword(request);
//
//        String username = request.getParameter("memberId");
//        String password = request.getParameter("memberPassword");
//
//        log.info("request = {}", request);
//        log.info("username = {}", username);
//        log.info("password = {}", password);
//
//        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
//
//        // AuthenticationManager를 통해 인증 시도
//        return this.getAuthenticationManager().authenticate(authenticationToken);
//    }
//
//
//    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
//        // 인증 성공 후 세션 ID 가져오기
//        String sessionId = ((SessionAuthenticationToken) authentication).getSessionId();
//        response.addHeader(HttpHeaders.SET_COOKIE, sessionId);
//
//        log.info("sessionId = {}", sessionId);
//
//        // 인증 정보 저장
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//    }
//
//}
