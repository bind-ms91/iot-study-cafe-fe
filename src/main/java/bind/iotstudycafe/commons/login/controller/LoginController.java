package bind.iotstudycafe.commons.login.controller;

import bind.iotstudycafe.commons.login.domain.LoginDto;
import bind.iotstudycafe.commons.login.service.LoginService;
import bind.iotstudycafe.member.domain.Member;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.DeferredResult;
import reactor.core.publisher.Mono;

@Controller
@RequiredArgsConstructor
@Slf4j
public class LoginController {

    private final LoginService loginService;

//    @ResponseBody
//    @PostMapping("/login")
//    public String login(@Validated  @ModelAttribute("loginDto") LoginDto loginDto, BindingResult bindingResult,
//                        @RequestParam(defaultValue ="/") String redirectURL, HttpServletRequest request) {
//
//        log.info("login form loginDto: {}", loginDto);
//
//        if(bindingResult.hasErrors()) {
//            return "/home";
//        }
//
//        Mono<ResponseEntity<Member>> loginMember = loginService.login(loginDto);
//
//        log.info("loginMember = {}",loginMember);
//
//        if(loginMember == null) {
//            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
//            return "/home";
//        }
//
//        return null;
//    }

    @Operation(summary = "로그인", description = "동기 로그인",
            responses = {@ApiResponse(responseCode = "200", description = "로그인 성공", content = @Content(schema = @Schema(implementation = LoginDto.class)))}
    )
    @PostMapping("/loginSynchronous")
    @ResponseBody
    public String loginSynchronous(@Validated @ParameterObject @ModelAttribute("loginDto") LoginDto loginDto, BindingResult bindingResult
                        ,@RequestParam(defaultValue ="/") String redirectURL, HttpServletRequest request, HttpServletResponse response) {

        if (bindingResult.hasErrors()) {
            log.info("bindingResult.getAllErrors() = {}", bindingResult.getAllErrors());
            return "login/loginForm";
        }

        Mono<ResponseEntity<Member>> loginMemberMono = loginService.login(loginDto);

        // Mono를 구독하여 결과를 비동기적으로 처리
        return loginMemberMono.flatMap(responseEntity -> {
            log.info("responseEntity.getStatusCode() = {}", responseEntity.getStatusCode());
            log.info("responseEntity.getBody() = {}", responseEntity.getBody());
            if (responseEntity.getStatusCode().is2xxSuccessful() && responseEntity.getBody() != null) {
                log.info("loginMember = {}", responseEntity.getBody());

                // 세션 정보 가져오기
                String sessionId = responseEntity.getHeaders().getFirst(HttpHeaders.SET_COOKIE);
                log.info("컨트롤러 sessionId = {}", sessionId);

//                // 세션 정보를 HttpSession에 저장
                HttpSession session = request.getSession();
//                log.info("session = {}", session);
                session.setAttribute("loginMember", responseEntity.getBody());
                session.setAttribute("JSESSIONID", sessionId);

//                // 클라이언트에게 세션 쿠키 전달
//                if (sessionId != null) {
//                    response.addHeader(HttpHeaders.SET_COOKIE, sessionId);
//                }

                return Mono.just("redirect:" + redirectURL);
            } else {
                bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
                return Mono.just("login/loginForm");
            }
        }).block();  // block()은 여기서 최종 결과를 반환하기 위해 사용 (주의: 비동기 방식에서는 다른 방법 권장)
    }

