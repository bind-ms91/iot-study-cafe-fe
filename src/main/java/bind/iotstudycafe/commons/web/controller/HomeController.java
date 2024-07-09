package bind.iotstudycafe.commons.web.controller;

import bind.iotstudycafe.commons.login.domain.LoginDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
@Slf4j
public class HomeController {

    @GetMapping
    public String home(@ModelAttribute("loginDto") LoginDto loginDto) {
        return "/home";
    }

}
