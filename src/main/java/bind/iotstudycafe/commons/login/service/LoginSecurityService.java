package bind.iotstudycafe.commons.login.service;

import bind.iotstudycafe.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Optional;

//@Service
//@RequiredArgsConstructor
//public class LoginSecurityService implements UserDetailsService {
//
//    @Autowired
//    private final WebClient iotCafeWebClient;
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//
//    }
//}