    @Operation(summary = "로그인", description = "로그인 V1",
            responses = {@ApiResponse(responseCode = "200", description = "로그인 성공", content = @Content(schema = @Schema(implementation = LoginDto.class)))}
    )
    @PostMapping("/loginAsynchronousV1")
    @ResponseBody
    public DeferredResult<String> loginAsynchronousV1(@Validated @ParameterObject @ModelAttribute("loginDto") LoginDto loginDto,
                                        BindingResult bindingResult, @RequestParam(defaultValue = "/") String redirectURL, HttpServletRequest request, HttpServletResponse response) {

        DeferredResult<String> deferredResult = new DeferredResult<>();

        if (bindingResult.hasErrors()) {
            log.info("bindingResult.getAllErrors() = {}", bindingResult.getAllErrors());
            deferredResult.setResult("login/loginForm");
            return deferredResult;
        }

        Mono<ResponseEntity<Member>> loginMemberMono = loginService.login(loginDto);

        loginMemberMono.subscribe(responseEntity -> {
            if (responseEntity.getStatusCode().is2xxSuccessful() && responseEntity.getBody() != null) {
                log.info("loginMember = {}", responseEntity.getBody());

                // 세션 정보 가져오기
                String sessionId = responseEntity.getHeaders().getFirst(HttpHeaders.SET_COOKIE);

                request.setAttribute("member", responseEntity.getBody());

                // 클라이언트에게 전달하기 위해 Set-Cookie 헤더를 설정
                response.addHeader(HttpHeaders.SET_COOKIE, sessionId + "; Path=/; HttpOnly");



                deferredResult.setResult("redirect:" + redirectURL);
            } else {
                bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
                deferredResult.setResult("login/loginForm");
            }
        }, throwable -> {
            log.error("Login failed", throwable);
            bindingResult.reject("loginFail", "로그인 중 오류가 발생했습니다.");
            deferredResult.setResult("login/loginForm");
        });

        return deferredResult;
    }

    @Operation(summary = "로그인", description = "비동기 로그인 V2",
            responses = {@ApiResponse(responseCode = "200", description = "로그인 성공", content = @Content(schema = @Schema(implementation = LoginDto.class)))}
    )
    @PostMapping("/loginAsynchronousV2")
    @ResponseBody
    public Mono<String> loginAsynchronousV2(@Validated @ParameterObject @ModelAttribute("loginDto") LoginDto loginDto,
                              BindingResult bindingResult, @RequestParam(defaultValue = "/") String redirectURL, HttpServletRequest request, HttpServletResponse response) {

        if (bindingResult.hasErrors()) {
            return Mono.just("login/loginForm");
        }

        return loginService.login(loginDto)
                .flatMap(responseEntity -> {
                    if (responseEntity.getStatusCode().is2xxSuccessful() && responseEntity.getBody() != null) {
                        log.info("loginMember = {}", responseEntity.getBody());

                        // 세션 정보 가져오기
                        String sessionId = responseEntity.getHeaders().getFirst(HttpHeaders.SET_COOKIE);

                        // 클라이언트에게 전달하기 위해 Set-Cookie 헤더를 설정
                        response.addHeader(HttpHeaders.SET_COOKIE, sessionId + "; Path=/; HttpOnly");

                        return Mono.just("redirect:" + redirectURL);
                    } else {
                        bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
                        return Mono.just("login/loginForm");
                    }
                })
                .onErrorResume(throwable -> {
                    log.error("Login failed", throwable);
                    bindingResult.reject("loginFail", "로그인 중 오류가 발생했습니다.");
                    return Mono.just("login/loginForm");
                });
    }

    @Operation(summary = "로그아웃", description = "로그아웃",
            responses = {@ApiResponse(responseCode = "200", description = "로그아웃 성공")}
    )
    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {

        HttpSession session = request.getSession(false);

        Member Member = (Member) session.getAttribute("loginMember");
        String sessionId = session.getAttribute("JSESSIONID").toString();

        log.info("Member = {}", Member);
        log.info("sessionId = {}", sessionId);

        // 클라이언트로부터 세션 쿠키를 가져옴

        if (sessionId != null) {

            // 로그아웃 서비스 호출
            loginService.logout(sessionId);

//            // 세션 무효화
//            session.invalidate();

        }

        // 세션 쿠키 삭제 (클라이언트 측에 전달)
//        expireCookie(response);

        return "redirect:/";
    }

//    @PostMapping("/logout")
//    public String logoutV3(HttpServletRequest request) {
//        HttpSession session = request.getSession(false);
//        if(session != null) {
//            session.invalidate();
//        }
//        return "redirect:/";
//    }

//    private static void expireCookie(HttpServletResponse response) {
//        Cookie cookie = new Cookie("JSESSIONID", null);
//        cookie.setMaxAge(0);
//        response.addCookie(cookie);
//    }

}
