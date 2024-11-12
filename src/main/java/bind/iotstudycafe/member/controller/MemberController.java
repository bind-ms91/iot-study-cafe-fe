package bind.iotstudycafe.member.controller;

import bind.iotstudycafe.member.domain.Member;
import bind.iotstudycafe.member.dto.MemberSaveDto;
import bind.iotstudycafe.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/members")
@Slf4j
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

//    // ID 중복 체크
//    @Operation(summary = "ID 중복 체크", description = "ID 중복 체크",
//            responses = {@ApiResponse(responseCode = "200", description = "회원가입 성공", content = @Content(schema = @Schema(implementation = Member.class)))}
//    )
//    @GetMapping()
//    public ResponseEntity<Map<String, Boolean>> checkDuplicateId(@RequestParam("memberId") String memberId) {
//        boolean exists = memberService.isMemberIdExists(memberId);
//        Map<String, Boolean> response = new HashMap<>();
//        response.put("exists", exists);
//        return ResponseEntity.ok(response);
//    }

    // ID 중복 체크
    @Operation(summary = "ID 중복 체크", description = "ID 중복 체크",
            responses = {@ApiResponse(responseCode = "200", description = "회원가입 성공", content = @Content(schema = @Schema(implementation = Member.class)))}
    )
    @PostMapping("/check-duplicate")
    @ResponseBody
    public boolean checkDuplicateId(@RequestBody Map<String, String> requestData) {

        String memberId = requestData.get("memberId");


        if (memberId == null || memberId.isEmpty()) {
            log.info("memberId is null or empty");
            return false;
        }

        return memberService.isMemberIdExists(memberId);
    }

    // 회원 가입 처리
    @Operation(summary = "회원가입", description = "회원가입 처리",
            responses = {@ApiResponse(responseCode = "200", description = "회원가입 성공", content = @Content(schema = @Schema(implementation = Member.class)))}
    )
    @PostMapping("/save")
    public String saveMember(@Validated @ModelAttribute("memberSaveDto") MemberSaveDto memberSaveDto,
                             BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {

        // 폼 검증에 실패한 경우
        if (bindingResult.hasErrors()) {
            log.info(bindingResult.getAllErrors().toString());
            return "members/signUpMemberForm";
        }

        // ID 중복 체크
        if (memberService.isMemberIdExists(memberSaveDto.getMemberId())) {
            model.addAttribute("duplicateId", true);
            return "members/signUpMemberForm";
        }

        // 비밀번호 일치 체크
        if (!memberSaveDto.getMemberPassword().equals(memberSaveDto.getMemberPasswordCheck())) {
            bindingResult.rejectValue("memberPasswordCheck", "passwordInCorrect",
                    "2개의 패스워드가 일치하지 않습니다.");
            return "members/signUpMemberForm";
        }

        // 회원 가입 처리
//        Member member = memberService.saveMember(memberSaveDto);
        Member member = null;
        redirectAttributes.addFlashAttribute("signUpSuccess", true);  // 성공 메시지 전달
        return "redirect:/";
        // 가입 성공 시 redirect 후에 성공 메시지를 전달
//        if (member != null) {
//            redirectAttributes.addFlashAttribute("signupSuccess", true);  // 성공 메시지 전달
//            return "redirect:/";
//        } else {
//            // 회원 가입 실패 시 처리
//            model.addAttribute("signupSuccess", false);
//            return "members/signUpMemberForm";

            //PRG(Post-Redirect-Get) 패턴
//            redirectAttributes.addFlashAttribute("signupSuccess", false);  // 성공 메시지 전달
//            redirectAttributes.addFlashAttribute("memberSaveDto", memberSaveDto);  // 성공 메시지 전달
//            return "redirect:" + "/members/save";
//        }
    }


    @Operation(summary = "저장", description = "저장",
            responses = {@ApiResponse(responseCode = "200", description = "저장 성공", content = @Content(schema = @Schema(implementation = Member.class)))}
    )
    @PostMapping("/saveV1")
    public String saveBodyToMono(@Validated @ParameterObject @ModelAttribute("memberSaveDto") MemberSaveDto memberSaveDto, BindingResult bindingResult,
                                       @RequestParam(defaultValue = "/") String redirectURL) {
        log.info("MemberController.save post memberSaveDto: {}", memberSaveDto);

        /**
         * TODO
         * 검증로직 필요
         */

        if (bindingResult.hasErrors()) {
            log.info("bindingResult.getAllErrors() = {}", bindingResult.getAllErrors());
            return "members/signUpMemberForm";
        }

        log.info("ExampleDomainController.save post memberSaveDto: {}", memberSaveDto);

        Mono<Member> member = memberService.saveBodyToMono(memberSaveDto);

        return member.flatMap( body -> {

            if (body != null) {
                return Mono.just("redirect:" + redirectURL);
            } else {
                return Mono.just("members/signUpMemberForm");
            }
        }).block();
    }

    @GetMapping("/save")
    public String showSignUpForm(@ModelAttribute("memberSaveDto") MemberSaveDto memberSaveDto) {
        return "members/signUpMemberForm";
    }

}
