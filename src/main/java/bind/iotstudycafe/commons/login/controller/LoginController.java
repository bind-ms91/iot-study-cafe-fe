package bind.iotstudycafe.commons.login.controller;

import bind.iotstudycafe.commons.login.SessionAuthenticationToken;
import bind.iotstudycafe.commons.login.domain.LoginDto;
import bind.iotstudycafe.commons.login.service.LoginService;
import bind.iotstudycafe.member.domain.Member;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;
import reactor.core.publisher.Mono;

@Controller
@RequiredArgsConstructor
@Slf4j
public class LoginController {


    private final LoginService loginService;

    private final AuthenticationManager authenticationManager;




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
    @PostMapping("/login")
    @ResponseBody
    public String login(@Validated @ParameterObject @ModelAttribute("loginDto") LoginDto loginDto, BindingResult bindingResult
            ,@RequestParam(defaultValue ="/") String redirectURL, HttpServletRequest request, HttpServletResponse response) {

        // 인증 요청 생성
        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(loginDto.getLoginId(), loginDto.getPassword());

        // 인증 시도
        Authentication authentication = authenticationManager.authenticate(token);

        // 인증 성공 시, 컨텍스트에 저장
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 세션 ID를 인증 객체에서 가져옴
        String sessionId = ((SessionAuthenticationToken) authentication).getSessionId();

        log.info("controller sessionId: {}", sessionId);

        response.addHeader(HttpHeaders.SET_COOKIE, sessionId);

        // 로그인 성공 후 리다이렉트
        return "redirect:"+redirectURL;
    }


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

                // 세션 정보 가져오기
                extractedSessionId(response, responseEntity);

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
//                extractedSessionId(request, responseEntity);

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
//                        extractedSessionId(request, responseEntity);

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
    @PostMapping("/logoutV1")
    public String logoutV1(HttpServletRequest request, HttpServletResponse response) {

        String sessionId = request.getHeader(HttpHeaders.COOKIE);

        log.info("sessionId = {}", sessionId);

        if (sessionId != null) {

            loginService.logout();

            // 쿠키 삭제 (클라이언트 측에 전달)
            expireCookie(response);
        }



//
//        log.info("sessionId = {}", sessionId);
//
//        if (sessionId != null) {
//            loginService.logout(sessionId);
//        }

//        // 쿠키 추출
//        Optional<Cookie> findCookie = Arrays.stream(request.getCookies())
//                .filter(cookie -> cookie.getName().equals("JSESSIONID"))
//                .findFirst();
//
//        log.info("findCookie = {}", findCookie);
//
//        if (findCookie.isPresent()) {
//            Cookie cookie = findCookie.get();
//            String sessionId = cookie.getValue();
//
//            log.info("sessionId = {}", sessionId);
//            loginService.logout(sessionId);
//        }



//        if(sessionId != null) {
//            loginService.logout(sessionId);
//        }

//        if (findCookie.isPresent()) {
//            String sessionId = findCookie.get().getValue();
//
//            log.info("logout sessionId = {}", sessionId);
//
//            loginService.logout(sessionId);
//        }

//        String sessionId = request.getAttribute("JSESSIONID").toString();
//
//        log.info("sessionId = {}", sessionId);
//
//        if (sessionId != null) {
//
//            // 로그아웃 서비스 호출
//            loginService.logout(sessionId);
//
//            // 세션 무효화
////            session.invalidate();
//
//        }

        // 쿠키 삭제 (클라이언트 측에 전달)
        expireCookie(response);

        return "redirect:/";
    }

    @Operation(summary = "로그아웃", description = "로그아웃",
            responses = {@ApiResponse(responseCode = "200", description = "로그아웃 성공")}
    )
    @PostMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {

        // 1. 현재 인증 객체를 가져옴
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        log.info("authentication = {}", authentication);

        if (authentication != null) {

            loginService.logout();

            // 3. SecurityContextHolder에서 인증 객체 제거
            SecurityContextHolder.clearContext();

        }

        return "redirect:/";  // 로그아웃 후 리다이렉트할 URL
    }

    private static void extractedSessionId(HttpServletResponse response, ResponseEntity<Member> responseEntity) {

        // 쿠기 정보 가져오기
        String cookieInfo  = responseEntity.getHeaders().getFirst(HttpHeaders.SET_COOKIE);

        log.info("login cookieInfo  = {}", cookieInfo );

        // 클라이언트에게 세션 쿠키 전달
        if (cookieInfo != null) {

//            // 세션 ID 추출
//            String sessionId = cookieInfo.split(";")[0].split("=")[1];
//            // 유효 시간 추출
//            int maxAge = Integer.parseInt(cookieInfo.split(";")[1].split("=")[1]);
//
//            log.info("Extracted sessionId: {}", sessionId);
//            log.info("Extracted maxAge: {}", maxAge);
//
//            // 쿠키 객체를 생성
//            Cookie cookie = new Cookie("JSESSIONID", sessionId);
//            cookie.setPath("/"); // 쿠키가 유효할 경로 설정
//            cookie.setHttpOnly(true); // 자바스크립트에서 접근할 수 없도록 설정
//            cookie.setMaxAge(maxAge); // 쿠키 유효 시간 설정
//
//            // 응답에 쿠키 추가
//            response.addCookie(cookie);
            response.addHeader(HttpHeaders.SET_COOKIE, cookieInfo);

        }

    }

//    @PostMapping("/logout")
//    public String logoutV3(HttpServletRequest request) {
//        HttpSession session = request.getSession(false);
//        if(session != null) {
//            session.invalidate();
//        }
//        return "redirect:/";
//    }

    private static void expireCookie(HttpServletResponse response) {
        Cookie cookie = new Cookie("JSESSIONID", null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }

}
