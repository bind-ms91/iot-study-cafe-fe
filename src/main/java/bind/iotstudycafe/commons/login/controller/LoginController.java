package bind.iotstudycafe.commons.login.controller;

import bind.iotstudycafe.commons.login.domain.LoginDto;
import bind.iotstudycafe.commons.login.service.LoginService;
import bind.iotstudycafe.member.domain.Member;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@Slf4j
public class LoginController {

    private final LoginService loginService;

    @ResponseBody
    @PostMapping("/login")
    public String login(@Validated  @ModelAttribute("loginDto") LoginDto loginDto, BindingResult bindingResult,
                        @RequestParam(defaultValue ="/") String redirectURL, HttpServletRequest request) {

        if(bindingResult.hasErrors()) {
            return "/home";
        }

        Member loginMember = loginService.login(loginDto.getLoginId(), loginDto.getPassword());

        if(loginMember == null) {
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
            return "/home";
        }

        return "ok";
    }

    @PostMapping("/logout")
    public String logoutV3(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if(session != null) {
            session.invalidate();
        }
        return "redirect:/";
    }

    private static void expireCookie(HttpServletResponse response, String cookieName) {
        Cookie cookie = new Cookie(cookieName, null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }

}
