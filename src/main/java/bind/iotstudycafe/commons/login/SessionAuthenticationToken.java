package bind.iotstudycafe.commons.login;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class SessionAuthenticationToken extends UsernamePasswordAuthenticationToken {

    private String sessionId;

    public SessionAuthenticationToken(Object principal, Object credentials, String sessionId, Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
        this.sessionId = sessionId;
    }

    public String getSessionId() {
        return sessionId;
    }
}