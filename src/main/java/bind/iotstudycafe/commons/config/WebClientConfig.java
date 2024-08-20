package bind.iotstudycafe.commons.config;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@Component
public class WebClientConfig {

    @Value("${domain.iot-cafe-be.endpoint}")
    private String iotCafeBeEndpoint;


    // 기본 WebClient 설정 - @Primary 사용
    @Bean
    @Primary
    public WebClient iotCafeBeWebClient(WebClient.Builder builder, HttpServletRequest request) {
        return builder.baseUrl(iotCafeBeEndpoint)
                .filter(addCookieFilter(request))
                .build();
    }

    // 서버 1을 위한 WebClient 설정
    @Bean
    @Qualifier("server1WebClient")
    public WebClient server1WebClient(WebClient.Builder builder) {
        return builder.baseUrl("https://server1.com")
                .build();
    }

    // 쿠키를 자동으로 추가하는 필터
    private ExchangeFilterFunction addCookieFilter(HttpServletRequest request) {
        return (clientRequest, next) -> {

            // JSESSIONID 쿠키 가져오기
            String sessionId  = request.getHeader(HttpHeaders.COOKIE);

            log.info("sessionId: {}", sessionId);

            // 요청 헤더에 JSESSIONID 쿠키 추가
            ClientRequest filteredRequest = ClientRequest.from(clientRequest)
                    .headers(headers -> {
                        if (sessionId != null) {
                            headers.add(HttpHeaders.COOKIE, sessionId);
                        }
                    })
                    .build();

            return next.exchange(filteredRequest);
        };
    }

}
