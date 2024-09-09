package bind.iotstudycafe.commons.config;

//import bind.iotstudycafe.commons.filter.CustomAnonymousAuthenticationFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

import static org.springframework.security.config.Customizer.withDefaults;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig {

    @Value("${domain.iot-cafe-be.endpoint}")
    private String iotCafeBeEndpoint;

    // 정적인 파일에 대한 요청들
    private static final String[] AUTH_WHITELIST = {
            // -- swagger ui
            "/api-docs",
            "/api-docs/**",
            "/v2/api-docs",
            "/v3/api-docs/**",
            "/swagger-resources/**",
            "/swagger-ui.html",
            "/swagger/**",
            "/swagger-ui/**",

            "/css/**"
            // other public endpoints of your API may be appended to this array
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // CSRF 보호 비활성화 (API 사용 시 주의)
            .authorizeHttpRequests(authorize  -> authorize
                    // Swagger 관련 경로 모두 예외 처리
//                    .requestMatchers("/swagger", "/swagger-ui.html", "/swagger-ui/**", "/api-docs", "/api-docs/**", "/v3/api-docs/**")
//                    .permitAll()
//                    .requestMatchers("/**").permitAll()
//                    .requestMatchers("/css/**").permitAll()
                    .anyRequest().authenticated() // 그 외 요청은 인증 필요
            )
//            .addFilterBefore(customAnonymousAuthenticationFilter(),AnonymousAuthenticationFilter.class)
            .cors(withDefaults())  // CORS 설정 추가
            .formLogin(form -> form
                    .loginPage("/")
                    .loginProcessingUrl("login")
                    .usernameParameter("loginId")
                    .passwordParameter("password")
                    .permitAll()
            )
            .logout( logout -> logout
                    .logoutUrl("logoutt")
                    .logoutRequestMatcher(new AntPathRequestMatcher("logoutt")) // logoutUrl과 logoutRequestMatcher가 둘다 설정되어있을경우 RequestMatcher가 우선적으로 실행된다.
                    .deleteCookies("JSESSIONID")  // 세션 쿠키 삭제
                    .permitAll()
            );

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        // 정적인 파일 요청에 대해 무시
        return (web) -> web.ignoring().requestMatchers(AUTH_WHITELIST);
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin(iotCafeBeEndpoint); // 외부 서버의 도메인을 지정
        configuration.addAllowedMethod("*"); // 모든 HTTP 메서드 허용
        configuration.addAllowedHeader("*"); // 모든 헤더 허용
        configuration.setAllowCredentials(true); // 쿠키 허용

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

}
