package bind.iotstudycafe.member.controller;

import bind.iotstudycafe.member.dto.MemberSaveDto;
import bind.iotstudycafe.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/add")
    public String addFrom(@ModelAttribute("MemberSaveDto") MemberSaveDto MemberSaveDto) {
        return "members/addMemberFrom";
    }

    @PostMapping("/add")
    public String save(@Validated @ModelAttribute("MemberSaveDto") MemberSaveDto memberSaveDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "members/addMemberForm";
        }

        //TODO 회원 가입 예외 처리

        memberService.save(memberSaveDto);
        return "redirect:/";
    }
}
