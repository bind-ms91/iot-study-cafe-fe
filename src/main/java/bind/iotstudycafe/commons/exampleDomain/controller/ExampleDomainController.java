package bind.iotstudycafe.commons.exampleDomain.controller;

import bind.iotstudycafe.commons.exampleDomain.domain.ExampleDomain;
import bind.iotstudycafe.commons.exampleDomain.service.ExampleDomainService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/example")
public class ExampleDomainController {

    @Autowired
    private ExampleDomainService exampleDomainService;

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

}
