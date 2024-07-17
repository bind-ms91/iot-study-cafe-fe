package bind.iotstudycafe.commons.exampleDomain.service;

import bind.iotstudycafe.commons.exampleDomain.domain.ExampleDomain;
import bind.iotstudycafe.commons.exampleDomain.dto.ExampleDomainSave;
import bind.iotstudycafe.commons.exampleDomain.dto.ExampleDomainSearchCond;
import bind.iotstudycafe.commons.exampleDomain.dto.ExampleDomainUpdate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExampleDomainServiceImpl implements ExampleDomainService{


    private static final String RequestMapping = "/example";

    @Autowired
    private final WebClient iotCafeWebClient;

    @Autowired
    @Qualifier("server1WebClient")
    private final WebClient server1WebClient;


    @Override
    public Mono<ResponseEntity<ExampleDomain>> saveToEntity(ExampleDomainSave exampleDomainSave) {

        return iotCafeWebClient.post()
                .uri(RequestMapping+"/save")
                .bodyValue(exampleDomainSave)
                .retrieve()
                .toEntity(ExampleDomain.class);
    }

    @Override
    public Mono<ExampleDomain> saveBodyToMono(ExampleDomainSave exampleDomainSave) {

        return iotCafeWebClient.post()
                .uri(RequestMapping+"/save")
                .bodyValue(exampleDomainSave)
                .retrieve()
                .bodyToMono(ExampleDomain.class);
    }

    @Override
    public Mono<ResponseEntity<ExampleDomain>> findByIdToEntity(Long id) {

        log.info("ExampleDomainService.findByIdToEntity findById id={}",id);

        return iotCafeWebClient.get()
                .uri(RequestMapping+"/{id}", id)
                .retrieve()
                .toEntity(ExampleDomain.class);
    }

    @Override
    public Mono<ExampleDomain> findByIdBodyToMono(Long id) {

        log.info("ExampleDomainService.findByIdBodyToMono findById id={}",id);

        return iotCafeWebClient.get()
                .uri(RequestMapping+"/{id}", id)
                .retrieve()
                .bodyToMono(ExampleDomain.class);
    }

    @Override
    public Mono<ResponseEntity<List<ExampleDomain>>> findExampleDomainsToEntityList(ExampleDomainSearchCond exampleDomainSearchCond) {
        return iotCafeWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(RequestMapping+"/list")
                        .queryParam("name", exampleDomainSearchCond.getName())
                        .queryParam("age", exampleDomainSearchCond.getAge())
                        .build())
                .retrieve()
                .toEntityList(ExampleDomain.class);
    }

    @Override
    public List<ExampleDomain> findExampleDomainsBodyToFlux(ExampleDomainSearchCond exampleDomainSearchCond) {

        Flux<ExampleDomain> exampleDomainFlux = iotCafeWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(RequestMapping + "/list")
                        .queryParam("name", exampleDomainSearchCond.getName())
                        .queryParam("age", exampleDomainSearchCond.getAge())
                        .build())
                .retrieve()
                .bodyToFlux(ExampleDomain.class);

        return exampleDomainFlux.collectList().block();
    }

    @Override
    public Mono<Void> update(Long id, ExampleDomainUpdate updateParam) {

        return iotCafeWebClient.put()
                .uri(RequestMapping+"/update/{id}",id)
                .bodyValue(updateParam)
                .retrieve()
                .bodyToMono(Void.class);
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
