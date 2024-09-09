package bind.iotstudycafe.commons.login.service;

import bind.iotstudycafe.commons.login.domain.LoginDto;
import bind.iotstudycafe.member.domain.Member;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

public interface LoginService {

    Mono<ResponseEntity<Member>> login(LoginDto loginDto);

    void logout();

}
