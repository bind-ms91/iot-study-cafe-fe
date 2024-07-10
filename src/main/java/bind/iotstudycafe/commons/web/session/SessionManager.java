package bind.iotstudycafe.commons.web.session;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface SessionManager {

    void createSession(Object value, HttpServletResponse response);

    /**
     * 세션 조회
     */
    Object getSession(HttpServletRequest request);

    /**
     * 세션 만료
     */
    void expire(HttpServletRequest request);

    Cookie findCookie(HttpServletRequest request, String cookieName);

}
