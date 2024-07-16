package bind.iotstudycafe.commons.exampleDomain.controller;

import bind.iotstudycafe.commons.exampleDomain.domain.ExampleDomain;
import bind.iotstudycafe.commons.exampleDomain.dto.ExampleDomainSave;
import bind.iotstudycafe.commons.exampleDomain.dto.ExampleDomainSearchCond;
import bind.iotstudycafe.commons.exampleDomain.dto.ExampleDomainUpdate;
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
    public Mono<ResponseEntity<ExampleDomain>> saveToEntity(@Validated @ModelAttribute("exampleDomainSave") ExampleDomainSave exampleDomainSave, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        log.info("ExampleDomainController.save post exampleDomain: {}", exampleDomainSave);

        /**
         * TODO
         * 검증로직 필요
         */

        log.info("ExampleDomainController.save post exampleDomainSave: {}", exampleDomainSave);


        return exampleDomainService.saveToEntity(exampleDomainSave);
    }

    @PostMapping("/body/save")
    @ResponseBody
    public Mono<ExampleDomain> saveBodyToMono(@Validated @ModelAttribute("exampleDomainSave") ExampleDomainSave exampleDomainSave, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        log.info("ExampleDomainController.save post exampleDomainSave: {}", exampleDomainSave);

        /**
         * TODO
         * 검증로직 필요
         */

        log.info("ExampleDomainController.save post exampleDomainSave: {}", exampleDomainSave);

        return exampleDomainService.saveBodyToMono(exampleDomainSave);
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
    public Mono<ResponseEntity<List<ExampleDomain>>> findExampleDomainsToEntityList(@Validated @ModelAttribute("exampleDomainSearchCondDto") ExampleDomainSearchCond exampleDomainSearchCond, Model model) {

        log.info("ExampleDomainController.findExampleDomainsToEntityList exampleDomainSearchCondDto: {}", exampleDomainSearchCond);

        return exampleDomainService.findExampleDomainsToEntityList(exampleDomainSearchCond);
    }

    @GetMapping("/body/list")
    @ResponseBody
    public List<ExampleDomain> findExampleDomainsBodyToFlux(@Validated @ModelAttribute("exampleDomainSearchCondDto") ExampleDomainSearchCond exampleDomainSearchCond, Model model) {


        log.info("ExampleDomainController.findExampleDomainsBodyToFlux exampleDomainSearchCondDto: {}", exampleDomainSearchCond);

        return exampleDomainService.findExampleDomainsBodyToFlux(exampleDomainSearchCond);
    }

    @PutMapping("/update/{id}")
    @ResponseBody
    public Mono<Void> update(@PathVariable Long id,
                             @Validated @RequestBody ExampleDomainUpdate updateParam) {

        return exampleDomainService.update(id, updateParam);
    }


}
