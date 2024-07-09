package bind.iotstudycafe.commons.exampleDomain.service;

import bind.iotstudycafe.commons.exampleDomain.domain.ExampleDomain;
import bind.iotstudycafe.commons.exampleDomain.dto.ExampleDomainDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExampleDomainServiceImpl implements ExampleDomainService{


    private static final String RequestMapping = "/example/";

    @Autowired

    private final WebClient iotCafeWebClient;

    @Autowired
    @Qualifier("server1WebClient")
    private final WebClient server1WebClient;


    @Override
    public Mono<ResponseEntity<ExampleDomain>> save(ExampleDomainDto exampleDomainDto) {

        ExampleDomain exampleDomain = new ExampleDomain();
        exampleDomain.setLoginId(exampleDomainDto.getLoginId());
        exampleDomain.setPassword(exampleDomainDto.getPassword());
        exampleDomain.setName(exampleDomainDto.getName());
        exampleDomain.setAge(exampleDomainDto.getAge());

        return iotCafeWebClient.post()
                .uri(RequestMapping)
                .bodyValue(exampleDomain)
                .retrieve()
                .toEntity(ExampleDomain.class);
    }

    @Override
    public Mono<ResponseEntity<ExampleDomain>> findByIdToEntity(Long id) {

        log.info("ExampleDomainService.findByIdToEntity findById id={}",id);

        return iotCafeWebClient.get()
                .uri(RequestMapping+"{id}", id)
                .retrieve()
                .toEntity(ExampleDomain.class);
    }

    @Override
    public Mono<ExampleDomain> findByIdBodyToMono(Long id) {

        log.info("ExampleDomainService.findByIdBodyToMono findById id={}",id);

        return iotCafeWebClient.get()
                .uri(RequestMapping+"{id}", id)
                .retrieve()
                .bodyToMono(ExampleDomain.class);
    }

    //    @Override
//    public List<ExampleDomain> findAll(ExampleDomain cond) {
//        return List.of();
//    }
//
//    @Override
//    public Mono<Void> update(Long id, ExampleDomainUpdateDto updateParam) {
//
//        WebClient webClient = webClientBuilder(iotCafeBeEndpoint);
//
//        return webClient.post()
//                .uri(RequestMapping+"{id}/edit", id)
//                .bodyValue()
//    }

//    private WebClient webClientBuilder(String iotCafeBeEndpoint) {
//
//        return webClientBuilder
//                .baseUrl(iotCafeBeEndpoint)
//                .build();
//    }

}
