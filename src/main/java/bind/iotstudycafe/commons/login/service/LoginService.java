package bind.iotstudycafe.commons.login.service;

import bind.iotstudycafe.commons.login.SessionAuthenticationToken;
import bind.iotstudycafe.commons.login.domain.LoginDto;
import bind.iotstudycafe.member.domain.Member;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import reactor.core.publisher.Mono;

public interface LoginService {

//    String  login(LoginDto loginDto);

    String loginAndCreateSession(LoginDto loginDto);

    Mono<ResponseEntity<Member>> login(LoginDto loginDto);

    void logout();

}
