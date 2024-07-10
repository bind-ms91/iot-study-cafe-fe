package bind.iotstudycafe.commons.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${domain.iot-cafe-be.endpoint}")
    private String iotCafeBeEndpoint;

//    @Bean
//    public WebClient.Builder webClientBuilder() {
//        return WebClient.builder();
//    }

    // 기본 WebClient 설정 - @Primary 사용
    @Bean
    @Primary
    public WebClient iotCafeBeWebClient(WebClient.Builder builder) {
        return builder.baseUrl(iotCafeBeEndpoint)
                .build();
    }

    // 서버 1을 위한 WebClient 설정
    @Bean
    @Qualifier("server1WebClient")
    public WebClient server1WebClient(WebClient.Builder builder) {
        return builder.baseUrl("https://server1.com")
                .build();
    }
}
