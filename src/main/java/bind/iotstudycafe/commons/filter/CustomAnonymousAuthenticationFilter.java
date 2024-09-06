package bind.iotstudycafe.commons.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;

import java.io.IOException;

//@Slf4j
//public class CustomAnonymousAuthenticationFilter extends AnonymousAuthenticationFilter {
//
//    private static final String SWAGGER_UI_REFERER = "swagger-ui/index.html";
//
//    public CustomAnonymousAuthenticationFilter(String key) {
//        super(key);
//    }
//
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//
//        // ServletRequest와 ServletResponse를 HttpServletRequest와 HttpServletResponse로 캐스팅
//        HttpServletRequest httpRequest = (HttpServletRequest) request;
//        HttpServletResponse httpResponse = (HttpServletResponse) response;
//
//
//        log.info("인증 필터 시작");
//
//        // Referer가 Swagger UI라면 필터를 건너뛴다.
//        String referer = httpRequest.getHeader("referer");
//
//        log.info("referer: {}", referer);
//
//        if (referer.contains(SWAGGER_UI_REFERER)) {
//            log.info("조건문 진입");
//
//            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//            log.info("auth: {}", auth);
//            chain.doFilter(request, response);
//            return;
//        }
//
//        super.doFilter(request, response, chain);
//    }
//}
