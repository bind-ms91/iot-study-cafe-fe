package bind.iotstudycafe.exampleDomain.service;

import bind.iotstudycafe.exampleDomain.domain.ExampleDomain;
import bind.iotstudycafe.exampleDomain.dto.ExampleDomainSave;
import bind.iotstudycafe.exampleDomain.dto.ExampleDomainSearchCond;
import bind.iotstudycafe.exampleDomain.dto.ExampleDomainUpdate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

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
                .toEntity(ExampleDomain.class)
                //toEntity 200 응답에 body 값이 null경우 반복 호출 문제 해결 예외처리
                .flatMap(response -> {
                    if (response.getStatusCode().is2xxSuccessful() && response.getBody() == null) {
                        // 응답 본문이 null인 경우 예외를 발생시켜 추가 호출 방지
                        return Mono.error(new RuntimeException("Entity not found"));
                    }
                    return Mono.just(response);
                })
                .onErrorResume(e -> {
                    if (e instanceof RuntimeException && "Entity not found".equals(e.getMessage())) {
                        // 404 상태 코드로 응답
                        return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
                    }
                    log.error("Error during findByIdToEntity", e);
                    return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
                });
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

    @Override
    public Mono<Void> delete(Long id) {

        return iotCafeWebClient.delete()
                .uri(RequestMapping+"/delete/{id}",id)
                .retrieve()
                .bodyToMono(Void.class);
    }

}
