package bind.iotstudycafe.commons.exampleDomain.controller;

import bind.iotstudycafe.commons.exampleDomain.domain.ExampleDomain;
import bind.iotstudycafe.commons.exampleDomain.dto.ExampleDomainDto;
import bind.iotstudycafe.commons.exampleDomain.service.ExampleDomainService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/example")
public class ExampleDomainController {

    @Autowired
    private ExampleDomainService exampleDomainService;


    @PostMapping("/entity/save")
    public Mono<ResponseEntity<ExampleDomain>> saveToEntity(@Validated @ModelAttribute("exampleDomainDto") ExampleDomainDto exampleDomainDto, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        log.info("ExampleDomainController.save post exampleDomain: {}", exampleDomainDto);

        /**
         * TODO
         * 검증로직 필요
         */

        ExampleDomain exampleDomain = new ExampleDomain();
        exampleDomain.setLoginId(exampleDomainDto.getLoginId());
        exampleDomain.setPassword(exampleDomainDto.getPassword());
        exampleDomain.setName(exampleDomainDto.getName());
        exampleDomain.setAge(exampleDomainDto.getAge());

        log.info("ExampleDomainController.save post exampleDomain: {}", exampleDomain);


        return exampleDomainService.saveToEntity(exampleDomain);
    }

    @PostMapping("/body/save")
    @ResponseBody
    public Mono<ExampleDomain> saveBodyToMono(@Validated @ModelAttribute("exampleDomainDto") ExampleDomainDto exampleDomainDto, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        log.info("ExampleDomainController.save post exampleDomain: {}", exampleDomainDto);

        /**
         * TODO
         * 검증로직 필요
         */

        ExampleDomain exampleDomain = new ExampleDomain();
        exampleDomain.setLoginId(exampleDomainDto.getLoginId());
        exampleDomain.setPassword(exampleDomainDto.getPassword());
        exampleDomain.setName(exampleDomainDto.getName());
        exampleDomain.setAge(exampleDomainDto.getAge());

        log.info("ExampleDomainController.save post exampleDomain: {}", exampleDomain);

        return exampleDomainService.saveBodyToMono(exampleDomain);
    }


    @GetMapping("/entity/{id}")
    public Mono<ResponseEntity<ExampleDomain>> findByIdToEntity(@PathVariable Long id) {

        log.info("ExampleDomainController.findByIdToEntity get id: {}", id);

        return exampleDomainService.findByIdToEntity(id);
    }

    @GetMapping("/body/{id}")
    @ResponseBody
    public Mono<ExampleDomain> findByIdBodyToMono(@PathVariable Long id) {

        log.info("ExampleDomainController.findByIdBodyToMono get id: {}", id);

        return exampleDomainService.findByIdBodyToMono(id);
    }

    @GetMapping("/entity/list")
    public Mono<ResponseEntity<List<ExampleDomain>>> findExampleDomainsToEntityList(@Validated @ModelAttribute("exampleDomainDto") ExampleDomainDto exampleDomainDto, Model model) {

        ExampleDomain exampleDomain = new ExampleDomain();
        exampleDomain.setName(exampleDomainDto.getName());
        exampleDomain.setAge(exampleDomainDto.getAge());

        log.info("findExampleDomains post exampleDomain: {}", exampleDomain);

        return exampleDomainService.findExampleDomainsToEntityList(exampleDomain);
    }

    @GetMapping("/body/list")
    @ResponseBody
    public List<ExampleDomain> findExampleDomainsBodyToFlux(@Validated @ModelAttribute("exampleDomainDto") ExampleDomainDto exampleDomainDto, Model model) {

        ExampleDomain exampleDomain = new ExampleDomain();
        exampleDomain.setName(exampleDomainDto.getName());
        exampleDomain.setAge(exampleDomainDto.getAge());

        log.info("findExampleDomains post exampleDomain: {}", exampleDomain);

        return exampleDomainService.findExampleDomainsBodyToFlux(exampleDomain);
    }

}
