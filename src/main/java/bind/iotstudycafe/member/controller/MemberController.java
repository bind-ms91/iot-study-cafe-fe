package bind.iotstudycafe.member.controller;

import bind.iotstudycafe.exampleDomain.domain.ExampleDomain;
import bind.iotstudycafe.exampleDomain.dto.ExampleDomainSave;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import reactor.core.publisher.Mono;

@Controller
@RequestMapping("/members")
@Slf4j
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @Operation(summary = "저장", description = "저장",
            responses = {@ApiResponse(responseCode = "200", description = "저장 성공", content = @Content(schema = @Schema(implementation = Member.class)))}
    )
    @PostMapping("/save")
    @ResponseBody
    public Mono<Member> saveBodyToMono(@Validated @ParameterObject @ModelAttribute("memberSaveDto") MemberSaveDto memberSaveDto, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        log.info("ExampleDomainController.save post memberSaveDto: {}", memberSaveDto);

        /**
         * TODO
         * 검증로직 필요
         */

        log.info("ExampleDomainController.save post memberSaveDto: {}", memberSaveDto);

        return memberService.saveBodyToMono(memberSaveDto);
    }


}
